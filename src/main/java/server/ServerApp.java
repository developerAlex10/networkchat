package server;

import java.io.File;
import java.util.Scanner;

import static common.ChatConstants.PORT_MESSAGE;
import static common.ChatConstants.SETTINGS_FILE;

public class ServerApp {
    public static void main(String[] args) {
        int port = readPortFromSettings();
        ChatServer server = new ChatServer(port);
        server.start();
    }

    private static int readPortFromSettings() {
        try (Scanner scanner = new Scanner(new File(SETTINGS_FILE))) {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            System.err.println(PORT_MESSAGE);
            return 8080;
        }
    }
}
