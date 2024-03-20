# aria2-api

> fork from [AgMonk/aria2-api](https://github.com/AgMonk/aria2-api)

-[x] 学习如何将github作为maven仓库
-[x] aria2-api用法

## 引入

```xml

<project>
  <repositories>
    <repository>
      <id>me.hqqich.aria2</id>
      <name>aria2Api</name>
      <url>https://hqqich.github.io/aria2-api/maven-repo/</url>
    </repository>
  </repositories>
  <dependencies>
    <dependency>
      <groupId>me.hqqich.aria2</groupId>
      <artifactId>aria2-api</artifactId>
      <version>1.0.3</version>
    </dependency>
  </dependencies>
</project>
```

## 使用

见 [MyTest](src/test/java/me/hqqich/aria2/MyTest.java)
