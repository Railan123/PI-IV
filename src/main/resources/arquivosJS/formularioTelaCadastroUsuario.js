// Captura o envio do formulário para validar os campos antes de enviar
document.getElementById("formCadastro").addEventListener("submit", function(event) {
    let cpf = document.getElementById("cpf").value;               
    let senha = document.getElementById("senha").value;           
    let confirmaSenha = document.getElementById("confirmaSenha").value; 

    // Validação do CPF usando a função importada de validadorCPF.js
    if (!validarCPF(cpf)) {
        document.getElementById("cpfErro").classList.remove("d-none"); // Exibe mensagem de erro
        event.preventDefault(); 
    } else {
        document.getElementById("cpfErro").classList.add("d-none"); 
    }

    // Validação da Senha (se as senhas são iguais)
    if (senha !== confirmaSenha) {
        document.getElementById("senhaErro").classList.remove("d-none"); // Exibe mensagem de erro
        event.preventDefault(); 
    } else {
        document.getElementById("senhaErro").classList.add("d-none"); 
    }
});
