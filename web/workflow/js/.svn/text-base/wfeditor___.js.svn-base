// global variables
var canvasSVG;		// Raphael paper for links
var canvas;			// jQuery object of divCanvas
var currentFocus;	// currently focused element;
var nextId = 0;
var linkMenu;
var artefactMenu;
var processMenu;
var edges;
var artefactList = {};
var processList = {};
var baseUrl = '/ourspaces/';
var propertyInputs = {
	integer: $('<input type="text"></input>').addClass('InetgerInput').blur(checkInteger),
	string: $('<input type="text"></input>').addClass(''),
	decimal: $('<input type="text"></input>').addClass('DecimalInput').blur(checkDecimal),
	time: $('<input type="text"></input>').addClass('TimeInput').blur(checkTime),
	date: $('<div/>').append($('<input type="text"></input>').addClass('DateInput').datepicker())
};

$(function(){
	canvas = $('#divCanvas');
	canvasSVG = Raphael(document.getElementById('divCanvas'), 900, 700);

	initComponent();
	initCanvas();
	initMenus();
	initLinkDialogue();
	initProcessMetaDialog();

	$('#btSave').click(onSave);

	// Fix the nested draggable bug in IE
	// http://dev.jqueryui.com/ticket/4333#comment:10
	$.extend($.ui.draggable.prototype, (function (orig) {
	  return {
		_mouseCapture: function (event) {
		  var o = this.options;

				  if (this.helper || o.disabled || $(event.target).is('.ui-resizable-handle'))
					  return false;

				  //Quit if we're not on a valid handle
				  this.handle = this._getHandle(event);
				  if (!this.handle)
					  return false;

				  /*change by Titkov Anton : bug #4333 (http://dev.jqueryui.com/ticket/4333)*/
				  if ($.browser.msie) event.stopPropagation();
				  /*change by Titkov Anton*/

				  return true;
		}
	  };
	})($.ui.draggable.prototype["_mouseCapture"]));
});

/**
 * initialize component nodes and place them in the toobox
 *
 */
function initComponent() {
	var artefactBox = $('#divArtefactBox');
	var processBox = $('#divProcessBox');

	var url = baseUrl + 'workflow/json/';
	$.getJSON(url, {method : 'getResource'}, function(jsonRes) {
		if(jsonRes.responseStatus == 'SUCCESS') {
			artefacts = jsonRes.data.resource;
			for(var i = 0; i < artefacts.length; i++) {
				createArtefactNode(artefacts[i]).appendTo(artefactBox);
				artefactList[artefacts[i].uri] = artefacts[i];
			}
		}
	});

	$.getJSON(url, {method: 'getProcess'}, function(jsonRes) {
		if(jsonRes.responseStatus == 'SUCCESS') {
			processRoot = jsonRes.data.processRoot;
			for(var i = 0; i < processRoot.subClasses.length; i++) {
				createProcessNode(processRoot.subClasses[i], 0).appendTo(processBox)
			}
		}
	});
}

function initCanvas() {
	$('#divCanvas').click(function(event) {
		if(currentFocus) {
			currentFocus.blur();
			currentFocus = null;
		}
	});
	canvas.droppable({
		accept: '.component_node',
		drop: function(event, ui) {
			if(ui.draggable.attr('toolbox') == 'true') {
				newNodeArrived(ui.draggable, ui.helper);
			}
		},
		over: function(event, ui) {
			//ui.helper.css({'background-color': 'red'});
		}
	});
}

function initMenus() {
	linkMenu = $('#ulLinkMenu').blur(function(event) {$(this).hide()}).hide();
	$('#mLinkEdit').click(onLinkEdit);
	$('#mLinkDelete').click(onLinkDelete);

	artefactMenu = $('#ulArtefactMenu').blur(function(event) {$(this).hide()}).hide();
	$('#mArtefactDelete').click(onArtefactDelete);

	processMenu = $('#ulProcessMenu').blur(function(event) {$(this).hide()}).hide();
	$('#mProcessMetaEdit').click(onProcessMetaEdit);
	$('#mProcessDelete').click(onProcessDelete);
}

function createArtefactNode(artefact) {
	var nodeElm = $('<div/>').html('<span class="NodeName">' + artefact.title + '</span>').addClass('component_node artefact').attr({toolbox: true, uri: artefact.id});
	nodeElm.draggable({ helper: 'clone', appendTo: '#divCanvas', revert: 'invalid'});
	return nodeElm;
}

