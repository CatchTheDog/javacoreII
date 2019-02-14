package com.java.core.concurrent;

/**
 * 对同步器记录
 * CyclicBarrier 允许线程集等待知道某个预定数目的线程到达一个公共障栅，然后可以选择执行一个处理障栅的动作   当大量的线程需要在它们的接口可用之前完成时使用
 * Phaser 类似于循环障栅，不过有一个可变的计数
 * CountDownLatch 允许线程等待直到计数器减为0  当一个或多个线程需要等待直到指定数目的事件发生时
 * Exchanger 允许两个线程在要交换的对象准备好时交换对象   当两个线程工作在同意数据结构的恋歌实例上时，一个向实例添加数据而一个向实例清除数据
 * Semaphore 允许线程集等待直到被允许继续运行位置  限制访问资源的线程总数
 * SyncronousQueue  允许一个线程把一个对象交给另一个线程  在没有显示同步的情况下，当两个线程准备好将一个对象从一个线程传递到另一个时
 * <p>
 * <p>
 * 信号量   同步原语
 * Swing 非线程安全
 * EventQueue.invokeLater() 线程异步执行，方法立即返回
 * EventQueue.invokeAndWait() 线程执行，方法等待run执行过后返回
 * SwingWorker类 在Swing中执行耗时的后台任务
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/14 13:38
 */
public class Synchronizer {
}
