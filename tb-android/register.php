<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
		$fullname = $_POST['fullname'];
		$email = $_POST['email'];
		$username = $_POST['username'];
		$password = $_POST['password'];
		$gender = $_POST['gender'];
		$address = $_POST['address'];
		$city = $_POST['city'];
		$telp = $_POST['telp'];
		
		if($fullname == '' || $email == '' || $username == '' || $password == '' || $gender == '' || $address == '' || $city == '' || $telp == ''){
			echo 'Mohon datanya dilengkapi terlebih dahulu!';
		}else{
			require_once('connection.php');
			$sql = "SELECT * FROM users WHERE username='$username' OR email='$email'";
			
			$check = mysqli_fetch_array(mysqli_query($con,$sql));
			
			if(isset($check)){
				echo 'Username atau email sudah ada';
			}else{				
				$sql = "INSERT INTO users (fullname,email,username,password,gender,address,city,telp) VALUES('$fullname','$email','$username','$password','$gender','$address','$city','$telp')";
				if(mysqli_query($con,$sql)){
					echo 'Register berhasil';
				}else{
					echo 'oops! Mohon coba lagi!';
				}
			}
			mysqli_close($con);
		}
}else{
echo 'error';
}