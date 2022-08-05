package ru.ann.mast.crudDataBaseApi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.ann.mast.crudDataBaseApi.entity.Region;

public interface RegionRepository extends 
				JpaRepository<Region, Integer>{
	
}
