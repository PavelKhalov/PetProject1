package ru.khalov.library.model;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class Client {
    private int id;

    @NotEmpty(message = "FIO should not be empty")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$", message = "Valid field is: Name Surname Patronymic")
    private String fio;

    @Min(value = 1930, message = "Year of born should be greatest than 1930")
    @Max(value = 2025, message = "Year of born should be lover than 2026")
    private int yearOfBorn;

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
