Brewer.Venda = (function() {
	
	function Venda(tabelaItens) {
		this.tabelaItens = tabelaItens;
		this.valorTotalBox = $('.js-valor-total-box');
		this.valorFreteInput = $('#valorFrete');
		this.valorDescontoInput = $('#valorDesconto');
		this.valorTotalBoxContainer = $('.js-valor-total-box-container');
		
		this.valorTotalItens = 0;// this.tabelaItens.valorTotal();  // <--------
		this.valorFrete = 0; // this.valorFreteInput.data('valor'); // <---------
		this.valorDesconto = 0; // this.valorDescontoInput.data('valor'); // <-----
	}
	
	Venda.prototype.iniciar = function() {
		this.tabelaItens.on('tabela-itens-atualizada', onTabelaItensAtualizada.bind(this));
		this.valorFreteInput.on('keyup', onValorFreteAlterado.bind(this));
		this.valorDescontoInput.on('keyup', onValorDescontoAlterado.bind(this));
		
		this.tabelaItens.on('tabela-itens-atualizada', onValoresAlterados.bind(this));
		this.valorFreteInput.on('keyup', onValoresAlterados.bind(this));
		this.valorDescontoInput.on('keyup', onValoresAlterados.bind(this));
		
		//onValoresAlterados.call(this);  // <------------
	}
	
	function onTabelaItensAtualizada(evento, valorTotalItens) {
		this.valorTotalItens = valorTotalItens == null ? 0 : valorTotalItens;
		//this.valorTotalBox.html(v); // <---------------------(Brewer.formatarMoeda(v));
	}
	
	function onValorFreteAlterado(evento) {
		this.valorFrete = $(evento.target).val();
		console.log('valor frete', Brewer.formatarMoeda($(evento.target).val()));  // Brewer.recuperarValor($(evento.target).val())
	}
	
	function onValorDescontoAlterado(evento) {
		this.valorDesconto = $(evento.target).val();   //  $(evento.target).val()
	}
	
	function onValoresAlterados() {
		var valorTotal = this.valorTotalItens + this.valorFrete - this.valorDesconto;
		//var valorTotal = numeral(this.valorTotalItens) + numeral(this.valorFrete) - numeral(this.valorDesconto);
		this.valorTotalBox.html(Brewer.formatarMoeda(valorTotal)); // Brewer.formatarMoeda(v));
		
		this.valorTotalBoxContainer.toggleClass('negativo', valorTotal < 0);
	}
	
	return Venda;
	
}());


$(function() {
	var autocomplete = new Brewer.Autocomplete();
	autocomplete.iniciar();
	
	var tabelaItens = new Brewer.TabelaItens(autocomplete);
	tabelaItens.iniciar();
	
	var venda = new Brewer.Venda(tabelaItens);
	venda.iniciar();
})