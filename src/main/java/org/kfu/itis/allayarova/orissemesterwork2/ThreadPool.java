package org.kfu.itis.allayarova.orissemesterwork2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    private final BlockingQueue<Runnable> taskQueue;
    private final MyThread[] threads;
    private volatile boolean isRunning = true;

    public ThreadPool(int poolSize) {
        taskQueue = new LinkedBlockingQueue<>();
        threads = new MyThread[poolSize];

        for (int i = 0; i < poolSize; i++) {
            threads[i] = new MyThread();
            threads[i].start();
        }
    }

    public void execute(Runnable task) {
        if (isRunning) {
            taskQueue.offer(task);
        }
    }

    public void stopAllThreads() {
        isRunning = false;
        for (MyThread thread : threads) {
            thread.interrupt();
        }
    }

    private class MyThread extends Thread {
        public void run() {
            while (isRunning || !taskQueue.isEmpty()) {
                try {
                    Runnable task = taskQueue.poll(10, TimeUnit.SECONDS);
//                    это метод интерфейса BlockingQueue.
//                    Он пытается извлечь и удалить задачу из очереди taskQueue.
//                    Если очередь пуста, метод ожидает появления задачи в течение указанного времени
                    if (task != null) {
                        task.run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
