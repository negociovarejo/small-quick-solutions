/* * Copyright (c) 2021 Negocio Varejo. All rights reserved. * */
package br.com.negocio.varejo.tag.emitter.dao;

import br.com.negocio.varejo.tag.emitter.models.Product;
import br.com.negocio.varejo.tag.emitter.database.VRDatabaseTemplate;
import br.com.negocio.varejo.tag.emitter.database.RowMapper;
import br.com.negocio.varejo.tag.emitter.models.Eancode;
import br.com.negocio.varejo.tag.emitter.utilities.NumberUtil;
import br.com.negocio.varejo.tag.emitter.utilities.VRProperties;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author derickfelix
 */
public class ProductDao {
    
    private final VRDatabaseTemplate template;
    private final long storeId;
    
    public ProductDao()
    {
        this.template = new VRDatabaseTemplate();
        Properties properties = VRProperties.getProperties();
        
        if (properties != null) {
            storeId = Long.parseLong(properties.getProperty("system.numeroloja"));
        } else {
            storeId = 0;
        }
    }
    
    public List<Product> findAllByTerm(String term)
    { 
        String sql = "select"
                + " p.id,"
                + " p.descricaocompleta,"
                + " pc.precovenda,"
                + " string_agg(pa.codigobarras || ',' || (case when pad.desconto is null then 0 else pad.desconto end), '|') as automacao,"
                + " ("
                + "     select"
                + "         (case when sp.id_tipopercentualvalor = 0 then ((spd.desconto / 100) * pc.precovenda) else spd.desconto end)"
                + "     from"
                + "         promocaodesconto spd"
                + "         inner join promocao sp on sp.id = spd.id_promocao"
                + "     where"
                + "         spd.id_produto = p.id"
                + "         and sp.id_loja = :store_id"
                + "         and sp.datatermino >= (select dp.data from dataprocessamento dp where dp.id_loja = :store_id)"
                + " ) as promocao "
                + "from"
                + " produto p"
                + " inner join produtocomplemento pc on pc.id_produto = p.id"
                + " inner join produtoautomacao pa on pa.id_produto = p.id"
                + " left join produtoautomacaodesconto pad on pad.codigobarras = pa.codigobarras and pad.id_loja = :store_id "
                + "where"
                + " pc.id_loja = :store_id "
                + " and (";
        
        if (term.matches("[0-9]+")) {
            sql += "pa.codigobarras = :eancode ";
            if (term.length() < 13) {
                sql += "or p.id = :id";
            }
        } else {
            sql += "p.descricaocompleta ilike :term";
        }
        
        sql += ") group by p.id, p.descricaocompleta, pc.precovenda";
        
        Map<String, Object> params = new HashMap();
        params.put("term", "%" + term + "%");
        params.put("eancode", term);
        params.put("id", term);
        params.put("store_id", storeId);

        return template.queryForList(sql, params, new ProductDaoRowMapper());
    }
    
    public Product findByIdOrEancode(String code)
    {
        String sql = "select"
                + " p.id,"
                + " p.descricaocompleta,"
                + " pc.precovenda,"
                + " string_agg(pa.codigobarras || ',' || (case when pad.desconto is null then 0 else pad.desconto end), '|') as automacao,"
                + " ("
                + "     select"
                + "         (case when sp.id_tipopercentualvalor = 0 then ((spd.desconto / 100) * pc.precovenda) else spd.desconto end)"
                + "     from"
                + "         promocaodesconto spd"
                + "         inner join promocao sp on sp.id = spd.id_promocao"
                + "     where"
                + "         spd.id_produto = p.id"
                + "         and sp.id_loja = :store_id"
                + "         and sp.datatermino >= (select dp.data from dataprocessamento dp where dp.id_loja = :store_id)"
                + " ) as promocao "
                + "from"
                + " produto p"
                + " inner join produtocomplemento pc on pc.id_produto = p.id "
                + " inner join produtoautomacao pa on pa.id_produto = p.id "
                + " left join produtoautomacaodesconto pad on pad.codigobarras = pa.codigobarras and pad.id_loja = :store_id "
                + "where"
                + " pc.id_loja = :store_id "
                + " and (pa.codigobarras = :eancode";
        if (code.length() < 13) {
            sql += " or p.id = :id";
        }
        
        sql += ") group by"
                + " p.id, p.descricaocompleta, pc.precovenda";

        Map<String, Object> params = new HashMap();
        params.put("id", code);
        params.put("eancode", code);
        params.put("store_id", storeId);

        return template.queryForObject(sql, params, new ProductDaoRowMapper());
    }

    private class ProductDaoRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs) throws SQLException {
            Product product = new Product();
            String[] tokens = rs.getString("automacao").split("\\|");
            List<Eancode> codes = new ArrayList<>(tokens.length);
            
            for (int i = 0; i < tokens.length; ++i) {
                Eancode eancode = new Eancode();
                String[] subTokens = tokens[i].split(",");
                eancode.setCode(NumberUtil.fixedDigits(13, subTokens[0]));
                eancode.setDiscount(Double.parseDouble(subTokens[1]));
                
                codes.add(eancode);
            }

            product.setId(rs.getLong("id"));
            product.setEancodes(codes);
            
            if (tokens.length > 0) {
                product.setSelected(codes.get(0));
            }
            
            product.setDescription(rs.getString("descricaocompleta"));
            product.setPrice1(rs.getDouble("precovenda"));
            product.setPrice2(product.getPrice1() - rs.getDouble("promocao"));

            return product;
        }
        
    }

}
