package ru.ann.mast.crudDataBaseApi.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerialize
@JsonAutoDetect
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotBlank(message = "Name is required field")
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "representative_of_company")
	private String representativeOfCompany;
	
	@Column(name = "representative_phone")
	 @Size(min = 6, max = 11, message 
     = "Phone must be between 6 and 11 symvols")
	@Pattern(regexp = "^\\d+$", message 
		     = "Phone is only digits")
	private String representativePhone;
	
	
	@Pattern(message = "The email address must be in the format: pochta@gmal.ru", 
			regexp = "^[A-Za-z0-9._%+-]+@[a-zA-Z0-9-]+.[a-zA-z]{2,4}$")
	@Column(name = "representative_email")
	private String representativeEmail;
	
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name = "region_id")
	private Region regionOfCompany;
	
	
	@Transient
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int regionID;
	

}
