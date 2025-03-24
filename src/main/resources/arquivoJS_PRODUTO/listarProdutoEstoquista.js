document.addEventListener("DOMContentLoaded", function () {
    carregarProdutos();
});

function carregarProdutos() {
    fetch("http://localhost:8080/produtos")
        .then(response => response.json())
        .then(produtos => preencherTabela(produtos))
        .catch(error => console.error("Erro ao buscar produtos:", error));
}

function preencherTabela(produtos) {
    let tabela = document.getElementById("tabelaProdutos");
    tabela.innerHTML = "";

    if (!Array.isArray(produtos) || produtos.length === 0) {
        tabela.innerHTML = `<tr><td colspan="6">Nenhum produto encontrado.</td></tr>`;
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
                <td>R$ ${produto.preco.toFixed(2)}</td>
                <td>${produto.quantidadeEstoque}</td>
                <td>${statusTexto}</td>
                <td>
                    <button class="btn ${statusClasse}" onclick="ativarDesativarProduto(${produto.id}, ${produto.ativo})">
                        ${statusAcao}
                    </button>
                    <button class="btn btn-warning" onclick="editarProduto(${produto.id})">Editar</button>
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
            return response.json();
        })
        .then(produtoAtualizado => {
            alert(`Produto ${produtoAtualizado.ativo ? 'ativado' : 'desativado'} com sucesso!`);
            carregarProdutos();
        })
        .catch(error => console.error("Erro ao alterar status do produto:", error));
}

function editarProduto(id) {
    fetch(`http://localhost:8080/produtos/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Produto não encontrado!");
            }
            return response.json();
        })
        .then(produto => {
            document.getElementById("produtoId").value = produto.id;
            document.getElementById("produtoNome").value = produto.nome;
            document.getElementById("produtoValor").value = produto.preco.toFixed(2);
            document.getElementById("produtoQuantidade").value = produto.quantidadeEstoque;
            document.getElementById("produtoStatus").value = produto.ativo ? "Ativo" : "Inativo";

            let modal = new bootstrap.Modal(document.getElementById("modalEditarProduto"));
            modal.show();
        })
        .catch(error => {
            console.error("Erro ao carregar produto para edição:", error);
            alert("Erro ao carregar produto para edição!");
        });
}

function salvarEdicaoProduto() {
    let id = document.getElementById("produtoId").value;
    let quantidade = parseInt(document.getElementById("produtoQuantidade").value);

    fetch(`http://localhost:8080/produtos/atualizarQuantidade/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ quantidadeEstoque: quantidade })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao atualizar a quantidade do produto.");
            }
            return response.json();
        })
        .then(() => {
            alert("Quantidade atualizada com sucesso!");
            let modal = bootstrap.Modal.getInstance(document.getElementById("modalEditarProduto"));
            modal.hide();
            carregarProdutos();
        })
        .catch(error => {
            console.error("Erro ao atualizar o produto:", error);
            alert("Erro ao atualizar o produto.");
        });
}
