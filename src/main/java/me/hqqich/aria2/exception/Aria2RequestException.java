package me.hqqich.aria2.exception;

import okhttp3.Call;

/**
 * Pixiv请求异常
 *
 * @author : hqqich
 */
public class Aria2RequestException extends Aria2Exception {

    Call call;

    /**
     * Constructs a new exception with the specified detail message.  The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public Aria2RequestException(int code, String message, Call call) {
        super(code, message);
        this.call = call;
    }
}
