/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.services;

import java.util.List;

/**
 * This interface was designed to boost the time of creating a service of a
 * specific type, since some services has the same standard operations like
 * list, find, create, update and delete.
 *
 * @param <Model> the type of the service
 * @author Derick Felix
 */
public interface Service<Model> {

    /**
     * Gets a list of entities from its repository.
     *
     * @return a {@code java.util.List} of the type of Model
     */
    List<Model> all();

    /**
     * Gets an entity from its repository by specifying an id number.
     *
     * @param id the id of the entity
     * @return an object of the type of Model
     */
    Model find(long id);

    /**
     * Checks whether an entity exists in its repository.
     *
     * @param model the entity to be checked
     * @return {@code true} if this model exists on its repository
     */
    boolean exists(Model model);

    /**
     * Creates an entity of its repository.
     *
     * @param model the entity to be created
     */
    void create(Model model);

    /**
     * Updates an entity of its repository.
     *
     * @param model the entity to be updated
     */
    void update(Model model);

    /**
     * Deletes an entity from its repository.
     *
     * @param id the id of the entity
     */
    void delete(long id);

}
