import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface FileService extends Remote {
    byte[] downloadFile(String fileName) throws RemoteException;
    boolean uploadFile(String fileName, byte[] data) throws RemoteException;
    boolean deleteFile(String fileName) throws RemoteException;
    List<String> listFiles() throws RemoteException;
}
