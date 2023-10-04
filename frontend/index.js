const listarArquivosButton = document.querySelector('#listar-arquivos-button');
const listaArquivosDiv = document.querySelector('#lista-arquivos');

listarArquivosButton.addEventListener('click', () => {
    fetch('http://localhost:8080/listar-arquivos', {
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

const uploadArquivoButton = document.querySelector('#upload-arquivo-button');
const arquivoInput = document.querySelector('#arquivo-input');

uploadArquivoButton.addEventListener('click', () => {
    const file = arquivoInput.files[0];
    const formData = new FormData();
    formData.append('file', file);

    fetch('http://localhost:8080/upload-arquivo', {
        method: 'POST',
        body: formData,
    })
    .then(response => response.json())
    .then(data => {
        console.log('Upload de arquivo concluído:', data);
    })
    .catch(error => {
        console.error('Erro ao fazer upload de arquivo:', error);
    });
});

