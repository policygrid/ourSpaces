/* tab2.js

{{IS_NOTE
 	Purpose:

 	Description:

 	History:
 		Tue Aug 09 10:21:12     2008, Created by Ryanwu
}}IS_NOTE

	Copyright (C) 2008 Potix Corporation. All Rights Reserved.

{{IS_RIGHT

 	This program is distributed under GPL Version 2.0 in the hope that
 	it will be useful, but WITHOUT ANY WARRANTY.

}}IS_RIGHT
 */
////
// tabbox //
zkTabbox2 = {
	init: function(cmp) {
		if (zkTabbox2._isAccord(cmp))
			zk.cleanVisibility(cmp);
	},
	setAttr: function(cmp, name, value) {
		switch (name) {
			case "z.sel":
				var tab = $e(value);
				if (!zkTab2._sliding(tab))
					zkTab2.selTab(tab, true);
				break;
			case "style":
			case "style.width":
			case "style.height":
				zkau.setAttr(cmp, name, value);
				var uuid = getZKAttr(cmp, "tabs");
				if (uuid) {
					zk.beforeSizeAt(cmp);
					zkTabs2._fixWidth(uuid);
					zk.onSizeAt(cmp);
				}
				return true;
		}
		return false;
	},
	childchg: function(cmp) {
		var uuid = getZKAttr(cmp, "tabs");
		if (uuid) {
			setTimeout("zkTabs2._fixWidth('" + uuid + "')", 0);
		}
	},
	/** Returns whether the tabbox is accordion.
	 * @since 3.5.0
	 */
	_isAccord: function(tabbox) {
		return getZKAttr(tabbox, "accd");
	},
	/** Returns whether the tabbox is scrollable.
	 * @since 3.5.0
	 */
	_isScroll: function(tabbox) {
		return getZKAttr(tabbox, "tabscrl");
	},
	/** Returns whether the tabbox is vertical.
	 * @since 3.5.0
	 */
	_isVert: function(tabbox) {
		return getZKAttr(tabbox, "orient")=="v";
	}
};
////
// tab //
zkTab2 = {
	init: function(cmp) {
		//onClick
		zk.listen(cmp, "click", zkTab2.onclick);
		var tabbox = $e(getZKAttr(cmp, "box"));
		//close Button
		var btn = $e(cmp.id + "!close");
		if (btn) {
			zk.listen(btn, "click", zkTab2.onCloseBtnClick);
			if (!btn.style.cursor)
				btn.style.cursor = "default";
			if (zk.ie6Only) {
				zk.listen(btn, "mouseover", this._onMouseOver);
				zk.listen(btn, "mouseout", this._onMouseOut);
			}
		}
		var meta = $parent(cmp);
		if (!meta._toscroll)
			meta._toscroll = function () {
				zkTabs2.scrollingchk($uuid(meta));
			};
		zk.addInit(meta._toscroll, false, $uuid(meta) );
	},
	setAttr: function(cmp, name, value) {
		switch (name) {
			case "z.disabled":
				zkTab2._disable(cmp, value);
				return true;
			case "style":
			case "style.width":
			case "style.height":
				zkau.setAttr(cmp, name, value);
				zk.beforeSizeAt(cmp);
				zkTabs2.scrollingchk($uuid($parent(cmp)));
				zkTabs2._fixWidth($uuid($parent(cmp)));
				zk.onSizeAt(cmp);
				return true;
		}
		return false;
	},
	/** Selects the specified tab. */
	onclick: function(evt) {
		if (!evt) evt = window.event;
		var tab = $parentByType(Event.element(evt), "Tab2");
		if (getZKAttr(tab, "disabled") == "true")
			return;//return when disabled
		if (!zkTab2._sliding(tab)) //Bug 1571408
			zkTab2.selTab(tab, true);
	},
	/** close button clicked**/
	onCloseBtnClick: function(evt) {
		if (!evt) evt = window.event;
		var tab = $parentByType(Event.element(evt), "Tab2");
		if (getZKAttr(tab, "disabled") == "true")
			return;//return when disabled
		zkau.sendOnClose(tab, true);
		Event.stop(evt);
	},
	/** Selects the specified tab (and unselect the current tab). */
	selTab: function(tab, notify) {
		var tabbox = $e(getZKAttr(tab, "box"));
		if (zkTabbox2._isVert(tabbox))
			zkTabs2.scrollingchk($uuid($parent(tab)),"vsel",tab);
		else if (!zkTabbox2._isAccord(tabbox))
			zkTabs2.scrollingchk($uuid($parent(tab)),"sel",tab);
		tab = $e(tab);
		if (!tab)
			return;

		var old = zkTab2._getSelTab(tab);
		if (old != tab) {
			if (old)
				zkTab2._setTabSel(old, false, false, notify);
			zkTab2._setTabSel(tab, true, notify, notify);
			//!notify is sent from the server, so no animation
		}
	},
	cleanup: function(cmp) {
		var parent = $parent(cmp);
		if (parent)
			parent._toscroll = null;//clean init
			parent = $outer($parent(cmp));
		var tbx = $e(getZKAttr(cmp, "box"));
		if (!zkTabbox2._isAccord(tbx)) { //if delete tab , need scroll back !
		zk.addCleanup(function () {
			zkTabs2.scrollingchk(parent.id, "cln", cmp);
				if (zkTabbox2._isVert(tbx)) {
					zkTabs2._fixHgh(tbx, parent);
				}
			}, false, parent.id + "Tabbox2");
		}
	},
	/** Returns the selected tab by giving any tab as the reference point. */
	_getSelTab: function(tab) {
		var tabboxId = getZKAttr(tab, "box");
		if (tabboxId) {
			var tabbox = $e(tabboxId);
			if (zkTabbox2._isAccord(tabbox))
				return zkTab2._getSelTabFromTop(tabbox, tabboxId);
		}

		//non-accordion: we can use sibling directly
		var node = tab;

		for (node = tab; node = node.nextSibling;)
			if (getZKAttr(node, "sel") == "true")
				return node;

		for (node = tab; node = node.previousSibling;)
			if (getZKAttr(node, "sel") == "true")
				return node;

		if (getZKAttr(tab, "sel") == "true") return tab;
	},
	/** Whether any tab is sliding. */
	_sliding: function(tab) {
		var tabboxId = getZKAttr(tab, "box");
		if (!tabboxId)
			return false;

		var tabbox = $e(tabboxId);
		if (!zkTabbox2._isAccord(tabbox))
			return false;

		//accordion: we must go to panel firs, and then browse its sibling
		var panel = $e(getZKAttr(tab, "panel"));
		for (var node = panel; node = node.nextSibling;)
			if (getZKAttr($real(node), "animating"))
				return true;

		for (var node = panel; node = node.previousSibling;)
			if (getZKAttr($real(node), "animating"))
				return true;
		return false;
	},
	/** Returns the selected tab by specified any HTML tag containing it. */
	_getSelTabFromTop: function(node, tabboxId) {
		if ($type(node) == "Tab2" && getZKAttr(node, "box") == tabboxId)
			return getZKAttr(node, "sel") == "true" ? node : null;

		for (var node = node.firstChild; node; node = node.nextSibling) {
			var n = zkTab2._getSelTabFromTop(node, tabboxId);
			if (n)
				return n;
		}
		return null;
	},
	/** Selects or unselect the specified tab. */
	_setTabSel: function(tab, toSel, notify, animation) {
		if ((getZKAttr(tab, "sel") == "true") == toSel)
			return; //nothing changed
		setZKAttr(tab, "sel", toSel ? "true" : "false");
		zk[toSel ? "addClass" : "rmClass"](tab, getZKAttr(tab, "zcls") + "-seld");

		var tabbox = $e(getZKAttr(tab, "box")),
			accd = tabbox && zkTabbox2._isAccord(tabbox),
			panel = $e(getZKAttr(tab, "panel"));
		if (panel)
			if (accd && animation) {
				if (toSel)
					anima.slideDown($real(panel));
				else
					anima.slideUp($real(panel));
			}
			else
				zk.show(accd ? $real(panel) : panel, toSel);

		if (!accd) {
			var tabs = $parentByType(tab, "Tabs2");
			   if (tabs) zkTabs2._fixWidth(tabs.id);
		}

		if (notify)
			zkau.sendasap({
				uuid: tab.id,
				cmd: "onSelect",
				data: [tab.id]
			});
	},
	/** inner method, disable this tab
	 * @param {Object} cmp tab element
	 * @param {string} disabled string "true" or "false"
	 */
	_disable: function(cmp, disabled) {
		var olddis = getZKAttr(cmp, "disabled");
		if (olddis == disabled)
			return;

		var btn = $e(cmp.id + "!close"),
			sel = getZKAttr(cmp, "sel"),
			zcls = getZKAttr(cmp, "zcls");

		zk.rmClass(cmp, zcls + "-disd-seld");
		zk.rmClass(cmp, zcls + "-disd");
		if (disabled == "true") {
			//change style from tab/tabsel to tabdis/tabdissel
			zk.addClass(cmp, zcls + (sel == "true" ? "-disd-seld" : "-disd"));
		} else {
			//change style from tabdis/tabdissel to tab/tabsel
			zk[sel == "true" ? "addClass" : "rmClass"](cmp, zcls + "-seld");
		}
		setZKAttr(cmp, "disabled", disabled);
	}
};

