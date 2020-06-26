/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.repositories;

import java.util.List;

/**
 * Repository for files, these are files exported from VRMaster.jar application.
 *
 * @author Derick Felix
 * @param <T> the type of the object
 */
public interface FileRepository<T> {

    /**
     * Reads the content of a file and return a list of object which this file
     * represents since each line of that file is an object.
     *
     * @param path the path of the file which represents this entity
     * @return a list of object of this type
     */
    List<T> read(String path);

}
