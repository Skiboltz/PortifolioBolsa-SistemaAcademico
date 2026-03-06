const API_URL = "http://localhost:8080";

 export function saveToken(token){
	
	localStorage.setItem("token", token);
	
}

export function getToken(){
	
	return localStorage.getItem("token");
	
}

export function logout(){
	
	localStorage.removeItem("token");
	
	window.location.href="/page/login";
	
}

export async function fetchAuth(endpoint, options = {}){
	
	const token = getToken();
	
	if(!token){
		
		window.location.href="/page/login";
		
	}
	
	if(!options.headers){
		options.headers = {};
	}
	
	options.headers["Authorization"] = "Bearer " + token;
	
	const response = await fetch(API_URL + endpoint, options);
	
	if(response.status === 401 || response.status === 403){
		
		logout();
		
		return;
		
	}
	
	return response;
	
}

export function getPayload(){
	
	const token = getToken();
	
	if(!token && window.location.href !== API_URL + "/page/login"){

		window.location.href = "/page/login";
		
	} else if(!token){ return; }
	
	try{
	
	const payload = JSON.parse(atob(token.split('.')[1]));
	
	const now = Date.now() / 1000;
	
	if(payload.exp && payload.exp < now){
		
		logout();
		return null;
		
	}
	
	return payload;
	
	} catch(e){
		
		logout();
		return null;
		
	}
	
}

if(window.location.pathname === "/page/login" && getPayload()){
	window.location.href = "/page/home";
}

document.addEventListener("DOMContentLoaded", () => {
	
	const loginForm = document.getElementById("loginForm");
	
	if(loginForm){
		
		loginForm.addEventListener("submit", async function(event){
			
			event.preventDefault();
			
			const login = document.getElementById("login").value;
			const password = document.getElementById("password").value;
			
			const response = await fetch(API_URL + "/login",{
				
				method:"POST",
				
				headers:{
					"Content-Type":"application/json"
				},
				
				body: JSON.stringify({
					
					login: login,
					password: password
					
				})
				
			});
			
			if(response.ok){
				
				const data = await response.json();
				
				saveToken(data.token);
				
				window.location.href="/page/home";
				
			} else {
				
				alert("Login inválido");
				
			}
			
		});
		
	}
	
})

