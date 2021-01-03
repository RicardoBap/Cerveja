package com.ricbap.brewer.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {
	
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("ric_bap@mail"));
	}

}
