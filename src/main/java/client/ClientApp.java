package client;

import java.io.File;
import java.util.Scanner;

import static common.ChatConstants.*;

public class ClientApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(ENTER_NAME);
        String username = scanner.nextLine();

        int port = readPortFromSettings();
        ChatClient client = new ChatClient(DEFAULT_HOST, port, username);
        client.start();
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
