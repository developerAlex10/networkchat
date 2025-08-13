package server;

import client.ChatClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static common.ChatConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerClientIntegrationTest {
    private ChatServer server;
    private ExecutorService executor;

    @BeforeEach
    void setUp() {
        server = new ChatServer(DEFAULT_PORT);
        executor = Executors.newFixedThreadPool(COUNT_THREAD_POOL);
    }

    @Test
    void serverClientsTest() throws InterruptedException {
        executor.submit(() -> server.start());
        Thread.sleep(3000);

        String[] clientNames = {USER_MASHA, USER_GENA, USER_ALEX};

        for (String clientName : clientNames) {
            startClient(clientName);
        }

        Thread.sleep(2000);
        assertEquals(clientNames.length, server.getClients().size());
    }

    private void startClient(String clientName) {
        executor.submit(() -> {
            ChatClient client = new ChatClient(DEFAULT_HOST, DEFAULT_PORT, clientName);
            client.start();
        });
    }

    @AfterEach
    void tearDown() {
        executor.shutdownNow();
    }
}
