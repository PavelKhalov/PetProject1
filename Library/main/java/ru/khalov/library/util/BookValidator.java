package ru.khalov.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.khalov.library.model.Book;
import ru.khalov.library.services.BookServices;


@Component
public class BookValidator implements Validator {
    private final BookServices bookServices;

    @Autowired
    BookValidator(BookServices bookServices){
        this.bookServices = bookServices;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target; //даункаст

        //посмотреть есть ли человек с таким же id в БД
        if(bookServices.getBookByTitle(book.getTitle()) != null){
            errors.rejectValue("title", "",  "Книга с таким названием уже существует ");
        }
    }
}
