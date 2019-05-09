package motorista;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;

/**
 *
 * @author cnmoro
 */
public class MotoristaManager {
    
    public static ArrayList<InteresseTransfer> meusTransfers = new ArrayList<>();
    
    public static synchronized void adicionaTransfer(TransferModel tm) {
        meusTransfers.add(new InteresseTransfer(tm, new ArrayList<>()));
    }
    
    public static synchronized void alteraTransfer(TransferModel tm) {
        for (InteresseTransfer it : meusTransfers) {
            if (it.getTransfer().getId() == tm.getId()) {
                it.getTransfer().change(tm);
                break;
            }
        }
    }
    
    public static synchronized void marcaTransferReservado(int transferId) {
        for (InteresseTransfer it : meusTransfers) {
            if (it.getTransfer().getId() == transferId) {
                it.getTransfer().setReservado(true);
                break;
            }
        }
    }
    
    public static void listaTransfersLocais() {
        Gson gsonPrettyPrinter = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("Listando transfers atuais: \n");
        for (InteresseTransfer it : meusTransfers) {
            System.out.println(gsonPrettyPrinter.toJson(it.getTransfer()) + " Número de interessados: " + it.getInteressados().size() + "\n");
        }
    }
    
    public static synchronized void adicionaInteresse(int transferCotadoId, int clienteId) {
        for (InteresseTransfer it : meusTransfers) {
            if (it.getTransfer().getId() == transferCotadoId) {
                it.getInteressados().add(clienteId);
                break;
            }
        }
    }
    
}
