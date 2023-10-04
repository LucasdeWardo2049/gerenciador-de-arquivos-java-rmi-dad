import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class FileServer {
    public static void main(String[] args) {
        try {
            // Crie o registro RMI na porta 1099
            LocateRegistry.createRegistry(1099);

            // Crie uma instância do serviço e o registre com o nome "FileService"
            FileService fileService = new FileServiceImpl();
            Naming.rebind("FileService", fileService);

            // Inicie a API HTTP
            FileServerAPI fileServerAPI = new FileServerAPI(fileService);
            fileServerAPI.start();

            System.out.println("Servidor pronto para receber solicitações.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}