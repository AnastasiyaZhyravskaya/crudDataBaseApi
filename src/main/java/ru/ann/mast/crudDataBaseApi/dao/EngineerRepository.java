package ru.ann.mast.crudDataBaseApi.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import ru.ann.mast.crudDataBaseApi.entity.Engineer;

public interface EngineerRepository extends 
					JpaRepository<Engineer, Integer>{
		
}
