/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class ClientTest {
    @Test public void testAppHasAGreeting() {
        Client classUnderTest = new Client();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}