function createProcessNode(process, marginLeft) {
	var nodeWrap = $('<div/>').css('margin-left', marginLeft + 'px');
	var icon = $('<div/>').addClass('treenode').appendTo(nodeWrap);
	var node = $('<div/>').append('<div class="NodeName">' + process.localName + '</div>').addClass('component_node process').attr({toolbox: true, classUri: process.uri}).draggable({ helper: 'clone', appendTo: canvas, revert: 'invalid'}).appendTo(nodeWrap);
	if(process.subClasses.length > 0) {
		icon.addClass('open');
		icon.toggle(
			function(e1) {
				var icon = $(this);
				icon.next().next().hide();
				icon.removeClass('open').addClass('close');
			},
			function(e2) {
				var icon = $(this);
				icon.next().next().show();
				icon.removeClass('close').addClass('open');
			}
		);
		var subNodeWrapper = $('<div/>').appendTo(nodeWrap);
		for(var i = 0; i < process.subClasses.length; i++) {
			subNodeWrapper.append(createProcessNode(process.subClasses[i], marginLeft + 15));
		}
	}
	else {
		icon.addClass('leaf');
	}
	processList[process.uri] = process;
	return nodeWrap;
}

function newNodeArrived(newNode, helper) {
	var nodeCopy = newNode.clone();
	var pos = helper.position();
	var siblingHeight = 0;
	helper.prevAll().each(function(index, element) {
		siblingHeight += $(this).outerHeight();
	});
	nodeCopy.data('siblingHeight', siblingHeight);

	nodeCopy.appendTo(canvas).attr({
		'toolbox': false,
		'canvas': true,
		'id': 'cn' + nextId++
	}).css({
		'position': 'relative',
		'top': (pos.top - siblingHeight) + 'px',
		'left': pos.left + 'px'
	}).draggable({
		revert: 'invalid',
		stop: function(event, ui) {
			updateLinks(ui.helper);
			canvasOffset = canvas.offset();
			var siblingHeight = $(this).data('siblingHeight');
			alert($(this).data('siblingHeight'));
			if((ui.position.top + siblingHeight + ui.helper.outerHeight()) > canvas.height() || (ui.position.left + ui.helper.outerWidth()) > canvas.width()) {
				var newHeight = ui.position.top + siblingHeight + ui.helper.outerHeight();
				var newWidth = ui.position.left + ui.helper.outerWidth();
				canvasSVG.setSize(newWidth, newHeight);
			}
		}
	}).droppable({
		accept: function(node) {
			var offsetParent = node.offsetParent();
			if((offsetParent.attr('id') != $(this).attr('id')) &&
				node.hasClass('dragHandler') &&
				( (offsetParent.hasClass('artefact') && $(this).hasClass('process')) || (offsetParent.hasClass('process') && $(this).hasClass('artefact')) ) ) {
				return true;
			}
			return false;
		},
		drop: function(event, ui) {
			var fromNode = ui.draggable.offsetParent();
			var toNode = $(this);
			createLink(fromNode, toNode);
		}
	}).data('linksFrom', {}).data('linksTo', {});

	var dragHandler = $('<div class="dragHandler"/>').draggable({
		helper: 'clone',
		//containment: '#divCanvas',
		revert: 'invalid',
		start: function(event, ui) {
			var canvasOffset = canvas.offset();
			var canvasWidth = canvas.width();
			var canvasHeight = canvas.height();
			var canvasTop = $('<div id="divCanvasTop"/>').css({
				'position': 'absolute',
				'top': canvasOffset.top + 'px',
				'left': canvasOffset.left + 'px',
				'width': canvasWidth + 'px',
				'height': canvasHeight + 'px',
				'z-index': '300'
			}).appendTo($('body'));
			var canvasTopSVG = Raphael(document.getElementById('divCanvasTop'), canvasWidth, canvasHeight);
			$(this).data('canvasTop', canvasTop);
			$(this).data('canvasTopSVG', canvasTopSVG);
		},
		drag: function(event, ui) {
			var canvasTopSVG = $(this).data('canvasTopSVG');
			if(canvasTopSVG) canvasTopSVG.clear();
			var startX = $(this).offset().left - canvas.offset().left;
			var startY = $(this).offset().top - canvas.offset().top;
			var endX = ui.helper.offset().left - canvas.offset().left;
			var endY = ui.helper.offset().top - canvas.offset().top;
			var path = canvasTopSVG.path('M ' + startX + ' ' + startY + ' L ' + endX + ' ' + endY);
		},
		stop: function(event, ui) {
			$(this).data('canvasTopSVG').remove();
			$(this).data('canvasTop').remove();
		}
	}).appendTo(nodeCopy);

	if(nodeCopy.hasClass('artefact')) {
		// For artefacts, make sure only one instance is in the canvase
		// disable draggable once it has dropped into canvase
		newNode.draggable( "option", "disabled", true );
		newNode.addClass('disabledComponetNode');

		nodeCopy.click(onArtefactClick);
	}
	else {
		nodeCopy.click(onProcessClick);
	}
}

