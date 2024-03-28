package br.com.unisinos.emovitool.dao;

import java.util.List;

import br.com.unisinos.emovitool.entity.Coder;

public interface CoderDAO {

	public List<Coder> getCodersByIdStudy(int id_study);
	public Coder getCoderById(int id);
	
}
