<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	INSERT INTO Signs
						VALUES(	'".$_REQUEST['representation']."',
								'".$_REQUEST['name']."',
								'".$_REQUEST['description']."')");	
	mysql_close();
?>