document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form");
    const cancelButton = document.querySelector(".button-group button:nth-child(2)");
  
    // Botão Cancelar: reseta os campos do formulário
    cancelButton.addEventListener("click", (e) => {
      e.preventDefault();
      form.reset();
    });
  
    form.addEventListener("submit", async (e) => {
      e.preventDefault();
  
      // O campo "nome" está sendo utilizado para o email do usuário
      const email = document.getElementById("nome").value.trim();
      const senha = document.getElementById("senha").value.trim();
  
      if (!email || !senha) {
        alert("Por favor, preencha todos os campos.");
        return;
      }
  
      try {
        // Faz uma requisição GET para buscar todos os usuários no endpoint fornecido
        const response = await fetch("/usuarios");
        const usuarios = await response.json();
  
        // Procura por um usuário com email, senha e status ativo
        const usuarioValido = usuarios.find(usuario => 
          usuario.email === email && 
          usuario.senha === senha && 
          usuario.status === "ativo"
        );
  
        if (usuarioValido) {
          // Usuário autenticado: redireciona para a tela principal do backoffice
          window.location.href = "backoffice.html";
        } else {
          // Se não encontrar ou usuário inativo, nega o acesso
          alert("Usuário ou senha inválidos, ou usuário inativo.");
        }
      } catch (error) {
        console.error("Erro ao buscar usuários:", error);
        alert("Ocorreu um erro ao tentar fazer login. Tente novamente.");
      }
    });
  });
  