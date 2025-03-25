document.addEventListener("DOMContentLoaded", function () {
    // Carregar produtos da API
    carregarProdutos();

    // Função para carregar os produtos na tela
    function carregarProdutos() {
        fetch("http://localhost:8080/produtos")
            .then(response => response.json())
            .then(produtos => {
                let listaProdutos = document.getElementById("listaProdutos");
                listaProdutos.innerHTML = ""; // Limpa a lista antes de adicionar os novos produtos

                produtos.forEach(produto => {
                    let imagemSrc = produto.imagemPadrao
                        ? `data:image/jpeg;base64,${arrayBufferToBase64(produto.imagemPadrao)}`
                        : "/imagens/placeholder.png";

                    listaProdutos.innerHTML += `
                        <div class="col-md-4 mb-4">
                            <div class="card">
                                <img src="${imagemSrc}" class="card-img-top" alt="${produto.nome}">
                                <div class="card-body">
                                    <h5 class="card-title">${produto.nome}</h5>
                                    <p class="card-text">R$ ${produto.preco.toFixed(2)}</p>
                                    <button class="btn btn-info" onclick="visualizarProduto(${produto.id})">Visualizar</button>
                                </div>
                            </div>
                        </div>
                    `;
                });
            })
            .catch(error => {
                console.error("Erro ao carregar os produtos:", error);
            });
    }

    // Função para exibir os detalhes do produto no modal
    window.visualizarProduto = function(id) {
        fetch(`http://localhost:8080/produtos/${id}`)
            .then(response => response.json())
            .then(produto => {
                document.getElementById("produtoNomeModal").innerText = produto.nome;
                document.getElementById("produtoQuantidadeModal").innerText = produto.quantidadeEstoque;

                document.getElementById("produtoPrecoModal").innerText = produto.preco.toFixed(2);

                if (produto.imagemPadrao) {
                    document.getElementById("produtoImagemModal").src = `data:image/png;base64,${produto.imagemPadrao}`;
                } else {
                    document.getElementById("produtoImagemModal").src = "https://via.placeholder.com/300?text=Sem+Imagem";
                }

                // Exibe o modal
                let modal = new bootstrap.Modal(document.getElementById("modalVisualizarProduto"));
                modal.show();
            })
            .catch(error => {
                console.error("Erro ao carregar os detalhes do produto:", error);
                alert("Erro ao carregar os detalhes do produto.");
            });
    };

    // Função para converter o array de bytes em base64
    function arrayBufferToBase64(buffer) {
        let binary = "";
        const bytes = new Uint8Array(buffer);
        for (let i = 0; i < bytes.byteLength; i++) {
            binary += String.fromCharCode(bytes[i]);
        }
        return btoa(binary);
    }
});
