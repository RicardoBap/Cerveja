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
	
    class MaskPhoneNumber {
        constructor() {
            this.inputPhoneNumber = (".js-phone-number");
        }
        enable() {
            var SPMaskBehavior = function(val) {
                return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
            };
            var spOptions = {
                onKeyPress: function(val, e, field, options) {
                    field.mask(SPMaskBehavior.apply({}, arguments), options);
                }
            };
            this.inputPhoneNUmber.mask(SPMaskBehavior, spOptions);
        }
    }
	
	return MaskPhoneNumber;
})


$(function() {
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
	
	var maskPhoneNumber = new Brewer.MaskPhoneNumber();
	maskPhoneNumber.enable();
})



/*$(function() {
	var decimal = $('.js-decimal')
	decimal.maskMoney({ decimal:',', thousands:'.' } );
	
	var plain = $('.js-plain')
	plain.maskMoney({ precision: 0, thousands: '.' });
})*/