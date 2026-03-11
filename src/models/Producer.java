package models;


import lombok.RequiredArgsConstructor;
import java.util.Random;


@RequiredArgsConstructor
public class Producer extends Thread {

    private final int maxMessages;

    private final Buffer buffer;

    private void sendMessage() {
        while (true) {
            buffer.addMessageToBuffer(maxMessages);
            try {
                Thread.sleep(new Random().nextInt(50, 200));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        sendMessage();
    }

}