/**
 * Draw a link from nodeA to nodeB
 *
 */
function drawLink(nodeA, nodeB, handler) {
	var canvasOffset = canvas.offset();
	var offsetA = nodeA.position();
	var offsetB = nodeB.position();

	var positionA = {top: offsetA.top/* + nodeA.data('siblingHeight')*/, left: offsetA.left};
	var positionB = {top: offsetB.top/* + nodeB.data('siblingHeight')*/, left: offsetB.left};
alert(offsetA.top + '\n' + positionA.top + '\n' + nodeA.data('siblingHeight'));
	var distanceX = Math.abs(positionA.left - positionB.left);
	var distanceY = Math.abs(positionA.top - positionB.top);

	var nodeAWidth = nodeA.outerWidth();
	var nodeAHeight = nodeA.outerHeight();
	var nodeBWidth = nodeB.outerWidth();
	var nodeBHeight = nodeB.outerHeight();
	var canvasHeight = canvas.height();
	var canvasWidth = canvas.width();
	var siblingHeight = 0;
	handler.prevAll().each(function(index, element) {
		//alert($(this).attr('tagName') + '\n' + $(this).attr('id'));
		siblingHeight += $(this).outerHeight();
	});
	if(distanceX >= distanceY) { 	//horizontal line first
		var aYPercent = positionA.top / (canvasHeight - nodeAHeight);
		var bYPercent = positionB.top / (canvasHeight - nodeBHeight);

		if(positionA.left < positionB.left) {
			var start = {x: positionA.left + nodeAWidth, y: positionA.top + nodeAHeight * bYPercent};
			var turn = {x: start.x + (positionB.left - start.x) / 2, Y: start.y};
			var end = {x: positionB.left, y: positionB.top + nodeBHeight * aYPercent};
		}
		else {
			var start = {x: positionA.left, y: positionA.top + nodeAHeight * bYPercent};
			var turn = {x: start.x + (positionB.left - start.x) / 2, Y: start.y};
			var end = {x: positionB.left + nodeBWidth, y: positionB.top + nodeBHeight * aYPercent};
		}
		pathStr = 'M ' + start.x.toFixed() + ' ' + start.y.toFixed() + ' H ' + turn.x.toFixed() + ' V ' + end.y.toFixed() + ' H ' + end.x.toFixed();

		handler.removeClass('verticalLineHandler');
		if(!handler.hasClass('horizontalLineHandler')) {
			handler.addClass('horizontalLineHandler');
		}
		handler.css({top: (start.y - siblingHeight).toFixed() + 'px', left: (Math.abs(turn.x - start.x) * (end.x > start.x ? 0.26 : -0.26) + start.x).toFixed() + 'px'});
	}
	else {		//vertical line first
		var aXPercent = positionA.left / (canvasWidth - nodeAWidth);
		var bXPercent = positionB.left / (canvasWidth - nodeBWidth);

		if(positionA.top < positionB.top) {
			var start = {x: positionA.left + nodeAWidth * bXPercent, y: positionA.top + nodeAHeight};
			var turn = {x: start.x, y: start.y + (positionB.top - start.y) / 2};
			var end = {x: positionB.left + nodeBWidth * aXPercent, y: positionB.top};
		}
		else {
			var start = {x: positionA.left + nodeAWidth * bXPercent, y: positionA.top};
			var turn = {x: start.x, y: start.y + (positionB.top - start.y) / 2};
			var end = {x: positionB.left + nodeBWidth * aXPercent, y: positionB.top + nodeBHeight};
		}
		pathStr = 'M ' + start.x.toFixed() + ' ' + start.y.toFixed() + ' V ' + turn.y.toFixed() + ' H ' + end.x.toFixed() + ' V ' + end.y.toFixed();
		handler.removeClass('horizontalLineHandler');
		if(!handler.hasClass('verticalLineHandler')) {
			handler.addClass('verticalLineHandler');
		}
		handler.css({top: (Math.abs(turn.y - start.y) * (end.y > start.y ? 0.26 : -0.26) + start.y + handler.height() - siblingHeight).toFixed() + 'px', left: (start.x).toFixed() + 'px'});
	}

	var path = canvasSVG.path(pathStr).attr({stroke: '#036'});
	handler.data('line', path);
}

