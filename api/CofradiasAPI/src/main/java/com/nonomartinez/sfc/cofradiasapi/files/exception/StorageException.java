package com.nonomartinez.sfc.cofradiasapi.files.exception;

public class StorageException extends RuntimeException{

    public StorageException(String msg) {
        super(msg);
    }

    public StorageException(String msg, Exception e) {
        super(msg, e);
    }
}
