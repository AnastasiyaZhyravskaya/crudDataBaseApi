package ru.ann.mast.crudDataBaseApi.servise;

import java.util.List;

import ru.ann.mast.crudDataBaseApi.entity.Region;


public interface RegionService {
	
	public List<Region> getAllRegions();
	
	public Region saveRegion(Region region);
	
	public Region updateRegion(Region region);
	
	public Region getRegion(int id);

	public void deleteRegion(int id);
	
	public void deleteAllRegion();

	public void resetAutoIncrement();
}
