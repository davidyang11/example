window.onload = function() {
	let decodedCookie = decodeURIComponent(document.cookie);

	//cookie format = userId=some_value; fname=some_value; lname=some_value;
    let ca = decodedCookie.split(';');
    da = ca[0].split('=');
    let userId = da[1];
    da = ca[1].split('=');
    let fname = da[1];
    da = ca[2].split('=');
    let lname = da[1];
    document.getElementById('welcomeTitle').innerHTML = 'Welcome to your Home Page, '+fname+' '+lname+'!';
        
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
    	if(xhttp.readyState == 4 && xhttp.status == 200) {
    		let temp = JSON.parse(xhttp.responseText);
//    		console.log(temp);
    		setValues(temp);
    	}
    };
    xhttp.open('POST','managerReimbAll.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send();
}

function setValues(reimb) {
	let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    da = ca[0].split('=');
    let userId = da[1];
//    console.log(userId);

	let tBody1 = document.getElementById('table2').getElementsByTagName('tbody')[0];
	for(let x = 0; x < reimb.length; x++) {
		let row = tBody1.insertRow(0);
		let cell1 = row.insertCell(0);//id
		let cell2 = row.insertCell(1);//type
		let cell3 = row.insertCell(2);//status
		let cell4 = row.insertCell(3);//requester
		let cell5 = row.insertCell(4);//amount
		let cell6 = row.insertCell(5);//issued
		let cell7 = row.insertCell(6);//description
		let cell8 = row.insertCell(7);//approve
		let cell9 = row.insertCell(8);//deny
		
		cell1.innerHTML = 'ID: ' + reimb[x].id;
		cell1.style.display = 'none';
		let seeType = reimb[x].type; 
		if(seeType == 0){
			cell2.innerHTML = 'Type: Lodging';
		} else if(seeType == 1) {
			cell2.innerHTML = 'Type: Travel';
		} else if(seeType == 2) {
			cell2.innerHTML = 'Type: Food';
		} else if(seeType == 3) {
			cell2.innerHTML = 'Type: Other';
		}
		let seeStatus = reimb[x].status;
		if(seeStatus == 0){
			cell3.innerHTML = 'Status: Pending';
		} else if(seeStatus == 1) {
			cell3.innerHTML = 'Status: Accepted';
		} else if(seeStatus == 2) {
			cell3.innerHTML = 'Status: Denied';
		}
		cell4.innerHTML = 'Requester: ' + reimb[x].name;
		cell5.innerHTML = "Amount: $" + parseFloat(reimb[x].amount).toFixed(2);
		
		let tempDate = new Date(reimb[x].dateSubmitted);
		let newDate = formatDate(tempDate);
		cell6.innerHTML = 'Issued: ' + newDate;
		cell7.innerHTML = 'Description: ' + reimb[x].description;
		
		let btnApprove = document.createElement('input');
		btnApprove.type = 'button';
		btnApprove.className = 'buttonApprove';
		btnApprove.value = 'Approve';
		btnApprove.onclick = function() {
			let xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if(xhttp.readyState == 4 && xhttp.status == 200) {
					window.location.reload();
				}
			};
			xhttp.open('POST','decide.ex',true);
			xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
			xhttp.send('reimbId='+reimb[x].id+'&status=1&resolverId='+userId);
		};
		cell8.appendChild(btnApprove);
		
		let btnDeny = document.createElement('input');
		btnDeny.type = 'button';
		btnDeny.className = 'buttonDeny';
		btnDeny.value = 'Deny';
		btnDeny.onclick = function() {
			let xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if(xhttp.readyState == 4 && xhttp.status == 200) {
					window.location.reload();						
				}
			};
			xhttp.open('POST','decide.ex',true);
			xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
			xhttp.send('reimbId='+reimb[x].id+'&status=2&resolverId='+userId);
		};
		cell9.appendChild(btnDeny);
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

function filterAll() {
	let tableBody = document.getElementById('table2').getElementsByTagName('tbody')[0];
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
	xhttp.open('POST','managerReimbAll.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send();
}
function filterPending() {
	let tableBody = document.getElementById('table2').getElementsByTagName('tbody')[0];
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
	xhttp.open('POST','filterStatus.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('status=0');
}
function filterAccepted() {
	let tableBody = document.getElementById('table2').getElementsByTagName('tbody')[0];
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
	xhttp.open('POST','filterStatus.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('status=1');
}
function filterDenied() {
	let tableBody = document.getElementById('table2').getElementsByTagName('tbody')[0];
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
	xhttp.open('POST','filterStatus.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('status=2');
}
function filterLodging() {
	let tableBody = document.getElementById('table2').getElementsByTagName('tbody')[0];
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
	xhttp.open('POST','filterType.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('type=0');
}
function filterTravel() {
	let tableBody = document.getElementById('table2').getElementsByTagName('tbody')[0];
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
	xhttp.open('POST','filterType.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('type=1');
}
function filterFood() {
	let tableBody = document.getElementById('table2').getElementsByTagName('tbody')[0];
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
	xhttp.open('POST','filterType.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('type=2');
}
function filterOther() {
	let tableBody = document.getElementById('table2').getElementsByTagName('tbody')[0];
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
	xhttp.open('POST','filterType.ex',true);
	xhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xhttp.send('type=3');
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