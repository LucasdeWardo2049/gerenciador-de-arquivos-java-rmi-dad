import java.util.Base64;
import java.util.List;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class FileServerAPI {
    private FileService fileService;
    private Gson gson;

    public FileServerAPI(FileService fileService) {
        this.fileService = fileService;
        this.gson = new Gson();
    }

    public void start() {
        // Definindo os endpoints HTTP e associando-os aos métodos correspondentes
        Spark.get("/listar-arquivos", listarArquivos);
        Spark.post("/upload-arquivo", uploadArquivo);
        Spark.get("/download-arquivo/:fileName", downloadArquivo);
        Spark.post("/excluir-arquivo", excluirArquivo);
    }

    private Route listarArquivos = (Request request, Response response) -> {
        // Chamada ao serviço FileService para obter a lista de arquivos
        List<String> arquivos = fileService.listFiles();
        
        // Convertendo a lista de arquivos para JSON usando a biblioteca Gson
        return gson.toJson(arquivos);
    };

    private Route uploadArquivo = (Request request, Response response) -> {
        // Obtendo o nome do arquivo e os dados do arquivo enviados na requisição
        String fileName = request.queryParams("fileName");
        String fileData = request.body();

        // Decodificando os dados do arquivo na forma de Base64 para um array de bytes
        byte[] data = Base64.getDecoder().decode(fileData);

        // Chamada ao serviço FileService para fazer o upload do arquivo
        boolean uploadResult = fileService.uploadFile(fileName, data);

        // Verificando o resultado do upload e retornando mensagens apropriadas
        if (uploadResult) {
            return "Arquivo enviado com sucesso.";
        } else {
            response.status(409); // Conflito - arquivo já existe
            return "O arquivo já existe no servidor.";
        }
    };

    private Route downloadArquivo = (Request request, Response response) -> {
        // Obtendo o nome do arquivo a ser baixado da URL da requisição
        String fileName = request.params(":fileName");

        // Chamada ao serviço FileService para baixar o arquivo
        byte[] fileData = fileService.downloadFile(fileName);

        // Verificando se o arquivo foi encontrado e preparando a resposta adequada
        if (fileData != null) {
            // Convertendo os dados do arquivo para Base64
            String fileBase64 = Base64.getEncoder().encodeToString(fileData);

            // Configurando o tipo de conteúdo da resposta para download
            response.type("application/octet-stream");

            // Retornando o arquivo codificado em Base64
            return fileBase64;
        } else {
            response.status(404); // Não encontrado - arquivo não existe
            return "O arquivo não foi encontrado no servidor.";
        }
    };

    private Route excluirArquivo = (Request request, Response response) -> {
        // Obtendo o nome do arquivo a ser excluído dos parâmetros da requisição
        String fileName = request.queryParams("fileName");

        // Chamada ao serviço FileService para excluir o arquivo
        boolean deleteResult = fileService.deleteFile(fileName);

        // Verificando o resultado da exclusão e retornando mensagens apropriadas
        if (deleteResult) {
            return "Arquivo excluído com sucesso.";
        } else {
            response.status(404); // Não encontrado - arquivo não existe
            return "O arquivo não foi encontrado no servidor.";
        }
    };
}