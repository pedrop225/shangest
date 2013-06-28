<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	INSERT INTO Queries (name, date, rep_sign, rep_witness_1, rep_witness_2, sign, witness_1, witness_2, desc_sign, desc_witness_1, desc_witness_2)
						VALUES(	'".$_REQUEST['name']."',
								'".$_REQUEST['date']."',
								'".$_REQUEST['rep_sign']."',
								'".$_REQUEST['rep_witness_1']."',
								'".$_REQUEST['rep_witness_2']."',
								'".$_REQUEST['sign']."',
								'".$_REQUEST['witness_1']."',
								'".$_REQUEST['witness_2']."',
								'".$_REQUEST['desc_sign']."',
								'".$_REQUEST['desc_witness_1']."',
								'".$_REQUEST['desc_witness_2']."')");
	mysql_close();
?>