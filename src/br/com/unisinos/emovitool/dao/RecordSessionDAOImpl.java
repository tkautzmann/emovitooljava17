package br.com.unisinos.emovitool.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisinos.emovitool.entity.RecordSession;

@Repository
public class RecordSessionDAOImpl implements RecordSessionDAO{

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<RecordSession> getSessionByIdCoder(int idcoder) {
		
		Session session = sessionFactory.getCurrentSession();
		
		String queryString = "select rs from Coder c inner join c.study s inner join s.sessions rs where c.id = " + idcoder;
		List<RecordSession> sessionList = session.createQuery(queryString).getResultList();
		
		return sessionList;
		
	}

	@Override
	@Transactional
	public RecordSession getSessionById(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		String queryString = "select rs from RecordSession rs where rs.id = " + id;
		RecordSession recordSession = (RecordSession) session.createQuery(queryString).getSingleResult();
		
		return recordSession;
		
	}

}
