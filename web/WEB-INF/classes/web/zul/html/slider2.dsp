<%--
slider2.dsp

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Thu Aug 6 21:06:03     2008, Created by robbiecheng
}}IS_NOTE

Copyright (C) 2008 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under GPL Version 2.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
--%><%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %>
<c:set var="self" value="${requestScope.arg.self}"/>
<c:set var="zcls" value="${self.zclass}"/>
<c:if test='${self.mold == "scale"}'><div class="${zcls}-tick" style="font-size: 13px; margin:0px"></c:if>
<div id="${self.uuid}"${self.outerAttrs}${self.innerAttrs} z.type="zul.sld.Sld" style="font-size: 13px; margin:0px">
	<div class="${zcls}-end" style="font-size: 13px; margin:0px">
		<div id="${self.uuid}!inner" class="${zcls}-inner" style="font-size: 13px; margin:0px">
			<div id="${self.uuid}!btn" class="${zcls}-btn" style="font-size: 13px; margin:0px"></div>
			<a class="${zcls}-focus" href="#" tabindex="-1" hidefocus="on"></a>
		</div>
	</div>
</div>
<c:if test='${self.mold == "scale"}'></div></c:if>