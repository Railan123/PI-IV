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

        // Captura os valores do formulário
        const email = document.getElementById("nome").value.trim(); // Alterar id para "email" no HTML
        const senha = document.getElementById("senha").value.trim();

        if (!email || !senha) {
            alert("Por favor, preencha todos os campos.");
            return;
        }

        try {
            // Fazendo requisição POST para o backend
            const response = await fetch("http://localhost:8080/usuarios/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ email, senha })
            });

            if (!response.ok) {
                throw new Error("Usuário ou senha inválidos");
            }

            const usuario = await response.json();

            // Login bem-sucedido
            alert(`Bem-vindo, ${usuario.nome}`);
            window.location.href = "menuBackAOffice.html"; // Redireciona para o painel

        } catch (error) {
            alert(error.message);
        }
    });
});
