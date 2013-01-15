// Tag Field   						v0.0.1
// License:               http://creativecommons.org/licenses/by/2.5/
// Author:                William Lindmeier (http://wdlindmeier.com/)

function updateTags(input,event){
	var e = event || window.event;
	var fieldTags = input.value.split(',').map(function(v,i){return v.strip()});
	var remainder = fieldTags.pop();
	var tagViewer = $(input.getAttribute('tagged_items'));	
	var tagMeasurer = $(input.getAttribute('tag_measurement'));
  tagViewer.innerHTML = fieldTags.length > 0 ? 
			'<span class="tagged_item">'+fieldTags.join('</span><span class="tagged_item">')+'</span>' : 
			'';				
	if(e.keyCode != 8){
		input.value = input.value.replace(/\s*,\s*/g, ' ,  ').replace(/\b\s+\b/g, ' ');
	}else if($(input).value == ''){
		tagViewer.innerHTML = '';
	} 
	tagMeasurer.innerHTML = input.value.replace(/\s{2}/g, ' &nbsp;'); 
	// measure the stuff
	var tagsWidth = $(tagMeasurer).getWidth();
	if(tagsWidth > 200){
		var inputWidth = (tagsWidth+10);
		$(input).style.width = inputWidth+'px';
		$(input).style.marginLeft = tagViewer.style.marginLeft = (200-inputWidth)+'px';
	}else{
		$(input).style.width = '200px';
		$(input).style.marginLeft = tagViewer.style.marginLeft = 0;
	}
}

document.observe('dom:loaded', function(){
	$$('input.taggable').each(function(field, index){
		var divid = field.id ? field.id+'_tagged_items' : '_'+Math.round(Math.random()*100000)+'_';
		field.id = field.id || field.id+'_taggable_field';
		var tagWindow = document.createElement('div');
		tagWindow.setAttribute('id', divid+'tag_window');
		tagWindow.setAttribute('class', 'tag_window');
		field.parentNode.replaceChild(tagWindow, field);
		tagWindow.appendChild(field);
		$(field).insert({after:'<div class="tagged_items" id="'+divid+'tagged_items"></div><div class="tag_measurement" id="'+divid+'tag_measurement"></div>'});		
		field.setAttribute('tagged_items', divid+'tagged_items');
		field.setAttribute('tag_measurement', divid+'tag_measurement');
		field.onkeyup = function(event){
			updateTags(field, event);
		}.bind(field);
	});
});

// CSS Browser Selector   v0.2.5
// Documentation:         http://rafael.adm.br/css_browser_selector
// License:               http://creativecommons.org/licenses/by/2.5/
// Author:                Rafael Lima (http://rafael.adm.br)
// Contributors:          http://rafael.adm.br/css_browser_selector#contributors

var css_browser_selector = function() {
	var 
		ua=navigator.userAgent.toLowerCase(),
		is=function(t){ return ua.indexOf(t) != -1; },
		h=document.getElementsByTagName('html')[0],
		b=(!(/opera|webtv/i.test(ua))&&/msie (\d)/.test(ua))?('ie ie'+RegExp.$1):is('gecko/')? 'gecko':is('opera/9')?'opera opera9':/opera (\d)/.test(ua)?'opera opera'+RegExp.$1:is('konqueror')?'konqueror':is('applewebkit/')?'webkit safari':is('mozilla/')?'gecko':'',
		os=(is('x11')||is('linux'))?' linux':is('mac')?' mac':is('win')?' win':'';
	var c=b+os+' js';
	h.className += h.className?' '+c:c;
}();
