package com.hxx.demo01;

public class SaleTicket {
    public static void main(String[] args) {
        //并发： 多个线程操作一个资源类
        Ticket ticket = new Ticket();

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

//资源类OOP
class Ticket{
    private int number = 50;

    public synchronized void sale(){
        if (number > 0){
            System.out.println(Thread.currentThread().getName() + (number --) + "票，剩余" + number);
        }
    }
}
