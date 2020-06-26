/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.repositories;

import java.util.List;

/**
 * This interface was designed to boost the time of creating a repository of a
 * specific type, since some repositories has the same standard operations like
 * list, find, create, update and delete.
 *
 * @param <Model> the type of the repository
 * @author Derick Felix
 */
public interface Repository<Model> {

    /**
     * Query for a list of entities.
     *
     * @return a {@code java.util.List} of the type of Model
     */
    List<Model> all();

    /**
     * Query for an entity by specifying an id number.
     *
     * @param id the id of the entity
     * @return an object of the type of Model
     */
    Model find(long id);
    
    /**
     * Inserts a new entity into the database.
     *
     * @param model the entity to be inserted
     */
    void create(Model model);

    /**
     * Updates an entity of the database
     *
     * @param model the entity to be updated
     */
    void update(Model model);

    /**
     * Deletes an entity from the database by its id number.
     *
     * @param id the id of the entity
     */
    void delete(long id);

}
