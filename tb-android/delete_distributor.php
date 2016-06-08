<?php 
	$id = $_GET['id'];
	
	require_once('connection.php');
	
	$sql = "DELETE FROM distributors WHERE id=$id;";
	
	if(mysqli_query($con,$sql)){
		echo 'Data user berhasil dihapus';
	}else{
		echo 'oops! Mohon coba lagi!';
	}
	
	mysqli_close($con);