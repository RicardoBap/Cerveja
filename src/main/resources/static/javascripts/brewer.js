var Brewer = Brewer || {};

Brewer.MaskMoney = (function() {
	
    class MaskMoney {
        constructor() {
            this.decimal = $('.js-decimal');
            this.plain = $('.js-plain');
        }
        enable() {
            this.decimal.maskMoney({ decimal: ',', thousands: '.' });
            this.plain.maskMoney({ precision: 0, thousands: '.' });
        }
    }		
	return MaskMoney;	
})();


Brewer.MaskPhoneNumber = (function() {
	
	function MaskPhoneNumber() {
		this.inputPhoneNumber = $('.js-phone-number');
	}
	
	MaskPhoneNumber.prototype.enable = function() {
		var maskBehavior = function (val) {
			  return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
		};
		var options = {
		  onKeyPress: function(val, e, field, options) {
		      field.mask(maskBehavior.apply({}, arguments), options);
		    }
		};
		this.inputPhoneNumber.mask(maskBehavior, options);
	}	
	return MaskPhoneNumber;	
})();

Brewer.MaskCep = (function() {
	
	function MaskCep() {
		this.inputCep = $('.js-cep');
	}
	
	MaskCep.prototype.enable = function() {
		var options =  {
				onKeyPress: function(cep, e, field, options) {
					var masks = ['00000-000', '0-00-00-00'];
				    var mask = (cep.length>7) ? masks[1] : masks[0];
				    $('.crazy_cep').mask(mask, options);
		}};
		this.inputCep.mask('00000-000', options);
	}	
	return MaskCep;
}());


$(function() {
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();	
	
	var maskPhoneNumber = new Brewer.MaskPhoneNumber();
	maskPhoneNumber.enable();
	
	var maskCep = new Brewer.MaskCep();
	maskCep.enable();
})


/*$(function() {
	var decimal = $('.js-decimal')
	decimal.maskMoney({ decimal:',', thousands:'.' } );
	
	var plain = $('.js-plain')
	plain.maskMoney({ precision: 0, thousands: '.' });
})*/