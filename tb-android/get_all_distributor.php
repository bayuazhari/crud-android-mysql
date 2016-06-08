<?php 
	require_once('connection.php');
	
	$sql = "SELECT * FROM distributors";
	
	$r = mysqli_query($con,$sql);
	
	$result = array();

	while($row = mysqli_fetch_array($r)){
		
		array_push($result,array(
			"id"=>$row['id'],
			"name"=>$row['name']
		));
	}
	
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);