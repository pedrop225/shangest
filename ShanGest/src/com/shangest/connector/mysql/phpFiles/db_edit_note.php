<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	UPDATE Notes
						SET	datetime='".$_REQUEST['datetime']."',
							description='".$_REQUEST['description']."'
						WHERE (id='".$_REQUEST['id']."')");
	mysql_close();
?>