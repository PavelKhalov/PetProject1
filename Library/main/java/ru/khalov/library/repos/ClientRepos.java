package ru.khalov.library.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khalov.library.model.Client;

@Repository
public interface ClientRepos extends JpaRepository<Client, Integer> {
    Client findClientByFio(String fio);
}
