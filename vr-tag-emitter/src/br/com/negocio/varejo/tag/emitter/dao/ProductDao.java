/* * Copyright (c) 2021 Negocio Varejo. All rights reserved. * */
package br.com.negocio.varejo.tag.emitter.dao;

import br.com.negocio.varejo.tag.emitter.models.Product;
import br.com.negocio.varejo.tag.emitter.database.VRDatabaseTemplate;
import br.com.negocio.varejo.tag.emitter.database.RowMapper;
import br.com.negocio.varejo.tag.emitter.models.Eancode;
import br.com.negocio.varejo.tag.emitter.utilities.NumberUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author derickfelix
 */
public class ProductDao {
    
    private final VRDatabaseTemplate template;
    
    public ProductDao()
    {
        this.template = new VRDatabaseTemplate();
    }
    
    public List<Product> findAllByTerm(String term)
    {
        long storeId = 1;
        
        String sql = "select"
                + " p.id,"
                + " p.descricaocompleta,"
                + " pc.precovenda,"
                + " string_agg(pa.codigobarras || ',' || (case when pad.desconto is null then 0 else pad.desconto end), '|') as automacao,"
                + " (select desconto from promocaodesconto spd inner join promocao sp on sp.id = spd.id_promocao where spd.id_produto = p.id and sp.datatermino >= current_date) as promocao "
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
        long storeId = 1;
        
        String sql = "select"
                + " p.id,"
                + " p.descricaocompleta,"
                + " pc.precovenda,"
                + " string_agg(pa.codigobarras || ',' || (case when pad.desconto is null then 0 else pad.desconto end), '|') as automacao,"
                + " (select desconto from promocaodesconto spd inner join promocao sp on sp.id = spd.id_promocao where spd.id_produto = p.id and sp.datatermino >= current_date) as promocao "
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
            double percentage = (100 - rs.getDouble("promocao")) / 100;
            product.setPrice2(product.getPrice1() * percentage);

            return product;
        }
        
    }

}
