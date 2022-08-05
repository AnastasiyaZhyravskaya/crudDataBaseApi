package ru.ann.mast.crudDataBaseApi.servise;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.ann.mast.crudDataBaseApi.dao.RegionRepository;
import ru.ann.mast.crudDataBaseApi.entity.Region;


@Service
public class RegionServiceImpl implements RegionService {
	@Autowired
	private RegionRepository regionRepository;

	@Override
	public List<Region> getAllRegions() {
		return regionRepository.findAll();
	}

	@Override
	public void saveRegion(Region region) {
		regionRepository.save(region);
	}

	@Override
	public Region getRegion(int id) {
		Region region = null;
		Optional<Region> optoinalRegion = regionRepository.findById(id);
		if(optoinalRegion.isPresent()) {
			region = optoinalRegion.get();
		}
		return region;
	};

	@Override
	public void deleteRegion(int id) {
		regionRepository.deleteById(id);
	};
	

	
}
