<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q0 = mysql_query("	INSERT INTO Users (user, password) 
						VALUES ('".$_REQUEST['name']."',
								'".$_REQUEST['mail']."',
								'".$_REQUEST['password']."',
								'".$_REQUEST['user']."')");
	mysql_close();
?>