package br.com.unisinos.emovitool.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisinos.emovitool.entity.BehaviorLabel;

@Repository
public class BehaviorLabelDAOImpl implements BehaviorLabelDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public BehaviorLabel getBehaviorLabelById(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		String query = "select bl from BehaviorLabel bl where bl.id = " + id;
		BehaviorLabel behaviorLabel = (BehaviorLabel) session.createQuery(query).getSingleResult();
		
		return behaviorLabel;
		
	}

}
