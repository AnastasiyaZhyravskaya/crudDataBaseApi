package ru.ann.mast.crudDataBaseApi.servise;

import java.util.List;

import ru.ann.mast.crudDataBaseApi.entity.Engineer;


public interface EngineerService {
	
	public List<Engineer> getAllEngineers();
	
	public void saveEngineer(Engineer engineer);
	
	public Engineer getEngineer(int id);

	public void deleteEngineer(int id);


}
