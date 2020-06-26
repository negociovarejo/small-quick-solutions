/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.wholesale.repositories;

import br.com.vrfortaleza.upextension.database.RowMapper;
import br.com.vrfortaleza.upextension.database.VRDatabaseTemplate;
import br.com.vrfortaleza.upextension.repositories.Repository;
import br.com.vrfortaleza.upextension.wholesale.models.Seller;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author derickfelix
 */
public class SellerRepository implements Repository<Seller> {

    private final VRDatabaseTemplate template;

    public SellerRepository()
    {
        this.template = new VRDatabaseTemplate();
    }

    @Override
    public List<Seller> all()
    {
        String sql = "select * from atacado.vendedor order by id";
        
        return template.queryForList(sql, null, new SellerRowMapper());
    }

    @Override
    public Seller find(long id)
    {
        String sql = "select * from atacado.vendedor where id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        
        return template.queryForObject(sql, params, new SellerRowMapper());
    }

    @Override
    public void create(Seller model)
    {
        String sql = "insert into atacado.vendedor values (:id, :name, :status, :percentual, :max_discount, :allow_change_price)";
        
        Map<String, Object> params = new HashMap<>();
        params.put("id", model.getId());
        params.put("name", model.getName());
        params.put("status", 1);
        params.put("percentual", model.getPercentual());
        params.put("max_discount", model.getMaxDiscount());
        params.put("allow_change_price", model.isAllowChangePrice());
        
        template.update(sql, params);
    }

    @Override
    public void update(Seller model)
    {
        String sql = "update atacado.vendedor set nome = :name, id_situacaocadastro = :status, percentual = :percentual, "
                + "descontomaximo = :max_discount, permitealterarpreco = :allow_change_price where id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", model.getId());
        params.put("name", model.getName());
        params.put("status", 1);
        params.put("percentual", model.getPercentual());
        params.put("max_discount", model.getMaxDiscount());
        params.put("allow_change_price", model.isAllowChangePrice());

        template.update(sql, params);
    }

    @Override
    public void delete(long id)
    {
        String sql = "delete from atacado.vendedor where id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        
        template.update(sql, params);
    }
    
    private class SellerRowMapper implements RowMapper<Seller> {

        @Override
        public Seller mapRow(ResultSet rs) throws SQLException
        {
            Seller seller = new Seller();
            seller.setId(rs.getLong("id"));
            seller.setName(rs.getString("nome"));
            seller.setPercentual(rs.getDouble("percentual"));
            seller.setMaxDiscount(rs.getDouble("descontomaximo"));
            seller.setAllowChangePrice(rs.getBoolean("permitealterarpreco"));
            
            return seller;
        }

    }
}
