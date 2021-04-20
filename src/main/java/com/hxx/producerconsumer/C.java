package com.hxx.producerconsumer;

import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class C {
    public static void main(String[] args) {
        Data2 data = new Data2();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }
}


//把sychronized 换成lock
class Data2{
    private int num = 0;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();//监视器

    public  void increment() throws InterruptedException {
        lock.lock();

        try {
            while (num != 0){//wait应该在循环中，主要是因为，线程可能被其他线程唤醒，但是不会通知，即虚假唤醒
                condition.await();
            }

            num ++;
            System.out.println(Thread.currentThread().getName() + num);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    /** 最重要的三部：判断等待、业务、唤醒其他线程
     *
     *  lock锁的用法：先加锁、try catch，最后 解锁
     *
     * @throws InterruptedException
     */
    public  void decrement() throws InterruptedException {
        lock.lock();

        try {
            while (num == 0){
                condition.await();//等待
            }

            //业务
            num --;
            System.out.println(Thread.currentThread().getName() + num);

            //唤醒
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
