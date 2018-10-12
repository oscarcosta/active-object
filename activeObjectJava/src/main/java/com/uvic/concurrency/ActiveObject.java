package com.uvic.concurrency;

import java.util.concurrent.*;

public class ActiveObject<Message> implements Proxy<Message>{

    private final BlockingQueue<Future<Message>> dispatchQueue = new LinkedBlockingQueue<>();

    // Scheduler dequeues method request at some point and runs it on the servant in a separate thread
    private final ExecutorService scheduler = Executors.newScheduledThreadPool(5);

    private Callable<Message> createMessageRequest(String message) {
        return () -> {
            Servant servant = new Servant();
            return (Message) servant.processMessage(message);
        };
    }

    private void enqueue(Callable<Message> messageRequest) throws InterruptedException{
        dispatchQueue.put(scheduler.submit(messageRequest));
    }

    @Override
    public Future<Message> processMessage(final String message) {
        try {
            // com.uvic.concurrency.Proxy converts method call into method request
            Callable<Message> messageRequest = createMessageRequest(message);

            // com.uvic.concurrency.Proxy passes to scheduler and enqueues method request
            enqueue(messageRequest);

            // com.uvic.concurrency.Proxy returns future to client
            return dispatchQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException("Active Object error: ", e);
        }
    }
}