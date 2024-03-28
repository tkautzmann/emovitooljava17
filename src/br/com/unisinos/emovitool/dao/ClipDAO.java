package br.com.unisinos.emovitool.dao;

import java.util.List;

import br.com.unisinos.emovitool.entity.Clip;

public interface ClipDAO {

	public List<Clip> getClipsByIdSessionAndCoder(int id_session, int id_coder);
	public void insert (Clip clip);
	public void update (Clip clip);
	public void unselectSessionClips(int session_id, int coder_id);
	
}
