package ru.ann.mast.crudDataBaseApi.servise;

import java.util.List;

import ru.ann.mast.crudDataBaseApi.entity.Company;


public interface CompanyService {
	
	public List<Company> getAllCompanys();
	
	public void saveCompany(Company company);
	
	public Company getCompany(int id);

	public void deleteCompany(int id);
}
