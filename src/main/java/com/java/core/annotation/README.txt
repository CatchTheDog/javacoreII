JAVA内置注解：
    1.元注解  -- 用于定义注解的注解
        @Target
        @Retention
        @Document
        @Inherited
    2.@Deprecated
    3.@SuppressWarnings
    4.@Override
    5.@PostConstruct
    6.@PreDestroy
    7.@Resource
    8.@Resources
    9.@Generated

    注解三种级别;
        1.源码级注解    注解处理器在编译时处理，生成源码文件，递归进行编译处理
        2.字节码级注解   BCEL
        3.运行期注解    在运行时对类进行自动扫描，然后使用反射获取注解类信息
