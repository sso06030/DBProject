package com.example.bookmanage;

public class Statistic {
    String bookAuthor;
    String bookTitle;
    String rentDate;
    String returnDate;
    int rent_num;

    public void setBookAuthor(String book_author){this.bookAuthor = book_author;}
    public void setBookTitle(String bookTitle){this.bookTitle = bookTitle;}
    public void setRentDate(String rentDate){this.rentDate = rentDate;}
    public void setReturnDate(String returnDate){this.returnDate=  returnDate;}
    public void setRent_num(int rent_num){this.rent_num = rent_num;}

    public String getBookAuthor(){return bookAuthor;}
    public String getBookTitle(){return bookTitle;}
    public String getRentDate(){return rentDate;}
    public String  getReturnDate(){return returnDate;}
    public int getRent_num(){return rent_num;}

    public Statistic(String bookAuthor,String bookTitle,String rentDate,String returnDate,int rent_num){
        this.bookAuthor = bookAuthor;
        this.bookTitle = bookTitle;
        this.returnDate = returnDate;
        this.rentDate = rentDate;
        this.rent_num = rent_num;
    }

}

