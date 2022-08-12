package ru.ann.mast.crudDataBaseApi.controllers;


import java.util.List;

import javax.validation.Valid;

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

@RestController
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;	
	
	@GetMapping("/companies")
	public List<Company> getAllCompanys(){
		return companyService.getAllCompanys();
	}
	
	@GetMapping("/companies/{id}")
	public Company getCompany(@PathVariable int id){
		return companyService.getCompany(id);
	}
	
	@PostMapping("/companies")
	public Company addNewCompany(@RequestBody @Valid Company company){
		companyService.saveCompany(company);
		return company;
	}
	
	@PutMapping("/companies")
	public Company updateCompany(@RequestBody @Valid Company company){
		companyService.updateCompany(company);
		return company;
	}
	
	@DeleteMapping("/companies/{id}")
	public String deleteCompany(@PathVariable int id){
		companyService.deleteCompany(id);
		return "Company with id="+id+" was deleted";
	}
	
	public String deleteAllCompany(){
		companyService.deleteAllCompany();
		return "all company was deleted";
	}
	
	public String resetAutoIncrement() {
		companyService.resetAutoIncrement();
		return "AUTO_INCREMENT = 1";
	}
}
