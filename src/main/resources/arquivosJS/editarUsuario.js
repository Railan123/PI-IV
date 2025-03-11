document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const userId = urlParams.get("id");

    if (!userId) {
        alert("ID do usuário não encontrado!");
        window.location.href = "listarUsuarios.html";
        return;
    }

    carregarDadosUsuario(userId);

    document.getElementById("formEditarUsuario").addEventListener("submit", function (event) {
        event.preventDefault();

        let nome = document.getElementById("nome").value.trim();
        let email = document.getElementById("email").value.trim();
        let grupo = document.getElementById("grupo").value;

        const usuarioAtualizado = { nome, email, grupo };

        // Verifique os dados antes de enviar
        console.log(usuarioAtualizado);

        fetch(`http://localhost:8080/usuarios/${userId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(usuarioAtualizado)
        })
            .then(response => response.json())
            .then(data => {
                alert("Usuário atualizado com sucesso!");
                window.location.href = "listarUsuarios.html";
            })
            .catch(error => {
                console.error("Erro ao atualizar usuário:", error);
                alert("Erro ao atualizar o usuário.");
            });
    });
});

function carregarDadosUsuario(userId) {
    fetch(`http://localhost:8080/usuarios/${userId}`)
        .then(response => response.json())
        .then(usuario => {
            document.getElementById("id").value = usuario.id;
            document.getElementById("nome").value = usuario.nome_completo; // Ajuste de acordo com o nome correto no backend
            document.getElementById("cpf").value = usuario.cpf;
            document.getElementById("email").value = usuario.email;
            document.getElementById("grupo").value = usuario.grupo;
        })
        .catch(error => {
            console.error("Erro ao buscar usuário:", error);
            alert("Usuário não encontrado!");
            window.location.href = "listarUsuarios.html";
        });
}
