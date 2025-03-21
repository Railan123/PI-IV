document.addEventListener("DOMContentLoaded", function () {
    carregarProdutos();
});

// Função para carregar os produtos na tabela
function carregarProdutos() {
    fetch("http://localhost:8080/produtos")
        .then(response => response.json())
        .then(produtos => preencherTabela(produtos))
        .catch(error => console.error("Erro ao buscar produtos:", error));
}

// Função para preencher a tabela com os produtos
function preencherTabela(produtos) {
    let tabela = document.getElementById("tabelaProdutos");
    tabela.innerHTML = "";

    if (produtos.length === 0) {
        tabela.innerHTML = `<tr><td colspan="7">Nenhum produto encontrado.</td></tr>`;
        return;
    }

    produtos.forEach(produto => {
        let statusTexto = produto.ativo ? "Ativo" : "Inativo";
        let novaLinha = `
            <tr>
                <td>${produto.id}</td>
                <td>${produto.nome}</td>
                <td>${produto.quantidadeEstoque}</td>
                <td>R$ ${produto.preco.toFixed(2)}</td>
                <td>${statusTexto}</td>
                <td>
                    <button class="btn btn-info" onclick="visualizarProduto(${produto.id})">Visualizar</button>
                    <button class="btn btn-warning" onclick="editarProduto(${produto.id})">Editar</button>
                    <button class="btn ${produto.ativo ? 'btn-danger' : 'btn-success'}" onclick="alterarStatusProduto(${produto.id}, ${produto.ativo})">
                        ${produto.ativo ? 'Desativar' : 'Ativar'}
                    </button>
                </td>
            </tr>
        `;
        tabela.innerHTML += novaLinha;
    });
}

// Função para visualizar detalhes do produto
function visualizarProduto(id) {
    fetch(`http://localhost:8080/produtos/${id}`)
        .then(response => response.json())
        .then(produto => {
            alert(`Produto: ${produto.nome}\nQuantidade: ${produto.quantidadeEstoque}\nPreço: R$ ${produto.preco.toFixed(2)}\nStatus: ${produto.ativo ? "Ativo" : "Inativo"}`);
        })
        .catch(error => alert("Erro ao carregar detalhes do produto."));
}

function editarProduto(id) {
    fetch(`http://localhost:8080/produtos/${id}`)
        .then(response => response.json())
        .then(produto => {
            document.getElementById("produtoId").value = produto.id;
            document.getElementById("produtoNome").value = produto.nome;
            document.getElementById("produtoValor").value = produto.preco;
            document.getElementById("produtoQuantidade").value = produto.quantidadeEstoque;
            document.getElementById("produtoDescricao").value = produto.descricao;
            document.getElementById("produtoAvaliacao").value = produto.avaliacao;
            document.getElementById("produtoStatus").value = produto.ativo;

            let modal = new bootstrap.Modal(document.getElementById("modalEditarProduto"));
            modal.show();
        })
        .catch(error => alert("Erro ao carregar os dados do produto."));
}


// Função para salvar alterações do produto
function salvarEdicaoProduto() {
    let id = document.getElementById("produtoId").value;
    let nome = document.getElementById("produtoNome").value;
    let preco = parseFloat(document.getElementById("produtoValor").value);
    let quantidade = parseInt(document.getElementById("produtoQuantidade").value);
    let descricao = document.getElementById("produtoDescricao").value;
    let avaliacao = parseFloat(document.getElementById("produtoAvaliacao").value);
    let status = document.getElementById("produtoStatus").value === "true";

    let produtoAtualizado = {
        id: id,
        nome: nome,
        preco: preco,
        quantidadeEstoque: quantidade,
        descricao: descricao,
        avaliacao: avaliacao,
        ativo: status
    };

    fetch(`http://localhost:8080/produtos/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(produtoAtualizado)
    })
        .then(() => {
            alert("Produto atualizado com sucesso!");
            let modal = bootstrap.Modal.getInstance(document.getElementById("modalEditarProduto"));
            modal.hide();
            carregarProdutos();
        })
        .catch(() => alert("Erro ao atualizar o produto."));
}

// Redireciona para a tela de cadastro
function cadastrarProduto() {
    window.location.href = "cadastrarProduto.html";
}
