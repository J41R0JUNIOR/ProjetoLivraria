package org.example;

public class ClientInDebt {
    String client;
    String book;

    public ClientInDebt(String clientName, String bookTitle) {
        this.client = clientName;
        this.book = bookTitle;
    }

    public String getClient() {
        return client;
    }
}
