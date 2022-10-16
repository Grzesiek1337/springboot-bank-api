package pl.gm.bankapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.model.Client;
import pl.gm.bankapi.repositories.ClientRepository;

@Service
@AllArgsConstructor
public class ClientService {

    private ClientRepository clientRepository;

    public void save(Client client) {
        clientRepository.save(client);
    }

    public void update(Client client) {
        clientRepository.save(client);
    }

    public Client get(long id) {
        return clientRepository.findById(id).get();
    }

    public void delete(long id) {
        clientRepository.deleteById(id);
    }
}
