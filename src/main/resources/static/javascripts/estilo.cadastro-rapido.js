var Brewer = Brewer || {};

Brewer.EstiloCadastroRapido = (function() {
	
    class EstiloCadastroRapido {
        constructor() {
            this.modal = $('#modalCadastroRapidoEstilo');
            this.botaoSalvar = this.modal.find('.js-modal-cadastro-estilo-salvar-botao');
            this.form = this.modal.find('form');
            this.url = this.form.attr('action');
            this.inputNomeEstilo = $('#nomeEstilo');
            this.containerMensagemErro = $(".js-mensagem-cadastro-rapido-estilo");
        }
        iniciar() {
            this.form.on('submit', function(event) { event.preventDefault(); });
            this.modal.on('shown.bs.modal', onModalShow.bind(this));
            this.modal.on('hide.bs.modal', onModalClose.bind(this));
            this.botaoSalvar.on('click', onBotaoSalvarClick.bind(this));
        }
    }
	
	
	function onModalShow() {
		this.inputNomeEstilo.focus();
	}
	
	function onModalClose() {
		this.inputNomeEstilo.val('');
		this.containerMensagemErro.addClass('hidden');
		this.form.find('.form-group').removeClass('has-error');
	}
	
	function onBotaoSalvarClick() {
		var nomeEstilo = this.inputNomeEstilo.val().trim();		
		$.ajax({
			url:  this.url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ nome: nomeEstilo }),
			error: onErrorSalvandoEstilo.bind(this),
			success: onEstiloSalvo.bind(this)
		})
	}
	
	function onErrorSalvandoEstilo(obj) {
		var mensagemErro = obj.responseText;
		this.containerMensagemErro.removeClass('hidden');
		this.containerMensagemErro.html('<span>' + mensagemErro + '</span>');
		this.form.find('.form-group').addClass('has-error');		
	}
	
	function onEstiloSalvo(estilo) {
		var comboEstilo = $('#estilo'); 
		comboEstilo.append('<option value=' + estilo.codigo + '>' + estilo.nome + '</option>');
		comboEstilo.val(estilo.codigo);
		this.modal.modal('hide');		
	}	
	
	return EstiloCadastroRapido;
	
})();


$(function() {	
	
	var estiloCadastroRapido = new Brewer.EstiloCadastroRapido();
	estiloCadastroRapido.iniciar();	
	
})



/*$(function() {
	
	var modal = $('#modalCadastroRapidoEstilo');
	var botaoSalvar = modal.find('.js-modal-cadastro-estilo-salvar-botao');
	
	// não submeter o formulario ao apertar enter
	var form = modal.find('form');
	form.on('submit', function(event) { event.preventDefault() });
	
	var url = form.attr('action');
	var inputNomeEstilo = $('#nomeEstilo')
	
	var containerMensagemErro = $(".js-mensagem-cadastro-rapido-estilo"); // variavel que define mensagem do alert
	
	modal.on('shown.bs.modal', onModalShow); // ao abrir modal
	modal.on('hide.bs.modal', onModalClose); // ao fechar modal
	
	botaoSalvar.on('click', onBotaoSalvarClick); // adiciona evento de click do botao
	
	//alto focus ao abrir modal
	function onModalShow() {
		inputNomeEstilo.focus();
	}
	
	// se tiver ao escrito no campo, limpa o modal quando fechar
	function onModalClose() {
		inputNomeEstilo.val('');
		containerMensagemErro.addClass('hidden');
		form.find('.form-group').removeClass('has-error');
	}
	
	// executa AJAX para o servidor para salvar estilo
	function onBotaoSalvarClick() {
		var nomeEstilo = inputNomeEstilo.val().trim();
		//console.log("nome estilo", nomeEstilo);
		$.ajax({
			url:  url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ nome: nomeEstilo }),
			error: onErrorSalvandoEstilo,
			success: onEstiloSalvo
		})
	}
	
	function onErrorSalvandoEstilo(obj) {
		var mensagemErro = obj.responseText;
		containerMensagemErro.removeClass('hidden');
		containerMensagemErro.html('<span>' + mensagemErro + '</span>');
		form.find('.form-group').addClass('has-error');
		//console.log(arguments);
	}
	
	function onEstiloSalvo(estilo) {
		var comboEstilo = $('#estilo'); // atributo #estilo no template de cadfastro de cerveja
		comboEstilo.append('<option value=' + estilo.codigo + '>' + estilo.nome + '</option>');
		comboEstilo.val(estilo.codigo);
		modal.modal('hide');
		//console.log(arguments);
	}	
	
})*/
