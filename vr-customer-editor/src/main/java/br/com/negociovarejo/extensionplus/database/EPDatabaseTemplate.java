/* * Copyright (c) 2020 Negocio Varejo. All rights reserved. * */
package br.com.negociovarejo.extensionplus.database;

import br.com.negociovarejo.extensionplus.utilities.MessageUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author derickfelix
 */
public class EPDatabaseTemplate {

    public void update(String sql, Map<String, Object> params)
    {
        try (Connection c = getConnection()) {
            // Create statement
            if (params != null) {
                sql = createSql(params, sql);
            }

            try (Statement stmt = c.createStatement()) {
                stmt.execute(sql);
            }

        } catch (NullPointerException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T queryForObject(String sql, Map<String, Object> params, RowMapper<T> rowMapper)
    {
        List<T> query = query(sql, params, rowMapper);

        if (query == null || query.isEmpty()) {
            return null;
        }

        return query.get(0);
    }

    public <T> List<T> queryForList(String sql, Map<String, Object> params, RowMapper<T> rowMapper)
    {
        return query(sql, params, rowMapper);
    }

    private <T> List<T> query(String sql, Map<String, Object> params, RowMapper<T> rowMapper)
    {
        try (Connection c = getConnection()) {
            // Create statement
            if (params != null) {
                sql = createSql(params, sql);
            }

            try (Statement stmt = c.createStatement()) {
                List<T> list = new ArrayList();
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        list.add(rowMapper.mapRow(rs));
                    }
                }
                return list;
            }
        } catch (NullPointerException | ClassNotFoundException | SQLException e) {
            MessageUtil.showException(null, e);
        }

        return null;
    }

    private String createSql(Map<String, Object> params, String sql)
    {
        StringBuilder builder = new StringBuilder();
        StringBuilder keyBuilder = new StringBuilder();

        char[] chars = sql.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == ':') {
                i++;
                while (i < chars.length && (chars[i] != ',' && chars[i] != ' ' && chars[i] != ')')) {
                    keyBuilder.append(chars[i]);
                    i++;
                }
                builder.append("'").append(params.get(keyBuilder.toString())).append("'");
                if (i < chars.length) {
                    keyBuilder = new StringBuilder();
                } else {
                    break;
                }
            }
            builder.append(chars[i]);
        }

        return builder.toString();
    }

    private static Properties getProperties()
    {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("/vr/vr.properties"));
            return properties;
        } catch (IOException e) {
            MessageUtil.showException(null, e);
        }
        return null;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException
    {
        Properties properties = getProperties();

        if (properties != null) {
            String host = properties.getProperty("database.ip");
            String port = properties.getProperty("database.porta");
            String database = properties.getProperty("database.nome");
            String user = "postgres";
            String password = "postgres";

            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database, user, password);
        }

        return null;
    }

}
