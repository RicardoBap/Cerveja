package com.ricbap.brewer.security;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ricbap.brewer.model.Usuario;
import com.ricbap.brewer.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = usuarioRepository.porEmailEAtivo(email);
		
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario ou senha incorrretos"));
						
		return new User(usuario.getEmail(), usuario.getSenha(), new HashSet<>());
	}

}
