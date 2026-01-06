package ru.khalov.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "title")
    @NotEmpty(message = "Title should be not empty")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Author should be not empty")
    private String author;

    @Column(name = "year")
    @Min(value = 1500)
    @Max(value = 2025, message = "Книга не может выйти в этом году")
    private int year;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private Client owner;

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

    public Client getOwner() {return owner;}
    public void setOwner(Client owner) {this.owner = owner;}
}