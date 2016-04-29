<?php

require "/home/sunil/vendor/autoload.php";
$app = new \Slim\Slim();

$app->get('/:id/:lat/:long', function ($id, $lat, $long) {
	// echo $id;
	// echo "<br>";
	// echo $message;

save($id, $lat, $long);

});

$final_ar=array();
/*------------------*/

function save($id, $lat, $long){
	$servername = "localhost";
	$username = "root";
	$password = "root";
	$dbname = "project";

	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);

	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	}

		$sql = "insert into location(id, latitude, longitude) values('$id','$lat','$long');";

		if ($conn->query($sql) === TRUE) {
			// echo "Successfully subscribed to ".$cate[$i]."<br>";
		} 
		else {
			echo "You are already subscribed to ";
		}
	$conn->close();
}




$app->run();
?>

