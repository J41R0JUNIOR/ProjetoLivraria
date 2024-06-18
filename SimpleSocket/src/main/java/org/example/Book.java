package org.example;

public class Book {
    private String tittle;
    private String author;
    private String genre;
    private int copies;

//    public void Book(){
//    }

    public Book(String tittle, String author, String genre, int copies) {
        this.tittle = tittle;
        this.author = author;
        this.genre = genre;
        this.copies = copies;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }
// Getters e Setters

    @Override
    public String toString() {
        return "Title: " + tittle + ", Author: " + author + ", Genre: " + genre + ", Copies: " + copies;
    }
}