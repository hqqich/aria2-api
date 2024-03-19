package me.hqqich.aria2;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import me.hqqich.aria2.call.Aria2MethodCall;
import me.hqqich.aria2.dto.base.Aria2Option;
import me.hqqich.aria2.dto.form.Aria2AddUriForm;
import me.hqqich.aria2.exception.Aria2RequestException;
import me.hqqich.aria2.main.Aria2Api;
import me.hqqich.aria2.main.Aria2Client;
import me.hqqich.aria2.response.result.Aria2GlobalOption;
import me.hqqich.aria2.response.result.Aria2TaskStatus;
import me.hqqich.aria2.response.result.Aria2Version;
import me.hqqich.aria2.utils.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenhao on 2024/3/18 is 18:16.<p/>
 *
 * @author chenhao
 */
@DisplayName("aira2-api测试")
class MyTest {

    public static final Aria2Api API = new Aria2Api(new Aria2Client());

    private static final Logger logger = LoggerFactory.getLogger(MyTest.class);

    // ===============================
    public static final String DIR = "d:/aria2-api/";
    public static final List<String> URLS = Arrays.asList(
            "https://i.pximg.net/img-original/img/2024/06/19/04/30/30/99147997_p0.jpg",
            "https://i.pximg.net/img-original/img/2024/03/16/16/03/05/106264693_p0.png",
            "https://i.pximg.net/img-original/img/2024/03/16/00/59/58/106252268_p0.png",
            "https://i.pximg.net/img-original/img/2024/03/16/14/46/12/106263362_p0.jpg",
            "https://i.pximg.net/img-original/img/2024/03/16/14/46/12/106263362_p1.jpg"
    );

    public static void main(String[] args)
            throws Aria2RequestException, IOException, InterruptedException {
        // 打印请求参数
        API.getClient().setPrintParam(true);

//        unitTest();
        testRetry();
    }

    private static void testRetry() throws IOException, Aria2RequestException {
        final List<Aria2TaskStatus> tasks = API.tellStop(1, 10).sync();
        if (!tasks.isEmpty()) {
            System.out.println(API.retry(tasks.get(0).getGid()));
        }
    }

    private static void addUri() throws Aria2RequestException, IOException {
        System.out.println("单个添加..");
        final Aria2Option params = new Aria2Option();
        params.setReferer("*");
        params.setDir(DIR + "addUri/3");
        API.addUri(new Aria2AddUriForm(Collections.singleton(URLS.get(0)), params)).sync();
    }

    private static void unitTest() throws IOException, Aria2RequestException, InterruptedException {

        final Aria2Option params = new Aria2Option();
        params.setReferer("*");
        {
            System.out.println("批量添加..");
            params.setDir(DIR + "addUri/1");
            final List<String> gid = API.addUris(new Aria2AddUriForm(URLS, params)).sync()
                    .stream().flatMap(Collection::stream).collect(Collectors.toList());
            System.out.println("批量查询状态..");
            JsonUtils.write2File(API.tellStatus(gid).sync(), DIR + "/status/status2.json");
            System.out.println("批量查询参数..");
            JsonUtils.write2File(API.getOption(gid).sync(), DIR + "/status/option2.json");
            System.out.println("批量停止..");
            API.remove(gid).sync();
        }
        {
            System.out.println("批量添加..");
            params.setDir(DIR + "addUri/2");
            final List<String> gid = API.addUris(new Aria2AddUriForm(URLS, params)).sync()
                    .stream().flatMap(Collection::stream).collect(Collectors.toList());

            System.out.println("查询单个状态..");
            JsonUtils.write2File(API.tellStatus(gid.get(0)).sync(), DIR + "/status/status.json");
            System.out.println("查询单个参数..");
            JsonUtils.write2File(API.getOption(gid.get(0)).sync(), DIR + "/status/option.json");
            System.out.println("单个停止..");
            API.remove(gid.get(0)).sync();
        }
        {
            addUri();
        }
        {
            System.out.println("查询概况统计..");
            JsonUtils.write2File(API.getGlobalStat().sync(), DIR + "/getGlobalStat/res.json");
        }
        {
            System.out.println("查询活跃任务..");
            JsonUtils.write2File(API.tellActive().sync(), DIR + "/tell/active.json");
            System.out.println("查询等待任务..");
            JsonUtils.write2File(API.tellWaiting(1, 10).sync(), DIR + "/tell/waiting.json");
            System.out.println("查询活跃和等待任务..");
            JsonUtils.write2File(API.tellQueue(1, 10).sync(), DIR + "/tell/queue.json");
            Thread.sleep(5000);
            {
                System.out.println("查询停止任务..");
                final List<Aria2TaskStatus> tasks = API.tellStop(1, 10).sync();
                JsonUtils.write2File(tasks, DIR + "/tell/stop.json");
                if (tasks.size() > 2) {
                    final List<String> gid = tasks.stream().map(Aria2TaskStatus::getGid)
                            .collect(Collectors.toList());
                    System.out.println("移除单个停止任务..");
                    API.removeDownloadResult(gid.get(0)).sync();
                    System.out.println("移除多个停止任务..");
                    API.removeDownloadResult(gid.subList(1, 3)).sync();
                }
            }
            System.out.println("查询全部任务..");
            JsonUtils.write2File(API.tellAll(1, 10).sync(), DIR + "/tell/all.json");
        }
    }

    @DisplayName("简单测试")
    @Test
    @SneakyThrows
    void test01() {
        // 1、 创建客户端,也可以使用另一个构造函数, 指定OkHttp客户端, Aria2地址, 密码
        final Aria2Client client = new Aria2Client();
        // 1.1、 在控制台打印请求参数
        client.setPrintParam(true);
        // 2、 创建 API 对象; 如果对 Aria2 规范很熟悉, 也可以不使用API对象, 直接使用 client的 call方法发送请求
        final Aria2Api api = new Aria2Api(client);
        // 3、 调用 API 对象的方法 获得 Aria2MethodCall 对象
        final Aria2MethodCall<Aria2Version> versionCall = api.getVersion();
        // 4、 Aria2MethodCall 对象 可以选择 异步(async) 还是 同步(sync) 方法发送请求; 以String结尾的方法会返回json字符串结果, 需要自行解析为实体对象
        // 4.1 同步
        try {
            JsonUtils.printJson(versionCall.sync());
        } catch (IOException | Aria2RequestException e) {
            throw new RuntimeException(e);
        }
        // 4.2 异步
        Aria2GlobalOption sync = api.getGlobalOption().sync();
        JsonUtils.printJson(sync);

        Assertions.assertEquals("aria2/1.36.0", sync.getPeerAgent());

    }

    @Test
    @DisplayName("查看当前运行速度")
    void testGetUserAgent() {

        // 1、 创建客户端,也可以使用另一个构造函数, 指定OkHttp客户端, Aria2地址, 密码
        final Aria2Client client = new Aria2Client();

        logger.info("a = {}", "test");
    }

}
