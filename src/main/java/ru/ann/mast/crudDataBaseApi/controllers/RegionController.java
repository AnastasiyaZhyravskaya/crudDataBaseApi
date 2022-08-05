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

import ru.ann.mast.crudDataBaseApi.entity.Region;
import ru.ann.mast.crudDataBaseApi.servise.RegionService;


@RestController
public class RegionController {
	
	@Autowired
	private RegionService regionService;
	
	@GetMapping("/regions")
	public List<Region> getAllRegions(){
		return regionService.getAllRegions();
	}
	
	@GetMapping("/regions/{id}")
	public Region getRegion(@PathVariable int id){
		return regionService.getRegion(id);
	}
	
	
	@PostMapping("/regions")
	public Region addNewRegion(@RequestBody Region region){
		regionService.saveRegion(region);
		return region;
	}
	
	@PutMapping("/regions")
	public Region updateRegion(@RequestBody Region region){
		regionService.saveRegion(region);
		return region;
	}
	
	@DeleteMapping("/regions/{id}")
	public String deleteRegion(@PathVariable int id){
		regionService.deleteRegion(id);
		return "region with id="+id+" was deleted";
	}
	
}
