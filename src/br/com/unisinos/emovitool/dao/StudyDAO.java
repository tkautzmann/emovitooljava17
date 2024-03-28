package br.com.unisinos.emovitool.dao;

import java.util.List;

import br.com.unisinos.emovitool.entity.Study;

public interface StudyDAO {

	public List<Study> getStudies();
	public Study getStudyByID(int id);
	
}
