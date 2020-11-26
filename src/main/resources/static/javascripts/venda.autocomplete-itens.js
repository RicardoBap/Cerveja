Brewer = Brewer || {};

Brewer.Autocomplete = (function() {
	
	function Autocomplete() {
		this.skuOuNomeInput = $('.js-sku-nome-cerveja-input');
		this.htmlTemplateAutocomplete = $('#template-autocomplete-cerveja').html();
		this.template = Handlebars.compile(this.htmlTemplateAutocomplete);
	}
	
	Autocomplete.prototype.iniciar = function() {
		var options = {
				url: function(skuOuNome) {
					return '/brewer/cervejas?skuOuNome=' + skuOuNome;
				},
				getValue: 'nome',
				minCharNumber: 3,
				requestDelay: 300,
				ajaxSettings: {
					contentType: 'application/json'
				},
				template: {
					type: 'custom',
					method: function(nome, cerveja) {
						//console.log(arguments);
						cerveja.valorFormatado = Brewer.formatarMoeda(cerveja.valor);
						return this.template(cerveja);
					}.bind(this)
				}
				
		};
		
		this.skuOuNomeInput.easyAutocomplete(options);
	}
	
	return Autocomplete;
	
}());

$(function() {
	var autocomplete = new Brewer.Autocomplete();
	autocomplete.iniciar();
})