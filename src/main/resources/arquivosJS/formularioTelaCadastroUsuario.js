document.addEventListener("DOMContentLoaded", function () {
    const cpfInput = document.getElementById("cpf");

    // Aplica a máscara ao CPF enquanto o usuário digita
    cpfInput.addEventListener("input", function () {
        let cpf = cpfInput.value.replace(/\D/g, ""); // Remove tudo que não for número

        if (cpf.length > 11) {
            cpf = cpf.substring(0, 11); // Garante que tenha no máximo 11 números
        }

        // Aplica a formatação
        cpfInput.value = cpf
            .replace(/^(\d{3})(\d)/, "$1.$2")
            .replace(/^(\d{3})\.(\d{3})(\d)/, "$1.$2.$3")
            .replace(/^(\d{3})\.(\d{3})\.(\d{3})(\d)/, "$1.$2.$3-$4");
    });

    document.getElementById("formCadastro").addEventListener("submit", function (event) {
        event.preventDefault();

        let nome = document.getElementById("nome").value.trim();
        let cpf = cpfInput.value.replace(/\D/g, ""); // Remove formatação para enviar só os números
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
                        window.location.href = "usuarioNaoLogado.html";
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

// Função para validar CPF (considera apenas 11 dígitos)
function validarCPF(cpf) {
    // Remove caracteres não numéricos
    cpf = cpf.replace(/\D/g, '');

    // Verifica se tem 11 dígitos
    if (cpf.length !== 11) return false;

    // Elimina CPFs inválidos conhecidos (exemplo: 000.000.000-00)
    if (/^(\d)\1{10}$/.test(cpf)) return false;

    // Calcula o primeiro dígito verificador
    let soma = 0;
    for (let i = 0; i < 9; i++) {
        soma += parseInt(cpf[i]) * (10 - i);
    }
    let resto = (soma * 10) % 11;
    let primeiroDigito = resto === 10 || resto === 11 ? 0 : resto;

    // Calcula o segundo dígito verificador
    soma = 0;
    for (let i = 0; i < 10; i++) {
        soma += parseInt(cpf[i]) * (11 - i);
    }
    resto = (soma * 10) % 11;
    let segundoDigito = resto === 10 || resto === 11 ? 0 : resto;

    // Verifica se os dígitos calculados são iguais aos originais
    return primeiroDigito === parseInt(cpf[9]) && segundoDigito === parseInt(cpf[10]);
}
