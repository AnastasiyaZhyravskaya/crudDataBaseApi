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

import ru.ann.mast.crudDataBaseApi.entity.Engineer;
import ru.ann.mast.crudDataBaseApi.servise.EngineerService;


@RestController
public class EngineerController {
	
	@Autowired
	private EngineerService engineerService;
	
	@GetMapping("/engineers")
	public List<Engineer> getAllEmployees(){
		return engineerService.getAllEngineers();
	}
	
	@GetMapping("/engineers/{id}")
	public Engineer getEngineer(@PathVariable int id){
		return engineerService.getEngineer(id);
	}
	
	
	@PostMapping("/engineers")
	public Engineer addNewEngineer(@RequestBody @Valid Engineer engineer){
		engineerService.saveEngineer(engineer);
		return engineer;
	}
	
	@PutMapping("/engineers")
	public Engineer updateEngineer(@RequestBody @Valid Engineer engineer){
		engineerService.updateEngineer(engineer);
		return engineer;
	}
	
	@DeleteMapping("/engineers/{id}")
	public String deleteEngineer(@PathVariable int id){
		engineerService.deleteEngineer(id);
		return "Engineer with id="+id+" was deleted";
	}
	
	public String deleteAllEngineer(){
		engineerService.deleteAllEngineer();
		return "all engineer was deleted";
	}
	
	public String resetAutoIncrement() {
		engineerService.resetAutoIncrement();
		return "AUTO_INCREMENT = 1";
	}
}
