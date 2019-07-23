package com.roger.git.exception;

/**
 * Custom repo exception class
 */
public class RepoUserNotFoundException extends  Exception {

    public RepoUserNotFoundException(String message){
        super(message);
    }
}
