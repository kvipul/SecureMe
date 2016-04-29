<?php

require "/home/sunil/vendor/autoload.php";
$app = new \Slim\Slim();

$app->get('/send_track/:mob1/:mob2/:lat/:long', function ($mob1, $mob2, $lat, $long) {
	send($mob1, $mob2, $lat, $long);
});

$app->get('/rec_track/:mob1/:mob2', function ($mob1, $mob2) {
	receive($mob1, $mob2);
});

$arr = array();

function send($mob1, $mob2, $lat, $long){
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

	$sql="insert into track_all_rec(mob1, mob2, latitude, longitude) values('$mob1','$mob2','$lat','$long');";
	if ($conn->query($sql) === TRUE) {
			// echo "Successfully subscribed to ".$cate[$i]."<br>";
	} 
	else {
		echo "You are already subscribed to ";
	}
	
	$conn->close();
}

function receive($mob1, $mob2){
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

	$sql = "select * from track_all_rec where mob2='$mob2' order by time DESC;";
	
	$result = $conn->query($sql);

	if ($result->num_rows > 0) {
    // output data of each row
    	while($row = $result->fetch_assoc()) {
    		// echo $row['latitude']."\t";
    		// echo $row['longitude'];
    		$arr['lat'] = $row['latitude'];
    		$arr['long'] = $row['longitude'];
    		echo json_encode($arr);
    		break;
    	}
	} 
	else {
    
	}

}











$app->run();

?>