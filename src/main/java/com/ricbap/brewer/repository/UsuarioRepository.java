package com.ricbap.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ricbap.brewer.model.Usuario;
import com.ricbap.brewer.repository.helper.usuario.UsuarioRepositoryQuery;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery {
	
	public Optional<Usuario> findByEmail(String email);
	
	//public Optional<Usuario> findByEmailIgnoreCaseAndAtivoTrue(String email);

}
