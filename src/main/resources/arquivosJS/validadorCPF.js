/**
 * Função para validar CPF
 * @param {string} cpf - Número do CPF a ser validado
 * @returns {boolean} - Retorna true se for válido, false se for inválido
 */
function validarCPF(cpf) {
    // Remove caracteres não numéricos (pontos e traços)
    cpf = cpf.replace(/[^\d]+/g, '');

    // Verifica se o CPF tem exatamente 11 dígitos
    if (cpf.length !== 11) return false;

    // Impede CPFs com números repetidos (exemplo: 000.000.000-00)
    if (/^(\d)\1{10}$/.test(cpf)) return false;

    // Cálculo do primeiro dígito verificador
    let soma = 0, resto;
    for (let i = 1; i <= 9; i++) {
        soma += parseInt(cpf.charAt(i - 1)) * (11 - i);
    }
    resto = (soma * 10) % 11;
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.charAt(9))) return false;

    // Cálculo do segundo dígito verificador
    soma = 0;
    for (let i = 1; i <= 10; i++) {
        soma += parseInt(cpf.charAt(i - 1)) * (12 - i);
    }
    resto = (soma * 10) % 11;
    if (resto === 10 || resto === 11) resto = 0;
    
    // Retorna verdadeiro se o CPF for válido, falso caso contrário
    return resto === parseInt(cpf.charAt(10));
}
