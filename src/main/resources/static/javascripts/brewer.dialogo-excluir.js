Brewer = Brewer || {};

Brewer.DialogoExcluir = (function() {
	
	function DialogoExcluir() {
		this.exclusaotBtn = $('.js-exclusao-btn')
	}
	
	DialogoExcluir.prototype.iniciar = function() {
		this.exclusaotBtn.on('click', onExcluirClicado.bind(this));
		
		if(window.location.search.indexOf('excluido') > -1) {
			swal('Pronto!', 'Excluido com sucesso!', 'success');
		}
	}
	
	function onExcluirClicado(evento) {
		event.preventDefault();
		var botaoClicado = $(evento.currentTarget);
		var url = botaoClicado.data('url');
		var objeto = botaoClicado.data('objeto');
		
		swal({
			title: 'Tem certeza?',
			text: 'Excluir "' + objeto + '"? Você não poderá recuperar depois.',
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Sim, exclua agora!',
			CloseOnConfirm: true
		}, onExcluirConfirmado.bind(this, url));
	}
	
	function onExcluirConfirmado(url) {
		//console.log('url', url);
		$.ajax({
			url: url,
			method: 'DELETE',
			success: onExcluidoSucesso.bind(this),
			error: onErroExcluir.bind(this)
		})
	}
	
	function onExcluidoSucesso() {
		//window.location.reload();
		var urlAtual = window.location.href;
		var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separador + 'excluido';
		
		window.location = novaUrl;
	}
	
	function onErroExcluir(e) {
		swal('Oops!', e.responseText, 'error');
	}
	
	return DialogoExcluir;
	
}());

$(function() {
	var dialogo = new Brewer.DialogoExcluir();
	dialogo.iniciar();
});