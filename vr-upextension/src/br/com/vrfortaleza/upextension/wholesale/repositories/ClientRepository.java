/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.wholesale.repositories;

import br.com.vrfortaleza.upextension.database.RowMapper;
import br.com.vrfortaleza.upextension.database.SQLQueryBuilder;
import br.com.vrfortaleza.upextension.database.VRDatabaseTemplate;
import br.com.vrfortaleza.upextension.repositories.Repository;
import br.com.vrfortaleza.upextension.wholesale.models.Client;
import br.com.vrfortaleza.upextension.wholesale.models.Seller;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A repository of {@link Client client} type.
 *
 * @author derickfelix
 */
public class ClientRepository implements Repository<Client> {

    private final VRDatabaseTemplate template;

    public ClientRepository()
    {
        this.template = new VRDatabaseTemplate();
    }

    public List<Client> allBySeller(Seller seller)
    {
        String sql = SQLQueryBuilder.select().from("atacado.vendedorcliente", "avc")
                .innerJoin("public.clienteeventual", "pec", "avc.id_clienteeventual = pec.id", "id", "nome")
                .with("id_vendedor = :seller_id")
                .build();
        Map<String, Object> params = new HashMap<>();
        params.put("seller_id", seller.getId());

        return template.queryForList(sql, params, new ClientRowMapper());
    }

    @Override
    public List<Client> all()
    {
        String sql = SQLQueryBuilder.select().from("public.clienteeventual", "pce").build();

        return template.queryForList(sql, null, new ClientRowMapper());
    }

    @Override
    public Client find(long id)
    {
        String sql = SQLQueryBuilder.select().from("public.clienteeventual", "pce").with("id = :id").build();
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return template.queryForObject(sql, params, new ClientRowMapper());
    }

    @Override
    public void create(Client model)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Client model)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(long id)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private class ClientRowMapper implements RowMapper<Client> {

        @Override
        public Client mapRow(ResultSet rs) throws SQLException
        {
            Client client = new Client();
            client.setId(rs.getLong("id"));
            client.setName(rs.getString("nome"));

            return client;
        }

    }
}
