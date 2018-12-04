package com.java.core.datetest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

/**
 * @author Mr.X
 * @version 1.0.0
 * Date:表示时间的类
 * LocalDate:表示日历表示法的类
 * java方法调用采用按值调用，方法得到的是所有参数值的一个拷贝，方法不能修改传递给它的任何参数变量的内容；
 * 所以，对于原始类型，方法内修改参数值并不会影响方法外部的变量值；
 * 对于引用类型：由于拷贝的是对象的引用，所以方法内对对象属性的修改，会影响外部对象的属性值。
 * <p>
 * 调用构造器的具体处理步骤：
 * 1 ) 所有数据域被初始化为默认值（0、 false 或 null。)
 * 2 ) 按照在类声明中出现的次序， 依次执行所有域初始化语句和初始化块。
 * 3 ) 如果构造器第一行调用了第二个构造器， 则执行第二个构造器主体
 * 4 ) 执行这个构造器的主体.
 * 如果子类的构造器没有显式地调用超类的构造器， 则将自动地调用超类默认（没有参数 )
 * 的构造器。 如果超类没有不带参数的构造器， 并且在子类的构造器中又没有显式地调用超类
 * 的其他构造器,则 Java 编译器将报告错误。
 * @since 2018/12/4
 */
public class DateTest {
    public static void main(String[] args) {
        LocalDate nowDate = LocalDate.now();
        LocalDate localDate = LocalDate.of(2018, 12, 4);
        printDate(nowDate);
        LocalDate newDate = nowDate.plusDays(5);
        printDate(newDate);
        LocalDate oldDate = nowDate.minusDays(nowDate.getDayOfMonth() - 1);//本月1号
        Month month = oldDate.getMonth();
        System.out.println(month.getValue());
        DayOfWeek weekDay = oldDate.getDayOfWeek();
        System.out.println(weekDay.getValue());
    }

    public static void printDate(LocalDate date) {
        System.out.println("Year:" + date.getYear());
        System.out.println("MonthValue:" + date.getMonthValue());
        System.out.println("Month:" + date.getMonth());
        System.out.println("DayOfMonth:" + date.getDayOfMonth());
        System.out.println("DayOfYear:" + date.getDayOfYear());
        System.out.println("DayOfWeek:" + date.getDayOfWeek());
    }
}
