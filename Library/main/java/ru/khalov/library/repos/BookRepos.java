package ru.khalov.library.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.khalov.library.model.Book;
import ru.khalov.library.model.Client;

import java.util.List;

@Repository
public interface BookRepos extends JpaRepository<Book, Integer> {
    Book getBookByTitle(String title);

    List<Book> getAllByOwner(Client owner);

    List<Book> getAllByOwnerId(int ownerId);
}
