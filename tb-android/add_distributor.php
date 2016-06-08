<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
		$name = $_POST['name'];
		$address = $_POST['address'];
		$city = $_POST['city'];
		$telp = $_POST['telp'];
		
		if($name == '' || $address == '' || $city == '' || $telp == ''){
			echo 'Mohon datanya dilengkapi terlebih dahulu!';
		}else{
			require_once('connection.php');
			$sql = "SELECT * FROM distributors WHERE name='$name'";
			
			$check = mysqli_fetch_array(mysqli_query($con,$sql));
			
			if(isset($check)){
				echo 'Nama distributor sudah ada';
			}else{				
				$sql = "INSERT INTO distributors (name,address,city,telp) VALUES('$name','$address','$city','$telp')";
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