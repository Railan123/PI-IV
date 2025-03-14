// Aguarda o carregamento do DOM para adicionar funcionalidade ao formulário
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("formCadastroProduto").addEventListener("submit", async function (event) {
        event.preventDefault(); // Evita recarregar a página ao enviar o formulário

        // Captura os valores do formulário
        let nome = document.getElementById("nome").value.trim();
        let avaliacao = parseFloat(document.getElementById("avaliacao").value);
        let descricao = document.getElementById("descricao").value.trim();
        let preco = document.getElementById("preco").value.replace("R$ ", "").replace(/\./g, "").replace(",", "."); // Converte para formato numérico
        let quantidadeEstoque = parseInt(document.getElementById("quantidade").value);

        // Validação da avaliação (1 a 5)
        if (isNaN(avaliacao) || avaliacao < 1 || avaliacao > 5) {
            alert("A avaliação deve estar entre 1 e 5.");
            return;
        }

        // Obtém a imagem e a converte para Base64
        let imagemInput = document.getElementById("imagem");
        let imagemPadrao = await converterImagemParaBase64(imagemInput.files[0]);

        // Cria o objeto do produto
        const produto = { nome, avaliacao, descricao, preco, quantidadeEstoque, imagemPadrao, ativo: true };

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
                alert("Produto cadastrado com sucesso!"); // Exibe mensagem de sucesso
                window.location.href = "listarProduto.html"; // Redireciona para a listagem de produtos
            })
            .catch(error => {
                console.error("Erro ao cadastrar produto:", error);
                alert("Erro ao cadastrar o produto.");
            });
    });

    // Exibe a prévia da imagem ao selecionar um arquivo
    document.getElementById("imagem").addEventListener("change", function (event) {
        let file = event.target.files[0];
        if (file) {
            let reader = new FileReader();
            reader.onload = function (e) {
                document.getElementById("previewImagem").src = e.target.result; // Atualiza o src da imagem
                document.getElementById("previewImagem").style.display = "block"; // Exibe a prévia
            };
            reader.readAsDataURL(file);
        }
    });

    // Valida a entrada da avaliação para permitir apenas números entre 1 e 5
    document.getElementById("avaliacao").addEventListener("input", function () {
        let valor = parseFloat(this.value);
        if (valor < 1) this.value = 1;
        if (valor > 5) this.value = 5;
    });
});

// Função para converter a imagem para Base64
function converterImagemParaBase64(imagem) {
    return new Promise((resolve, reject) => {
        if (!imagem) {
            resolve(null);
            return;
        }
        let reader = new FileReader();
        reader.readAsDataURL(imagem);
        reader.onload = () => resolve(reader.result.split(",")[1]); // Remove prefixo "data:image/png;base64,"
        reader.onerror = error => reject(error);
    });
}

// Função para formatar o preço automaticamente enquanto o usuário digita
function formatarPreco(input) {
    let valor = input.value.replace(/\D/g, ""); // Remove tudo que não for número
    valor = (parseInt(valor) / 100).toFixed(2); // Divide por 100 para inserir as casas decimais
    valor = valor.replace(".", ","); // Substitui ponto por vírgula para padrão brasileiro

    // Adiciona separador de milhar (R$ XXX.XXX,XX)
    input.value = "R$ " + valor.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}