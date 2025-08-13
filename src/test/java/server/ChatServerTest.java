package server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static common.ChatConstants.DEFAULT_PORT;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChatServerTest {
    private ChatServer server;
    private Socket mockSocket;
    private PrintWriter mockWriter;
    private BufferedReader mockReader;

    @BeforeEach
    void setUp() throws IOException {
        server = new ChatServer(DEFAULT_PORT);
        mockSocket = mock(Socket.class);
        mockWriter = mock(PrintWriter.class);
        mockReader = mock(BufferedReader.class);

        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
    }

    @Test
    void addClientTest() {
        ClientHandler client = new ClientHandler(mockSocket, server);
        server.getClients().add(client);
        Assertions.assertEquals(1, server.getClients().size());
    }

    @Test
    void removeClientTest() {
        ClientHandler client = new ClientHandler(mockSocket, server);
        server.getClients().add(client);
        server.removeClient(client);
        Assertions.assertTrue(server.getClients().isEmpty());
    }
}
