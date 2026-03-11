package models;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Buffer {

    private final long SLEEP_TIME;
    private final Random random;
    private final Lock lock;
    private final Condition condition;
    private final List<String> messages;

    public Buffer() {
        this.SLEEP_TIME = 200L;
        this.random = new Random();
        this.lock = new ReentrantLock();
        this.messages = new ArrayList<>();
        this.condition = lock.newCondition();
    }

    public void addMessageToBuffer(int maxMessageCount) {
        lock.lock();
        try {
            while (messages.size() == maxMessageCount) {
                System.out.println("Producer " + Thread.currentThread().getName() + " await because buffer is full");
                condition.await();
            }
            int valToAdd = random.nextInt(1000, 5000);
            messages.add(String.valueOf(valToAdd));
            Thread.sleep(SLEEP_TIME);
            System.out.println("Producer " + Thread.currentThread().getName()
                    + " added message:" + valToAdd + " Size: " + messages.size());
            condition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void getMessageFromBuffer() {
        lock.lock();
        try {
            while (messages.isEmpty()) {
                System.out.println("Consumer " + Thread.currentThread().getName() + " await because buffer is empty");
                condition.await();
            }
            Thread.sleep(SLEEP_TIME);
            System.out.println("Consumer " + Thread.currentThread().getName()
                    + " get message: " + messages.removeLast() + " Size: " + messages.size());
            condition.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

}
