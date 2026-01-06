package ru.khalov.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khalov.library.model.Book;
import ru.khalov.library.model.Client;
import ru.khalov.library.repos.BookRepos;
import ru.khalov.library.repos.ClientRepos;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookServices {
    private final BookRepos bookRepos;

    @Autowired
    BookServices(BookRepos bookRepos, ClientRepos clientRepos){
        this.bookRepos = bookRepos;
    }

    public List<Book> findAll(){
        return bookRepos.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> book = bookRepos.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void add (Book book){
        bookRepos.save(book);
    }

    @Transactional
    public void update(int id, Book bookUpdate){
        bookUpdate.setId(id);
        bookRepos.save(bookUpdate);
    }

    @Transactional
    public void delete(int id) {
        bookRepos.deleteById(id);
    }

    public Client getBookOwner(int id){
        Optional<Book> book = bookRepos.findById(id);
        return book.get().getOwner();
    }

    public Book getBookByTitle(String title){
        return bookRepos.getBookByTitle(title);
    }

    @Transactional
    public void assignBook (int bookId, Client client){
        Book book = bookRepos.findById(bookId).get();
        book.setOwner(client);
        bookRepos.save(book);
    }

    @Transactional
    public void releaseBook (int id){
        Book book = bookRepos.findById(id).get();
        book.setOwner(null);
    }
}
