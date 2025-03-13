document.addEventListener("DOMContentLoaded", function () {
    carregarProdutos();
});

function carregarProdutos() {
    fetch("http://localhost:8080/produtos")
        .then(response => response.json())
        .then(produtos => preencherTabela(produtos))
        .catch(error => console.error("Erro ao buscar produtos:", error));
}

function buscarProduto() {
    let termo = document.getElementById("searchInput").value.trim();

    if (termo === "") {
        alert("Digite um ID ou Nome para buscar!");
        return;
    }

    if (!isNaN(termo)) {
        buscarProdutoPorId(termo);
    } else {
        buscarProdutoPorNome(termo);
    }
}

function buscarProdutoPorId(id) {
    fetch(`http://localhost:8080/produtos/${id}`)
        .then(response => response.json())
        .then(produto => preencherTabela([produto]))
        .catch(error => {
            console.error("Erro ao buscar produto por ID:", error);
            alert("Produto não encontrado!");
        });
}

function buscarProdutoPorNome(nome) {
    fetch(`http://localhost:8080/produtos/nome/${nome}`)
        .then(response => response.json())
        .then(produtos => preencherTabela(produtos))
        .catch(error => {
            console.error("Erro ao buscar produto por Nome:", error);
            alert("Produto não encontrado!");
        });
}

function preencherTabela(produtos) {
    let tabela = document.getElementById("tabelaProdutos");
    tabela.innerHTML = "";

    if (produtos.length === 0) {
        tabela.innerHTML = `<tr><td colspan="8">Nenhum produto encontrado.</td></tr>`;
        return;
    }

    produtos.forEach(produto => {
        let statusTexto = produto.ativo ? "Ativo" : "Inativo";
        let statusClasse = produto.ativo ? "btn-danger" : "btn-success";
        let statusAcao = produto.ativo ? "Desativar" : "Ativar";

        let linha = `
            <tr>
                <td>${produto.id}</td>
                <td>${produto.nome}</td>
                <td>${produto.avaliacao ?? '-'}</td>
                <td>R$ ${produto.preco.toFixed(2)}</td>
                <td>${produto.quantidadeEstoque}</td>
                <td><img src="${produto.imagemPadrao || 'placeholder.jpg'}" alt="Imagem" width="50"></td>
                <td>${statusTexto}</td>
                <td>
                    <button class="btn ${statusClasse}" onclick="ativarDesativarProduto(${produto.id}, ${produto.ativo})">
                        ${statusAcao}
                    </button>
                    <button class="btn btn-warning" onclick="editarProduto(${produto.id})">Editar</button>
                    <button class="btn btn-danger" onclick="removerProduto(${produto.id})">Remover</button>
                </td>
            </tr>
        `;
        tabela.innerHTML += linha;
    });
}

function ativarDesativarProduto(id, ativo) {
    fetch(`http://localhost:8080/produtos/ativarDesativar/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao alterar status do produto.");
            }
            return response.json(); // Agora o backend retorna um JSON válido
        })
        .then(produtoAtualizado => {
            alert(`Produto ${produtoAtualizado.ativo ? 'ativado' : 'desativado'} com sucesso!`);
            carregarProdutos();
        })
        .catch(error => console.error("Erro ao alterar status do produto:", error));
}


function editarProduto(id) {
    window.location.href = `editarProduto.html?id=${id}`;
}

function removerProduto(id) {
    fetch(`http://localhost:8080/produtos/${id}`, {
        method: "DELETE"
    })
        .then(() => {
            alert("Produto removido com sucesso!");
            carregarProdutos();
        })
        .catch(error => console.error("Erro ao remover produto:", error));
}
