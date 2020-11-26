package com.giovanny.study.threadstudy;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @packageName: com.example.demo1.threadstudy
 * @className: ThreadDemo1
 * @description: 线程间通讯
 * @author: YangJun
 * @date: 2020/4/9 9:37
 * @version: v1.0
 **/
public class ThreadDemo1 {
    public static void main(String[] args) {
//        practise1();
//        practise2();
//        practise3();
//        practise4();
//        practise5();
        practise6();


    }

    /**
     * 不作任何操作时,打印错乱的
     * A B在争抢资源
     */
    public static void practise1() {
        Thread threadA = new Thread(() -> printNum("A"));
        Thread threadB = new Thread(() -> printNum("B"));
        threadA.start();
        threadB.start();
    }

    /**
     * 如何让两个线程依次执行？ 就是当A运行结束了再运行B 可以利用 thread.join() 方法
     * 假设有两个线程，一个是线程 A，另一个是线程 B，两个线程分别依次打印 1 2 3 三个数字即可
     */
    public static void practise2() {
        Thread threadA = new Thread(() -> printNum("A"));
        Thread threadB = new Thread(() -> {
            System.out.println("threadB 开始等待 threadA");
            try {
                threadA.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printNum("B");
        });
        //故意把B线程先启动
        threadB.start();
        threadA.start();
    }

    /**
     * 那如何让 两个线程按照指定方式有序交叉运行呢？
     * 还是上面那个例子，我现在希望 A 在打印完 1 后，再让 B 打印 1, 2, 3，最后再回到 A 继续打印 2, 3。
     * 这种需求下，显然 Thread.join() 已经不能满足了。我们需要更细粒度的锁来控制执行顺序。
     * 这里，我们可以利用 object.wait() 和 object.notify() 两个方法来实现。
     * <p>
     * 这个过程如下：
     * 首先创建一个 A 和 B 共享的对象锁 lock = new Object();
     * 当 A 得到锁后，先打印 1，然后调用 lock.wait() 方法，交出锁的控制权，进入 wait 状态；
     * 对 B 而言，由于 A 最开始得到了锁，导致 B 无法执行；直到 A 调用 lock.wait() 释放控制权后， B 才得到了锁；
     * B 在得到锁后打印 1， 2， 3；然后调用 lock.notify() 方法，唤醒正在 wait 的 A;
     * A 被唤醒后，继续打印剩下的 2，3。
     */
    public static void practise3() {
        Object lock = new Object();
        Thread threadA = new Thread(() -> {
            // 不加锁会发生java.lang.IllegalMonitorStateException 异常,
            // 并且加锁对象不能变，比如Boolean Integer String，在复制时会创建新的对象
            // 此时可以使用AtomicBoolean等来保证锁的对象不变
            // 需要加锁是因为notify要在wait后执行，加锁相当于将两个线程绑在一起了，不然会互相抢夺资源
            synchronized (lock) {
                System.out.println("A:1");
                try {
                    // A 到这里停下来，将资源让出来
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A:2");
                System.out.println("A:3");
            }
        });
        Thread threadB = new Thread(() -> {
            synchronized (lock) {
                System.out.println("B:1");
                System.out.println("B:2");
                System.out.println("B:3");
                // A 开始执行 ，唤醒等待的随机一个线程
                // 这里也可以用notifyAll()，唤醒所有线程，当有多个线程等待的时候，用notify容易造成死锁
                lock.notify();
            }

        });
        //A在B前启动
        threadA.start();
        threadB.start();
    }

    /**
     * 四个线程 A B C D，其中 D 要等到 A B C 全执行完毕后才执行，而且 A B C 是同步运行的
     * <p>
     * 最开始我们介绍了 thread.join()，可以让一个线程等另一个线程运行完毕后再继续执行，
     * 那我们可以在 D 线程里依次 join A B C，不过这也就使得 A B C 必须依次执行，而我们要的是这三者能同步运行。
     * 或者说，我们希望达到的目的是：A B C 三个线程同时运行，各自独立运行完后通知 D；
     * 对 D 而言，只要 A B C 都运行完了，D 再开始运行。
     * 针对这种情况，我们可以利用 CountdownLatch 来实现这类通信方式。它的基本用法是：
     * 创建一个计数器，设置初始值，CountdownLatch countDownLatch = new CountDownLatch(2);
     * 在 等待线程 里调用 countDownLatch.await() 方法，进入等待状态，直到计数值变成 0；
     * 在 其他线程 里，调用 countDownLatch.countDown() 方法，该方法会将计数值减小 1；
     * 当 其他线程 的 countDown() 方法把计数值变成 0 时，等待线程 里的 countDownLatch.await() 立即退出，继续执行下面的代码。
     * <p>
     * CountDownLatch 适用于一个线程去等待多个线程的情况
     */
    public static void practise4() {
        // 这个数值要设置好，不然等 countDown() 等于 0 后 ok 就不会再等了，就回去抢占资源
        int worker = 3;
        CountDownLatch downLatch = new CountDownLatch(worker);
        Thread threadD = new Thread(() -> {
            System.out.println("ok 等待其他线程执行...");
            try {
                // 等待其它线程执行
                downLatch.await();
                System.out.println("ok 执行...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadD.start();

        for (char threadName = 'A'; threadName <= 'C'; threadName++) {
            String thread = String.valueOf(threadName);
            new Thread(() -> {
                System.out.println(thread + ":start...");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thread + ":end...");
                // 减
                downLatch.countDown();
            }).start();
        }
    }

    /**
     * 针对 线程 A B C 各自开始准备，直到三者都准备完毕，然后再同时运行 。
     * 也就是要实现一种 线程之间互相等待 的效果，那应该怎么来实现呢？
     * 可以利用 CyclicBarrier 数据结构，它的基本用法是：
     * 1、先创建一个公共 CyclicBarrier 对象，设置 同时等待 的线程数，CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
     * 2、这些线程同时开始自己做准备，自身准备完毕后，需要等待别人准备完毕，这时调用 cyclicBarrier.await(); 即可开始等待别人；
     * 3、当指定的 同时等待 的线程数都调用了 cyclicBarrier.await();时，意味着这些线程都准备完毕好，然后这些线程才 同时继续执行。
     * <p>
     * 可以看到，主线程调用 futureTask.get() 方法时阻塞主线程；
     * 然后 Callable 内部开始执行，并返回运算结果；此时 futureTask.get() 得到结果，主线程恢复运行。
     * 这里我们可以学到，通过 FutureTask 和 Callable 可以直接在主线程获得子线程的运算结果，只不过需要阻塞主线程。
     * 当然，如果不希望阻塞主线程，可以考虑利用 ExecutorService，把 FutureTask 放到线程池去管理执行。
     */
    public static void practise5() {
        int runner = 3;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(runner);
        final Random random = new Random();
        for (char runnerName = 'A'; runnerName <= 'C'; runnerName++) {
            final String rN = String.valueOf(runnerName);
            new Thread(() -> {
                long prepareTime = random.nextInt(10000) + 100;
                System.out.println(rN + " is preparing for time: " + prepareTime);
                try {
                    Thread.sleep(prepareTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(rN + " is prepared, waiting for others");
                try {
                    // 当前运动员准备完毕，等待别人准备好
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                // 所有运动员都准备好了，一起开始跑
                System.out.println(rN + " starts running");
            }).start();
        }

    }

    private static void practise6() {
        Callable<Integer> callable = () -> {
            System.out.println("Task starts");
            Thread.sleep(1000);
            int result = 0;
            for (int i = 0; i <= 100; i++) {
                result += i;
            }
            System.out.println("Task finished and return result");
            return result;
        };
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        try {
            System.out.println("Before futureTask.get()");
            System.out.println("Result: " + futureTask.get());
            System.out.println("After futureTask.get()");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    /**
     * 打印数字
     *
     * @param threadName 线程名
     */
    public static void printNum(String threadName) {
        int i = 0;
        while (i++ < 3) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadName + " print:" + i);
        }
    }
}
