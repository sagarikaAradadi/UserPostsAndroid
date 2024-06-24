package com.samplerecyclerview.base;

/**
 * Interface to make sure all pages have basic implementations
 */
public interface InPage {
    void navigate() throws Exception;

    void validate() throws Exception;

    default void exit() throws Exception {
    }
}
