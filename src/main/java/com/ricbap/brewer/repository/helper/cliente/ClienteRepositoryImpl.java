package com.ricbap.brewer.repository.helper.cliente;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ricbap.brewer.model.Cliente;
import com.ricbap.brewer.repository.filter.ClienteFilter;
import com.ricbap.brewer.repository.paginacao.PaginacaoUtil;

public class ClienteRepositoryImpl implements ClienteRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> filtrar(ClienteFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		
		/*
		 * int paginaAtual = pageable.getPageNumber(); int totalRegistrosPorPagina =
		 * pageable.getPageSize(); int primeiroRegistro = paginaAtual *
		 * totalRegistrosPorPagina;
		 * 
		 * criteria.setFirstResult(primeiroRegistro);
		 * criteria.setMaxResults(totalRegistrosPorPagina);
		 * 
		 * Sort sort = pageable.getSort(); if (sort != null) { Sort.Order order =
		 * sort.iterator().next(); String propriedade = order.getProperty();
		 * criteria.addOrder(order.isAscending() ? Order.asc(propriedade) :
		 * Order.desc(propriedade)); }
		 */
		paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(filter, criteria);
		
		criteria.createAlias("endereco.cidade", "c", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("c.estado", "e", JoinType.LEFT_OUTER_JOIN);
		
		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}
	
	private Long total(ClienteFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(ClienteFilter filter, Criteria criteria) {
		if (filter != null) {
			if (!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}
			if (!StringUtils.isEmpty(filter.getCpfCnpj())) {
				criteria.add(Restrictions.eq("cpfCnpj", filter.getCpfCnpj()));
			}
		}
	}	

}
