package ru.ann.mast.crudDataBaseApi.servise;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ru.ann.mast.crudDataBaseApi.dao.RegionRepository;
import ru.ann.mast.crudDataBaseApi.entity.Region;
import ru.ann.mast.crudDataBaseApi.exceptionHandling.NoSuchException;


@Service
public class RegionServiceImpl implements RegionService {
	@Autowired
	private RegionRepository regionRepository;

	@Override
	public List<Region> getAllRegions() {
		return regionRepository.findAll();
	}

	@Override
	public Region saveRegion(Region region) {
		return regionRepository.save(region);
	}
	
	@Override
	public Region updateRegion(Region region) {
		regionRepository.findById(region.getId()).orElseThrow(() -> 
		new NoSuchException("Entity is not found, id="+region.getId()));
		return regionRepository.save(region);
	}

	
	@Override
	public Region getRegion(int id) {
		Region region = regionRepository.findById(id).orElseThrow(() -> 
				new NoSuchException("Entity is not found, id="+id));
		return region;
	};

	@Override
	public void deleteRegion(int id) {
		getRegion(id);
		regionRepository.deleteById(id);
	};
	
	public void deleteAllRegion() {
		regionRepository.deleteAll();
	}
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public void resetAutoIncrement() {
		jdbcTemplate.update("ALTER TABLE mastdatabasetest.regions AUTO_INCREMENT = 1");

	}
	
}
