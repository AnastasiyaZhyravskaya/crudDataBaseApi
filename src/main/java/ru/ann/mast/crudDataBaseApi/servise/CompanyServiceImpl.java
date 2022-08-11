package ru.ann.mast.crudDataBaseApi.servise;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ru.ann.mast.crudDataBaseApi.dao.CompanyRepository;
import ru.ann.mast.crudDataBaseApi.entity.Company;
import ru.ann.mast.crudDataBaseApi.entity.Region;
import ru.ann.mast.crudDataBaseApi.exceptionHandling.NoSuchException;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;


	@Override
	public List<Company> getAllCompanys() {
		return companyRepository.findAll();
	}

	@Override
	public Company saveCompany(Company company) {
		companyRepository.save(company);
		return company;
	}
	
	@Override
	public Company updateCompany(Company company) {
		companyRepository.findById(company.getId()).orElseThrow(() -> 
				new NoSuchException("Company is not found, id="+company.getId()));
		return companyRepository.save(company);
	}

	@Override
	public Company getCompany(int id) {
		Company company = companyRepository.findById(id).orElseThrow(()->
					new NoSuchException("Company is not found, id="+id));
		return company;
	};

	@Override
	public void deleteCompany(int id) {
		getCompany(id);
		companyRepository.deleteById(id);
	};
	
	@Override
	public void deleteAllCompany() {
		companyRepository.deleteAll();
	}
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public void resetAutoIncrement() {
		jdbcTemplate.update("ALTER TABLE companies AUTO_INCREMENT = 1");

	}
	
}
