package ru.ann.mast.crudDataBaseApi.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


@Entity
@Table(name = "engineers")
public class Engineer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})//если удалить работника, то детали о нем тоже удаляться
	@JsonIgnoreProperties("engineer")
	@JoinColumn(name = "contingent_id")
	private Contingent contingent;
	
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "region_id")//внешний ключ
	private Region region;
	
	
	@Transient
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int contingentId;
	
	@Transient
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int regionId;
	
	
	public Engineer() {	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Region getRegion() {
		return region;
	}


	public void setRegion(Region region) {
		this.region = region;
	}


	public Contingent getContingent() {
		return contingent;
	}


	public void setContingent(Contingent contingent) {
		this.contingent = contingent;
	}


	public int getContingentId() {
		return contingentId;
	}


	public void setContingentId(int contingentId) {
		this.contingentId = contingentId;
	}


	public int getRegionId() {
		return regionId;
	}


	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	
	
}
