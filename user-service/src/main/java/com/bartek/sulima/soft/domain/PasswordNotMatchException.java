package com.bartek.sulima.soft.domain;

public class PasswordNotMatchException extends RuntimeException {

    public PasswordNotMatchException(String msg) {
        super(msg);
    }
}