////
// tabs //
zkTabs2 = {
	init: function(cmp) {
		zkTabs2._fixWidth(cmp.id);
		//horizontal
		var btn = $e(cmp.id + "!right");
		if (btn) zk.listen(btn, "click", zkTabs2.onClickArrow);
		btn = $e(cmp.id + "!left");
		if (btn) zk.listen(btn, "click", zkTabs2.onClickArrow);
		//vertical
		btn = $e(cmp.id + "!down");
		if (btn) zk.listen(btn, "click", zkTabs2.onClickArrow);
		btn = $e(cmp.id + "!up");
		if (btn) zk.listen(btn, "click", zkTabs2.onClickArrow);
		var meta = $parent(cmp);
		if (!meta.initscroll)
			meta.initscroll = function () {
				zkTabs2.scrollingchk(cmp.id,"init");
			};
		zk.addInit(meta.initscroll, false, meta.id);
	},
	onVisi: function(cmp) {
		if (zkTabbox2._isVert($parent($e(cmp.id)))) {
			cmp.style.height = "";
		} else {
			cmp.style.width = "";
		}
		zkTabs2._fixWidth(cmp.id);
		zkTabs2.scrollingchk(cmp.id);
	},
	onSize: function(cmp) {
		zkTabs2._fixWidth(cmp.id);
		zkTabs2.scrollingchk(cmp.id);
		zk.cleanVisibility($parentByType(cmp, "Tabbox2"));
	},
	beforeSize: function(cmp) {
		if (zkTabbox2._isVert($parent($e(cmp.id)))) {
			cmp.style.height = "";
		}else{
			cmp.style.width = "";
		}
	},
	cleanup: function(cmp) {
		cmp._width = null;
		var meta = $parent(cmp);
		if(meta) meta.initscroll = null;
	},
	/** Check Tab Auto scrolling
	 * @param {string} : uuid .
	 * @param {string} : use to direct scroll to somewhere.
	 * @param {cmp}	   : use to scroll to specific tab
	 */
	scrollingchk: function(uuid, way, cmp) {
		var tbsdiv = $e(uuid),
			tabbox = $parentByType($e(uuid), "Tabbox2"),
			zcls = getZKAttr(tabbox, "zcls");

		if (!tbsdiv) return;	// tabbox is delete , no need to check scroll
		if (tbsdiv.offsetWidth < 36)  return; //tooooo small
		if (zkTabbox2._isVert(tabbox)) {//vertical
			if (tbsdiv.offsetHeight < 36)  return; //tooooo small
			var header = $e(uuid , "header"),
				ul_si = $e(uuid , "ul"),
				headheight = header.offsetHeight,
				cldheight = 0,
				tab = zk.childNodes(ul_si, zkTabs2._isLegalType);
				for (var i=0,count=tab.length;i<count;i++) {
					cldheight = cldheight + tab[i].offsetHeight;
				}
				if (zk.hasClass(tabbox, zcls + "-scrolling")) { //already in scrolling status
					if (cldheight <= (headheight+18)) {
						zk.rmClass(tabbox, zcls + "-scrolling", true);
						header.style.height= tabbox.offsetHeight-2 + "px";
						header.scrollTop = 0;
					}
					switch (way) {
						case "init":
							if (cmp == null) cmp = zkTab2._getSelTab(zk.firstChild(ul_si,"LI"));
							var ost = cmp.offsetTop,
								tosh = cmp.offsetHeight,
								sct = header.scrollTop,
								hosh = headheight;
							if (ost < sct) {
								zkTabs2._tabscroll(uuid, "up", sct - ost );
							}
							else if (ost + tosh > sct + hosh) {
									zkTabs2._tabscroll(uuid, "down", ost + tosh - sct - hosh + 4);
							}
							break;
						case "end":
							var d = cldheight - header.offsetHeight - header.scrollTop ;
							d >= 0 ? zkTabs2._tabscroll(uuid, "down", d) : zkTabs2._tabscroll(uuid, "up", Math.abs(d));
							break;
						case "vsel":
							var ost = cmp.offsetTop,
								tosh = cmp.offsetHeight,
								scltop = header.scrollTop,
								host = headheight;
							//over left
							if (ost < scltop) {
								zkTabs2._tabscroll(uuid, "up", scltop - ost);
							} else if (ost + tosh > scltop + host) {
								zkTabs2._tabscroll(uuid, "down", ost + tosh - scltop - host + 2);
							}
							break;
					}
				}else { // not enough tab to scroll
					if (cldheight > (headheight - 18)) {
						zk.addClass(tabbox, zcls + "-scrolling", true);
						header.style.height = tabbox.offsetHeight - 36 + "px";
						if (way == "end") {
							var d = cldheight - header.offsetHeight - header.scrollTop + 2;
							d < 0 ? "" : zkTabs2._tabscroll(uuid, "down", d);
						}
					}
				}
		} else if(!zkTabbox2._isAccord(tabbox)) {
			var tabs = $e(uuid + "!cave"), header = $e(uuid + "!header"),
			 a = zk.childNodes(tabs, zkTabs2._isLegalType);
			if (zkTabbox2._isScroll(tabbox)) {
				var headwidth = header.offsetWidth, cldwidth = 0;
				for (var i = 0, count = a.length; i < count; i++)
					cldwidth = cldwidth + $int(a[i].offsetWidth) + 2;

				if (zk.hasClass(tabbox, zcls + "-scrolling")) { //already in scrolling status
					if (cldwidth <= (headwidth)) {
						zk.rmClass(tabbox, zcls + "-scrolling", true);
						header.style.width = tabbox.offsetWidth - 2 + "px";
						header.scrollLeft = 0;
					}
					// scroll to specific position
					switch (way) {
						case "init":
							if (cmp == null)cmp = zkTab2._getSelTab(zk.firstChild(tabs,"LI"));
							var osl = cmp.offsetLeft, tosw = cmp.offsetWidth, scl = header.scrollLeft, hosw = headwidth;
							if (osl < scl) {
								zkTabs2._tabscroll(uuid, "left", scl - osl + 2);
							}
							else if (osl + tosw > scl + hosw) {
									zkTabs2._tabscroll(uuid, "right", osl + tosw - scl - hosw + 2);
							}
							break;
						case "end":
							var d = cldwidth - header.offsetWidth - header.scrollLeft + 2;
							d >= 0 ? zkTabs2._tabscroll(uuid, "right", d) : zkTabs2._tabscroll(uuid, "left", Math.abs(d));
							break;
						case "sel":
							var osl = cmp.offsetLeft, tosw = cmp.offsetWidth, scl = header.scrollLeft, hosw = headwidth;
							//over left
							if (osl < scl) {
								zkTabs2._tabscroll(uuid, "left", scl - osl + 2);
							}
							else if (osl + tosw > scl + hosw) {
									zkTabs2._tabscroll(uuid, "right", osl + tosw - scl - hosw + 2);
							}
							break;
					}

				}
				else { // not enough tab to scroll
					if (cldwidth > (headwidth - 10)) {
						zk.addClass(tabbox, zcls + "-scrolling", true);
						var caveul = $e(getZKAttr(tabbox, "tabs"),"cave");
						caveul.style.width = "5000px";
						header.style.width = tabbox.offsetWidth - 38 + "px";
						if (way == "sel") {
							var d = cldwidth - header.offsetWidth - header.scrollLeft + 2;
							d < 0 ? "" : zkTabs2._tabscroll(uuid, "right", d);
						}
					}
				}
			}
		};
	},
	/** Scroll to next tab  . */
	onClickArrow: function(evt) {
		if (!evt) evt = window.event;
		var ele = Event.element(evt),
		uuid = $outer(ele).id,
		move = 0,
		head = $e(uuid + "!header");

		//Scroll to next right tab
		if (ele.id == uuid + "!right") {
			var hosw = head.offsetWidth,
				scl = head.scrollLeft,
				a =  zk.childNodes($e(uuid + "!cave"), zkTabs2._isLegalType);
			if (!a.length) return; // nothing to do
			for (var i = 0, count = a.length; i < count; i++) {
				if ($type(a[i]) == "Tab2") {
					if (a[i].offsetLeft + a[i].offsetWidth > scl + hosw) {
						move = a[i].offsetLeft + a[i].offsetWidth - scl - hosw + 2;
						if (move == 0 || move == null || isNaN(move))
							return null;
						zkTabs2._tabscroll(uuid, "right", move);
						return;
					};
				}
			};
		}else if (ele.id == uuid + "!left") {//Scroll to next left tab
				var a =  zk.childNodes($e(uuid + "!cave"), zkTabs2._isLegalType),
					scl = head.scrollLeft;
				if (!a.length) return; // nothing to do
				for (var i = 0, count = a.length; i < count; i++) {
					if (a[i].offsetLeft > scl) {
						//if no Sibling tab no sroll
						if (zk.previousSibling(a[i], "LI") == null)  return;
						move = scl - zk.previousSibling(a[i], "LI").offsetLeft + 2;
						if (isNaN(move)) return;
						zkTabs2._tabscroll(uuid, "left", move);
						return;
					};
				};
				move = scl - a[a.length-1].offsetLeft + 2;
				if (isNaN(move)) return;
				zkTabs2._tabscroll(uuid, "left", move);
				return;
		}else if (ele.id == uuid + "!up") {
				var scltop =  head.scrollTop,
					tab = zk.childNodes($e(uuid,"ul"), zkTabs2._isLegalType);
				if (!tab.length) return; // nothing to do
				for (var i = 0, count = tab.length; i < count; i++) {
					if (tab[i].offsetTop >= scltop) {
						var preli = zk.previousSibling(tab[i], "LI");
						if (preli==null) return;
						move = scltop - preli.offsetTop ;
						zkTabs2._tabscroll(uuid, "up", move);
						return;
					};
				};
				var preli = tab[tab.length-1];
				if (preli == null) return;
				move = scltop - preli.offsetTop ;
				zkTabs2._tabscroll(uuid, "up", move);
				return;
		}else if (ele.id == uuid + "!down") {
			var scltop = head.scrollTop,
				tab = zk.childNodes($e(uuid,"ul"), zkTabs2._isLegalType),
				scltop =  head.scrollTop,
				hosh = head.offsetHeight;
			if (!tab.length) return; //nothing to do
			for (var i = 0, count = tab.length; i < count; i++) {
				if (tab[i].offsetTop + tab[i].offsetHeight > scltop + hosh ) {
					move = tab[i].offsetTop + tab[i].offsetHeight - scltop - hosh + 2;
					if (move == 0 || move == null || isNaN(move)) return ;
					zkTabs2._tabscroll(uuid, "down", move);
					return;
				};
			};
		}
	},
	/** Scroll Tabs */
	_tabscroll: function(uuid, to, move) {
		if (move <= 0)
			return;
		var step, header = $e(uuid + "!header");
		//the tab bigger , the scroll speed faster
		step = move <= 60 ? 5 : eval(5 * ($int(move / 60) + 1));
		var run = setInterval(function() {
			if (move == 0) {
				clearInterval(run);
				return;
			}
			else {
				//high speed scroll, need break
				move < step ? goscroll(header, to, move) : goscroll(header, to, step);
				move = move - step;
				move = move < 0 ? 0 : move;
			}
		}, 10);
		//Use to scroll
		goscroll = function(header, to, step) {
			if (to == "right") {
				header.scrollLeft = header.scrollLeft + step;
			}else if (to == "left") {
				header.scrollLeft = header.scrollLeft - step;
			}else if (to == "up") {
				header.scrollTop = header.scrollTop - step;
			}else if (to == "down") {
				header.scrollTop = header.scrollTop + step;
			}
			header.scrollLeft = (header.scrollLeft <= 0 ? 0 : header.scrollLeft);
			header.scrollTop = (header.scrollTop <= 0 ? 0 : header.scrollTop);

		}
	},
	/** Check Node Type. */
	_isLegalType: function (n) {return ($type(n) == "Tab2");},
	/** Check Node Tag. */
	_isLegalLI: function (n) {return $tag(n) == "LI";},
	/** Fix the width of header. */
	_fixWidth: function(uuid) {
		var tbx = $parent($e(uuid)),
			tabs =  $e(uuid),
			head = $e(uuid + "!header");
		zkTabs2._fixHgh(tbx, tabs);
		if (zkTabbox2._isVert(tbx)) {
			ul = zk.firstChild($e(tabs, "header"), "UL"),
			li = zk.childNodes(ul, zkTabs2._isLegalLI);
			var most = 0;
			 //li in IE doesn't have width...
			 if (tabs.style.width) {
			 	tabs._width = tabs.style.width;;
			 } else {
			 	tabs.style.width = tabs._width;
			 }
		} else {
			if (tbx.offsetWidth < 36) return;
			if (zkTabbox2._isScroll(tbx)) {
				var zcls = getZKAttr(tbx, "zcls");
				if (!tbx.style.width) {
					zkTabs2._forceStyle(tbx,"w","100%");
					zkTabs2._forceStyle(tabs,"w",zk.revisedSize(tabs,tbx.offsetWidth)+ "px");
					if (zk.hasClass(tbx, zcls + "-scrolling")) {
						zkTabs2._forceStyle(head,"w",tbx.offsetWidth - 38 + "px");
					} else {
						zkTabs2._forceStyle(head,"w",zk.revisedSize(head,tbx.offsetWidth)+ "px");
					}
				} else {
					zkTabs2._forceStyle(tabs,"w",zk.revisedSize(tabs,tbx.offsetWidth)+ "px");
					zkTabs2._forceStyle(head,"w",tabs.style.width);
					if (zk.hasClass(tbx, zcls + "-scrolling")) {
						zkTabs2._forceStyle(head,"w",head.offsetWidth - 36 + "px");
					} else {
						zkTabs2._forceStyle(head,"w",head.offsetWidth + "px");
					}
				}
			} else {
				if (!tbx.style.width) {
					zkTabs2._forceStyle(tbx,"w",tbx.offsetWidth + "px");
					zkTabs2._forceStyle(tabs,"w",tbx.offsetWidth + "px");
				} else {
					zkTabs2._forceStyle(tabs,"w",tbx.offsetWidth + "px");
				}
			}
		}
	},
	_fixHgh: function (tabbox, tabs) {
		if ($e(tabbox.id) == null || $e(tabs) == null) return;
		//fix tabpanels's height if tabbox's height is specified
		//Ignore accordion since its height is controlled by each tabpanel
		if (zkTabbox2._isVert(tabbox)) {
			var child = zk.childNodes(tabbox, function (n) {return ($tag(n) == "DIV");}),
			ul = zk.firstChild($e(tabs, "header"), "UL"),
			li = zk.childNodes(ul, zkTabs2._isLegalLI);
			if (tabbox.style.height) {
				zkTabs2._forceStyle(tabs, "h", zk.revisedSize(tabs,tabbox.offsetHeight,true)+"px");
			} else {
				zkTabs2._forceStyle(tabbox,"h", li.length*35+"px");//give it default height
				zkTabs2._forceStyle(tabs, "h", zk.revisedSize(tabs,tabbox.offsetHeight,true)+"px");
			}
			//coz we have to make the header full
			if (zk.hasClass(tabbox, getZKAttr(tabbox, "zcls") + "-scrolling")) {
				zkTabs2._forceStyle($e(tabs, "header"),"h", tabs.offsetHeight - 38 + "px");
			} else {
				zkTabs2._forceStyle($e(tabs, "header"),"h", zk.revisedSize($e(tabs, "header"),tabs.offsetHeight,true) + "px");
			}
			//separator(+border)
			zkTabs2._forceStyle(child[1],"h",zk.revisedSize(child[1],tabs.offsetHeight,true)+"px");
			//tabpanels(+border)
			zkTabs2._forceStyle(child[2],"h",zk.revisedSize(child[1],tabs.offsetHeight,true)+"px");
		} else {
			$e(tabs.id,"header").style.height="";
		}
	},

	_forceStyle: function(cmp,attr,value) {
		if ($int(value) < 0) return;
		switch(attr) {
		case "h":
			if (zk.ie6Only) {
				cmp.style.height = "0px";
			} else {
				cmp.style.height = "";
			}
			cmp.style.height = value;
			break;
		case "w":
			if (zk.ie6Only) {
				cmp.style.width = "0px";
			} else {
				cmp.style.width = "";
			}
			cmp.style.width = "";
			cmp.style.width = value;
			break;
		}
	}
};
if (zk.ie6Only) {
	zkTab2._onMouseOver = function(evt) {
		if (!evt) evt = window.event;
		var cmp = Event.element(evt),
			zcls = getZKAttr($parentByType(cmp, "Tab2"), "zcls");
		zk.addClass(cmp, zcls + "-close-over");
	};
	zkTab2._onMouseOut = function(evt) {
		if (!evt) evt = window.event;
		var cmp = Event.element(evt),
			zcls = getZKAttr($parentByType(cmp, "Tab2"), "zcls");
		zk.rmClass(cmp, zcls + "-close-over");
	};
	zkTabs2.beforeSize = function(tabs) {
		var tabbox = $parentByType(tabs, "Tabbox2");
		if (!zkTabbox2._isAccord(tabbox)) {
			if (!zkTabbox2._isVert($parent(tabs))) {
				tabs.style.width = "0px";
			}
		}
	};
}
////
//tabpanel2//
zkTabpanel2 = {
	init: function(cmp) {
		var tbx = $e(getZKAttr(cmp, "box"));
		this._fixPanelHgh(tbx,cmp);
	},

	onVisi: function(cmp) {
		var tbx = $e(getZKAttr(cmp, "box"));
		this._fixPanelHgh(tbx,cmp);//Bug 2104974
		 if (zk.ie6Only) zk.repaint(tbx);
	},
	_fixPanelHgh: function(tabbox,tabpanel){
		if (!zkTabbox2._isAccord(tabbox)) {
			var hgh = tabbox.style.height;
			var panels = $parent(tabpanel);
			if (panels) {
				for (var pos, n = panels.firstChild; n; n = n.nextSibling) {
					if (n.id) {
						if (zk.ie) { // Bug: 1968434, this solution is very dirty but necessary.
							pos = n.style.position;
							n.style.position = "relative";
						}
						if (hgh && hgh != "auto") {//tabbox has height
							hgh = zk.getVflexHeight(panels);
							zk.setOffsetHeight(n, hgh);
						}
						//let real div 100% height
						zk.addClass($e(n.id + "!real"), getZKAttr(n, "zcls") + "-real");
						if (zk.ie) n.style.position = pos;
					}
				}
			}
		}
	}
}
zkTabpanel2.onSize = zkTabpanel2.onVisi;
