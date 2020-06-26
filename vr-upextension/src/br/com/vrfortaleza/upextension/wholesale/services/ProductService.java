/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.wholesale.services;

import br.com.vrfortaleza.upextension.services.Service;
import br.com.vrfortaleza.upextension.wholesale.repositories.ProductRepository;
import br.com.vrfortaleza.upextension.wholesale.models.Product;
import java.util.List;

/**
 * A service of {@link Product product} type.
 *
 * @author derickfelix
 */
public class ProductService implements Service<Product> {

    private final ProductRepository repository;

    public ProductService()
    {
        this.repository = new ProductRepository();
    }

    @Override
    public List<Product> all()
    {
        return this.repository.all();
    }

    @Override
    public Product find(long id)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean exists(Product model)
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

}
