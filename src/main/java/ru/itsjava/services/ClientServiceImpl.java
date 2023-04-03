package ru.itsjava.services;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@Log4j
public class ClientServiceImpl implements ClientService {
    public final static int PORT = 8081;
    public final static String HOST = "localhost";
    public MessageInputService messageInputService;
    public PrintWriter serverWriter;
    public Scanner scanner;

    @SneakyThrows
    @Override
    public void start() {
        Socket socket = new Socket(HOST, PORT);
        if (socket.isConnected()) {
            new Thread(new SocketRunnable(socket, this)).start();
            log.info("Client start");
            serverWriter = new PrintWriter(socket.getOutputStream());
            messageInputService = new MessageInputServiceImpl(System.in);
            scanner = new Scanner(System.in);
            authorizationOrRegistration();
        }
    }

    public void authorizationOrRegistration() {
        System.out.println("1) Авторизиция ");
        System.out.println("2) Регистрация ");

        int menu = scanner.nextInt();
        String prefix = null;
        if (menu == 1) {
            prefix = "!autho!";
            log.info("Authorization request");
        } else if (menu == 2) {
            prefix = "!regis!";
            log.info("Registration request");
        }
        System.out.println("Введите свой логин");
        String login = messageInputService.getMessage();

        System.out.println("Введите свой пароль");
        String password = messageInputService.getMessage();

        serverWriter.println(prefix + login + ":" + password);
        serverWriter.flush();
    }

    public void putMessage() {
        while (true) {
            String consoleMessage = messageInputService.getMessage();
            if (!consoleMessage.equals("Exit")) {
                if (consoleMessage.isEmpty()) {
                    System.out.println("Сообщение не должно быть пустым");
                } else {
                    serverWriter.println(consoleMessage.trim());
                    serverWriter.flush();
                }
            } else {
                serverWriter.println("Client disconnected");
                serverWriter.flush();
                log.info("Client disconnected");
                System.exit(0);
            }
        }

    }
}



