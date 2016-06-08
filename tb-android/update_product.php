<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		$id = $_POST['id'];
		$name = $_POST['name'];
		$description = $_POST['description'];
		$category = $_POST['category'];
		$price = $_POST['price'];
		$stock = $_POST['stock'];
		
		require_once('connection.php');
		$sql = "UPDATE products SET name = '$name', description = '$description', category = '$category', price = '$price', stock = '$stock' WHERE id = $id;";
		if(mysqli_query($con,$sql)){
			echo 'Data produk berhasil diupdate';
		}else{
			echo 'oops! Mohon coba lagi!';
		}
		
		mysqli_close($con);
	}