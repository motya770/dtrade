package com.dtrade.utils;

import java.util.function.Consumer;

/**
 * Created by kudelin on 1/7/17.
 */
@FunctionalInterface
public interface CheckedFunction<T> extends Consumer<T> {

    @Override
    default void accept(final T elem) {
        try {
            acceptThrows(elem);
        } catch (final Exception e) {
            // Implement your own exception handling logic here..
            // For example:
            //System.out.println("handling an exception...");
            throw new RuntimeException(e);
        }
    }

    void acceptThrows(T elem) throws Exception;

}
