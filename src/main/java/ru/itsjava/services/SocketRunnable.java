package ru.itsjava.services;

import lombok.Getter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.net.Socket;

@Getter
@RequiredArgsConstructor
public class SocketRunnable implements Runnable {
    private final Socket socket;

    @SneakyThrows
    @Override
    public void run() {
        MessageInputService serverReader =
                new MessageInputServiceImpl(socket.getInputStream());
        while (true) {
            System.out.println(serverReader.getMessage());
        }
    }
}
