
window.onload = function() {
	let decodedCookie = decodeURIComponent(document.cookie);
	//cookie format = userId=some_value; fname=some_value; lname=some_value;
    let ca = decodedCookie.split(';');
    da = ca[0].split('=');
    let userId = da[1];
//    console.log(userId);
    da = ca[1].split('=');
    let fname = da[1];
    da = ca[2].split('=');
    let lname = da[1];
    document.getElementById('welcomeTitle').innerHTML = 'Welcome to your Home Page, '+fname+' '+lname+'!';
    
    document.getElementById('submitRequest').addEventListener('click',function(event){
    	event.preventDefault();
    	create_reimb();
    });
    
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    	if(xhttp.readyState == 4 && xhttp.status == 200) {
    		let temp = JSON.parse(xhttp.responseText);
    		setValues(temp);
    	}
    };
    xhttp.open('POST','employeeReimbAll.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('userId='+userId);
}

function setValues(reimb) {
	let tBody1 = document.getElementById('table1').getElementsByTagName('tbody')[0];
	for(let x = 0; x < reimb.length; x++) {
		let row = tBody1.insertRow(0);
		let cell1 = row.insertCell(0);
		let cell2 = row.insertCell(1);
		let cell3 = row.insertCell(2);
		let cell4 = row.insertCell(3);
		let cell5 = row.insertCell(4);
		let cell6 = row.insertCell(5);
		
		let seeType = reimb[x].type;
		if(seeType == 0){
			cell1.innerHTML = 'Lodging';
		} else if(seeType == 1) {
			cell1.innerHTML = 'Travel';
		} else if(seeType == 2) {
			cell1.innerHTML = 'Food';
		} else if(seeType == 3) {
			cell1.innerHTML = 'Other';
		}
		let seeStatus = reimb[x].status;
		if(seeStatus == 0){
			cell2.innerHTML = 'Pending';
		} else if(seeStatus == 1) {
			cell2.innerHTML = 'Accepted';
		} else if(seeStatus == 2) {
			cell2.innerHTML = 'Denied';
		}
		cell3.innerHTML = "$" + parseFloat(reimb[x].amount).toFixed(2);

		let submitDate = new Date(reimb[x].dateSubmitted);
		let newDate = formatDate(submitDate);
		cell4.innerHTML = newDate;
		cell5.innerHTML = reimb[x].description;
		let seeResolved = reimb[x].dateResolved;
		if(seeResolved == null) {
			cell6.innerHTML = 'Not Yet';
		} else {
			let resolvedDate = new Date(seeResolved);
			let newResolved = formatDate(resolvedDate);
			cell6.innerHTML = newResolved;
		}
	}
}

function formatDate(dateX) {
	  let hours = dateX.getHours();
	  let minutes = dateX.getMinutes();
	  let ampm = hours >= 12 ? 'pm' : 'am';
	  hours = hours % 12;
	  hours = hours ? hours : 12; // the hour '0' should be '12'
	  minutes = minutes < 10 ? '0'+minutes : minutes;
	  let strTime = hours + ':' + minutes + ' ' + ampm;
	  return dateX.getMonth()+1 + "/" + dateX.getDate() + "/" + dateX.getFullYear() + "  " + strTime;
}

function create_reimb() {
	let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    da = ca[0].split('=');
    let userId = da[1];
    
    let type = document.getElementById('reimbType').value;
    let amount = document.getElementById('reimbAmount').value;
    let description = document.getElementById('description').value;
    
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    	if(xhttp.readyState == 4 && xhttp.status == 200) {
    		let temp = JSON.parse(xhttp.responseText);
    		setLines(temp[0]);
    	}
    };
    xhttp.open('POST','employeeAddReimb.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('userId='+userId+'&type='+type+'&amount='+amount+'&description='+description);
}

function setLines(reimb){
	if(reimb == 0){
		document.getElementById('message').innerHTML = "SHIT";
	} else {
		window.location.reload();
	}
}

