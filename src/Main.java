import models.Buffer;
import models.Consumer;
import models.Producer;
import java.util.*;


public class Main {

    private static final int MAX_MESSAGES_COUNT = 10;
    private static final int PRODUCERS_COUNT = 10;
    private static final int CONSUMERS_COUNT = 5;
    private static final Buffer buffer = new Buffer();

    public static void main(String[] args) {

        Set<Thread> threadSet = new HashSet<>();

        for (int i = 0; i < PRODUCERS_COUNT; i++) {
            Thread thread = new Producer(MAX_MESSAGES_COUNT, buffer);
            threadSet.add(thread);
        }

        for (int i = 0; i < CONSUMERS_COUNT; i++) {
            Thread thread = new Consumer(buffer);
            threadSet.add(thread);
        }

        for (Thread t: threadSet) {
            t.start();
        }
        
    }
}