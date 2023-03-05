package ru.itsjava.services;

import lombok.SneakyThrows;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientServiceImpl implements ClientService {
    public final static int PORT = 8081;
    public final static String HOST = "localhost";

    @SneakyThrows
    @Override
    public void start() {
        Socket socket = new Socket(HOST, PORT);
        if (socket.isConnected()) {
            new Thread(new SocketRunnable(socket)).start();

            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream());
            MessageInputService messageInputService =
                    new MessageInputServiceImpl(System.in);
            Scanner scanner = new Scanner(System.in);
            System.out.println("1) Авторизиция ");
            System.out.println("2) Регистрация ");
            int menu = scanner.nextInt();
            if (menu == 1) {
                System.out.println("Введите свой логин");
                String login = messageInputService.getMessage();

                System.out.println("Введите свой пароль");
                String password = messageInputService.getMessage();

                serverWriter.println("!autho!" + login + ":" + password);
                serverWriter.flush();

            } else if (menu == 2) {
                System.out.println("Введите свой логин");
                String login = messageInputService.getMessage();

                System.out.println("Введите свой пароль");
                String password = messageInputService.getMessage();

                serverWriter.println("!regis!" + login + ":" + password);
                serverWriter.flush();
            }
            while (true) {
                String consoleMessage = messageInputService.getMessage();
                if (!consoleMessage.equals("Exit")) {
                    serverWriter.println(consoleMessage);
                    serverWriter.flush();
                } else System.exit(0);
            }
        }
    }
}

