package com.ricbap.brewer.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ricbap.brewer.model.Usuario;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
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