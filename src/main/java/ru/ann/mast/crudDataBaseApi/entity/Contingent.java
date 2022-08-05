package ru.ann.mast.crudDataBaseApi.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


@Entity
@Table(name = "contingents")
public class Contingent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotBlank(message = "Name is required field")
	@NotEmpty(message = "Name is required field")
	@Column(name = "name")
	private String name;
	
	@NotBlank(message = "Name is required field")
	@NotEmpty(message = "Name is required field")
	@Column(name = "surname")
	private String surname;
	
	@Column(name = "patronymic")
	private String patronymic;

	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonIgnoreProperties("contingent")
	@OneToOne(mappedBy = "contingent",
			cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})//если удалить работника, то детали о нем тоже удаляться
	private Engineer engineer;
	
	
	public Contingent() {	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}


	public Engineer getEngineer() {
		return engineer;
	}

	public void setEngineer(Engineer engineer) {
		this.engineer = engineer;
	}
}
