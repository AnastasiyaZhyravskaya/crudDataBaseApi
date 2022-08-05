package ru.ann.mast.crudDataBaseApi.servise;

import java.util.List;

import ru.ann.mast.crudDataBaseApi.entity.Contingent;


public interface ContingentService {
	
	public List<Contingent> getAllContingents();
	
	public void saveContingent(Contingent contingent);
	
	public Contingent getContingent(int id);

	public void deleteContingent(int id);
}
