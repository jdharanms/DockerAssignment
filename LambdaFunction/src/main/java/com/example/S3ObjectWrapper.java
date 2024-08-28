package com.example;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class S3ObjectWrapper {
    private final S3Object s3Object;

    public S3ObjectWrapper(S3Object s3Object) {
        this.s3Object = s3Object;
    }

    public S3ObjectInputStream getObjectContent() {
        return s3Object.getObjectContent();
    }

}
