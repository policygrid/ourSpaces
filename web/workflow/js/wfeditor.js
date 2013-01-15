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
var propertyInputs;

$(function(){
	canvas = $('#divCanvas');
	canvasSVG = Raphael(document.getElementById('divCanvas'), 990, 800);

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

	propertyInputs = {
		integer: $('<input type="text"></input>').addClass('InetgerInput').change(checkInteger),
		string: $('<input type="text"></input>').addClass('StringInput'),
		decimal: $('<input type="text"></input>').addClass('DecimalInput').change(checkDecimal),
		time: $('<input type="text"></input>').addClass('TimeInput').change(checkTime),
		date: $('<div/>').append($('<input type="text"></input>').addClass('DateInput').datepicker())
	};
});

/**
 * initialize component nodes and place them in the toobox
 *
 */
function initComponent() {
	var artefactBox = $('#divArtefactBox');
	var processBox = $('#divProcessBox');
	artefactBox.addClass('loading');
	processBox.addClass('loading');
	var url = baseUrl + 'workflow/json/';
	$.getJSON(url, {method : 'getResource'}, function(jsonRes) {
		if(jsonRes.responseStatus == 'SUCCESS') {
			artefacts = jsonRes.data.resource;
			for(var i = 0; i < artefacts.length; i++) {
				createArtefactNode(artefacts[i]).appendTo(artefactBox);
				artefactList[artefacts[i].uri] = artefacts[i];
			}
			artefactBox.removeClass('loading');
		}
	});

	$.getJSON(url, {method: 'getProcess'}, function(jsonRes) {
		if(jsonRes.responseStatus == 'SUCCESS') {
			processRoot = jsonRes.data.processRoot;
			for(var i = 0; i < processRoot.subClasses.length; i++) {
				createProcessNode(processRoot.subClasses[i], 0).appendTo(processBox)
			}
			processBox.removeClass('loading');
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
	var node = $('<div/>').append('<div class="NodeName">' + process.localName.replace(/(?!^)(?=[A-Z])/g, ' ') + '</div>').addClass('component_node process').attr({toolbox: true, classUri: process.uri}).draggable({ helper: 'clone', appendTo: canvas, revert: 'invalid'}).appendTo(nodeWrap);
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
	var pos = helper.offset();
	nodeCopy.appendTo(canvas).attr({
		'toolbox': false,
		'canvas': true,
		'id': 'cn' + nextId++
	}).css({
		'position': 'absolute',
		'top': pos.top + 'px',
		'left': pos.left + 'px'
	}).draggable({
		containment: 'parent',
		revert: 'invalid',
		stop: function(event, ui) {
			updateLinks(ui.helper);
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

	var leftDragHandler = $('<div class="dragHandler leftDragHandler"></div>').draggable({
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
	});
	var rightDragHandler = $('<div class="dragHandler rightDragHandler"></div>').draggable({
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
	});
	nodeCopy.append(leftDragHandler);
	nodeCopy.append(rightDragHandler);

	if(nodeCopy.hasClass('artefact')) {
		// For artefacts, make sure only one instance is in the canvase
		// disable draggable once it has dropped into canvase
		newNode.draggable( "option", "disabled", true );
		newNode.addClass('disabledComponetNode');

		nodeCopy.click(onArtefactClick);
	}
	else {
		nodeCopy.click(onProcessClick);
		nodeCopy.data('properties', {});
		var rdfIndicator = $('<span class="RDFIndicator">[?]</span>').click(function(event) {
			var processNode = $(this).parent();
			processMenu.data('processNode', processNode)
			showProcessMetaDialog(processNode);
			event.stopPropagation();
		}).hover(
			function(event) {
				var tooltip = $(this).next();
				if(tooltip.html().length > 0) {
					var parentOffet = tooltip.parent().offset();
					tooltip.css({top: (event.pageY - parentOffet.top + 9) +'px', left: (event.pageX -parentOffet.left + 15) + 'px'}).show('blind', {}, 270);
				}
			},
			function(event) {
				var tooltip = $(this).next();
				tooltip.hide();
			}
		);
		nodeCopy.append(rdfIndicator);
		nodeCopy.append($('<div class="Tooltip"></div>'));
	}
}

/**
 * Draw a link from nodeA to nodeB
 *
 */
function drawLink(nodeA, nodeB, handler) {
	var canvasOffset = canvas.offset();
	var offsetA = nodeA.offset();
	var offsetB = nodeB.offset();

	var positionA = {top: offsetA.top - canvasOffset.top, left: offsetA.left - canvasOffset.left};
	var positionB = {top: offsetB.top - canvasOffset.top, left: offsetB.left - canvasOffset.left};

	var distanceX = Math.abs(positionA.left - positionB.left);
	var distanceY = Math.abs(positionA.top - positionB.top);

	var nodeAWidth = nodeA.outerWidth();
	var nodeAHeight = nodeA.outerHeight();
	var nodeBWidth = nodeB.outerWidth();
	var nodeBHeight = nodeB.outerHeight();
	var canvasHeight = canvas.height();
	var canvasWidth = canvas.width();

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
		handler.css({top: (start.y + canvasOffset.top).toFixed() + 'px', left: (Math.abs(turn.x - start.x) * (end.x > start.x ? 0.26 : -0.26) + start.x + canvasOffset.left).toFixed() + 'px'});
	}
	else {		//vertical line first
		var aXPercent = positionA.left / (canvasWidth - nodeAWidth - 2);
		var bXPercent = positionB.left / (canvasWidth - nodeBWidth - 2);

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
		handler.css({top: (Math.abs(turn.y - start.y) * (end.y > start.y ? 0.26 : -0.26) + start.y + handler.height() + canvasOffset.top).toFixed() + 'px', left: (start.x + canvasOffset.left).toFixed() + 'px'});
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
	var info = $('#tbRelInfo span');
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
	$('#sltLinkRelation').unbind('change');
	$('#sltLinkRelation').bind('change', {'linkHandler' : linkHandler}, function(event) {
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
	var artefactInstances = new Array();
	var processInstances = new Array();
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


	$('#divCanvas .process').each(function(index, element) {
		var processNode = $(this);
		var process = {
			'classUri': processNode.attr('classUri'),
			'nodeId': processNode.attr('id'),
			'properties': []
		};
		var properties = processNode.data('properties');
		for(uri in properties) {
			process.properties.push({uri: uri, value: properties[uri]})
		}
		processInstances.push(process);
	});

	/*$('#divCanvas .artefact').each(function(index, element) {
		var artefactNode = $(this);
		var artefact = {

		};
		artefactInstances.push(artefact);
	});*/

	var toServer = {
		edgeInstances: edgeInstances,
 		processInstances: processInstances,
		artefactInstances: artefactInstances
	}
	$.post(baseUrl + "workflow/json/?method=save", {toServerJSON: $.toJSON(toServer)}, function(data) {
		alert(data);
	});
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
		var wrapper = $('<div/>').append($('<span>' + property.localName + '</span>:&nbsp;')).append(input).append($('<span class="errorMsg"></span>')).append($('&nbsp;[<a class="LinkButton">remove</a>]').one('click', {propertyIndex: sltIndex}, function(event) {document.getElementById('sltAvailableProperties').options[parseInt(event.data.propertyIndex)].disabled = false; $(this).parent().remove();}));
		$('#divProcessPrpoerties').append(wrapper);
		document.getElementById('sltAvailableProperties').options[sltIndex].disabled = true;
		document.getElementById('sltAvailableProperties').options[sltIndex].selected = false;
	});

	$('#divProcessMetaDialog').dialog({
		modal: true,
		title: 'Edit Process Meta Data',
		height: 300,
		width: 490,
		autoOpen: false,
		buttons: {
			'OK': function() {
				var processNode = processMenu.data('processNode');
				var rdfIndicator = processNode.find('.RDFIndicator');
				var tooltip = processNode.find('.Tooltip');
				var tooltipHtml = '';

				tooltip.html('');
				var properties = {};
				var hasErrors = false;
				var count = 0;
				$(this).find('input').each(function(index, element) {
					var input = $(this);
					input.change();
					if(input.hasClass('errorInput')) {
						hasErrors = true;
						return;
					}
					if(input.attr('propertyUri') && input.val() && input.val().length > 0) {
						properties[input.attr('propertyUri')] = input.val();
						tooltipHtml += "<p>" + input.prev().text() + ": " + input.val() + "</p>";
						count++;
					}
				});
				if(!hasErrors) {
					processNode.data('properties', properties);
					rdfIndicator.text(count > 0 ? '[RDF]' : '[?]');
					tooltip.html(tooltipHtml);
					$(this).dialog('close');
				}
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
		var property = processClass.properties[i];
		var opt = new Option(property.localName, i);
		optAvailableProperties[optAvailableProperties.length] = opt;
	}

	var btAddProperty = $('#btAddProperty');
	if(optAvailableProperties == 0) {
		btAddProperty.attr('disabled', 'disabled');
	}
	else {
		btAddProperty.removeAttr('disabled');
	}
	$('#divProcessPrpoerties').html('');
	var existingProperties = processNode.data('properties');
	for(propertyUri in existingProperties) {
		for(var i = 0; i < processClass.properties.length; i++) {
			var porperty = processClass.properties[i];
			if(propertyUri = property.uri) {
				var input = propertyInputs[property.rangeLocalName].clone(true);
				input.attr('propertyUri', property.uri).val(existingProperties[propertyUri]);
				var wrapper = $('<div/>').text(property.localName + ': ').append(input).append($('<span class="errorMsg"></span>')).append($(' [<a class="LinkButton">remove</a>]').one('click', {propertyIndex: i}, function(event) {document.getElementById('sltAvailableProperties').options[parseInt(event.data.propertyIndex)].disabled = false; $(this).parent().remove();}));
				$('#divProcessPrpoerties').append(wrapper);
				document.getElementById('sltAvailableProperties').options[i].disabled = true;
			}
		}
	}

	$('#divProcessMetaDialog').dialog('open');
}

function checkInteger() {
	var input = $(this);
	var value = $(this).val();
	if(value && value.length > 0 && !/^-?\d+$/.test(value)) {
		input.addClass('errorInput');
		input.next().text('invalid integer').addClass('errorMsg');
	}
	else {
		input.removeClass('errorInput');
		input.next().text('');
	}
}

function checkDecimal() {
	var input = $(this);
	var value = $(this).val();
	if(value && value.length > 0 && !/^-?((\d+(\.\d+)?)|(\.\d+))$/.test(value)) {
		input.addClass('errorInput');
		input.next().text('invalid integer').addClass('errorMsg');
	}
	else {
		input.removeClass('errorInput');
		input.next().text('');
	}
}

function checkTime() {

}