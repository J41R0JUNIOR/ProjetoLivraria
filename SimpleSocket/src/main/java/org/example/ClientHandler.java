package org.example;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    private String clientState;


    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.clientState = String.valueOf(ClientState.NORMAL.getDescricao());

            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            System.out.println("New client called: " + clientUsername);
            clientHandlers.add(this);
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String message;
        String listBooks = String.valueOf(ClientOptions.LIST_BOOKS.getDescription());
        String rentBook = String.valueOf(ClientOptions.RENT_BOOKS.getDescription());
        String returnBook = String.valueOf(ClientOptions.RETURN_BOOKS.getDescription());
        String registerBook = String.valueOf(ClientOptions.REGISTER_BOOKS.getDescription());
        String out = String.valueOf(ClientOptions.OUT.getDescription());

        while (socket.isConnected()) {
            try {
                if (clientState.equals(ClientState.NORMAL.getDescricao())) {
                    showOptionsToClient();

                } else if (clientState.equals(ClientState.ALUGANDO_LIVRO.getDescricao())) {
                    message = bufferedReader.readLine();
                    if (message != null) {
                        boolean success = BookHandler.rentBook(message, clientUsername);
                        message = success ? clientUsername + " rented a book: " + message : "Failed to rent book: " + message;
                        broadcastMessage(message);
                        clientState = ClientState.NORMAL.getDescricao();
                        continue;

                    }
                } else if (clientState.equals(ClientState.DEVOLVENDO_LIVRO.getDescricao())) {
                    // Implementar lógica para devolver livro
                    message = bufferedReader.readLine();
                    if(message != null) {
                        boolean success = BookHandler.putBookBack(message, clientUsername);
                        message = success ? clientUsername + " returned a book: " + message : "Failed to return the book: " + message;
                        broadcastMessage(message);
                        clientState = ClientState.NORMAL.getDescricao();
                        continue;

                    }
                } else if (clientState.equals(ClientState.CADASTRANDO_LIVRO.getDescricao())) {
                    // Implementar lógica para cadastrar livro
                    message = bufferedReader.readLine();
                    if(message != null){
                        String[] messageSplitted = message.split(",");
                        String tittle = messageSplitted[0].trim();
                        String author = messageSplitted[1].trim();
                        String genre = messageSplitted[2].trim();
                        int copies = Integer.parseInt(messageSplitted[3].trim());

                        Book book = new Book(tittle, author, genre, copies);
                        boolean success = BookHandler.registeBook(book);

                        message = success ? clientUsername + "registered the book " + message: "Failed to register the book" + message;
                        broadcastMessage(message);
                        clientState = ClientState.NORMAL.getDescricao();
                        continue;
                    }

                }


                message = bufferedReader.readLine();

                if (message != null) {
                    if (message.equalsIgnoreCase(listBooks) && Objects.equals(clientState, ClientState.NORMAL.getDescricao())) {
                        ArrayList<Book> livros = BookHandler.searchBooks();
                        BookHandler.sendBooks(bufferedWriter, livros);

                    } else if (message.startsWith(rentBook)) {
                        clientState = ClientState.ALUGANDO_LIVRO.getDescricao();
                        broadcastMessage("Type the name of the book");

                    } else if (message.startsWith(returnBook)) {
                        clientState = ClientState.DEVOLVENDO_LIVRO.getDescricao();
                        broadcastMessage("Type the name of the book");

                    } else if(message.startsWith(registerBook)){
                        clientState = ClientState.CADASTRANDO_LIVRO.getDescricao();
                        broadcastMessage("Type the info of the book like this(tittle, author, genre, copies)");


                    } else if (message.startsWith(out)) {
                        closeEverything(socket, bufferedReader, bufferedWriter);

                    } else {
                        broadcastMessage("Type a real option");
                    }
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }


    private void showOptionsToClient(){
        broadcastMessage("\n\nOptions\n1 - List books\n2 - Rent book\n3 - Return book\n4 - Register a book\n5 - Out");
    }

    public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler() {
        broadcastMessage("Leaving the library (please type enter)");
        clientHandlers.remove(this);
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null && socket.isConnected()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
