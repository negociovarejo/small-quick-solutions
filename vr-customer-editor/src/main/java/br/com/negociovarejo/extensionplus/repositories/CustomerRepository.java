/* * Copyright (c) 2020 Negocio Varejo. All rights reserved. * */
package br.com.negociovarejo.extensionplus.repositories;

import br.com.negociovarejo.extensionplus.database.EPDatabaseTemplate;
import br.com.negociovarejo.extensionplus.database.RowMapper;
import br.com.negociovarejo.extensionplus.database.SQLQueryBuilder;
import br.com.negociovarejo.extensionplus.models.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author derickfelix
 */
public class CustomerRepository {
    private final EPDatabaseTemplate template;
    
    public CustomerRepository()
    {
        this.template = new EPDatabaseTemplate();
    }
    
    public Customer find(Long id)
    {
        String sql = SQLQueryBuilder.select("id", "nome as name", "cnpj as taxPayerRegistration")
                .from("clientepreferencial", "cp")
                .with("cp.id = :id")
                .build();
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        
        return template.queryForObject(sql, params, new CustomerMapper());
    }
    
    public void save(Customer customer)
    {
        String sql = "update clientepreferencial set cnpj = :cnpj where id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", customer.getId());
        params.put("cnpj", customer.getTaxPayerRegistration().replaceAll("[^0-9]", ""));
        
        template.update(sql, params);
    }
    
    private class CustomerMapper implements RowMapper<Customer> {

        @Override
        public Customer mapRow(ResultSet rs) throws SQLException
        {
            Customer customer = new Customer();
            customer.setId(rs.getLong("id"));
            customer.setName(rs.getString("name"));
            customer.setTaxPayerRegistration(rs.getString("taxPayerRegistration"));
            
            return customer;
        }
        
    }
}
