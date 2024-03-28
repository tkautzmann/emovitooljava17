package br.com.unisinos.emovitool.dao;

import java.util.List;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisinos.emovitool.entity.Coder;

@Repository
public class CoderDAOImpl implements CoderDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Coder> getCodersByIdStudy(int id_study) {
		
		Session session = sessionFactory.getCurrentSession();
		
		List<Coder> coderList = session.createQuery("from Coder c where c.study.id = " + id_study).getResultList();
		
		return coderList;
		
	}

	@Override
	@Transactional
	public Coder getCoderById(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Coder coder = (Coder) session.createQuery("from Coder c where c.id = " + id).getSingleResult();
		
		return coder;
		
	}

}
