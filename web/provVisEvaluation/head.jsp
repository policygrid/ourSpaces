<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<script type="text/javascript" src="/ourspaces/javascript/jquery-1.6.2.min.js"></script> 
<script type="text/javascript" src="/ourspaces/javascript/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="/ourspaces/javascript/jquery.qtip-1.0.0-rc3.min.js"></script>
<script>

/**
 * Logs given things into database. 
 * @param table name of the table, where the things should be inserted.
 * @param insert comma separated list of values. The string will be inserted as such into SQL INSERT statement.
 */
function log(table, insert){
	$.ajax({
        type: "post",
        cache: false,
        url: "/ourspaces/LoggerSearch",
        data: {table:table, insert:insert}
    });
}

userID = '<%=session.hashCode()%>';
</script>


<link rel="stylesheet" type="text/css" type="text/css" href="/ourspaces/provVisEvaluation/style.css"  />
<link rel="stylesheet" type="text/css" type="text/css" href="/ourspaces/css/custom-theme/jquery-ui-1.8.12.custom.css"  />
<title>Evaluation of provenance visualisations for PolicyGrid II project</title>
</head>