/*www.indas.ru, bna */
(function ($) {
'use strict';

 function ih_snipper_OnPointerDown(elm, callback) {
  elm.on("mousedown", function (e) {
   e.preventDefault();
   callback(e);
  });
 }
 function ih_snipper_OnPointerUp(elm, callback) {
  elm.on("mouseup mouseout","button", function (e) {
   callback(e);
  });
 }

 class IHSpinnerImpl {
  step=1;
  autoDelayHandler = null;
  autoIntervalHandler = null;
  container=null;
  btnDec=null;
  btnInc=null;
  value = 0;
  min=-Infinity;
  max=Infinity;
  constructor(el, implementOptions) {
   this.elm = el;
   this.ioptions = implementOptions;
   this._init();
  }
  _init(){
   this.min =parseInt(this.ioptions.min||NaN);
   this.max =parseInt(this.ioptions.max||NaN);

   this.min=isNaN(this.ioptions.min)? -Infinity : parseInt(this.ioptions.min);
   this.max =isNaN(this.ioptions.max)? Infinity : parseInt(this.ioptions.max);
   this._buildInputGroup();
  }
  _buildInputGroup() {
    let self=this;
    let html='<div class="input-group ${groupClass}"></div>';
    html=html.replace(/\${groupClass}/g, this.ioptions.groupClass)
    this.container = this.elm.wrap(html).parent();
    html='<div class="input-group-prepend"><button style="min-width: 2.5rem" class="btn btn-decrement btn-outline-secondary btn-minus" type="button"><i class="fa fa-minus"></i></button></div>';
    $(html).insertBefore(this.elm);
    html='<div class="input-group-append"><button style="min-width: 2.5rem" class="btn btn-increment btn-outline-secondary btn-plus" type="button"><i class="fa fa-plus"></i></button></div>';
    $(html).insertAfter(this.elm);
    this.elm.addClass("form-control form-control-text-input text-center");
    this.elm.width(this.ioptions.width);
    if (this.ioptions.readonly){
     this.elm.prop('readonly', true);
    }else{
     this.elm.prop('readonly', false);
    }
    this._setValue(0,false);
    this.btnDec = this.container.find(".btn-decrement");
    this.btnInc = this.container.find(".btn-increment");
    ih_snipper_OnPointerDown(this.btnDec, function () {
     self._stepHandling(-self.step);
    });
    ih_snipper_OnPointerDown(this.btnInc, function () {
     self._stepHandling(self.step);
    });
    ih_snipper_OnPointerUp(this.container, function () {
     self._resetTimer();
    });
  }
  setVal_withoutEventInput(newValue){
    this._setValue(newValue,false);
  }
  setVal_withEventInput(newValue){
         this._setValue(newValue,true);
  }
  _setValue(newValue,genEvent) {
   try{newValue=parseInt(newValue);}catch(e){}
   if (isNaN(newValue)) {
    this.value=0;
   } else {
    newValue = Math.min(Math.max(newValue, this.min), this.max);
    this.value = newValue;
   }
   if (genEvent) {
       this.elm.val(this.value).trigger('input');
   }else{
       this.elm.val(this.value);
   }
  }
  _calcStep(step) {
   if (isNaN(this.value)) {
    this.value = 0;
   }
   this._setValue(this.value+step,true);
  }
  _stepHandling(step) {
   let self=this;
   this._calcStep(step);
   this._resetTimer();
   this.autoDelayHandler = setTimeout(function () {
       self.autoIntervalHandler = setInterval(function () {
     self._calcStep(step);
    }, 60);
   }, 500);
  }
  _resetTimer() {
   clearTimeout(this.autoDelayHandler);
   clearInterval(this.autoIntervalHandler);
  }
  remove(){
    this._resetTimer();
    this.btnDec.off();
    this.btnInc.off();
    this.container.off();
    this._resetTimer();
    this.container.find('*').not("input").remove();
    this.elm.unwrap();
    this.container.remove();
    this.elm.removeData();
    this.btnDec=null;
    this.btnInc=null;
    this.container=null;
    this.elm=null;
    this.ioptions=null;
  }
 }

 $.fn.IHSpinner = function(options) {
  var implementOptions =$.extend({
   width:"100px",
   readonly:true,
   groupClass:""
  }, options);
  this.each(function() {
   let el = $(this);
   if (el.data('IHSpinner'))
    el.data('IHSpinner').remove();
    el.data('IHSpinner', new IHSpinnerImpl(el, implementOptions));
  });
  return this;
 };

}(jQuery));