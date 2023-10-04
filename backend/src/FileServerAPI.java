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
        Spark.get("/listar-arquivos", this::listarArquivos);
        Spark.post("/upload-arquivo", this::uploadArquivo);
        Spark.get("/download-arquivo/:fileName", this::downloadArquivo);
        Spark.post("/excluir-arquivo", this::excluirArquivo);
    }

    private Route listarArquivos = (Request request, Response response) -> {
        List<String> arquivos = fileService.listFiles();
        return gson.toJson(arquivos);
    };

    private Route uploadArquivo = (Request request, Response response) -> {
        String fileName = request.queryParams("fileName");
        String fileData = request.body();

        byte[] data = Base64.getDecoder().decode(fileData);

        boolean uploadResult = fileService.uploadFile(fileName, data);

        if (uploadResult) {
            return "Arquivo enviado com sucesso.";
        } else {
            response.status(409); // Conflito - arquivo já existe
            return "O arquivo já existe no servidor.";
        }
    };

    private Route downloadArquivo = (Request request, Response response) -> {
        String fileName = request.params(":fileName");
        byte[] fileData = fileService.downloadFile(fileName);

        if (fileData != null) {
            String fileBase64 = Base64.getEncoder().encodeToString(fileData);
            response.type("application/octet-stream");
            return fileBase64;
        } else {
            response.status(404); // Não encontrado - arquivo não existe
            return "O arquivo não foi encontrado no servidor.";
        }
    };

    private Route excluirArquivo = (Request request, Response response) -> {
        String fileName = request.queryParams("fileName");
        boolean deleteResult = fileService.deleteFile(fileName);

        if (deleteResult) {
            return "Arquivo excluído com sucesso.";
        } else {
            response.status(404); // Não encontrado - arquivo não existe
            return "O arquivo não foi encontrado no servidor.";
        }
    };
}