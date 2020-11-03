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

$(function() {
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
})

/*$(function() {
	var decimal = $('.js-decimal')
	decimal.maskMoney({ decimal:',', thousands:'.' } );
	
	var plain = $('.js-plain')
	plain.maskMoney({ precision: 0, thousands: '.' });
})*/