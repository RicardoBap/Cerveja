package com.ricbap.brewer.mail;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ricbap.brewer.model.Cerveja;
import com.ricbap.brewer.model.ItemVenda;
import com.ricbap.brewer.model.Venda;
import com.ricbap.brewer.storage.FotoStorage;

@Component
public class Mailer {
	
	private static final Logger logger = LoggerFactory.getLogger(Mailer.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine thymeleaf;	
	
	@Autowired
	private FotoStorage fotoStotage;
	
	@Async
	public void enviar(Venda venda) {			
		
		Context context = new Context();
		context.setVariable("venda", venda);
		context.setVariable("logo", "logo");
		
		Map<String, String> fotos = new HashMap<>();
		boolean adicionarMockCerveja = false;
		for(ItemVenda item : venda.getItens()) {
			Cerveja cerveja = item.getCerveja();
			if(cerveja.temFoto()) {
				String cid = "foto-" + cerveja.getCodigo();
				context.setVariable(cid, cid);
				
				fotos.put(cid, cerveja.getFoto() + "|" + cerveja.getContentType());
			} else {
				adicionarMockCerveja = true;
				context.setVariable("mockCerveja", "mockCerveja");
			}
		}
		
		try {
			String email = thymeleaf.process("mail/ResumoVenda", context);
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setFrom("");
			helper.setTo(venda.getCliente().getEmail());
			helper.setSubject("Brewer - Venda realizada");
			helper.setText(email, true);
			
			helper.addInline("logo", new ClassPathResource("static/images/logo-gray.png"));
			
			if(adicionarMockCerveja) {
				helper.addInline("mockCerveja", new ClassPathResource("static/images/cerveja-mock.png"));
			}
			
			for (String cid : fotos.keySet()) {
				String[] fotoContentType = fotos.get(cid).split("\\|");
				String foto = fotoContentType[0];
				String contentType = fotoContentType[1];
				
				byte[] arrayFoto = fotoStotage.recuperarThumbnail(foto);
				helper.addInline(cid, new ByteArrayResource(arrayFoto), contentType);
			}
			
			mailSender.send(mimeMessage);
		} catch(MessagingException e) {
			logger.error("Erro enviando e-mail", e);
		}
		
	}

}

/*
SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom("ricbapdevs@gmail.com");
		mensagem.setTo(venda.getCliente().getEmail());
		mensagem.setSubject("Venda efetuada");
		mensagem.setText("Obrigado sua venda foi processada");
		
		mailSender.send(mensagem);
*/



/*
 * System.out.println(">>>>> enviando e-mail.... " );
 * 
 * try { Thread.sleep(10000); } catch(InterruptedException e) {
 * e.printStackTrace(); }
 * 
 * System.out.println(">>>> e-mail enviado!");
 * 
 * }
 */