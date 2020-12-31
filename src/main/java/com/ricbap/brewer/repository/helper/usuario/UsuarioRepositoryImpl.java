package com.ricbap.brewer.repository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ricbap.brewer.model.Grupo;
import com.ricbap.brewer.model.Usuario;
import com.ricbap.brewer.model.UsuarioGrupo;
import com.ricbap.brewer.repository.filter.UsuarioFilter;
import com.ricbap.brewer.repository.paginacao.PaginacaoUtil;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Override
	public Optional<Usuario> porEmailEAtivo(String email) {		
		return manager
				.createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
				.setParameter("email", email).getResultList().stream().findFirst();				
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		return manager
				.createQuery(
				"select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario", String.class)
				.setParameter("usuario", usuario)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		
		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filter, criteria);
		
		List<Usuario> filtrados = criteria.list();
		filtrados.forEach(u -> Hibernate.initialize(u.getGrupos()));
		
		return new PageImpl<>(filtrados, pageable, total(filter));
	}
	
	
	private Long total(UsuarioFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	
	private void adicionarFiltro(UsuarioFilter filter, Criteria criteria) {
		if (filter != null) {
			if(!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}
			if(!StringUtils.isEmpty(filter.getEmail())) {
				criteria.add(Restrictions.ilike("email", filter.getEmail(), MatchMode.START));
			}
			
			//criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
			if(filter.getGrupos() != null && !filter.getGrupos().isEmpty()) {
				List<Criterion> subqueries = new ArrayList<>();
				for (Long codigoGrupo : filter.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()) {
					//System.out.println(">>>>> codigoGrupo " + codigoGrupo);
					DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
					dc.add(Restrictions.eq("id.grupo.codigo", codigoGrupo));
					dc.setProjection(Projections.property("id.usuario"));
					
					subqueries.add(Subqueries.propertyIn("codigo", dc));
				}
				
				Criterion[] criterions = new Criterion[subqueries.size()];
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
			}			
		}		
	}

	
	// Atualizacao de usuarios
	@Transactional(readOnly = true)
	@Override
	public Usuario buscarComGrupos(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (Usuario) criteria.uniqueResult();
	}
	

}



/*
select u.email usuario
, group_concat(substring(p.nome, 6) order by p.nome separator ', ') permissao
from usuario u
, usuario_grupo ug
, grupo g
, grupo_permissao gp
, permissao p
where ug.codigo_usuario = u.codigo
and ug.codigo_grupo = g.codigo
and g.codigo = gp.codigo_grupo
and gp.codigo_permissao = p.codigo
group by usuario
*/



/*
SELECT * FROM usuario u
LEFT OUTER JOIN usuario_grupo ug ON u.codigo = ug.codigo_usuario
LEFT OUTER JOIN grupo g ON ug.codigo_grupo = g.codigo
	WHERE (
		u.codigo IN (SELECT codigo_usuario FROM usuario_grupo WHERE codigo_grupo = 1)
	AND     u.codigo IN (SELECT codigo_usuario FROM usuario_grupo WHERE codigo_grupo = 2))


SELECT * FROM usuario u
INNER JOIN usuario_grupo ug ON u.codigo = ug.codigo_usuario
INNER JOIN grupo g ON ug.codigo_grupo = g.codigo
	WHERE (
		u.codigo IN (SELECT codigo_usuario FROM usuario_grupo WHERE codigo_grupo = 1)
	AND     u.codigo IN (SELECT codigo_usuario FROM usuario_grupo WHERE codigo_grupo = 2))	

*/