package com.hxx.producerconsumer;


import java.util.Currency;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 线程之间的通信 ： 生产者和消费者
 *  线程交替执行 a,b
 */
public class A{
    public static void main(String[] args) {
        /*ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue(10);
        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();*/

        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue(10);
        Data1 data1 = new Data1(queue);
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data1.incre();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data1.dere();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();
    }
}

class Producer implements Runnable{
    private BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(20);
                queue.put(i);
                System.out.println("生产者" + queue.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable{

    BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
                queue.take();
                System.out.println("消费" + queue.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Data1{
    BlockingQueue<Integer> queue;

    public Data1(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public synchronized void incre() throws InterruptedException {
        while (queue.size() != 0){
            this.wait();
        }
        this.queue.put(1);
        System.out.println(Thread.currentThread().getName() + queue.size());
        this.notifyAll();
    }

    public synchronized void dere() throws InterruptedException {
        while (queue.size() == 0){//等待应该尽量在循环中
            this.wait();
        }
        this.queue.take();
        System.out.println(Thread.currentThread().getName() + queue.size());
        this.notifyAll();
    }
}
