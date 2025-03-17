document.addEventListener("DOMContentLoaded", function () {
    const inputImagens = document.getElementById("inputImagens");
    const carouselInner = document.getElementById("carouselInner");
    const imagemPrincipal = document.getElementById("imagemPrincipal");
    let imagensSelecionadas = [];

    // Lida com a seleção de imagens
    inputImagens.addEventListener("change", function () {
        let arquivos = Array.from(this.files);
        if (arquivos.length === 0) return;

        carouselInner.innerHTML = "";
        imagensSelecionadas = arquivos;

        arquivos.forEach((file, index) => {
            let reader = new FileReader();

            reader.onload = function (e) {
                let divItem = document.createElement("div");
                divItem.classList.add("carousel-item");
                if (index === 0) {
                    divItem.classList.add("active");
                    imagemPrincipal.src = e.target.result; // Define a imagem principal
                }

                let img = document.createElement("img");
                img.src = e.target.result;
                img.classList.add("d-block", "w-100");
                img.style.height = "400px";
                img.style.objectFit = "cover";

                divItem.appendChild(img);
                carouselInner.appendChild(divItem);
            };

            reader.readAsDataURL(file);
        });
    });

    // Salvar imagens no modal e atualizar a imagem principal
    window.salvarImagens = function () {
        if (imagensSelecionadas.length > 0) {
            let reader = new FileReader();
            reader.onload = function (e) {
                imagemPrincipal.src = e.target.result;
            };
            reader.readAsDataURL(imagensSelecionadas[0]);
        }

        let modalEl = document.getElementById("modalImagem");
        let modalInstance = bootstrap.Modal.getInstance(modalEl);
        if (modalInstance) {
            modalInstance.hide();
        }
    };

    // Confirmação do cancelamento
    window.confirmarCancelamento = function () {
        window.location.href = "listarProduto.html";
    };

    // Função para salvar o produto
    window.confirmarSalvar = function () {
        let nome = document.getElementById("nome").value.trim();
        let quantidade = document.getElementById("quantidade").value.trim();
        let descricao = document.getElementById("descricao").value.trim();
        let avaliacao = document.getElementById("avaliacao").value.trim();
        let preco = document.getElementById("preco").value.trim().replace(/[^0-9,]/g, "").replace(",", ".");

        if (!nome || !preco || !quantidade || !descricao || !avaliacao) {
            alert("Todos os campos são obrigatórios!");
            return;
        }

        if (isNaN(parseFloat(preco)) || parseFloat(preco) <= 0) {
            alert("O preço deve ser um número positivo.");
            return;
        }

        if (isNaN(parseInt(quantidade)) || parseInt(quantidade) < 0) {
            alert("A quantidade deve ser um número inteiro positivo.");
            return;
        }

        if (isNaN(parseFloat(avaliacao)) || parseFloat(avaliacao) < 1 || parseFloat(avaliacao) > 5) {
            alert("A avaliação deve estar entre 1 e 5.");
            return;
        }

        let formData = new FormData();
        formData.append("nome", nome);
        formData.append("preco", parseFloat(preco));
        formData.append("quantidadeEstoque", parseInt(quantidade));
        formData.append("descricao", descricao);
        formData.append("avaliacao", parseFloat(avaliacao));

        if (imagensSelecionadas.length > 0) {
            formData.append("imagem", imagensSelecionadas[0]); // Apenas a primeira imagem será enviada como principal
        }

        fetch("http://localhost:8080/produtos", {
            method: "POST",
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Erro ao salvar o produto.");
                }
                return response.json();
            })
            .then(() => {
                alert("Produto cadastrado com sucesso!");
                window.location.href = "listarProduto.html";
            })
            .catch(error => console.error("Erro ao cadastrar produto:", error));
    };


    // Formatar o campo de preço em moeda brasileira (R$)
    document.getElementById("preco").addEventListener("input", function () {
        let valor = this.value.replace(/[^0-9]/g, "");
        valor = (parseInt(valor) / 100).toLocaleString("pt-BR", {
            style: "currency",
            currency: "BRL"
        });
        this.value = valor;
    });

    // Impedir valores fora do intervalo no campo de avaliação
    document.getElementById("avaliacao").addEventListener("input", function () {
        let valor = parseFloat(this.value);
        if (valor < 1) this.value = "1";
        if (valor > 5) this.value = "5";
    });
});
