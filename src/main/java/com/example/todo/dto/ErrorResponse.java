package com.example.todo.dto;

import java.time.Instant;

public class ErrorResponse {
    public Instant timestamp;
    public int status;
    public String error;
    public String message;
    public String path;

    public ErrorResponse(){

    }

    public ErrorResponse(int status,String error,String message,String path){
       this.timestamp = Instant.now();
       this.status = status;
       this.error = error;
       this.message = message;
       this.path = path;

    }

}
