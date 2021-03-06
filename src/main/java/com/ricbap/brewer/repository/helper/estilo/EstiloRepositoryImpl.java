package com.ricbap.brewer.repository.helper.estilo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ricbap.brewer.model.Estilo;
import com.ricbap.brewer.repository.filter.EstiloFilter;
import com.ricbap.brewer.repository.paginacao.PaginacaoUtil;

public class EstiloRepositoryImpl implements EstiloRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true) // transacao apenas leitura
	public Page<Estilo> filtrar(EstiloFilter filter, Pageable pageable) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);
		
		/*
		 * int paginaAtual = pageable.getPageNumber(); int totalRegistrosPorPagina =
		 * pageable.getPageSize(); int primeiroRegistro = paginaAtual *
		 * totalRegistrosPorPagina;
		 * 
		 * criteria.setFirstResult(primeiroRegistro);
		 * criteria.setMaxResults(totalRegistrosPorPagina);
		 * 
		 * //Ordenação Sort sort = pageable.getSort(); if (sort != null) { Sort.Order
		 * order = sort.iterator().next(); String property = order.getProperty();
		 * criteria.addOrder(order.isAscending() ? Order.asc(property) :
		 * Order.desc(property)); }
		 */
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filter, criteria);
		return new PageImpl<>(criteria.list(), pageable, total(filter)) ;
	}
	
	private long total(EstiloFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Estilo.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(EstiloFilter filter, Criteria criteria) {
		if (filter != null) {
			if (!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE ));
			}
		}
	}

}