function filterAll() {
	let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    da = ca[0].split('=');
    let userId = da[1];
	let tableBody = document.getElementById('table1').getElementsByTagName('tbody')[0];
	for(let i = tableBody.rows.length; i > 0; i--){
		tableBody.deleteRow(i - 1);
	}
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			let temp = JSON.parse(xhttp.responseText);
			setValues(temp);
		}
	};
    xhttp.open('POST','employeeReimbAll.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('userId='+userId);
}
function filterPending() {
	let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    da = ca[0].split('=');
    let userId = da[1];
	let tableBody = document.getElementById('table1').getElementsByTagName('tbody')[0];
	for(let i = tableBody.rows.length; i > 0; i--){
		tableBody.deleteRow(i - 1);
	}
	
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			let temp = JSON.parse(xhttp.responseText);
			setValues(temp);
		}
	};
	xhttp.open('POST','eFilterStatus.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('userId='+userId+'&status=0');
}
function filterAccepted() {
	let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    da = ca[0].split('=');
    let userId = da[1];
	let tableBody = document.getElementById('table1').getElementsByTagName('tbody')[0];
	for(let i = tableBody.rows.length; i > 0; i--){
		tableBody.deleteRow(i - 1);
	}
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			let temp = JSON.parse(xhttp.responseText);
			setValues(temp);
		}
	};
	xhttp.open('POST','eFilterStatus.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('userId='+userId+'&status=1');
}
function filterDenied() {
	let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    da = ca[0].split('=');
    let userId = da[1];
	let tableBody = document.getElementById('table1').getElementsByTagName('tbody')[0];
	for(let i = tableBody.rows.length; i > 0; i--){
		tableBody.deleteRow(i - 1);
	}
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			let temp = JSON.parse(xhttp.responseText);
			setValues(temp);
		}
	};
	xhttp.open('POST','eFilterStatus.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('userId='+userId+'&status=2');
}
function filterLodging() {
	let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    da = ca[0].split('=');
    let userId = da[1];
	let tableBody = document.getElementById('table1').getElementsByTagName('tbody')[0];
	for(let i = tableBody.rows.length; i > 0; i--){
		tableBody.deleteRow(i - 1);
	}
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			let temp = JSON.parse(xhttp.responseText);
			setValues(temp);
		}
	};
	xhttp.open('POST','eFilterType.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('userId='+userId+'&type=0');
}
function filterTravel() {
	let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    da = ca[0].split('=');
    let userId = da[1];
	let tableBody = document.getElementById('table1').getElementsByTagName('tbody')[0];
	for(let i = tableBody.rows.length; i > 0; i--){
		tableBody.deleteRow(i - 1);
	}
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			let temp = JSON.parse(xhttp.responseText);
			setValues(temp);
		}
	};
	xhttp.open('POST','eFilterType.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('userId='+userId+'&type=1');
}
function filterFood() {
	let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    da = ca[0].split('=');
    let userId = da[1];
	let tableBody = document.getElementById('table1').getElementsByTagName('tbody')[0];
	for(let i = tableBody.rows.length; i > 0; i--){
		tableBody.deleteRow(i - 1);
	}
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			let temp = JSON.parse(xhttp.responseText);
			setValues(temp);
		}
	};
	xhttp.open('POST','eFilterType.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('userId='+userId+'&type=2');
}
function filterOther() {
	let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    da = ca[0].split('=');
    let userId = da[1];
	let tableBody = document.getElementById('table1').getElementsByTagName('tbody')[0];
	for(let i = tableBody.rows.length; i > 0; i--){
		tableBody.deleteRow(i - 1);
	}
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(xhttp.readyState == 4 && xhttp.status == 200) {
			let temp = JSON.parse(xhttp.responseText);
			setValues(temp);
		}
	};
	xhttp.open('POST','eFilterType.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('userId='+userId+'&type=3');
}

function openPage(pageName,elmnt) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].style.backgroundColor = "";
    }
    document.getElementById(pageName).style.display = "block";
    elmnt.style.backgroundColor = "#262626";

}
// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();

function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("main").style.marginLeft= "0";
}

document.getElementById('logOUT').addEventListener('click',function(){
	let decodedCookie = decodeURIComponent(document.cookie);
	//cookie format = userId=some_value; fname=some_value; lname=some_value;
    let ca = decodedCookie.split(';');
    da = ca[0].split('=');
    let userId = da[1];
    console.log(userId);
    da = ca[1].split('=');
    let fname = da[1];
    da = ca[2].split('=');
    let lname = da[1];
    let date = new Date();
    date.setTime(date.getTime() + (days*24*60*60*1000));
    let expires = ";expires=" + date.toUTCString();
	document.cookie = 'userId='+userId+expires+';path=/';
	document.cookie = 'fname='+fname+expires+';path=/';
	document.cookie = 'lname='+lname+expires+';path=/';
});