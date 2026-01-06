package ru.khalov.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.khalov.library.model.Client;
import ru.khalov.library.services.ClientServices;

@Component
public class ClientValidator implements Validator {
    private final ClientServices clientServices;

    @Autowired
    ClientValidator(ClientServices clientServices){
        this.clientServices =  clientServices;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client client = (Client) target; //даункаст

        //посмотреть есть ли человек с таким же id в БД
        if(clientServices.getClientByFio(client.getFio()) != null){
            errors.rejectValue("fio", "",  "Человек с таким ФИО уже существует ");
        }
    }
}
