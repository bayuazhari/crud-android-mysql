<?php 
	$id = $_GET['id'];
	
	require_once('connection.php');
	
	$sql = "DELETE FROM products WHERE id=$id;";
	
	if(mysqli_query($con,$sql)){
		echo 'Data produk berhasil dihapus';
	}else{
		echo 'oops! Mohon coba lagi!';
	}
	
	mysqli_close($con);