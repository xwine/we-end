# we-end
### util-end 是什么

we-end 是一个jar包，提供数据mock功能，与其他mock框架不同的是，we-end被设计成一个零侵入，可插拔，非中心化的工具。we-end 的核心功能包括： 方法数据自动生成，多人协作mock数据共享。
-end 目的是要解决开发人员本地测试依赖的上游接口没有测试环境数据，以及服务端测试数据难造，导致部分场景难以测试等问题。

1. [起步](#起步)
1. [测试](#测试)
1. [高级用法](#高级用法)

### 起步

1. 引入maven依赖
```xml
    <dependency>
        <groupId>com.github.xwine</groupId>
        <artifactId>we-end-spring</artifactId>
        <version>1.3.2</version>
    </dependency>
```


2. 启动项目

```shell
                              __  
   ____ ___   ____   _____   / /__
  / __ `__ \ / __ \ / ___/  / //_/
 / / / / / // /_/ // /__   / ,<   
/_/ /_/ /_/ \____/ \___/  /_/|_|  
                                  
...[OMock]  web-end::http://localhost:8080/console/index.html
```
控制台能看到上面这个图案，说明启动成功.



### 测试
访问图案后的链接：http://localhost:8080/console/index.html 进入util-end控制台

#### 对象数据生成
调用util-end包提供的`ObjectMock`工具类获取对象数据

```java
@Data
public class MockDomain {

    public static void main(String[] args) {
        MockDomain mockDomain = ObjectMock.parseClass(MockDomain.class);
        System.out.println(mockDomain);
    }
    
    
    private int age;
    private Integer count;
    private String name;
    private char sex;
    private int[] eggs;
    private MockDomain mockDomain;
    private String[] subjects;
    private Date holiday;
    private List<Integer> integerList;
    private Map<String,Integer> map;
    
}

```

输出结果

` MockDomain(age=123, count=123, name=abc, sex=o, eggs=[123, 123], mockDomain=MockDomain(age=0, count=null, name=null, sex=0, eggs=null, mockDomain=null, subjects=null, holiday=null, integerList=null, map=null), subjects=[abc, abc], holiday=Wed Jul 01 10:15:05 CST 2020, integerList=[123, 123, 123], map={abc=123}) `


#### 配置手册
util-end 默认有许多可配置项，不配置都是走默认，比如数据生成策略：字符串类型默认就是`abc`，整形类型默认`123` ... 可前往IDEA `.mock/config.json`文件进行修改配置

| 属性               | 类型         | 默认值                 | 描述                                  | 起始版本 |
| ------------------ | ------------ | ---------------------- | ------------------------------------- | -------- |
| appName            | String       |  ...                   | 根据当前项目所在路径获取到的项目名         |          |
| nowUser            | String       |  ...                   | 从系统环境变量中获取系统用户              |          |
| path               | String       |                        | mock文件存储位置                      |          |
| localCacheSwitch   | Boolean      | false                   | 是否开启本地缓存                      |          |
| remoteCacheSwitch  | Boolean      | true                   | 是否开启远程缓存                      |          |
| idCachePath        | String       | data                   | 接口方法mock数据缓存目录              |          |
| template           | String       | type                   | 对象mock数据缓存目录                  |          |
| templateString     | String       | abc                    | String类型生成mock默认值              |          |
| templateInteger    | Integer      | 123                    | -                                     |          |
| templateLong       | Long         | 12345                  | -                                     |          |
| templateDouble     | Double       | 1111111.0              | -                                     |          |
| templateFloat      | Float        | 12345.0                | -                                     |          |
| templateBigDecimal | BigDecimal   | 2020                   | -                                     |          |
| templateBoolean    | Boolean      | true                   | -                                     |          |
| templateCharacter  | Character    | o                      | -                                     |          |
| templateByte       | Byte         | 1                      | -                                     |          |
| templateDate       | Date         | Jul 1, 2020 4:23:06 PM | -                                     |          |
| mockOn             | Boolean      | true                   | 是否开启mock                          |          |
| consolePrefix      | String       | /console               | 本地控制台拦截url                     |          |
| mockClasses        | List<String> | []                     | 需要mock的包，接口，或方法，接口_方法 |          |



#### FAQ
1. Maven依赖错误?
> 默认您的项目中存在包：`aspectjrt`,`spring-web`,`slf4j-api`,`javax.servlet-api`,`jsf`,`fastjson`,springboot项目的话默认存在`spring-boot-starter`，缺少包都可能导致依赖错误。

2. 本地控制台拦截路径和当前项目冲突？
> 可修改`.mock/config.json`文件`consolePrefix`属性，配置成自己的路径。
