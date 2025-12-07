package ru.khalov.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.khalov.library.model.Book;
import ru.khalov.library.model.Client;

import java.util.List;
import java.util.Optional;

@Component
public class ClientDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    ClientDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Client> getAllClient(){
        return jdbcTemplate.query("SELECT * FROM Client", new ClientMapper());
    }

    public Client getClientById(int id){
        return jdbcTemplate.query("SELECT * FROM Client WHERE client_id=?", new Object[]{id},
                new ClientMapper()).stream().findFirst().orElse(null);
    }

    public Optional<Client> getClientByFio(String fio){
        return jdbcTemplate.query("SELECT * FROM Client WHERE fio=?", new Object[]{fio},
                new ClientMapper()).stream().findAny();
    }

    public void add (Client client){
        jdbcTemplate.update("INSERT INTO Client (fio, yearOfBorn) VALUES (?, ?)",
                client.getFio(), client.getYearOfBorn());
    }

    public void edit(int id, Client client){
        jdbcTemplate.update("UPDATE Client SET fio=?, yearOfBorn=? WHERE client_id=?",
                client.getFio(), client.getYearOfBorn(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Client WHERE client_id=?", id);
    }

    public List<Book> getAllBooksClient(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE client_id=?", new Object[]{id},
                new BookMapper());
    }


}
