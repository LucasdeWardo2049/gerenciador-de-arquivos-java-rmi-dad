import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

public class FileClient {
    public static void main(String[] args) {
        try {
            // Obtém a referência para o serviço remoto
            FileService fileService = (FileService) Naming.lookup("rmi://localhost/FileService");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Escolha uma opção:");
                System.out.println("1. Listar arquivos");
                System.out.println("2. Upload de arquivo");
                System.out.println("3. Download de arquivo");
                System.out.println("4. Excluir arquivo");
                System.out.println("5. Sair");

                System.out.print("\nInsira uma opção: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha após a leitura do número

                switch (choice) {
                    case 1:
                        List<String> files = fileService.listFiles();
                        System.out.println("Arquivos no servidor:");
                        for (String file : files) {
                            System.out.println(file);
                        }
                        break;
                    case 2:
                        System.out.println("Digite o nome do arquivo para fazer upload:");
                        String uploadFileName = scanner.nextLine();
                        System.out.println("Digite o caminho completo do arquivo:");
                        String filePath = scanner.nextLine();

                        // Ler o conteúdo do arquivo em um byte array
                        Path pathUpload = Paths.get(filePath);
                        byte[] fileData = Files.readAllBytes(pathUpload);

                       // Fazer upload do arquivo para o servidor
                        boolean uploadResult = fileService.uploadFile(uploadFileName, fileData);
                        if (uploadResult) {
                            System.out.println("Arquivo enviado com sucesso.");
                        } else {
                            System.out.println("O arquivo já existe no servidor.");
                        }

                        break;
                    case 3:
                        // Solicitar o nome do arquivo para download
                        System.out.println("Digite o nome do arquivo para download:");
                        String downloadFileName = scanner.nextLine();

                        // Chamar o método de download no servidor
                        byte[] downloadedData = fileService.downloadFile(downloadFileName);

                        if (downloadedData != null) {
                            // Especifique o caminho onde você deseja salvar o arquivo baixado
                            System.out.println("Digite o caminho completo onde deseja salvar o arquivo:");
                            String savePath = scanner.nextLine();
                            Path pathDownload = Paths.get(savePath, downloadFileName);

                            // Escrever o byte array no arquivo
                            try {
                                Files.write(pathDownload, downloadedData);
                                System.out.println("Arquivo baixado com sucesso e salvo em: " + pathDownload.toAbsolutePath());
                            } catch (IOException e) {
                                System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
                            }
                        } else {
                            System.out.println("O arquivo não foi encontrado no servidor.");
                        }

                        break;
                    case 4:
                        System.out.println("Digite o nome do arquivo para excluir:");
                        String deleteFileName = scanner.nextLine();
                        boolean deleteResult = fileService.deleteFile(deleteFileName);
                        if (deleteResult) {
                            System.out.println("Arquivo excluído com sucesso.");
                        } else {
                            System.out.println("O arquivo não foi encontrado no servidor.");
                        }
                        break;
                    case 5:
                        System.out.println("Encerrando o cliente.");
                        System.exit(0);
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
