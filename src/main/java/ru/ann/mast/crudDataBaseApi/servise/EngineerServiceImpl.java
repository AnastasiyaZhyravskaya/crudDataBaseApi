package ru.ann.mast.crudDataBaseApi.servise;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ann.mast.crudDataBaseApi.dao.EngineerRepository;
import ru.ann.mast.crudDataBaseApi.entity.Engineer;


@Service
public class EngineerServiceImpl implements EngineerService {
	
	@Autowired
	private EngineerRepository engineerRepository;

	@Override
	public List<Engineer> getAllEngineers() {
//		return engineerRepository.getAllEngineers();
		return engineerRepository.findAll();
	}

	@Override
	public void saveEngineer(Engineer engineer) {
//		engineerRepository.saveEngineer(engineer);
		engineerRepository.save(engineer);
	}

	@Override
	public Engineer getEngineer(int id) {
	//	Engineer engineer = engineerRepository.getEngineer(id);
		Engineer engineer = null;
		Optional<Engineer> optional = engineerRepository.findById(id);
		engineer = optional.get();
		return engineer;
	};

	@Override
	public void deleteEngineer(int id) {
//		engineerRepository.deleteEngineer(id);
		engineerRepository.deleteById(id);
	};
	
}
