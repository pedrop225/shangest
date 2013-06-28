<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	INSERT INTO Notes (name, datetime, description)
						VALUES(	'".$_REQUEST['name']."',
								'".$_REQUEST['datetime']."',
								'".$_REQUEST['description']."')");	
	mysql_close();
?>