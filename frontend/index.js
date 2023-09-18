const listarArquivosButton = document.querySelector('#listar-arquivos-button');
const listaArquivosDiv = document.querySelector('#lista-arquivos');

listarArquivosButton.addEventListener('click', () => {
    fetch('http://endereco-do-servidor:porta/listar-arquivos', {
        method: 'GET',
    })
    .then(response => response.json())
    .then(data => {
        // Processar e exibir a lista de arquivos no front-end
        listaArquivosDiv.innerHTML = data.join('<br>');
    })
    .catch(error => {
        console.error('Erro ao listar arquivos:', error);
    });
});
