package com.myblog.myblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{


    private String resourceName;
    private String fieldName;
    private long filedValue;

    public ResourceNotFoundException(String resourceName, String fieldName, long filedValue) {
        super(String.format("%s not found with %s:'%s'",resourceName,fieldName,filedValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.filedValue = filedValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFiledValue() {
        return filedValue;
    }
}
