package br.com.unisinos.emovitool.dao;

import java.util.List;

import br.com.unisinos.emovitool.entity.RecordSession;

public interface RecordSessionDAO {

	public List<RecordSession> getSessionByIdCoder(int idcoder);
	
	public RecordSession getSessionById(int id);
	
}
