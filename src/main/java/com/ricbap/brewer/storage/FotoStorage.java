package com.ricbap.brewer.storage;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {
	
	public final String THUMBNAIL_PREFIX = "thumbnail.";
	
	//public String salvarTemporariamente(MultipartFile[] files);

	public String salvar(MultipartFile[] files);
	
	//public byte[] recuperarFotoTemporaria(String nome);

	//public void salvar(String foto);

	public byte[] recuperar(String foto);
	
	public byte[] recuperarThumbnail(String fotoCerveja);

	public void excluir(String foto);

	public String getUrl(String foto);
	
	default String renomearArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}
}
