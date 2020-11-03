package com.ricbap.brewer.repository.helper.estilo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ricbap.brewer.model.Estilo;
import com.ricbap.brewer.repository.filter.EstiloFilter;

public class EstiloRepositoryImpl implements EstiloRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true) // transacao apenas leitura
	public List<Estilo> filtrar(EstiloFilter filter) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);
		
		if (filter != null) {
			if (!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE ));
			}
		}
		return criteria.list();
	}

}
