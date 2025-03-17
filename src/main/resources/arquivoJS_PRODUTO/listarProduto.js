// Aguarda o carregamento completo da página antes de executar o script
document.addEventListener("DOMContentLoaded", function () {
    carregarProdutos();
});

// Função para buscar e exibir todos os produtos da API
function carregarProdutos() {
    fetch("http://localhost:8080/produtos") // Faz a requisição para a API
        .then(response => response.json()) // Converte a resposta para JSON
        .then(produtos => preencherTabela(produtos)) // Chama a função para preencher a tabela
        .catch(error => console.error("Erro ao buscar produtos:", error)); // Captura erros e exibe no console
}

// Função para buscar um produto pelo ID ou Nome
function buscarProduto() {
    let termo = document.getElementById("searchInput").value.trim(); // Obtém o valor do campo de busca

    if (termo === "") {
        alert("Digite um ID ou Nome para buscar!");
        return;
    }

    if (!isNaN(termo)) {
        buscarProdutoPorId(termo); // Se for um número, busca pelo ID
    } else {
        buscarProdutoPorNome(termo); // Se for texto, busca pelo nome
    }
}

// Função para buscar um produto pelo ID
function buscarProdutoPorId(id) {
    fetch(`http://localhost:8080/produtos/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Produto não encontrado!");
            }
            return response.json();
        })
        .then(produto => preencherTabela([produto])) // Insere o produto encontrado na tabela
        .catch(error => {
            console.error("Erro ao buscar produto por ID:", error);
            alert("Produto não encontrado!");
        });
}

// Função para buscar produtos pelo nome
function buscarProdutoPorNome(nome) {
    fetch(`http://localhost:8080/produtos/nome/${nome}`)
        .then(response => response.json())
        .then(produtos => preencherTabela(produtos))
        .catch(error => {
            console.error("Erro ao buscar produto por Nome:", error);
            alert("Produto não encontrado!");
        });
}

// Função para preencher a tabela com os produtos recebidos
function preencherTabela(produtos) {
    let tabela = document.getElementById("tabelaProdutos");
    tabela.innerHTML = ""; // Limpa a tabela antes de adicionar novos itens

    if (produtos.length === 0) {
        tabela.innerHTML = `<tr><td colspan="8">Nenhum produto encontrado.</td></tr>`;
        return;
    }

    produtos.forEach(produto => {
        let statusTexto = produto.ativo ? "Ativo" : "Inativo"; // Define o status do produto
        let statusClasse = produto.ativo ? "btn-danger" : "btn-success"; // Define a classe do botão
        let statusAcao = produto.ativo ? "Desativar" : "Ativar"; // Define a ação do botão

        // Define a URL da imagem do produto, caso não exista usa um placeholder
        let imagemSrc = produto.id ? `http://localhost:8080/produtos/${produto.id}/imagem` : 'placeholder.jpg';

        // Criação da linha da tabela para exibir os dados do produto
        let linha = `
            <tr>
                <td>${produto.id}</td>
                <td>${produto.nome}</td>
                <td>${produto.avaliacao ?? '-'}</td>
                <td>R$ ${produto.preco.toFixed(2)}</td>
                <td>${produto.quantidadeEstoque}</td>
                <td>
                    <img src="${imagemSrc}" alt="Imagem do Produto" width="50" 
                         onerror="this.onerror=null; this.src='placeholder.jpg'">
                </td>
                <td>${statusTexto}</td>
                <td>
                    <button class="btn ${statusClasse}" onclick="ativarDesativarProduto(${produto.id}, ${produto.ativo})">
                        ${statusAcao}
                    </button>
                    <button class="btn btn-warning" onclick="editarProduto(${produto.id})">Editar</button>
                </td>
            </tr>
        `;
        tabela.innerHTML += linha; // Adiciona a linha à tabela
    });
}

// Função para ativar ou desativar um produto
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
            carregarProdutos(); // Recarrega a lista de produtos após a atualização
        })
        .catch(error => console.error("Erro ao alterar status do produto:", error));
}

// Função para redirecionar para a página de edição de um produto
function editarProduto(id) {
    window.location.href = `editarProduto.html?id=${id}`;
}

// Função para remover um produto da lista e da API
function removerProduto(id) {
    fetch(`http://localhost:8080/produtos/${id}`, {
        method: "DELETE"
    })
        .then(() => {
            alert("Produto removido com sucesso!");
            carregarProdutos(); // Atualiza a tabela após a remoção
        })
        .catch(error => console.error("Erro ao remover produto:", error));
}
