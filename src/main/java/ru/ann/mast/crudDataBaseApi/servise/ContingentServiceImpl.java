package ru.ann.mast.crudDataBaseApi.servise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ru.ann.mast.crudDataBaseApi.dao.ContingentRepository;
import ru.ann.mast.crudDataBaseApi.entity.Contingent;
import ru.ann.mast.crudDataBaseApi.exceptionHandling.NoSuchException;

@Service
public class ContingentServiceImpl implements ContingentService {
	
	@Autowired
	private ContingentRepository contingentRepository;


	@Override
	public List<Contingent> getAllContingents() {
		return contingentRepository.findAll();
	}

	@Override
	public void saveContingent(Contingent contingent) {
		contingentRepository.save(contingent);
	}
	
	@Override
	public Contingent updateContingent(Contingent contingent) {
		contingentRepository.findById(contingent.getId()).orElseThrow(() -> 
				new NoSuchException("Contingent is not found, id="+contingent.getId()));
		return contingentRepository.save(contingent);
	}

	@Override
	public Contingent getContingent(int id) {
		Contingent contingent = contingentRepository.findById(id).orElseThrow(() -> 
		new NoSuchException("Contingent is not found, id="+id));
		return contingent;
	};

	@Override
	public void deleteContingent(int id) {
		getContingent(id);
		contingentRepository.deleteById(id);
	};
	
	@Override
	public void deleteAllContingent() {
		contingentRepository.deleteAll();
	}
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public void resetAutoIncrement() {
		jdbcTemplate.update("ALTER TABLE contingents AUTO_INCREMENT = 1");

	}
	
}
