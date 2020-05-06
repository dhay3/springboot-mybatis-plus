package com.chz.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus//如果加载异常类上一定会sendError,即使异常被捕获
public class UserNotExistException extends RuntimeException {
    private String id;

    public UserNotExistException(String id) {
        super("user not exist");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
