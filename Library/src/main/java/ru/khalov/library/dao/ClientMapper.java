package ru.khalov.library.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.khalov.library.model.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements RowMapper<Client> {

    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        Client client = new Client();
        client.setId(rs.getInt("client_id"));
        client.setFio(rs.getString("fio"));
        client.setYearOfBorn(rs.getInt("yearOfBorn"));
        return client;
    }
}
