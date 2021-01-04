package com.ricbap.brewer.storage.s3;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ricbap.brewer.storage.FotoStorage;

@Profile("nuvem")
@Component
public class FotoStotageS3 implements FotoStorage {

	@Override
	public String salvar(MultipartFile[] files) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @Override public byte[] recuperarFotoTemporaria(String nome) { return null; }
	 */

	/*
	 * @Override public void salvar(String foto) { }
	 */

	@Override
	public byte[] recuperar(String foto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] recuperarThumbnail(String fotoCerveja) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void excluir(String foto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getUrl(String foto) {
		// TODO Auto-generated method stub
		return null;
	}

}
