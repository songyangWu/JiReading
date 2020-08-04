package com.wsy.jireading.model;

/**
 * Created by songYangWu
 * Date:2020/8/2
 */
public class Book {
    private String BookName;
    private String author;
    private int BookType;
    private BookImage bookImage;
    private BookText bookText;

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBookType() {
        return BookType;
    }

    public void setBookType(int bookType) {
        BookType = bookType;
    }

    public BookImage getBookImage() {
        return bookImage;
    }

    public void setBookImage(BookImage bookImage) {
        this.bookImage = bookImage;
    }

    public BookText getBookText() {
        return bookText;
    }

    public void setBookText(BookText bookText) {
        this.bookText = bookText;
    }
}
