package com.ricbap.brewer.repository.helper.usuario;

import java.util.Optional;

import com.ricbap.brewer.model.Usuario;

public interface UsuarioRepositoryQuery {
	
	public Optional<Usuario> porEmailEAtivo(String email);

}
