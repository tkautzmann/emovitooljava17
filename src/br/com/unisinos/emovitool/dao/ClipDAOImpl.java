package br.com.unisinos.emovitool.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisinos.emovitool.entity.Clip;

@Repository
public class ClipDAOImpl implements ClipDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Clip> getClipsByIdSessionAndCoder(int id_session, int id_coder) {
		
		Session session = sessionFactory.getCurrentSession();
		
		String query = "select cl from Clip cl inner join cl.session se inner join cl.coder co where se.id = " + id_session + " and co.id = " + id_coder;
		List<Clip> clipList = session.createQuery(query).getResultList();
		
		return clipList;
		
	}

	@Override
	@Transactional
	public void insert(Clip clip) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(clip);
		
	}

	@Override
	@Transactional
	public void update(Clip clip) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Clip clipForUpdate = session.get(Clip.class, clip.getId());
		
		String query = "delete from ClipEmotionLabel where clip.id = " + clipForUpdate.getId();
		session.createQuery(query).executeUpdate();
		
		query = "delete from ClipBehaviorLabel where clip.id = " + clipForUpdate.getId();
		session.createQuery(query).executeUpdate();
		
		clipForUpdate.setComment(clip.getComment());
		clipForUpdate.setCurrent(clip.getCurrent());
		clipForUpdate.setEmotions(clip.getEmotions());
		clipForUpdate.setBehaviors(clip.getBehaviors());
		
	}

	@Override
	@Transactional
	public void unselectSessionClips(int session_id, int coder_id) {
		
		Session session = sessionFactory.getCurrentSession();
		String query = "update from Clip set current = false where session.id = " + session_id + " and coder.id = " + coder_id;
		session.createQuery(query).executeUpdate();
		
	}

}
