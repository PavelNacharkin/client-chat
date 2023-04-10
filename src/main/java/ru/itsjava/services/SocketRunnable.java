package ru.itsjava.services;

import lombok.Getter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.net.Socket;

@Getter
@RequiredArgsConstructor
public class SocketRunnable implements Runnable {
    private final Socket socket;
    private final ClientService clientService;

    @SneakyThrows
    @Override
    public void run() {
        MessageInputService serverReader = new MessageInputServiceImpl(socket.getInputStream());
        while (true) {
            String inputFromServer = serverReader.getMessage();
            System.out.println(inputFromServer);
            if (inputFromServer.equals("Пользователь не найден") || inputFromServer.equals("Кажется такой пользователь уже есть, попробуйте авторизироваться")) {
                clientService.authorizationOrRegistration();
            } else clientService.putMessage();
        }
    }
}
