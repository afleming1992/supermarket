package me.ajfleming.qikserve.message;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * Created by andrew on 15/01/17.
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void addValue(String key, String value)
    {
        values.put(key, value);
    }

    public void removeValue(String key)
    {
        values.remove(key);
    }
}
