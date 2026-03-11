package models;


import lombok.RequiredArgsConstructor;

import java.util.Random;


@RequiredArgsConstructor
public class Consumer extends Thread {

    private final Buffer buffer;

    private void getMessages() {
        while (true) {
            buffer.getMessageFromBuffer();
            try {
                Thread.sleep(new Random().nextInt(50, 200));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        getMessages();
    }
}
