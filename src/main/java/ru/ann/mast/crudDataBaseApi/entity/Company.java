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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;


@Entity
@Table(name = "companys")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotBlank(message = "Name is required field")
	@NotEmpty(message = "Name is required field")
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "representative_of_company")
	private String representativeOfCompany;
	
	@Column(name = "representative_phone")
	 @Size(min = 6, max = 11, message 
     = "phonee must be between 6 and 11 symvols")
	@Pattern(regexp = "^\\d+$")
	private String representativePhone;
	
	
	@Pattern(message = "должно соответствовать формату pochta@gmal.ru", 
			regexp = "^[A-Za-z0-9._%+-]+@[a-zA-Z0-9-]+.[a-zA-z]{2,4}$")
	@Column(name = "representative_email")
	private String representativeEmail;
	
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "region_id")
	private Region regionOfCompany;
	
	
	@Transient
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int regionID;
	
	
	public Company() {	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRepresentativeOfCompany() {
		return representativeOfCompany;
	}

	public void setRepresentativeOfCompany(String representativeOfCompany) {
		this.representativeOfCompany = representativeOfCompany;
	}

	public String getRepresentativePhone() {
		return representativePhone;
	}

	public void setRepresentativePhone(String representativePhone) {
		this.representativePhone = representativePhone;
	}

	public String getRepresentativeEmail() {
		return representativeEmail;
	}

	public void setRepresentativeEmail(String representativeEmail) {
		this.representativeEmail = representativeEmail;
	}


	public Region getRegionOfCompany() {
		return regionOfCompany;
	}


	public void setRegionOfCompany(Region regionOfCompany) {
		this.regionOfCompany = regionOfCompany;
	}



	public int getRegionID() {
		return regionID;
	}


	public void setRegionID(int regionID) {
		this.regionID = regionID;
	}

}
