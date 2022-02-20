package com.company.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.company.project.dto.ClientDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Document(collection = "client")
public class Client {

	@Id
    private String id;

	private String cpf;
	private String name;
	private Integer age;
	private String address;
	private String city;
	private String state;

	public Client(ClientDTO dto) {
		this.id = dto.getId();
		this.cpf = dto.getCpf();
		this.name = dto.getName();
		this.age = dto.getAge();
		this.address = dto.getAddress();
		this.city = dto.getCity();
		this.state = dto.getState();
	}
}