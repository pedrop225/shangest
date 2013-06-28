<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	UPDATE Signs
						   SET description='".$_REQUEST['description']."' 
						   WHERE (representation='".$_REQUEST['representation']."')");
	mysql_close();
?>