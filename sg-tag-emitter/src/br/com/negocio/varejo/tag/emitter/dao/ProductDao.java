/* * Copyright (c) 2019 Negocio Varejo. All rights reserved. * */
package br.com.negocio.varejo.tag.emitter.dao;

import br.com.negocio.varejo.tag.emitter.models.Product;
import br.com.negocio.varejo.tag.emitter.database.FBDatabaseTemplate;
import br.com.negocio.varejo.tag.emitter.database.RowMapper;
import br.com.negocio.varejo.tag.emitter.utilities.NumberUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author derickfelix
 */
public class ProductDao {
    
    private final FBDatabaseTemplate template;
    
    public ProductDao()
    {
        this.template = new FBDatabaseTemplate();
    }
    
    public List<Product> findAllByTerm(String term)
    {
        String sql = "select codbarras, produto, precovenda from testoque where produto like :term or codbarras like :term";
        Map<String, Object> params = new HashMap();
        params.put("term", "%" + term + "%");

        return template.queryForList(sql, params, new ProductDaoRowMapper());
    }

    private class ProductDaoRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs) throws SQLException {
            Product product = new Product();
            product.setEancode(NumberUtil.fixedDigits(13, rs.getString("codbarras")));
            product.setDescription(rs.getString("produto"));
            product.setPrice1(rs.getDouble("precovenda"));

            return product;
        }
        
    }

}
