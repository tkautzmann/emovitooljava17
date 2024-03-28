package br.com.unisinos.emovitool.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisinos.emovitool.entity.Config;

@Repository
public class ConfigDAOImpl implements ConfigDAO{

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Config> getConfigs() {
		
		Session session = sessionFactory.getCurrentSession();
		
		String query = "from Config";
		List<Config> configs = session.createQuery(query).getResultList();
		
		return configs;
		
	}

}
