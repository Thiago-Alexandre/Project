package com.company.project.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.company.project.dto.ClientDTO;
import com.company.project.exceptions.DuplicateCpfException;
import com.company.project.exceptions.NotFoundException;
import com.company.project.exceptions.ServiceException;
import com.company.project.model.Client;
import com.company.project.repository.ClientRepository;

@Service
public class ClientServices {

	Logger log = LoggerFactory.getLogger(ClientServices.class);

	@Autowired
	private ClientRepository clientRepository;

	public ClientDTO save(ClientDTO clientDTO) throws DuplicateCpfException, ServiceException {

		if (this.verifyCPF(clientDTO.getCpf()) != null) {
			throw new DuplicateCpfException();
		}
		try {
			Client clientForSave = new Client(clientDTO);
			Client clientSaved = clientRepository.save(clientForSave);
			if (clientSaved == null) {
				throw new ServiceException("Error with data persistence!");
			}
			return new ClientDTO(clientSaved);
		} catch(Exception e) {
			log.error("ClientServices.save() Failed!", e);
			throw new ServiceException("Error with data persistence!");
		}
	}

	public ClientDTO update(String id, ClientDTO clientDTO) throws NotFoundException, ServiceException, DuplicateCpfException {

		try {
			Optional<Client> result = clientRepository.findById(id);
			if (result == null) {
				throw new NotFoundException();
			}
			Client clientForSave = new Client(clientDTO);
			clientForSave.setId(id);
			Client clientFind = this.verifyCPF(clientDTO.getCpf());
			if (clientFind != null && !clientFind.getId().equals(id)) {
				throw new DuplicateCpfException();
			}
			Client clientSaved = clientRepository.save(clientForSave);
			if (clientSaved == null) {
				return null;
			}
			return new ClientDTO(clientSaved);
		} catch(DuplicateCpfException de) {
			throw de;
		} catch(Exception e) {
			log.error("ClientServices.update(" + id + ") Failed!", e);
			throw new ServiceException("Error with data persistence!");
		}
	}

	public void delete(String id) throws NotFoundException, ServiceException {

		try {
			Optional<Client> result = clientRepository.findById(id);
			if (result == null) {
				throw new NotFoundException();
			}
			clientRepository.deleteById(id);
		} catch(Exception e) {
			log.error("ClientServices.delete() Failed!", e);
			throw new ServiceException("Error with data persistence!");
		}
	}

	public Map<String, Object> find(String id, Integer page, Integer size) throws ServiceException, NotFoundException {

		try {
			List<ClientDTO> clientsDTO = new ArrayList<ClientDTO>();
			Pageable paging = PageRequest.of(page, size);
			Map<String, Object> response = new HashMap<>();
			if (id == null) {
				Page<ClientDTO> pageClients = clientRepository.findAll(paging).map(obj -> new ClientDTO(obj));
				response.put("currentPage", pageClients.getNumber());
				response.put("totalItems", pageClients.getTotalElements());
				response.put("totalPages", pageClients.getTotalPages());
				clientsDTO = pageClients.getContent();
			} else {
				Optional<Client> result = clientRepository.findById(id);
				if (result == null || result.get() == null) {
					throw new NotFoundException();
				}
				ClientDTO clientDTO = new ClientDTO(result.get());
				response.put("currentPage", 0);
				response.put("totalItems", 1);
				response.put("totalPages", 1);
				clientsDTO.add(clientDTO);
				response.put("clients", clientsDTO);
			}
			response.put("clients", clientsDTO);
			return response;
		} catch(NotFoundException nfe) {
			throw nfe;
		} catch(Exception e) {
			log.error("ClientServices.find(" + id + ") Failed!", e);
			throw new ServiceException("Error with data persistence!");
		}
	}

	private Client verifyCPF(String cpf) throws ServiceException {

		try {
			List<Client> result = clientRepository.findAll();
			for (Iterator<Client> iterator = result.iterator(); iterator.hasNext();) {
				Client client = iterator.next();
				if (client.getCpf().equals(cpf)) {
					return client;
				}
			}
			return null;	
		} catch(Exception e) {
			throw new ServiceException("Error with data persistence!");
		}
	}
}