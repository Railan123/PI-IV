document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("formCadastro").addEventListener("submit", function(event) {
        event.preventDefault();

        let nome = document.getElementById("nome").value.trim();
        let cpf = document.getElementById("cpf").value.trim();
        let email = document.getElementById("email").value.trim();
        let senha = document.getElementById("senha").value.trim();
        let confirmaSenha = document.getElementById("confirmaSenha").value.trim();
        let grupo = document.getElementById("grupo").value;

        let formularioValido = true;

        // Validação do CPF
        if (!validarCPF(cpf)) {
            document.getElementById("cpfErro").classList.remove("d-none");
            formularioValido = false;
        } else {
            document.getElementById("cpfErro").classList.add("d-none");
        }

        // Validação da Senha
        if (senha !== confirmaSenha) {
            document.getElementById("senhaErro").classList.remove("d-none");
            formularioValido = false;
        } else {
            document.getElementById("senhaErro").classList.add("d-none");
        }

        // Se todas as validações passaram, envia os dados para o backend
        if (formularioValido) {
            const usuario = { nome, cpf, email, senha, grupo };

            fetch("http://localhost:8080/usuarios", {
                method: "POST",
                mode: "cors",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(usuario)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.id) {
                        alert("Usuário cadastrado com sucesso!");
                        document.getElementById("formCadastro").reset();
                    } else {
                        alert("Erro ao cadastrar usuário!");
                    }
                })
                .catch(error => {
                    console.error("Erro na requisição:", error);
                    alert("Erro de conexão com o servidor.");
                });
        }
    });
});

// Função para validar CPF (básica)
function validarCPF(cpf) {
    return cpf.length === 11; // Simplesmente verifica se tem 11 dígitos
}
