package ru.khalov.library.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Book {
    private int id;

    @NotEmpty(message = "Title should be not empty")
    private String title;

    @NotEmpty(message = "Author should be not empty")
    private String author;

    @Min(value = 1500)
    @Max(value = 2025, message = "Книга не может выйти в этом году")
    private int year;


    public Book(){}

    public Book(String title, String author, int year){
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
}