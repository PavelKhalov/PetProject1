package ru.khalov.library.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@Table(name = "Client")
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "FIO should not be empty")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$", message = "Valid field is: Name Surname Patronymic")
    @Column (name = "fio")
    private String fio;

    @Min(value = 1930, message = "Year of born should be greatest than 1930")
    @Max(value = 2025, message = "Year of born should be lover than 2026")
    @Column(name = "yearOfBorn")
    private int yearOfBorn;

    @OneToMany (mappedBy = "owner", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Book> books;

    public Client(){}

    public Client(String fio, int yearOfBorn){
        this.fio = fio;
        this.yearOfBorn = yearOfBorn;
    }

    public int getId(){ return id;}
    public void setId(int id) { this.id = id;   }

    public String getFio() {return fio;}
    public void setFio(String fio) {this.fio = fio;}

    public int getYearOfBorn() {return yearOfBorn;}
    public void setYearOfBorn(int yearOfBorn) {this.yearOfBorn = yearOfBorn;}
}
