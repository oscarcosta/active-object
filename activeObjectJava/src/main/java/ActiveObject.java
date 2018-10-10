import java.util.concurrent.*;

public class ActiveObject<T> implements Proxy<T>{

    private final BlockingQueue<Future<T>> dispatchQueue = new LinkedBlockingQueue<>();

    // Scheduler dequeues method request at some point and runs it on the servant in a separate thread
    private final ExecutorService scheduler = Executors.newScheduledThreadPool(5);

    private Callable<T> createMessageRequest(String message) {
        return () -> {
            Servant servant = new Servant();
            return (T) servant.processMessage(message);
        };
    }

    private void enqueue(Callable<T> messageRequest) throws InterruptedException{
        dispatchQueue.put(scheduler.submit(messageRequest));
    }

    @Override
    public Future<T> processMessage(final String message) {
        try {
            // Proxy converts method call into method request
            Callable<T> messageRequest = createMessageRequest(message);

            // Proxy passes to scheduler and enqueues method request
            enqueue(messageRequest);

            // Proxy returns future to client
            return dispatchQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException("Active Object error: ", e);
        }
    }
}