function createLink(fromNode, toNode) {
	if(linkExist(fromNode, toNode)) {
		alert('link already exists');
		return;
	}
	var linkId = 'cl-' + fromNode.attr('id') + '-' + toNode.attr('id');
	var linkHandler = $('<div/>').appendTo(canvas).attr('id', linkId);

	drawLink(fromNode, toNode, linkHandler);

	linkHandler.data('fromNode', fromNode);
	linkHandler.data('toNode', toNode);
	linkHandler.addClass('linkHandler');
	linkHandler.click(onLinkHandlerClick);

	if(fromNode.hasClass('artefact')) {
		linkHandler.data('artefact', fromNode);
		linkHandler.data('process', toNode);
	}
	else {
		linkHandler.data('artefact', toNode);
		linkHandler.data('process', fromNode);
	}

	fromNode.data('linksFrom')[linkId] = linkHandler;
	toNode.data('linksTo')[linkId] = linkHandler;

	showLinkDialogue(linkHandler);

	linkHandler.blur(function(event) {
		var svgLine = $(this).data('line');
		var currentPath = svgLine.attr('path');
		svgLine.remove();
		var newSvgLine = canvasSVG.path(currentPath);
		newSvgLine.attr({stroke: '#036'});
		$(this).data('line', newSvgLine);
	});

	return linkHandler;
}

function updateLinks(node) {
	var linksFrom = node.data('linksFrom');
	var linksTo = node.data('linksTo');

	for(linkId in linksFrom) {
		var linkHandler = linksFrom[linkId];
		linkHandler.data('line').remove();
		drawLink(node, linkHandler.data('toNode'), linkHandler);
	}

	for(linkId in linksTo) {
		var linkHandler = linksTo[linkId];
		linkHandler.data('line').remove();
		drawLink(linkHandler.data('fromNode'), node, linkHandler);
	}
}

function onLinkHandlerClick(event) {
	var linkHandler = $(this);

	var svgLine = linkHandler.data('line');
	var currentPath = svgLine.attr('path');
	svgLine.remove();
	var newSvgLine = canvasSVG.path(currentPath);
	newSvgLine.attr({stroke: '#600'});
	linkHandler.data('line', newSvgLine);
	if(currentFocus) {
		currentFocus.blur();
	}
	linkMenu.data('linkHandler', linkHandler);

	currentFocus = linkMenu.css({top: (event.pageY + 7) +'px', left: (event.pageX + 7) + 'px'}).show('blind', {}, 300);
	event.stopPropagation();
}

function onLinkEdit(event) {
	var linkHandler = $(this).offsetParent().data('linkHandler');
	fromNode = linkHandler.data('fromNode');
	toNode = linkHandler.data('toNode');
	$('#spanNodeA').text(fromNode.find('.NodeName').text());
	$('#spanNodeB').text(toNode.find('.NodeName').text());
	showLinkDialogue(linkHandler);
}

function onLinkDelete(event) {
	var linkHandler = $(this).offsetParent().data('linkHandler');
	deleteLink(linkHandler);
	linkMenu.blur();
	event.stopPropagation();
}

function deleteLink(linkHandler) {
	delete linkHandler.data('fromNode').data('linksFrom')[linkHandler.attr('id')];
	delete linkHandler.data('toNode').data('linksTo')[linkHandler.attr('id')];
	linkHandler.data('line').remove();
	linkHandler.remove();
}

function onArtefactClick(event) {
	artefactMenu.data('artefactNode', $(this));
	if(currentFocus) {
		currentFocus.blur();
	}
	currentFocus = artefactMenu.css({top: (event.pageY + 7) + 'px', left: (event.pageX + 7) + 'px'}).show('blind', {}, 300);
	event.stopPropagation();
}

function onArtefactDelete(event) {
	var artefactNode = $(this).offsetParent().data('artefactNode');
	var linksFrom = artefactNode.data('linksFrom');
	var linksTo = artefactNode.data('linksTo');

	uri = artefactNode.attr('uri');

	for(linkId in linksFrom) {
		linkHandler = linksFrom[linkId];
		deleteLink(linkHandler)
	}

	for(linkId in linksTo) {
		linkHandler = linksTo[linkId];
		deleteLink(linkHandler)
	}

	artefactNode.remove();

	$('#divArtefactBox div[uri="' + uri + '"]').draggable( "option", "disabled", false ).removeClass('disabledComponetNode');

	if(currentFocus) {
		currentFocus.blur();
	}

	event.stopPropagation();
}

