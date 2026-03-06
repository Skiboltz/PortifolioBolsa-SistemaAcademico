import {fetchAuth, getPayload, logout} from "./auth.js"

document.addEventListener("DOMContentLoaded", async () => {
	
	const response = await fetchAuth("/api/users/me");
	
	if(!response || !response.ok){
		return;
	}
	
	const user = await response.json();
	
	if(window.location.pathname === "/page/home"){
	document.getElementById("email").innerText = user.email;
	document.getElementById("ra").innerText = user.RA || "-";
	}
	
	if(user.role === "ROLE_ADMIN"){
		
		document.getElementById("adminLink").style.display="block";
		document.getElementById("professorLink").style.display="block"
		
	} else if(user.role === "ROLE_TEACHER"){
		
		document.getElementById("professorLink").style.display="block"
		
	}
	
		document.getElementById("logout").addEventListener("click", logout);
	
});