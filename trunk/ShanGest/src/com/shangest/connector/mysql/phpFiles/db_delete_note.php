<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("DELETE FROM Notes
					  WHERE (name='".$_REQUEST['name']."'
					  		 AND datetime='".$_REQUEST['datetime']."')");
	
	mysql_close();
?>