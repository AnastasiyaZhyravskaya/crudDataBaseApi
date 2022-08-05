package ru.ann.mast.crudDataBaseApi.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.ann.mast.crudDataBaseApi.entity.Engineer;
import ru.ann.mast.crudDataBaseApi.servise.ContingentService;
import ru.ann.mast.crudDataBaseApi.servise.EngineerService;
import ru.ann.mast.crudDataBaseApi.servise.RegionService;


@RestController
//@RequestMapping("/api")
public class EngineerController {
	
	@Autowired
	private EngineerService engineerService;
	
	@Autowired
	private ContingentService contingentService;
	
	@Autowired
	private RegionService regionService;
	
	@GetMapping("/engineers")
	public List<Engineer> getAllEmployees(){
		return engineerService.getAllEngineers();
	}
	
	@GetMapping("/engineers/{id}")
	public Engineer getEngineer(@PathVariable int id){
		return engineerService.getEngineer(id);
	}
	
	
	@PostMapping("/engineers")
	public Engineer addNewEngineer(@RequestBody Engineer engineer){
		engineer.setContingent(contingentService.getContingent(engineer.getContingentId()));
		engineer.setRegion(regionService.getRegion(engineer.getRegionId()));
		engineerService.saveEngineer(engineer);
		return engineer;
	}
	
	@PutMapping("/engineers")
	public Engineer updateEngineer(@RequestBody Engineer engineer){
		engineer.setContingent(contingentService.getContingent(engineer.getContingentId()));
		engineer.setRegion(regionService.getRegion(engineer.getRegionId()));
		engineerService.saveEngineer(engineer);
		return engineer;
	}
	
	@DeleteMapping("/engineers/{id}")
	public String deleteEngineer(@PathVariable int id){
		engineerService.deleteEngineer(id);
		return "engineer with id="+id+" was deleted";
	}
	
}
