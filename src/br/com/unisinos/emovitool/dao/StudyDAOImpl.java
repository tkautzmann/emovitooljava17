package br.com.unisinos.emovitool.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisinos.emovitool.entity.Study;

@Repository
public class StudyDAOImpl implements StudyDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Study> getStudies() {
		
		Session session = sessionFactory.getCurrentSession();
		
		List<Study> studyList = session.createQuery("from Study").getResultList();
		
		return studyList;
		
	}

	@Override
	@Transactional
	public Study getStudyByID(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Study study = (Study) session.createQuery("from Study s where s.id = " + id).getSingleResult();
		
		return study;
		
	}

}
