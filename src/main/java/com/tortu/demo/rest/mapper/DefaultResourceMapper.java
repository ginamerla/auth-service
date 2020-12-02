package com.tortu.demo.rest.mapper;

/**
 *Interface to map a model <M> to its Rest Resource <R>
 * @param <M> Model to convert
 * @param <R> new resource based on the model information
 */
public interface DefaultResourceMapper<M,R> {

    R map(M model);

}