function linkExist(fromNode, toNode) {
	var linkId1 = 'cl-' + fromNode.attr('id') + '-' + toNode.attr('id');
	var linkId2 = 'cl-' + toNode.attr('id') + '-' + fromNode.attr('id');

	return (document.getElementById(linkId1) || document.getElementById(linkId2)) ? true : false;
}

function initLinkDialogue() {
	$('#divLinkDialogue').hide();
	var url = baseUrl + 'workflow/json/';
	$.getJSON(url, {method : 'getEdge'}, function(jsonRes) {
		if(jsonRes.responseStatus == 'SUCCESS') {
			edges = jsonRes.data.edges;
			var options = document.getElementById('sltLinkRelation').options;
			for(var i = 0; i < edges.length; i++) {
				options[options.length] = new Option(edges[i].localName, i);
			}
		}
	});

	$('#divLinkDialogue').dialog({
		modal: true,
		title: 'Edit Link Information',
		height: 300,
		width: 470,
		autoOpen: false,
		buttons: {
			'OK': function() {
				var linkHandler = $(this).data('linkHandler');
				var relationVal = $('#sltLinkRelation').val();
				if(relationVal != "-1") {
					$('#sltLinkRelation').removeClass('errorInput');
					$('#spanLinkRelationError').hide();
				}
				else {
					$('#sltLinkRelation').addClass('errorInput');
					$('#spanLinkRelationError').text('Please select a relationship').addClass('errorMsg');
					$('#spanLinkRelationError').show();
					return;
				}
				if(currentFocus) {
					currentFocus.blur();
				}
				linkHandler.focus();
				$(this).dialog('close');
			},
			'Cancel': function() {
				$(this).dialog('close');
			}
		},
		close: function(event, ui) {
			var linkHandler = $(this).data('linkHandler');
			if(!linkHandler.data('edgeIndex')) {
				deleteLink(linkHandler);	   // delete the link if user did not select a relation when the link is creating
			}
		}
	});
}

function showLinkDialogue(linkHandler) {
    var edgeIndex = linkHandler.data('edgeIndex');
	var sltIndex = edgeIndex ? edgeIndex : '-1';
	var info = $('#divRelInfo');
	var opts = document.getElementById('sltLinkRelation').options;

	$('#spanNodeA').text(linkHandler.data('fromNode').find('.NodeName').text());
	$('#spanNodeB').text(linkHandler.data('toNode').find('.NodeName').text());

	for(var i = 0; i < opts.length; i++) {
		var opt = opts[i];
		opt.selected = (opt.value == sltIndex);
	}

	if(edgeIndex) {
		edge = edges[edgeIndex];
		if(edge.causeType == 'Artifact') {
			$('#spanCause').text(linkHandler.data('artefact').find('.NodeName').text());
			$('#spanEffect').text(linkHandler.data('process').find('.NodeName').text());
		}
		else {
			$('#spanCause').text(linkHandler.data('process').find('.NodeName').text());
			$('#spanEffect').text(linkHandler.data('artefact').find('.NodeName').text());
		}
		info.show();
    }
	else {
		info.hide();
	}

	$('#sltLinkRelation').one('change', {'linkHandler' : linkHandler}, function(event) {
		var linkHandler = event.data.linkHandler;
		var relationVal = $('#sltLinkRelation').val();
		if(relationVal != "-1") {
			edge = edges[relationVal];
			linkHandler.data('edgeIndex', relationVal).text(edge.localName);

			if(edge.causeType == 'Artifact') {
				linkHandler.data('cause', linkHandler.data('artefact')).data('effect', linkHandler.data('process'));
				$('#spanCause').text(linkHandler.data('artefact').find('.NodeName').text());
				$('#spanEffect').text(linkHandler.data('process').find('.NodeName').text());
			}
			else {
				linkHandler.data('cause', linkHandler.data('process')).data('effect', linkHandler.data('artefact'));
				$('#spanCause').text(linkHandler.data('process').find('.NodeName').text());
				$('#spanEffect').text(linkHandler.data('artefact').find('.NodeName').text());
			}
			info.show();
		}
		else {
			info.hide();
		}
	});

	$('#spanLinkRelationError').hide();


	$('#divLinkDialogue').data('linkHandler', linkHandler).dialog('open');
}

