package pl.gm.bankapi.client.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.gm.bankapi.client.dto.ClientDto;
import pl.gm.bankapi.client.model.Client;
import pl.gm.bankapi.client.repository.ClientRepository;

@Service
public class ClientService {

    private final   ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    public ClientService(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    public void update(Client client) {
        clientRepository.save(client);
    }

    public ClientDto getById(long id) {
        return modelMapper.map(clientRepository.findById(id).get(),ClientDto.class);
    }

    public void delete(long id) {
        clientRepository.deleteById(id);
    }
}
