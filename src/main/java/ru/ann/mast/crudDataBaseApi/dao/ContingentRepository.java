package ru.ann.mast.crudDataBaseApi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.ann.mast.crudDataBaseApi.entity.Contingent;


public interface ContingentRepository extends 
				JpaRepository<Contingent, Integer> {
		
}
