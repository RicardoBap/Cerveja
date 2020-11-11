package com.ricbap.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ricbap.brewer.model.Usuario;
import com.ricbap.brewer.repository.UsuarioRepository;
import com.ricbap.brewer.service.exception.EmailUsuarioJaCadastradoException;
import com.ricbap.brewer.service.exception.SenhaObrigatoriaUsuarioException;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public void cadastrar(Usuario usuario) {
		Optional<Usuario> emailExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if(emailExistente.isPresent()) {
			throw new EmailUsuarioJaCadastradoException("E-mail já cadastrado");
		}
		
		if(usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaUsuarioException("Senha obrigatória para novo usuário");
		}
		
		usuarioRepository.save(usuario);
	}

}
