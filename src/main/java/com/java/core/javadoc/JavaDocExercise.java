package com.java.core.javadoc;

/**
 * @param
 * @author Mr.X
 * <em>javadoc 工具</em> 用于从源代码中抽取信息生成文档；
 * 在<strong>文档注释</strong>中可以使用<strong>自由格式文本</strong>；
 * <em>但是不能使用段落标签（h1与hr等）</em>；
 * 若要键入等宽代码，则需要使用{@code JavaDocExercise}
 * <strong>
 * <em>方法注释：</em>
 * @author
 * @return
 * @throws <em>域注释：</em>只需对公有静态常量建立文档
 * <em>通用注释：</em>
 * @see <em>包注释：</em>
 * package.html 文件  在标记<body>...</body>之间的所有文本都会被抽取
 * package-info.java 文件，此文件必须包含一个javadoc注释
 * <em>源代码注释：</em>
 * 可以为所有源文件提供一个概述性的注释文件——overview.html
 *
 *
 * </strong>
 * @since {@link}
 * //@deprecated
 */
public class JavaDocExercise {
    /**
     * javadoc 工具只从公有域（通常只对静态常量）建立文档，
     * 私有域注释不会被抽取到文档中
     */
    private int id = 0;

    /**
     * @author Junqiang.Ma
     * @version 1.0.0
     * @see com.java.core.javadoc.JavaDocExercise.User#sayHello(String)
     * {@link com.java.core.javadoc.JavaDocExercise.User#sayHello(String)}
     * @since 2018/11/5 13:42
     */
    public class User {
        public String name;
        protected int age;
        private String sex;

        public User(String name, int age, String sex) {
            this.name = name;
            this.age = age;
            this.sex = sex;
        }

        /**
         * @deprecated Use <code>sayHello(String msg)</code> instead
         */
        @Deprecated
        public void sayHello() {
            System.out.println("Hello!");
        }

        /**
         * 用于<em>问候</em>
         *
         * @param msg 问候信息
         */
        public void sayHello(String msg) {
            System.out.println("Hello,I am " + name + " !");
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", sex='" + sex + '\'' +
                    '}';
        }
    }
}
