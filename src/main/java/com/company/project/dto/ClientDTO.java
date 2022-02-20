package com.company.project.dto;

import com.company.project.model.Client;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClientDTO {

	private String id;
	private String cpf;
	private String name;
	private Integer age;
	private String address;
	private String city;
	private String state;

	public ClientDTO(Client client) {
		this.id = client.getId();
		this.cpf = client.getCpf();
		this.name = client.getName();
		this.age = client.getAge();
		this.address = client.getAddress();
		this.city = client.getCity();
		this.state = client.getState();
	}
}