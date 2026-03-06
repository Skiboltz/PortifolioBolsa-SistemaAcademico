import {fetchAuth, getPayload} from "./auth.js";

document.addEventListener("DOMContentLoaded", async () => {
	
	const payload = getPayload();
	
	if(!payload){
		window.location.href="/page/login";
		return;
	}
	
	const role = payload.role
	
	if(role !== "ROLE_ADMIN" && role !== "ROLE_TEACHER"){
		window.location.href="/page/login";
		return;
	}
	
	const tableBody = document.getElementById("usersTable");
	
	
 async function loadUsers(){
	tableBody.innerHTML = "";
	
	const response = await fetchAuth("/api/users");
	
	if(!response || !response.ok) return;
	
	const users = await response.json();
	
	users.forEach(user => {
		
		if(role === "ROLE_TEACHER" && user.role !== "ROLE_STUDENT") return;
		
		const row = document.createElement("tr");
		
		row.innerHTML = `
		<th scope="row">${user.ID}</th>
		<td>${user.name}</td>
		<td>${user.RA || ""}</td>
		<td>${user.email}</td>
		<td>${user.role}</td>
		<td>
			${role === "ROLE_ADMIN" ? 
				`<button class="btn btn-danger btn-sm delete-btn" data-id="${user.ID}">
						Deletar
				  </button>` : ""
			}
			
			${role === "ROLE_ADMIN" ? 
							`<button class="btn btn-primary btn-sm edit-btn" data-id="${user.ID}">
									Editar
							  </button>` : ""
						}
			
			${role === "ROLE_TEACHER" ? 
				`<button class="btn btn-secondary btn-sm">
					Inserir Notas
				</button>` : "" 
			}
			
		</td>
		`;
		
		tableBody.appendChild(row);
		
		});
		
		attachButtons();
	
	}
	
	function attachButtons(){
		
		document.querySelectorAll(".delete-btn").forEach(button => {
			
			button.addEventListener("click", async () => {
				
				const id = button.dataset.id;
				const confirmDelete = confirm("Deseja realmente deletar este usuário?");
				
				if(!confirmDelete) return;
				
				const response = await fetchAuth(`/api/users/${id}`, {method: "DELETE"});
				
				if(response.ok){
					alert("Usuário deletado!");
					loadUsers();
				} else {
					alert("Erro ao deletar usuário");
				}
				
			});
				
		});
		
		document.querySelectorAll(".edit-btn").forEach(button =>{
			
			button.addEventListener("click", async () => {
				
				const id = button.dataset.id;
				const get = await fetchAuth(`/api/users/${id}`);
				
				if(!get || !get.ok) return;
					
					const user = await get.json();
				
				const newName = prompt("Nome: ", user.name);
				const newEmail = prompt("Email: ", user.email);
				const newRA = prompt("RA: ", user.ra || "");
				let newRole = prompt("Role (ROLE_ADMIN, ROLE_TEACHER, ROLE_STUDENT): ", user.role);

				const updatedUser = {ID: user.ID, name: newName, email: newEmail, RA: newRA, role: newRole};
				
				const response = await fetchAuth(`/api/users/${id}`, {
					method: "PUT",
					headers: {"Content-Type": "application/json"},
					body: JSON.stringify(updatedUser)
				});
				
				if(response.ok){
					alert("Usuário atualizado!");
				} else {
					alert("Erro ao atualizar usuário");
				}
				
				loadUsers();
				
			});
			
		});
		
	}
	
	const newUserBtn = document.getElementById("newUserBtn");
	
	if(newUserBtn && role === "ROLE_ADMIN"){
		
		newUserBtn.addEventListener("click", async () => {
		
		const name = prompt("Nome: ");
		const email = prompt("Email: ");
		const roleInput = prompt("Role (ROLE_ADMIN, ROLE_TEACHER, ROLE_STUDENT): ");
		
		const newUser = {name, email, role: roleInput};
		
		console.log(newUser)
		
		const response = await fetchAuth("/api/users", {
			
				method: "POST",
				headers: {"Content-Type": "application/json"},
				body: JSON.stringify(newUser)
			
			});
		
			if(response.ok){
				alert("Usuário criado!");
			} else {
				alert("Erro ao criar usuário");
			}
			
			loadUsers();
		
		});
		
	} else if(newUserBtn) { alert("Você não tem permissão para criar usuários"); }
	
	loadUsers();
	
});