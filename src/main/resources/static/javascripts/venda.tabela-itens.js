Brewer.TabelaItens= (function() {
	
	function TabelaItens(autocomplete) {
		this.autocomplete = autocomplete;
		this.tabelaCervejasContainer = $('.js-tabela-cerveja-container');
	}
	
	TabelaItens.prototype.iniciar = function() {
		this.autocomplete.on('item-selecionado', onItemSelecionado.bind(this));
	}
	
	function onItemSelecionado(evento, item) {
		//console.log('Item recebido do autocomplete', item);
		var resposta = $.ajax({
			url: 'item',
			method: 'POST',
			data: {
				codigoCerveja: item.codigo
			}
		});
		resposta.done(onItemAtualizadoNoServidor.bind(this));
	}
	
	function onItemAtualizadoNoServidor(html) {
		this.tabelaCervejasContainer.html(html);
		$('.js-tabela-cerveja-quantidade-item').on('change', onQuantidadeItensAlterado.bind(this));
	}
	
	function onQuantidadeItensAlterado(evento) {
		var input = $(evento.target);
		var quantidade = input.val();
		//console.log('novaQuantidade', quantidade)
		var codigoCerveja = input.data('codigo-cerveja');
		
		var resposta = $.ajax({
			url: 'item/' + codigoCerveja,
			method: 'PUT',
			data: {
				novaQuantidade: quantidade
			}
		});
		resposta.done(onItemAtualizadoNoServidor.bind(this));
	}
	
	return TabelaItens;
	
}());

$(function() {
	var autocomplete = new Brewer.Autocomplete();
	autocomplete.iniciar();
	
	var tabelaItens = new Brewer.TabelaItens(autocomplete);
	tabelaItens.iniciar();
})