document.addEventListener("DOMContentLoaded", function () {
    carregarUsuarios(); // Garante que os usuários sejam carregados ao abrir a página
});

function carregarUsuarios() {
    fetch("http://localhost:8080/usuarios")
        .then(response => response.json())
        .then(usuarios => preencherTabela(usuarios))
        .catch(error => console.error("Erro ao buscar usuários:", error));
}

function buscarUsuario() {
    let termo = document.getElementById("searchInput").value.trim();

    if (termo === "") {
        alert("Digite um nome ou email para buscar!");
        return;
    }

    // Verifica se é um email pelo formato
    if (termo.includes("@")) {
        buscarUsuarioPorEmail(termo);
    } else {
        buscarUsuarioPorNome(termo);
    }
}

function buscarUsuarioPorNome(nome) {
    fetch(`http://localhost:8080/usuarios/nome/${nome}`)
        .then(response => {
            if (!response.ok) throw new Error("Usuário não encontrado");
            return response.json();
        })
        .then(usuario => preencherTabela([usuario])) // Converte para array para exibir na tabela
        .catch(error => {
            console.error("Erro ao buscar usuário por nome:", error);
            alert("Usuário não encontrado!");
        });
}

function buscarUsuarioPorEmail(email) {
    fetch(`http://localhost:8080/usuarios/email/${email}`)
        .then(response => {
            if (!response.ok) throw new Error("Usuário não encontrado");
            return response.json();
        })
        .then(usuario => preencherTabela([usuario]))
        .catch(error => {
            console.error("Erro ao buscar usuário por email:", error);
            alert("Usuário não encontrado!");
        });
}

function preencherTabela(usuarios) {
    let tabela = document.getElementById("tabelaUsuarios");
    tabela.innerHTML = ""; // Limpa a tabela antes de preencher

    if (usuarios.length === 0) {
        tabela.innerHTML = `<tr><td colspan="5">Nenhum usuário encontrado.</td></tr>`;
        return;
    }

    usuarios.forEach(usuario => {
        let linha = `
            <tr>
                <td>${usuario.id}</td>
                <td>${usuario.nome}</td>
                <td>${usuario.email}</td>
                <td>Ativo</td>
                <td>${usuario.grupo}</td>
            </tr>
        `;
        tabela.innerHTML += linha;
    });
}
