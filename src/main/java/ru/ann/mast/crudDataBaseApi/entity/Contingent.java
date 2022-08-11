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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "contingents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerialize
@JsonAutoDetect
public class Contingent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotBlank(message = "Name is required field")
	@Column(name = "name")
	private String name;
	
	@NotBlank(message = "Name is required field")
	@Column(name = "surname")
	private String surname;
	
	@Column(name = "patronymic")
	private String patronymic;

	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonIgnoreProperties("contingent")
	@OneToOne(mappedBy = "contingent",
			cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})//если удалить работника, то детали о нем тоже удаляться
	private Engineer engineer;
	
}
