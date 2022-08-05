package ru.ann.mast.crudDataBaseApi.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.ann.mast.crudDataBaseApi.entity.Company;
import ru.ann.mast.crudDataBaseApi.servise.CompanyService;
import ru.ann.mast.crudDataBaseApi.servise.RegionService;


@RestController
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private RegionService regionService;

	
	
	@GetMapping("/companys")
	public List<Company> getAllCompanys(){
		return companyService.getAllCompanys();
	}
	
	@GetMapping("/companys/{id}")
	public Company getCompany(@PathVariable int id){
		return companyService.getCompany(id);
	}
	
	@PostMapping("/companys")
	public Company addNewCompany(@RequestBody Company company){
		company.setRegionOfCompany(regionService.getRegion(company.getRegionID()));
		companyService.saveCompany(company);
		return company;
	}
	
	@PutMapping("/companys")
	public Company updateCompany(@RequestBody Company company){
		company.setRegionOfCompany(regionService.getRegion(company.getRegionID()));
		companyService.saveCompany(company);
		return company;
	}
	
	@DeleteMapping("/companys/{id}")
	public String deleteCompany(@PathVariable int id){
		companyService.deleteCompany(id);
		return "company with id="+id+" was deleted";
	}
	
}
