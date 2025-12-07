package ru.khalov.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.khalov.library.model.Book;
import ru.khalov.library.model.Client;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    BookDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBook(){
        return jdbcTemplate.query("SELECT * FROM book", new BookMapper());
    }

    public Book getBookById(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?",
                        new BookMapper(), id)
                .stream().findFirst().orElse(null);
    }

    public void add(Book book){
        jdbcTemplate.update("INSERT INTO book (title, author, year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void edit(int id, Book bookEdit){
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE book_id=?",
                bookEdit.getTitle(), bookEdit.getAuthor(), bookEdit.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", id);
    }

    public Optional<Client> getBookOwner(int id){
        return jdbcTemplate.query("SELECT Client.* FROM Book JOIN Client ON Book.client_id = Client.client_id WHERE Book.book_id=?",
                new Object[]{id}, new ClientMapper()).stream().findAny();
    }

    public void assignBook(int bookId, Client client){
        jdbcTemplate.update("UPDATE book SET client_id=? WHERE book_id=?", client.getId(), bookId);
    }

    public void releaseBook(int bookId){
        jdbcTemplate.update("UPDATE book SET client_id=NULL WHERE book_id=?", bookId);
    }
}

