<%--
timebox2.dsp

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Thu Jun  5 16:08:30 TST 2008, Created by jumperchen
}}IS_NOTE

Copyright (C) 2008 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
--%><%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %>
<%@ taglib uri="http://www.zkoss.org/dsp/zk/core" prefix="z" %>
<c:set var="self" value="${requestScope.arg.self}"/>
<c:set var="zcls" value="${self.zclass}"/>
<span id="${self.uuid}"${self.outerAttrs} z.type="zul.tb.Tmbox" z.combo="true"><input id="${self.uuid}!real" class="${zcls}-inp" autocomplete="off"${self.innerAttrs}/><span id="${self.uuid}!btn" class="${zcls}-btn"${self.buttonVisible?'':' style="display:none"'}><img class="${zcls}-img" onmousedown="return false;" src="${c:encodeURL('~./img/spacer.gif')}"/></span></span>