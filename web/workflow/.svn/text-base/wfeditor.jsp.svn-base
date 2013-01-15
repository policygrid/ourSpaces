<jsp:include page="/top.jsp" />




<title>Workflow Editor</title>

<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.8.2.custom.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.4.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="js/raphael-min.js"></script>
<script type="text/javascript" src="js/wfeditor.js"></script>


<script type="text/javascript">
	$(function() {
		$("#dialog").dialog();
	});
	</script>
	
<div id="dialog" title="Basic dialog">
		<div id="divComponetBox" style="width: 200px; float: left; border: solid 1px #666666;">
			<div id="divArtefactBox" style="height: 370px; margin: 5px 3px; overflow: scroll">
				<h3>Artifacts</h3>
			</div>
			<div id="divProcessBox" style="height: 370px; margin: 5px 3px; overflow: scroll">
				<h3>Processes</h3>
			</div>
		</div>
</div>


<div style="margin: 2px auto; width: 990px;">
	
	<div id="divCanvas" ></div>

	<p style="text-align: right; margin-right: 10px;"><input id="btSave" type="button" value="Submit" style="padding: 3px 6px; font-size: 1.2em" /></p>

</div>

	<!-- hidden dialogue divs -->
	<div id="divLinkDialogue">
		<p>Link between:<span id="spanNodeA" style="font-weight: bold"></span> and <span id="spanNodeB" style="font-weight: bold"></span></p>
		<table id="tbRelInfo">
			<tr>
				<td align="center" width="28%"><span id="spanEffect"></span></td>
				<td align="center">
					<select id="sltLinkRelation">
						<option value="-1">Please select relationship</option>
					</select>
				</td>
				<td align="center" width="28%"><span id="spanCause"></span></td>
			</tr>
		</table>
	</div>

	<div id="divProcessMetaDialog">
		<div id="divAvailableProperties">
			Available Properties
			<select id="sltAvailableProperties" size="5" style="margin: 5px 2px; clear: right; display: block; width: 98%"></select>
			<button id="btAddProperty" style="padding: 2px 3px; float: right;">Add</button>
		</div>
		<div id="divProcessPrpoerties"></div>
	</div>

	<ul id="ulLinkMenu" class="menu shadow">
		<li id="mLinkEdit">Edit</li>
		<li id="mLinkDelete">Delete</li>
	</ul>

	<ul id="ulArtefactMenu" class="menu shadow">
		<li id="mArtefactDelete">Delete</li>
	</ul>

	<ul id="ulProcessMenu" class="menu shadow">
		<li id="mProcessMetaEdit">Edit Meta Data</li>
		<li id="mProcessDelete">Delete</li>
	</ul>
<jsp:include page="/bottom.jsp" />

