package br.com.unisinos.emovitool.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisinos.emovitool.entity.EmotionLabel;

@Repository
public class EmotionLabelDAOImpl implements EmotionLabelDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public EmotionLabel getEmotionLabelById(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		String query = "select el from EmotionLabel el where el.id = " + id;
		EmotionLabel emotionLabel = (EmotionLabel) session.createQuery(query).getSingleResult();
		
		return emotionLabel;
		
	}

}
