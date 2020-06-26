/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.wholesale.repositories;

import br.com.vrfortaleza.upextension.database.RowMapper;
import br.com.vrfortaleza.upextension.database.VRDatabaseTemplate;
import br.com.vrfortaleza.upextension.repositories.Repository;
import br.com.vrfortaleza.upextension.wholesale.models.Product;
import br.com.vrfortaleza.upextension.utilities.NumberUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * A repository of {@link Product product} type.
 *
 * @author derickfelix
 */
public class ProductRepository implements Repository<Product> {

    private final VRDatabaseTemplate template;

    private final String sql = "select "
            + "x.id as \"Codigo\", "
            + "x.descricaocompleta as \"Descricao\", "
            + "pa2.codigobarras as \"Codigo de Barras\", "
            + "to_char (pc.estoque,'9G999G990D999') as \"Estoque\", "
            + "te.descricao as \"Embalagem\", "
            + "pa2.qtdembalagem as \"Qtd Emb\", "
            + "to_char (pc.precovenda,'9G999G990D999') as \"Preco\", "
            + "to_char (ds.desconto,'9G999G990D99') as \"Desconto\", "
            + "to_char ((pc.precovenda-(pc.precovenda*ds.desconto)/100),'9G999G990D999') as \"Preco Atacado\" "
            + "from ( "
            + "        select "
            + "        z.id, z.descricaocompleta,count(z.codigobarras) "
            + "              from ( "
            + "                     select p.id, p.descricaocompleta,pa.codigobarras "
            + "                     from produto p "
            + "                     join produtoautomacao pa on (p.id = pa.id_produto)) z "
            + "group by z.id, z.descricaocompleta "
            + "having count(z.codigobarras) > 1  "
            + ") x "
            + "join produtoautomacao pa2 on (x.id = pa2.id_produto) "
            + "join tipoembalagem te on te.id = pa2.id_tipoembalagem "
            + "join produtocomplemento pc on (x.id = pc.id_produto) and pc.id_loja = 1 "
            + "join produtoautomacaodesconto as  ds on ds.codigobarras = pa2.codigobarras  and ds.id_loja = 1 "
            + "where pc.precovenda > 0 and pc.id_loja = 1 "
            + "order by x.descricaocompleta";

    public ProductRepository()
    {
        this.template = new VRDatabaseTemplate();
    }

    @Override
    public List<Product> all()
    {
        return this.template.queryForList(sql, null, new ProductRowMapper());
    }

    @Override
    public Product find(long id)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void create(Product model)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Product model)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(long id)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private class ProductRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs) throws SQLException
        {
            Product wp = new Product();
            wp.setId(rs.getLong("Codigo"));
            wp.setDescription(rs.getString("Descricao"));
            wp.setBarcode(NumberUtil.fixedDigits(14, rs.getString("Codigo de barras")));
            wp.setWrapper(rs.getString("Embalagem"));
            wp.setQtyWrapper(rs.getInt("Qtd Emb"));
            wp.setStock(rs.getString("Estoque").replace(" ", ""));
            wp.setDiscount(rs.getString("Desconto").replace(" ", ""));
            wp.setRetailPrice(rs.getString("Preco").replace(" ", ""));
            wp.setWholeSalePrice(rs.getString("Preco Atacado").replace(" ", ""));

            return wp;
        }

    }

}
