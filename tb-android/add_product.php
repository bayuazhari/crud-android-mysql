<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
		$name = $_POST['name'];
		$description = $_POST['description'];
		$category = $_POST['category'];
		$price = $_POST['price'];
		$stock = $_POST['stock'];
		
		if($name == '' || $description == '' || $category == '' || $price == '' || $stock == ''){
			echo 'Mohon datanya dilengkapi terlebih dahulu!';
		}else{
			require_once('connection.php');
			$sql = "SELECT * FROM products WHERE name='$name'";
			
			$check = mysqli_fetch_array(mysqli_query($con,$sql));
			
			if(isset($check)){
				echo 'Nama produk sudah ada';
			}else{				
				$sql = "INSERT INTO products (name,description,category,price,stock) VALUES('$name','$description','$category','$price','$stock')";
				if(mysqli_query($con,$sql)){
					echo 'Data berhasil diinput';
				}else{
					echo 'oops! Mohon coba lagi!';
				}
			}
			mysqli_close($con);
		}
}else{
echo 'error';
}