function onProcessClick(event) {
	processMenu.data('processNode', $(this));
	if(currentFocus) {
		currentFocus.blur();
	}
	currentFocus = processMenu.css({top: (event.pageY + 7) + 'px', left: (event.pageX + 7) + 'px'}).show('blind', {}, 300);
	event.stopPropagation();
}

function onProcessMetaEdit(event) {
	var processNode = $(this).offsetParent().data('processNode');
	showProcessMetaDialog(processNode);

	event.stopPropagation();
}

function onProcessDelete(event) {
	var processNode = $(this).offsetParent().data('processNode');
	var linksFrom = processNode.data('linksFrom');
	var linksTo = processNode.data('linksTo');

	for(linkId in linksFrom) {
		linkHandler = linksFrom[linkId];
		deleteLink(linkHandler)
	}

	for(linkId in linksTo) {
		linkHandler = linksTo[linkId];
		deleteLink(linkHandler)
	}

	processNode.remove();

	if(currentFocus) {
		currentFocus.blur();
	}
	event.stopPropagation();
}

function onSave(event) {
	var edgeInstances = new Array();
	$('.linkHandler').each(function(index, element) {
		var linkHandler = $(this);
		var causeNode = linkHandler.data('cause');
		var effectNode = linkHandler.data('effect');

		edgeInstances.push({
			'edgeClassUri': edges[linkHandler.data('edgeIndex')].uri,
			'causeUri': causeNode.attr('uri'),
			'causeNodeId': causeNode.attr('id'),
			'effectUri': effectNode.attr('uri'),
			'effectNodeId': effectNode.attr('id')
		});
	});

	var processInstances = new Array();
	$('#divCanvas .process').each(function(index, element) {
		var process = $(this);
		processInstances.push({
			'classUri': process.attr('classUri'),
			'nodeId': process.attr('id')
		});
	});

	var toServer = {edgeInstances: edgeInstances, processInstances: processInstances}
	$.post(baseUrl + "workflow/json/?method=save", {toServerJSON: $.toJSON(toServer)}, function(jsonRes) {

	}, 'json');
}

function initProcessMetaDialog() {
	$('#btAddProperty').click(function() {
		var sltAvailableProperties = $('#sltAvailableProperties');
		sltIndex = sltAvailableProperties.val();
		if(!sltIndex) return;
		var processNode = processMenu.data('processNode');
		var property = processList[processNode.attr('classUri')].properties[sltIndex];
		var input = propertyInputs[property.rangeLocalName].clone(true);
		input.attr('propertyUri', property.uri);
		var wrapper = $('<div/>').text(property.localName + ': ').append(input).append($('<span class="errorMsg"></span>')).append($('<a>remove property</a>').one('click', {propertyIndex: sltIndex}, function(event) {document.getElementById('sltAvailableProperties').options[parseInt(event.data.propertyIndex)].disabled = false; $(this).parent().remove();}));
		$('#divProcessPrpoerties').append(wrapper);
		document.getElementById('sltAvailableProperties').options[sltIndex].disabled = true;
		document.getElementById('sltAvailableProperties').options[sltIndex].selected = false;
	});

	$('#divProcessMetaDialog').dialog({
		modal: true,
		title: 'Edit Process Meta Data',
		height: 300,
		width: 470,
		autoOpen: false,
		buttons: {
			'OK': function() {

				$(this).dialog('close');
			},
			'Cancel': function() {
				$(this).dialog('close');
			}
		},
		close: function(event, ui) {

		}
	});
}

function showProcessMetaDialog(processNode) {
	var processClass = processList[processNode.attr('classUri')];
	var sltAvailableProperties = document.getElementById('sltAvailableProperties');
	var optAvailableProperties = sltAvailableProperties.options;
	optAvailableProperties.length = 0
	for(var i = 0; i < processClass.properties.length; i++) {
		var porperty = processClass.properties[i];
		var opt = new Option(porperty.localName, i);
		optAvailableProperties[optAvailableProperties.length] = opt;
	}

	var btAddProperty = $('#btAddProperty');
	if(optAvailableProperties == 0) {
		btAddProperty.attr('disabled', 'disabled');
	}
	else {
		btAddProperty.removeAttr('disabled');
	}
	$('#divProcessMetaDialog').dialog('open');
}

function checkInteger() {

}

function checkDecimal() {

}

function checkTime() {

}