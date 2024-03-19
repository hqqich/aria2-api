package me.hqqich.aria2.call;

import java.io.IOException;
import me.hqqich.aria2.callback.ClassAria2Callback;
import me.hqqich.aria2.exception.Aria2RequestException;
import me.hqqich.aria2.response.clazz.Aria2Response;
import me.hqqich.aria2.utils.JsonUtils;
import okhttp3.Call;

/**
 * @author : hqqich
 */
public class Aria2MethodCall<T> extends Aria2Call {

    final Class<? extends Aria2Response<T>> responseClass;

    public Aria2MethodCall(Call call, Class<? extends Aria2Response<T>> responseClass) {
        super(call);
        this.responseClass = responseClass;
    }

    public void async(ClassAria2Callback<T> callback) {
        callback.setEClass(responseClass);
        this.asyncString(callback);
    }

    /**
     * 同步请求
     *
     * @return 响应
     */
    public T sync() throws IOException, Aria2RequestException {
        final String s = this.syncString();
        if (s == null) {
            return null;
        }
        final Aria2Response<T> res = JsonUtils.parse(s, responseClass);
        return res == null ? null : res.getResult();
    }
}
