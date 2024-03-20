package me.hqqich.aria2

import me.hqqich.aria2.utils.JsonUtils

/**
 * Created by chenhao on 2024/3/20 is 10:15.<p/>
 * 这是一个示例类。
 */
class Example {

    /**
     * 这是一个示例方法。
     * @param param 示例参数。
     * @return 返回一个字符串。
     */
    fun exampleMethod(param: String): String {
        JsonUtils.printJson("""{"param": ${param.toInt()}}""")
        return "Hello, $param!"
    }
}
