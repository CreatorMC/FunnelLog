# 介绍

个人练习作品。

本项目是一个整合了`日志`和`限流`功能的中间件。

按照规范打包为 SpringBootStarter。

日志使用 `SLF4J`，限流使用 `RateLimiter`。

# 使用

## 安装

因没有发布至 Maven 中央仓库，您可以下载项目后自己用 Maven 的 Install 到本地仓库中使用。

## 日志

在要输出日志的方法上添加 `@SystemLog(businessName = "业务名称")` 注解。

```java
    @SystemLog(businessName = "更新对应文章的点赞量")
    public ResponseResult updateLikeCount(@PathVariable Long id) {
        return articleService.updateLikeCount(id);
    }
```

## 限流

在要限流的方法上添加 `@DoRateLimiter(permitsPerSecond = 1.0, returnJson = "JSON 对象")` 注解。

```java
    @DoRateLimiter(permitsPerSecond = 1.0, returnJson =
            "{" +
                    "\"code\": 500," +
                    "\"msg\": \"请休息一下再点击~\"," +
                    "\"data\": null" +
                    "}")
    public ResponseResult updateLikeCount(@PathVariable Long id) {
        return articleService.updateLikeCount(id);
    }
```

### 自己实现限流

作为扩展，您可以自己实现 `LimitService` 接口中的方法，自己定义一个限流方法类。这样，您可以不局限于使用 `RateLimiter`，还可以使用任何其他的中间件，甚至是您自己实现的限流！

只需在 `yml` 文件中如下配置：

```yml
funnellog:
  limit:
    # 您自己实现的类的全类名
    clazz: com.creator.service.impl.MyLimitServiceImpl
```

对应的类：

```java
public class MyLimitServiceImpl implements LimitService {
    @Override
    public Object access(ProceedingJoinPoint joinPoint, Method method, DoRateLimiter doRateLimiter, Object[] args) throws Throwable {
        // TODO
    }
}
```

# 效果

## 日志

![image](https://github.com/CreatorMC/FunnelLog/assets/103886337/8405237d-173c-4e8a-8316-50c668e5c132)

## 限流

![image](https://github.com/CreatorMC/FunnelLog/assets/103886337/3dc742dc-4d32-4de8-9f53-acf6e90bb9e5)
