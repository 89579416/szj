package com.s.z.j.rxjava;

/**
 * Created by Administrator on 2017-08-29.
 */
public class BaseResult {
    private int success;
    private String message;
    private int user_id;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public BaseResult(int success, String message, int user_id) {
        this.success = success;
        this.message = message;
        this.user_id = user_id;
    }

    public BaseResult() {
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
