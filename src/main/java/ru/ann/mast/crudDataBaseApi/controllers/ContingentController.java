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

import ru.ann.mast.crudDataBaseApi.entity.Contingent;
import ru.ann.mast.crudDataBaseApi.servise.ContingentService;


@RestController
public class ContingentController {
	
	@Autowired
	private ContingentService contingentService;
	
	
	@GetMapping("/contingents")
	public List<Contingent> getAllContingents(){
		return contingentService.getAllContingents();
	}
	
	@GetMapping("/contingents/{id}")
	public Contingent getContingent(@PathVariable int id){
		return contingentService.getContingent(id);
	}
	
	@PostMapping("/contingents")
	public Contingent addNewContingent(@RequestBody @Valid Contingent contingent){
		contingentService.saveContingent(contingent);
		return contingent;
	}
	
	@PutMapping("/contingents")
	public Contingent updateContingent(@RequestBody @Valid Contingent contingent){
		contingentService.updateContingent(contingent);
		return contingent;
	}
	
	@DeleteMapping("/contingents/{id}")
	public String deleteContingent(@PathVariable int id){
		contingentService.deleteContingent(id);
		return "Contingent with id="+id+" was deleted";
	}
	
	public String deleteAllContingent(){
		contingentService.deleteAllContingent();
		return "all contingent was deleted";
	}
	
	public String resetAutoIncrement() {
		contingentService.resetAutoIncrement();
		return "AUTO_INCREMENT = 1";
	}
	
}
