document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("form").addEventListener("submit", function (event) {
        event.preventDefault();



        let email = document.getElementById("email").value.trim();
        let senha = document.getElementById("senha").value.trim();

        fetch("http://localhost:8080/usuarios/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, senha })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Email ou senha inválidos!");
                }
                return response.text();
            })
            .then(message => {
                alert(message);
                window.location.href = "menuBackOffice.html"; // Redireciona o admin para o painel
            })
            .catch(error => {
                alert(error.message);
            });
    });
});
