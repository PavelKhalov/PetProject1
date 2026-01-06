package ru.khalov.library.services;

import org.jspecify.annotations.Nullable;
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
public class ClientServices {
    private final ClientRepos clientRepos;
    private final BookRepos bookRepos;

    @Autowired
    ClientServices (ClientRepos clientRepos, BookRepos bookRepos) {
        this.clientRepos = clientRepos;
        this.bookRepos = bookRepos;
    }

    public List<Client> findAll(){
        return clientRepos.findAll();
    }

    public Client findOne (int id) {
        Optional<Client> client = clientRepos.findById(id);
        return client.orElse(null);
    }

    public Client getClientByFio (String fio) {
        return clientRepos.findClientByFio(fio);
    }

    @Transactional
    public void add(Client client) {
        clientRepos.save(client);
    }

    @Transactional
    public void update(int id, Client clientUpdate){
        clientUpdate.setId(id);
        clientRepos.save(clientUpdate);
    }

    @Transactional
    public void delete(int id){
        clientRepos.deleteById(id);
    }

    public List<Book> getAllBooksClient (int id) {
        return bookRepos.getAllByOwnerId(id);
    }


}
