/* * Copyright (c) 2020 Negocio Varejo. All rights reserved. * */
package br.com.negociovarejo.extensionplus.repositories;

import br.com.negociovarejo.extensionplus.database.EPDatabaseTemplate;
import br.com.negociovarejo.extensionplus.database.RowMapper;
import br.com.negociovarejo.extensionplus.database.SQLQueryBuilder;
import br.com.negociovarejo.extensionplus.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author derickfelix
 */
public class UserRepository {
    
    private final EPDatabaseTemplate template;
    
    public UserRepository()
    {
        this.template = new EPDatabaseTemplate();
    }
    
    public User authenticate(String username, String password)
    {
        String sql = SQLQueryBuilder.select("id", "login as username", " senha as password")
                .from("usuario", "u")
                .with("login = :username and senha = md5(:password)")
                .build();
        
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        
        return template.queryForObject(sql, params, new UserMapper());
    }
    
    private class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs) throws SQLException
        {
            User user = new User();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            
            return user;
        }
        
    }
}
