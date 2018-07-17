window.onload = function(){
	document.getElementById('create_account').addEventListener('click',createAccount);
}

function createAccount(){
	let username = document.getElementById('userUsername').value;
	let password = document.getElementById('userPassword').value;
	let fname = document.getElementById('userFirstName').value;
	let lname = document.getElementById('userLastName').value;
	let email = document.getElementById('userEmail').value;
	console.log(username);
	console.log(password);
	console.log(fname);
	console.log(lname);
	console.log(email);
	
	let xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function(){
		
		if(xhttp.readyState == 4 && xhttp.status == 200){
			let temp = JSON.parse(xhttp.responseText);
			setValues(temp);
		}
	};
	
	xhttp.open('POST','create.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('username='+username+'&password='+password+'&fname='+fname+'&lname='+lname+'&email='+email);
}

function setValues(user) {
	//result = 0 --> create account failed
	//result = 1 --> create account successful
	let result = user[0];
	if(result == 0) {
		document.getElementById('message').innerHTML = "Creating your account has failed for some unsuspecting reasons!";
	} else if(result == 1) {
		window.location.replace('login.html');
	}
}