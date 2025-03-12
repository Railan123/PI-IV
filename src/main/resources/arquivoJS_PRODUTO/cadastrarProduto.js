// Aguarda o carregamento do DOM para adicionar funcionalidade ao formulário
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("formCadastroProduto").addEventListener("submit", function (event) {
        event.preventDefault(); // Evita recarregar a página ao enviar o formulário

        // Captura os valores do formulário
        let nome = document.getElementById("nome").value.trim();
        let avaliacao = parseFloat(document.getElementById("avaliacao").value);
        let descricao = document.getElementById("descricao").value.trim();
        let preco = document.getElementById("preco").value.replace("R$ ", "").replace(/\./g, "").replace(",", "."); // Converte para formato numérico
        let quantidade = parseInt(document.getElementById("quantidade").value);
        let imagem = document.getElementById("imagem").value.trim() || null;

        // Cria o objeto do produto
        const produto = { nome, avaliacao, descricao, preco, quantidade, imagem };

        // Envia os dados para a API via POST
        fetch("http://localhost:8080/produtos", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(produto)
        })
            .then(response => {
                if (!response.ok) throw new Error("Erro ao cadastrar produto");
                return response.json();
            })
            .then(data => {
                alert("Produto cadastrado com sucesso!");
                document.getElementById("formCadastroProduto").reset(); // Limpa o formulário
            })
            .catch(error => {
                console.error("Erro ao cadastrar produto:", error);
                alert("Erro ao cadastrar o produto.");
            });
    });
});

// Função para formatar o preço automaticamente enquanto o usuário digita
function formatarPreco(input) {
    let valor = input.value.replace(/\D/g, ""); // Remove tudo que não for número
    valor = (parseInt(valor) / 100).toFixed(2); // Divide por 100 para inserir as casas decimais
    valor = valor.replace(".", ","); // Substitui ponto por vírgula para padrão brasileiro

    // Adiciona separador de milhar (R$ XXX.XXX,XX)
    input.value = "R$ " + valor.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}
