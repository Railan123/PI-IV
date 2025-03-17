document.addEventListener("DOMContentLoaded", function () {
    const inputImagens = document.getElementById("inputImagens");
    const carouselInner = document.getElementById("carouselInner");
    const imagemPrincipal = document.getElementById("imagemPrincipal");
    let imagensSelecionadas = [];

    inputImagens.addEventListener("change", function () {
        let arquivos = Array.from(this.files);
        if (arquivos.length === 0) return;

        carouselInner.innerHTML = "";
        imagensSelecionadas = [];

        arquivos.forEach((file, index) => {
            let reader = new FileReader();

            reader.onload = function (e) {
                let divItem = document.createElement("div");
                divItem.classList.add("carousel-item");
                if (index === 0) {
                    divItem.classList.add("active");
                    imagemPrincipal.src = e.target.result;
                }

                let img = document.createElement("img");
                img.src = e.target.result;
                img.classList.add("d-block", "w-100");
                img.style.height = "400px";
                img.style.objectFit = "cover";

                divItem.appendChild(img);
                carouselInner.appendChild(divItem);
                imagensSelecionadas.push(file);
            };

            reader.readAsDataURL(file);
        });

        let carousel = new bootstrap.Carousel("#carouselImagens", {
            interval: false,
            wrap: true
        });
    });

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

    document.getElementById("formCadastroProduto").addEventListener("submit", function (event) {
        event.preventDefault();

        let nome = document.getElementById("nome").value.trim();
        let preco = document.getElementById("preco").value.trim().replace("R$ ", "").replace(/\./g, "").replace(",", ".");
        let quantidade = document.getElementById("quantidade").value.trim();
        let descricao = document.getElementById("descricao").value.trim();
        let avaliacao = document.getElementById("avaliacao").value.trim();

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

        let produto = {
            nome: nome,
            preco: parseFloat(preco),
            quantidadeEstoque: parseInt(quantidade),
            descricao: descricao,
            avaliacao: parseFloat(avaliacao)
        };

        fetch("http://localhost:8080/produtos", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(produto)
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
    });

    document.getElementById("preco").addEventListener("input", function () {
        let valor = this.value.replace(/[^0-9]/g, "");
        valor = (parseInt(valor) / 100).toLocaleString("pt-BR", {
            style: "currency",
            currency: "BRL"
        });
        this.value = valor;
    });

    document.getElementById("avaliacao").addEventListener("input", function () {
        let valor = parseFloat(this.value);
        if (valor < 1) this.value = "1";
        if (valor > 5) this.value = "5";
    });

    window.confirmarCancelamento = function () {
        window.location.href = "listarProduto.html";
    };
});
