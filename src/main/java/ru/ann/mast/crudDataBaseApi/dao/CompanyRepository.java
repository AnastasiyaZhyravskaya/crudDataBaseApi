package ru.ann.mast.crudDataBaseApi.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import ru.ann.mast.crudDataBaseApi.entity.Company;

public interface CompanyRepository extends 
				JpaRepository<Company, Integer> {
}
