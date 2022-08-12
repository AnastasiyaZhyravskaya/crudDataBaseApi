package ru.ann.mast.crudDataBaseApi.servise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ru.ann.mast.crudDataBaseApi.dao.ContingentRepository;
import ru.ann.mast.crudDataBaseApi.dao.EngineerRepository;
import ru.ann.mast.crudDataBaseApi.dao.RegionRepository;
import ru.ann.mast.crudDataBaseApi.entity.Engineer;
import ru.ann.mast.crudDataBaseApi.exceptionHandling.NoSuchException;


@Service
public class EngineerServiceImpl implements EngineerService {
	
	@Autowired
	private EngineerRepository engineerRepository;
	
	@Autowired
	private ContingentRepository contingentRepository;
	
	@Autowired
	RegionRepository regionRepository;

	@Override
	public List<Engineer> getAllEngineers() {
		return engineerRepository.findAll();
	}

	@Override
	public Engineer saveEngineer(Engineer engineer) {
		return engineerRepository.save(engineer);
	}
	
	@Override
	public Engineer updateEngineer(Engineer engineer) {
		engineerRepository.findById(engineer.getId()).orElseThrow(() -> 
				new NoSuchException("Engineer is not found, id="+engineer.getId()));
		contingentRepository.findById(engineer.getContingent().getId()).orElseThrow(() -> 
				new NoSuchException("Contingent is not found, id="+engineer.getContingent().getId()));
		regionRepository.findById(engineer.getRegion().getId()).orElseThrow(() -> 
				new NoSuchException("Region is not found, id="+engineer.getRegion().getId()));
		return engineerRepository.save(engineer);
	}


	@Override
	public Engineer getEngineer(int id) {
		Engineer engineer = engineerRepository.findById(id).orElseThrow(() -> 
					new NoSuchException("Engineer is not found, id="+id));
		return engineer;
	};

	@Override
	public void deleteEngineer(int id) {
		getEngineer(id);
		engineerRepository.deleteById(id);
	};
	
	@Override
	public void deleteAllEngineer() {
		engineerRepository.deleteAll();
	}
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public void resetAutoIncrement() {
		jdbcTemplate.update("ALTER TABLE engineers AUTO_INCREMENT = 1");

	}
	
}
