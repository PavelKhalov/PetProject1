package ru.khalov.library.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.khalov.library.dao.BookDAO;
import ru.khalov.library.dao.ClientDAO;
import ru.khalov.library.model.Book;
import ru.khalov.library.model.Client;

import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookDAO bookDAO;
    private final ClientDAO clientDAO;

    @Autowired
    BookController (BookDAO bookDAO, ClientDAO clientDAO){
        this.bookDAO = bookDAO;
        this.clientDAO = clientDAO;
    }

    @GetMapping()
    public String getAllBook(Model model){
        model.addAttribute("books", bookDAO.getAllBook());
        return "book/getAllBooks";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable ("id") int id, Model model){
        model.addAttribute("book", bookDAO.getBookById(id));

        Optional<Client> bookOwner = bookDAO.getBookOwner(id);

        if(bookOwner.isPresent()){
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("clients", clientDAO.getAllClient());
        }

        return "book/getBookById";
    }

    @GetMapping("/new")
    public String createNewBook(Model model){
        model.addAttribute("book", new Book());
        return "book/addNewBook";
    }

    @PostMapping()
    public String addNewBook(@ModelAttribute ("book") @Valid Book book,
                             BindingResult bindingResult){

        if(bindingResult.hasErrors())
            return "book/addNewBook";

        bookDAO.add(book);
        return "redirect:/book";
    }

    /// Назначает книгу ///
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("client") Client selectedClient){
        bookDAO.assignBook(id, selectedClient);
        return "redirect:/book/" + id;
    }

    /// Освобождает книгу ///
    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        bookDAO.releaseBook(id);
        return "redirect:/book/" + id;
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDAO.getBookById(id));
        return "book/editBook";
    }

    @PatchMapping("/{id}")
    public String editBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){

        if(bindingResult.hasErrors()) return "book/editBook";

        bookDAO.edit(id, book);
        return "redirect:/book";
    }


    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/book";
    }
}
