<?php
	$to = $_REQUEST['mail'];
	$subject = "Confirmaci�n de registro (RhinosApp)";
	
	$headers = "From:<RhinosApp>"."\r\n";
	$headers .= 'MIME-Version: 1.0' . "\r\n";
	$headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";

	$message = "<html>\n\t<body>";
	$message .= "	Estimado <b>".$_REQUEST['user'].":</b></br></br>";
	$message .= "<p>Con este mail le confirmamos que su direcci�n de correo (".$_REQUEST['mail'].") ha sido registrada recientemente en el servidor ". 
				"de RhinosApp. Rogamos no responda al mismo, ya que ha sido elaborado por un sistema de gesti�n autom�tica. ".
				"Ya puede descargar la aplicaci�n desde: http://pedrop225.comuf.com/rhinos/download/Rhinos_".$_REQUEST['c_version'].".apk .". 
				"A continuaci�n se encuentran los datos de su registro. </p></br></br>".
				
				"<b>Datos de registro:</b></br>".

				"<blockquote>".
				"usuario: <b>".$_REQUEST['user']."</b><br>".
				"contrase�a: <b>".$_REQUEST['password']."</b></br></br>".
				"</blockquote>".
				
				"Atentamente: </br>".
				"<blockquote><b>RhinosApp Services</b></blockquote>";
	
	$message .= "\t</body>\n</html>";
	
	mail($to, $subject, $message, $headers);
?>