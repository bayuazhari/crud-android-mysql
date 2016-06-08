<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		$id = $_POST['id'];
		$name = $_POST['name'];
		$address = $_POST['address'];
		$city = $_POST['city'];
		$telp = $_POST['telp'];
		
		require_once('connection.php');
		$sql = "UPDATE distributors SET name = '$name', address = '$address', city = '$city', telp = '$telp' WHERE id = $id;";
		if(mysqli_query($con,$sql)){
			echo 'Data distributor berhasil diupdate';
		}else{
			echo 'oops! Mohon coba lagi!';
		}
		
		mysqli_close($con);
	}