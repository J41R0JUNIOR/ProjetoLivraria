package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;

public class BookHandler {

    private static final String BOOKS_FILE_PATH = "livros.json";
    private static final String RENT_BOOKS_FILE_PATH = "alugueis.json";

    public static ArrayList<Book> searchBooks() {
        return loadBooksFromFile();
    }

    public static void sendBooks(BufferedWriter bufferedWriter, ArrayList<Book> books) {
        try {
            for (Book book : books) {
                bufferedWriter.write(book.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean rentBook(String title, String clientName) {
        ArrayList<Book> books = loadBooksFromFile();
        ArrayList<ClientInDebt> clientsInDebt = loadCostumersInDebtFromFile();

        boolean isClientInDebt = clientInDebt(clientsInDebt, clientName);

        if (!isClientInDebt) {
            for (Book book : books) {
                if (book.getTittle().equalsIgnoreCase(title) && book.getCopies() > 0) {
                    book.setCopies(book.getCopies() - 1);
                    saveBooksToFile(books);
                    addRentRecord(clientName, title);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean putBookBack(String titulo, String clientName) {
        ArrayList<Book> books = loadBooksFromFile();
        ArrayList<ClientInDebt> clientsInDebt = loadCostumersInDebtFromFile();
        boolean isClientInDebt = clientInDebt(clientsInDebt, clientName);
        String bookName = bookRented(clientsInDebt, clientName);

        if (isClientInDebt && titulo.equals(bookName)) {
            for (Book book : books) {
                if (book.getTittle().equalsIgnoreCase(titulo)) {
                    book.setCopies(book.getCopies() + 1);
                    saveBooksToFile(books);
                    removeRentRecord(clientName, clientsInDebt);
                    return true;
                }
            }
        }
        return false;
    }

    private static String bookRented(ArrayList<ClientInDebt> clients, String clientName){
        String bookName = null;
        for (ClientInDebt clientInDebt : clients) {
            if (clientInDebt.getClient().equalsIgnoreCase(clientName)) {
                bookName = clientInDebt.book;
            }
        }
        return bookName;
    }

    private static void addRentRecord(String clientName, String bookTitle) {
        ArrayList<ClientInDebt> rentRecords = loadRentRecordsFromFile();
        rentRecords.add(new ClientInDebt(clientName, bookTitle));
        saveRentRecordsToFile(rentRecords);
    }

    private static void removeRentRecord(String clientName, ArrayList<ClientInDebt> clients) {


        clients.removeIf(clientInDebt -> clientInDebt.getClient().equals(clientName));

        saveRentRecordsToFile(clients);
    }

    public static boolean clientInDebt(ArrayList<ClientInDebt> clients, String clientName) {
        for (ClientInDebt clientInDebt : clients) {
            if (clientInDebt.getClient().equalsIgnoreCase(clientName)) {
                return true;
            }
        }
        return false;
    }

    private static ArrayList<Book> loadBooksFromFile() {
        ArrayList<Book> books = new ArrayList<>();
        try {
            File file = new File(BOOKS_FILE_PATH);

            if (!file.exists()) {
                System.out.println("File livros.json not found at the path: " + file.getAbsolutePath());
                return books;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder jsonContent = readFromReader(reader);

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonContent.toString(), JsonObject.class);

            books = gson.fromJson(jsonObject.get("books"), new TypeToken<ArrayList<Book>>() {}.getType());
        } catch (Exception e) {
            System.out.println(e);
        }
        return books;
    }

    private static StringBuilder readFromReader(BufferedReader reader) throws IOException {
        StringBuilder jsonContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonContent.append(line);
        }
        reader.close();
        return jsonContent;
    }

    private static ArrayList<ClientInDebt> loadCostumersInDebtFromFile() {
        return getClientInDebts();
    }

    private static ArrayList<ClientInDebt> getClientInDebts() {
        ArrayList<ClientInDebt> clientsInDebt = new ArrayList<>();
        try {
            File file = new File(RENT_BOOKS_FILE_PATH);

            if (!file.exists()) {
                System.out.println("File alugueis.json not found at the path: " + file.getAbsolutePath());
                return clientsInDebt;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder jsonContent = readFromReader(reader);

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonContent.toString(), JsonObject.class);

            clientsInDebt = gson.fromJson(jsonObject.get("rent"), new TypeToken<ArrayList<ClientInDebt>>() {}.getType());
        } catch (Exception e) {
            System.out.println(e);
        }
        return clientsInDebt;
    }

    private static ArrayList<ClientInDebt> loadRentRecordsFromFile() {
        return getClientInDebts();
    }

    private static void saveRentRecordsToFile(ArrayList<ClientInDebt> rentRecords) {
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("rent", gson.toJsonTree(rentRecords));

            BufferedWriter writer = new BufferedWriter(new FileWriter(RENT_BOOKS_FILE_PATH));
            writer.write(gson.toJson(jsonObject));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveRegistedBookToFile(ArrayList<Book> books){
        arrayOfBooks(books);
    }

    public static boolean registeBook(Book book){
        ArrayList<Book> books = loadBooksFromFile();

        for(Book searchedBook: books){
            if(searchedBook.equals(book)){
                return false;
            }
        }

        books.add(book);
        saveRegistedBookToFile(books);
        return true;
    }



    private static void saveBooksToFile(ArrayList<Book> books) {
        arrayOfBooks(books);
    }

    private static void arrayOfBooks(ArrayList<Book> books) {
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("books", gson.toJsonTree(books));

            BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE_PATH));
            writer.write(gson.toJson(jsonObject));
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
