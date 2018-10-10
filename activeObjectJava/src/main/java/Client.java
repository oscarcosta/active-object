import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Client {

    public static void main(String[] args) {
        Proxy<Message> proxy = new ActiveObject<>();

        for (int i = 0; i < 10; i++) {
            final int id = i;
            final String message = "Message #" + i;
            new Thread(() -> {
                try {
                    System.out.println("Client " + id + " sent: " + message);

                    Future<Message> future = proxy.processMessage(message);

                    System.out.println("Client " + id + " received: " + future.get());
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException("Client error: ", e);
                }
            }).start();
        }
    }
}
