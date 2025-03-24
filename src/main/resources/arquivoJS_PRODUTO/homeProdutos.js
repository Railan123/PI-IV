document.addEventListener("DOMContentLoaded", function () {
    fetch("http://localhost:8080/produtos")
        .then(response => response.json())
        .then(produtos => {
            const listaProdutos = document.getElementById("listaProdutos");
            listaProdutos.innerHTML = ""; // Limpa a lista antes de adicionar os produtos

            produtos.forEach(produto => {
                const card = document.createElement("div");
                card.classList.add("col-md-4", "mb-4");

                const imagemSrc = produto.imagemPadrao
                    ? `data:image/jpeg;base64,${arrayBufferToBase64(produto.imagemPadrao)}`
                    : "/imagens/placeholder.png"; // Placeholder caso não haja imagem

                card.innerHTML = `
                    <div class="card h-100">
                        <img src="${imagemSrc}" class="card-img-top" alt="${produto.nome}">
                        <div class="card-body">
                            <h5 class="card-title">${produto.nome}</h5>
                            <p class="card-text">R$ ${produto.preco.toFixed(2)}</p>
<a href="/detalhes.html?id=${produto.id}" class="btn btn-primary">Detalhes</a>
                        </div>
                    </div>
                `;

                listaProdutos.appendChild(card);
            });
        })
        .catch(error => console.error("Erro ao carregar produtos:", error));
});

function arrayBufferToBase64(buffer) {
    let binary = "";
    const bytes = new Uint8Array(buffer);
    for (let i = 0; i < bytes.byteLength; i++) {
        binary += String.fromCharCode(bytes[i]);
    }
    return btoa(binary);
}
