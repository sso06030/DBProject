package com.example.bookmanage;

public class Book {
    String book_id;
    String ISBN;
    String title;
    String author;
    String genre;
    String publisher;
    int price;

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getPrice() {
        return price;
    }

    public Book(String book_id, String ISBN, String title, String author, String genre, String publisher, int price) {
        this.book_id = book_id;
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.price = price;
    }
}
