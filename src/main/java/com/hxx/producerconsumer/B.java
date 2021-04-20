package com.hxx.producerconsumer;


import com.sun.xml.internal.bind.v2.model.core.ID;

//生产者、消费者
//原来的
public class B {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A");

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B");
    }
}


//判断等待、业务、通知
class Data{
    private int num = 0;

    public synchronized void increment() throws InterruptedException {
        while (num != 0){//wait应该在循环中，主要是因为，线程可能被其他线程唤醒，但是不会通知，即虚假唤醒
            this.wait();
        }

        num ++;
        System.out.println(Thread.currentThread().getName() + num);
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        while (num == 0){
            this.wait();
        }
        num --;
        System.out.println(Thread.currentThread().getName() + num);
        this.notifyAll();
    }
}
