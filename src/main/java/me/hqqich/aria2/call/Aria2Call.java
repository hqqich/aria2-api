package me.hqqich.aria2.call;

import java.io.IOException;
import me.hqqich.aria2.callback.AbstractAria2Callback;
import me.hqqich.aria2.exception.Aria2RequestException;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author : hqqich
 */
public class Aria2Call {

    final Call call;

    public Aria2Call(Call call) {
        this.call = call;
    }

    /**
     * 异步请求, 回调参数为字符串类型
     *
     * @param callback 回调方法
     */
    public void asyncString(AbstractAria2Callback callback) {
        this.call.enqueue(callback);
    }

    /**
     * 同步请求
     *
     * @return 字符串
     */
    public String syncString() throws IOException, Aria2RequestException {
        try (Response response = this.call.execute()) {
            ResponseBody body = AbstractAria2Callback.body(this.call, response);
            return body != null ? body.string() : null;
        }
    }
}
