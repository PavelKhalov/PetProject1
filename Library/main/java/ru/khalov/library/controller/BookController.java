package ru.khalov.library.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.khalov.library.model.Book;
import ru.khalov.library.model.Client;
import ru.khalov.library.services.BookServices;
import ru.khalov.library.services.ClientServices;
import ru.khalov.library.util.BookValidator;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookServices bookServices;
    private final ClientServices clientServices;
    private final BookValidator bookValidator;

    @Autowired
    BookController (BookServices bookServices, ClientServices clientServices, BookValidator bookValidator){
        this.bookServices = bookServices;
        this.clientServices = clientServices;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String getAllBook(Model model){
        model.addAttribute("books", bookServices.findAll());
        return "book/getAllBooks";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable ("id") int id, Model model){
        model.addAttribute("book", bookServices.findOne(id));

        Client bookOwner = bookServices.getBookOwner(id);

        if(bookOwner != null){
            model.addAttribute("owner", bookOwner);
        } else {
            model.addAttribute("clients", clientServices.findAll());
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

        bookServices.add(book);
        return "redirect:/book";
    }

    /// Назначает книгу ///
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("client") Client selectedClient){
        bookServices.assignBook(id, selectedClient);
        return "redirect:/book/" + id;
    }

    /// Освобождает книгу ///
    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        bookServices.releaseBook(id);
        return "redirect:/book/" + id;
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookServices.findOne(id));
        return "book/editBook";
    }

    @PatchMapping("/{id}")
    public String editBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                           @PathVariable("id") int id){

        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()) return "book/editBook";

        bookServices.update(id, book);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookServices.delete(id);
        return "redirect:/book";
    }
}
