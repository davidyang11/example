
window.onload = function(){
	document.getElementById('login_button').addEventListener('click',authenticate);
}

function authenticate(){
	let username = document.getElementsByName('inputUsername');
	let password = document.getElementsByName('inputPassword');
	
	let xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function(){
		
		if(xhttp.readyState == 4 && xhttp.status == 200){
			let temp = JSON.parse(xhttp.responseText);
			setValues(temp);
		}
	};
		
	xhttp.open('POST','check.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('inputUsername='+username[0].value+'&inputPassword='+password[0].value);
//	window.location.replace('employee.html');
}

function setValues(temp){
	//result = 0 --> username or password or both are incorrect
	//result = 1 --> sign in is correct
	let result = temp[0];
	
	if(result == 0) {
		document.getElementById('message').innerHTML = "Your usernamae OR password OR BOTH is incorrect! OR you are a new user!";
	} else{
		//role = 0 --> employee
		//role = 1 --> financial manager
		let role = temp[1];
//		console.log(role);
		if(role == 0){
			document.cookie = "userID="+temp[2]+";path=/ProjectOne/html/employee.html";
			document.cookie = "fname="+temp[3]+";path=/ProjectOne/html/employee.html";
			document.cookie = "lname="+temp[4]+";path=/ProjectOne/html/employee.html";
			window.location.replace('employee.html');
		} else if(role == 1) {
			document.cookie = "userID="+temp[2]+";path=/ProjectOne/html/manager.html";
			document.cookie = "fname="+temp[3]+";path=/ProjectOne/html/manager.html";
			document.cookie = "lname="+temp[4]+";path=/ProjectOne/html/manager.html";
			window.location.replace('manager.html');
		}
	}
}