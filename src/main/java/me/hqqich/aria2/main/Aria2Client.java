package me.hqqich.aria2.main;

import java.util.Collections;
import java.util.List;
import me.hqqich.aria2.call.Aria2Call;
import me.hqqich.aria2.call.Aria2MethodCall;
import me.hqqich.aria2.dto.base.Aria2Param;
import me.hqqich.aria2.dto.base.Aria2RequestBody;
import me.hqqich.aria2.enums.Aria2Method;
import me.hqqich.aria2.response.clazz.Aria2Response;
import me.hqqich.aria2.utils.JsonUtils;
import me.hqqich.aria2.utils.ObjUtils;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Aria2客户端
 *
 * @author : hqqich
 */
public class Aria2Client {

    public static final String DEFAULT_HOST = "http://localhost:6800/jsonrpc";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse(
            "application/json; charset=utf-8");
    final String host;
    final OkHttpClient client;
    final String token;
    /**
     * 是否打印请求参数
     */
    boolean printParam = false;
    int id = 0;

    public Aria2Client(OkHttpClient client, String host, String token) {
        this.host = ObjUtils.isEmpty(host) ? DEFAULT_HOST : host;
        this.client = client != null ? client : new OkHttpClient();
        this.token = token;
    }

    public Aria2Client() {
        this(null, null, null);
    }

    public void setPrintParam(boolean printParam) {
        this.printParam = printParam;
    }

    /**
     * 发送单个请求
     *
     * @param param 参数
     * @return Call
     */

    public Aria2Call call(Aria2Param param) {
        return new Aria2Call(pCall(param));
    }

    /**
     * 发送单个请求
     *
     * @param param 参数
     * @return Call
     */
    public <T> Aria2MethodCall<T> call(Aria2Param param,
            Class<? extends Aria2Response<T>> responseClass) {
        return new Aria2MethodCall<>(pCall(param), responseClass);
    }

    /**
     * 批量发送请求
     *
     * @param params 参数
     * @return Call
     */
    public Aria2Call call(List<Aria2Param> params) {
        return call(new Aria2Param(Aria2Method.multicall, Collections.singletonList(params)));
    }

    /**
     * 批量发送请求
     *
     * @param params        请求参数
     * @param responseClass 响应类型
     * @return Call
     */
    public <T> Aria2MethodCall<T> call(List<Aria2Param> params,
            Class<? extends Aria2Response<T>> responseClass) {
        final Aria2Param param = new Aria2Param(Aria2Method.multicall,
                Collections.singletonList(params));
        return call(param, responseClass);
    }

    /**
     * 生成请求
     *
     * @param param 参数
     * @return Call
     */
    private Call pCall(Aria2Param param) {
        final List<Object> params = param.getParams();
        if (!ObjUtils.isEmpty(this.token)) {
            params.add(0, "token:" + token);
        }

        final Aria2RequestBody aria2RequestBody = new Aria2RequestBody(this.id++,
                param.getMethodName().getName(), params);
        if (printParam) {
            JsonUtils.printJson(aria2RequestBody);
        }
        final RequestBody requestBody = RequestBody.create(JsonUtils.obj2Str(aria2RequestBody),
                MEDIA_TYPE_JSON);
        final Request request = new Request.Builder().url(host).post(requestBody).build();
        return this.client.newCall(request);
    }
}
