package ru.khalov.library.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.khalov.library.dao.ClientDAO;
import ru.khalov.library.model.Client;
import ru.khalov.library.util.ClientValidator;


@Controller
@RequestMapping("/client")
public class ClientController {
    private final ClientDAO clientDAO;
    private final ClientValidator clientValidator;

    @Autowired
    ClientController (ClientDAO clientDAO, ClientValidator clientValidator){
        this.clientDAO= clientDAO;
        this.clientValidator = clientValidator;
    }

    @GetMapping
    public String getAllClient(Model model) {
        model.addAttribute("clients", clientDAO.getAllClient());
        return "client/getAllClient";
    }

    @GetMapping("/{id}")
    public String getClientById(@PathVariable("id") int id, Model model){
        model.addAttribute("client", clientDAO.getClientById(id));
        model.addAttribute("books", clientDAO.getAllBooksClient(id));
        return "client/getClientById";
    }

    @GetMapping("/new")
    public String createNewClient(Model model){
        model.addAttribute("client", new Client());
        return "client/addNewClient";
    }

    @PostMapping()
    public String addNewClient(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult){
        clientValidator.validate(client, bindingResult);

        if(bindingResult.hasErrors())
            return "client/addNewClient";

        clientDAO.add(client);
        return "redirect:/client";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model){
        model.addAttribute("client", clientDAO.getClientById(id));
        return "client/editClient";

    }

    @PatchMapping("/{id}")
    public String editClient(@ModelAttribute("client")@Valid Client client, BindingResult bindingResult,
                             @PathVariable ("id") int id){

        clientValidator.validate(client, bindingResult);
        if(bindingResult.hasErrors()) return "client/editClient";

        clientDAO.edit(id ,client);
        return "redirect:/client";
    }


    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable("id") int id){
        clientDAO.delete(id);
        return "redirect:/client";
    }
}
