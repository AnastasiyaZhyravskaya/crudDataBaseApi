package ru.ann.mast.crudDataBaseApi.servise;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ann.mast.crudDataBaseApi.dao.ContingentRepository;
import ru.ann.mast.crudDataBaseApi.entity.Contingent;

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
	public Contingent getContingent(int id) {
		Contingent contingent = null;
		Optional<Contingent> optoinalContingent = contingentRepository.findById(id);
		if(optoinalContingent.isPresent()) {
			contingent = optoinalContingent.get();
		}
		return contingent;
	};

	@Override
	public void deleteContingent(int id) {
		contingentRepository.deleteById(id);
	};
	
}
