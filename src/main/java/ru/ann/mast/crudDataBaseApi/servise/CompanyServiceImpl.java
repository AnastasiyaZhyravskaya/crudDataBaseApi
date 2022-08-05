package ru.ann.mast.crudDataBaseApi.servise;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ann.mast.crudDataBaseApi.dao.CompanyRepository;
import ru.ann.mast.crudDataBaseApi.entity.Company;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;


	@Override
	public List<Company> getAllCompanys() {
		return companyRepository.findAll();
	}

	@Override
	public void saveCompany(Company company) {
		companyRepository.save(company);
	}

	@Override
	public Company getCompany(int id) {
		Company company = null;
		Optional<Company> optoinalCompany = companyRepository.findById(id);
		if(optoinalCompany.isPresent()) {
			company = optoinalCompany.get();
		}
		return company;
	};

	@Override
	public void deleteCompany(int id) {
		companyRepository.deleteById(id);
	};
	
}
