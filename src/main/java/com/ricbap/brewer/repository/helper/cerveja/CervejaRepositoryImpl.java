package com.ricbap.brewer.repository.helper.cerveja;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

import com.ricbap.brewer.dto.ValorItensEstoque;
import com.ricbap.brewer.model.Cerveja;
import com.ricbap.brewer.model.Cerveja_;
import com.ricbap.brewer.repository.filter.CervejaFilter;
import com.ricbap.brewer.repository.filter.CervejaSkuOuNomeFilter;
import com.ricbap.brewer.repository.paginacao.PaginacaoUtil;
import com.ricbap.brewer.repository.projection.ResumoCerveja;

public class CervejaRepositoryImpl implements CervejaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	// DASHBOARD
	@Override
	public ValorItensEstoque valorItensEstoque() {
		String query = "select new com.ricbap.brewer.dto.ValorItensEstoque(sum(valor * quantidadeEstoque), sum(quantidadeEstoque)) from Cerveja";
		return manager.createQuery(query, ValorItensEstoque.class).getSingleResult();
	} 
		
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable) {		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(filter, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}
	
	
	private Long total(CervejaFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}


	private void adicionarFiltro(CervejaFilter filter, Criteria criteria) {
		if (filter != null) {
			if (!StringUtils.isEmpty(filter.getSku())) {
				criteria.add(Restrictions.eq("sku", filter.getSku()));
			}
			
			if (!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}
			
			if (isEstiloPresente(filter)) {
				criteria.add(Restrictions.eq("estilo", filter.getEstilo()));
			}
			
			if (filter.getSabor() != null) {
				criteria.add(Restrictions.eq("sabor", filter.getSabor()));
			}
			
			if (filter.getOrigem() != null) {
				criteria.add(Restrictions.eq("origem", filter.getOrigem()));
			}
			
			if (filter.getValorDe() != null) {
				criteria.add(Restrictions.ge("valor", filter.getValorDe()));
			}
			
			if (filter.getValorAte() != null) {
				criteria.add(Restrictions.le("valor", filter.getValorAte()));
			}
		}
	}	

	private boolean isEstiloPresente(CervejaFilter filter) {
		return filter.getEstilo() != null && filter.getEstilo().getCodigo() != null;
	}
	
	@Override
	public List<ResumoCerveja> porSkuOuNome(CervejaSkuOuNomeFilter cervejaSkuOuNomeFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoCerveja> criteria = builder.createQuery(ResumoCerveja.class);		
		Root<Cerveja> root = criteria.from(Cerveja.class);
		
		criteria.select(builder.construct(ResumoCerveja.class
				, root.get(Cerveja_.codigo)
				, root.get(Cerveja_.sku)
				, root.get(Cerveja_.nome)
				, root.get(Cerveja_.origem)
				, root.get(Cerveja_.valor)
				, root.get(Cerveja_.foto)));
		
		Predicate[] predicates = criarRestricoes(cervejaSkuOuNomeFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoCerveja> query = manager.createQuery(criteria);		
		return query.getResultList();
	}


	private Predicate[] criarRestricoes(CervejaSkuOuNomeFilter cervejaSkuOuNomeFilter, CriteriaBuilder builder,
			Root<Cerveja> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(cervejaSkuOuNomeFilter.getSku())) {
			predicates.add(builder.like(
					builder.lower(root.get("sku")), "%" + cervejaSkuOuNomeFilter.getSku().toLowerCase() + "%"));
		}
		
		if(!StringUtils.isEmpty(cervejaSkuOuNomeFilter.getNome())) {
			predicates.add(builder.like(
					builder.lower(root.get("nome")), "%" + cervejaSkuOuNomeFilter.getNome().toLowerCase() + "%"));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	} 
	
	

}

/*
public List<CervejaDTO> porSkuOuNome(String skuOuNome) {
		String jpql = "select new com.ricbap.brewer.dto.CervejaDTO(codigo, sku, nome, origem, valor, foto) "
				+ "from Cerveja where lower(sku) like lower(:skuOuNome) or lower(nome) like lower(:skuOuNome)";
		List<CervejaDTO> cervejasFiltradas = manager.createQuery(jpql, CervejaDTO.class) 
				.setParameter("SkuOuNome", skuOuNome + "%")
				.getResultList();
		cervejasFiltradas.forEach(c -> c.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + c.getFoto())));
		return cervejasFiltradas;
	}
*/

 
 
