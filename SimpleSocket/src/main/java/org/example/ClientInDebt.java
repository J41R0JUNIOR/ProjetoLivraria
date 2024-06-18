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

    public void setClient(String client) {
        this.client = client;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }
}
