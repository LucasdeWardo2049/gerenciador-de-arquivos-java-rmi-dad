import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;

public class FileServer {
    public static void main(String[] args) {
        // Configurar as rotas do servidor Spark
        get("/listar-arquivos", listarArquivosRoute);
        post("/upload-arquivo", uploadArquivoRoute);
        // Outras rotas podem ser adicionadas para as operações restantes

        // Iniciar o servidor Spark
        init();
    }

    // Rota para listar os arquivos
    private static Route listarArquivosRoute = (Request request, Response response) -> {
        // Lógica para listar os arquivos no servidor
        List<String> files = // Obtenha a lista de arquivos do sistema de arquivos
        return files;
    };

    // Rota para fazer upload de arquivo
    private static Route uploadArquivoRoute = (Request request, Response response) -> {
        try {
            // Obter o arquivo enviado na requisição
            Path tempDir = Files.createTempDirectory("temp");
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement(tempDir.toString()));
            Part filePart = request.raw().getPart("file");
            String fileName = filePart.getSubmittedFileName();
            Path filePath = Paths.get("caminho/para/armazenar/os/arquivos", fileName);

            // Salvar o arquivo no sistema de arquivos
            try (InputStream inputStream = filePart.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return "Upload de arquivo concluído com sucesso.";
        } catch (Exception e) {
            response.status(500);
            return "Erro ao fazer upload de arquivo: " + e.getMessage();
        }
    };
}