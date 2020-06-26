/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.wholesale.services;

import br.com.vrfortaleza.upextension.services.Service;
import br.com.vrfortaleza.upextension.wholesale.models.Seller;
import br.com.vrfortaleza.upextension.wholesale.repositories.SellerRepository;
import java.util.List;

/**
 * A service of {@link Seller seller} type.
 * 
 * @author derickfelix
 */
public class SellerService implements Service<Seller> {

    private final SellerRepository repository;
    
    public SellerService()
    {
        this.repository = new SellerRepository();
    }
    
    @Override
    public List<Seller> all()
    {
        return this.repository.all();
    }

    @Override
    public Seller find(long id)
    {
        return this.repository.find(id);
    }

    @Override
    public boolean exists(Seller model)
    {
        return this.repository.all().contains(model);
    }

    @Override
    public void create(Seller model)
    {
        this.repository.create(model);
    }

    @Override
    public void update(Seller model)
    {
        this.repository.update(model);
    }

    @Override
    public void delete(long id)
    {
        this.repository.delete(id);
    }
    
}
