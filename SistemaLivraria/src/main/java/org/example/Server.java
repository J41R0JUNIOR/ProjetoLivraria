package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;

    // Criação do Socket
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    // Loop para ficar recebendo clientes e aceitar conexoes
    public void startServer() {
        System.out.println("Server up on port: " + this.serverSocket.getLocalPort());
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new client has come");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // metodo para fechar o servidor
    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws IOException {
        // Cria um Server com porta 1111
        ServerSocket serverSocket = new ServerSocket(1111);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}