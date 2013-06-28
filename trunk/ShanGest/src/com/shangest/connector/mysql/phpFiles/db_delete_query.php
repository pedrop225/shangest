<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("DELETE FROM Queries
					  WHERE (name='".$_REQUEST['name']."'
					  		 AND date='".$_REQUEST['date']."')");
	
	mysql_close();
?>