package ru.khalov.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.khalov.library.dao.ClientDAO;
import ru.khalov.library.model.Client;

@Component
public class ClientValidator implements Validator {
    private final ClientDAO clientDAO;

    @Autowired
    ClientValidator(ClientDAO clientDAO){
        this.clientDAO = clientDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client client = (Client) target; //даункаст

        //посмотреть есть ли человек с таким же id в БД
        if(clientDAO.getClientByFio(client.getFio()).isPresent()){
            errors.rejectValue("fio", "",  "Человек с таким ФИО уже существует ");
        }
    }
}
