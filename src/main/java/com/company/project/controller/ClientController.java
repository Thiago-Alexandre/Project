package com.company.project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.project.dto.ClientDTO;
import com.company.project.exceptions.DuplicateCpfException;
import com.company.project.exceptions.NotFoundException;
import com.company.project.exceptions.ServiceException;
import com.company.project.services.ClientServices;

@CrossOrigin
@RequestMapping("/company/project/client")
@RestController
public class ClientController {

	@Autowired
	private ClientServices clientServices;

	@PostMapping(value = "/new")
	public ResponseEntity<Object> save(@RequestBody ClientDTO clientDTO) {

		try {
			ClientDTO clientSaved = clientServices.save(clientDTO);
			if (clientSaved != null) {
				return new ResponseEntity<>(clientSaved, HttpStatus.CREATED);
			}
			return new ResponseEntity<>(clientSaved, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(DuplicateCpfException de) {
			return new ResponseEntity<>(de.getMessage(), HttpStatus.CONFLICT);
		} catch(ServiceException se) {
			return new ResponseEntity<>(se.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/save")
	public ResponseEntity<Object> update(@RequestParam(value = "id", required = true) String id,
											@RequestBody ClientDTO clientDTO) {

		try {
			ClientDTO clientSaved = clientServices.update(id, clientDTO);
			return new ResponseEntity<>(clientSaved, HttpStatus.OK);
		} catch (NotFoundException nfe) {
			return new ResponseEntity<>(nfe.getMessage(), HttpStatus.NOT_FOUND);
		} catch (DuplicateCpfException de) {
			return new ResponseEntity<>(de.getMessage(), HttpStatus.CONFLICT);
		} catch (ServiceException se) {
			return new ResponseEntity<>(se.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<String> delete(@RequestParam(value = "id", required = true) String id) {

		try {
			clientServices.delete(id);
			return new ResponseEntity<>("Successful deleting client!", HttpStatus.OK);
		} catch (NotFoundException nfe) {
			return new ResponseEntity<>(nfe.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ServiceException se) {
			return new ResponseEntity<>(se.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/find")
	public ResponseEntity<Object> find(@RequestParam(required = false) String id, 
													@RequestParam(defaultValue = "0") Integer page, 
													@RequestParam(defaultValue = "10") Integer size) {

		try {
			Map<String, Object> response = clientServices.find(id, page, size);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (ServiceException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException nfe) {
			return new ResponseEntity<>(nfe.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}