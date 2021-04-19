package com.hxx.demo01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SaleTicket1 {
    public static void main(String[] args) {
        //并发： 多个线程操作一个资源类
        Ticket1 ticket = new Ticket1();

        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                ticket.sale();
            }
        },"a").start();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                ticket.sale();
            }
        },"b").start();
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                ticket.sale();
            }
        },"c").start();
    }
}

//Lock:三部

/**
 * 1、创建锁
 * 2、加锁
 * 3、解锁
 */
class Ticket1{
    private int number = 50;

    Lock lock = new ReentrantLock();//创建锁

    public void sale(){
        lock.lock();//加锁
        try {
            if (number > 0){
                System.out.println(Thread.currentThread().getName() + (number --) + "票，剩余" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//解锁 必须解锁
        }
    }
}