package client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static common.ChatConstants.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChatClientTest {

    private Socket mockSocket;
    private ChatClient client;
    private PrintWriter mockWriter;
    private BufferedReader mockReader;

    @BeforeEach
    void setUp() throws IOException {
        mockSocket = mock(Socket.class);
        mockWriter = mock(PrintWriter.class);
        mockReader = mock(BufferedReader.class);

        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        client = new ChatClient(DEFAULT_HOST, DEFAULT_PORT, TEST_NAME);
    }

    @Test
    void logToFileTest() {
        client.logToFile(TEST_MESSAGE);

        File logFile = new File(LOG_FILE);
        Assertions.assertTrue(logFile.exists());
    }
}
