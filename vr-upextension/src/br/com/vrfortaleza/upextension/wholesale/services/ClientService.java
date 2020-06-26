/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.wholesale.services;

import br.com.vrfortaleza.upextension.services.Service;
import br.com.vrfortaleza.upextension.wholesale.models.Client;
import br.com.vrfortaleza.upextension.wholesale.models.Seller;
import br.com.vrfortaleza.upextension.wholesale.repositories.ClientRepository;
import java.util.List;

/**
 * A service of {@link Client client} type.
 * 
 * @author derickfelix
 */
public class ClientService implements Service<Client> {

    private final ClientRepository repository;
    
    public ClientService()
    {
        this.repository = new ClientRepository();
    }
    
    @Override
    public List<Client> all()
    {
        return this.repository.all();
    }
    
    public List<Client> allBySellers(Seller seller)
    {
        return this.repository.allBySeller(seller);
    }

    @Override
    public Client find(long id)
    {
        return this.repository.find(id);
    }

    @Override
    public boolean exists(Client model)
    {
        return this.repository.all().contains(model);
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
    
}
