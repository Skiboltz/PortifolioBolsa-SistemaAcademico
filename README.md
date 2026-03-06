# Sistema Acadêmico - Projeto Web

Este projeto é um protótipo de página acadêmica desenvolvido em **Java (Spring Boot)** com **Front-end em HTML, Bootstrap e Thymeleaf**, com foco em autenticação JWT, autorização baseada em roles (admin, professor, estudante) e operações CRUD.

---

## Tecnologias

- Java 17
- Spring Boot (Security, Web, Data JPA)
- Thymeleaf
- HTML e Bootstrap
- JavaScript (modular, para CRUD e manipulação dinâmica da tabela)
- JWT (JSON Web Token) para autenticação e autorização
- Banco de PostgreSQL (configuração local via `application.properties`)

---

## Funcionalidades

### Autenticação e Autorização
- Login com e-mail (ou RA) e senha.
- Tokens JWT contendo informações do usuário e role.
> A secret para assinatura dos JWTs foi gerada randomicamente com os algoritmos da biblioteca JJWT.
- Proteção de rotas: somente usuários autenticados podem acessar páginas internas.
- Admin tem acesso completo, professor tem acesso limitado e estudante apenas a suas informações.

### CRUD de Usuários (Admin)
- **Listagem:** tabela dinâmica mostrando todos os usuários.
- **Criação:** admin pode criar novos usuários com nome, e-mail e role.
> Senhas atualmente não podem ser alteradas e são geradas randomicamente como UUID, a senha é exibida no console interno na hora da criação.
- **Edição:** admin pode editar qualquer usuário.
- **Deleção:** admin pode deletar usuários com confirmação.
- Professores podem visualizar apenas alunos e executar ações restritas (como inserir notas, sem funcionalidade completa neste protótipo).

### Front-end
- Interface responsiva usando Bootstrap.
- Navbar e footer reutilizáveis via Thymeleaf fragments.
- Tabelas dinâmicas atualizadas via JS fetch com endpoints protegidos da API.
> Note que foi prefirido a geração de tabela dinâmica via JS para não expor informações utilizando models do Thymeleaf em endpoints abertos.

---

## Como Rodar o Projeto

1. Configure o banco de dados no `application.properties`
2. Compile e execute com Spring Boot
3. Acesse no navegador "http://localhost:8080/page/login" (ou configure a porta à sua preferência)
> Está disponível no package test um algoritmo de inicialização do primeiro usuário (Admin) no banco, para que possa ser realizado os testes. Basta descomentá-lo.

---
