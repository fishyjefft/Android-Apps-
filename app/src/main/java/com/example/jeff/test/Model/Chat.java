package com.example.jeff.test.Model;

/**
 * Created by Jeff on 30/11/2017.
 */

public class Chat {
    public String message;
    public boolean Sent;

    public Chat(String message, boolean sent) {
        this.message = message;
        this.Sent = sent;
    }

    public Chat() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean Sent() {
        return Sent;
    }

    public void setSent(boolean sent) {
        Sent = sent;
    }
}
