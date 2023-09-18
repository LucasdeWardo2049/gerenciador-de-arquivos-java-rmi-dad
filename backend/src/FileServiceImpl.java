import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileServiceImpl extends UnicastRemoteObject implements FileService {
    private Map<String, byte[]> files = new HashMap<>();

    protected FileServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public byte[] downloadFile(String fileName) throws RemoteException {
        return files.get(fileName);
    }

    @Override
    public boolean uploadFile(String fileName, byte[] data) throws RemoteException {
        if (!files.containsKey(fileName)) {
            files.put(fileName, data);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteFile(String fileName) throws RemoteException {
        if (files.containsKey(fileName)) {
            files.remove(fileName);
            return true;
        }
        return false;
    }

    @Override
    public List<String> listFiles() throws RemoteException {
        return new ArrayList<>(files.keySet());
    }
}
