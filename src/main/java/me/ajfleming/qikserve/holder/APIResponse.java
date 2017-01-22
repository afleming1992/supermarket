package me.ajfleming.qikserve.holder;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 *  Class: APIResponse
 *  Purpose: This class is used for holding API responses to be returned to the APIController
 *  Author: Andrew Fleming
 */

public class APIResponse {

    private HttpStatus status;
    private String message;
    private HashMap<String, String> values;

    public APIResponse(HttpStatus status, String message)
    {
        this.status = status;
        this.message = message;
        this.values = new HashMap<String, String>();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void addValue(String key, String value)
    {
        values.put(key, value);
    }
}
