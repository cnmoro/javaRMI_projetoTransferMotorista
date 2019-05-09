package motorista;

import java.util.ArrayList;

/**
 *
 * @author cnmoro
 */
public class InteresseTransfer {

    TransferModel transfer;
    ArrayList<Integer> interessados;

    public InteresseTransfer() {
    }

    public InteresseTransfer(TransferModel transfer, ArrayList<Integer> interessados) {
        this.transfer = transfer;
        this.interessados = interessados;
    }

    public TransferModel getTransfer() {
        return transfer;
    }

    public void setTransfer(TransferModel transfer) {
        this.transfer = transfer;
    }

    public ArrayList<Integer> getInteressados() {
        return interessados;
    }

    public void setInteressados(ArrayList<Integer> interessados) {
        this.interessados = interessados;
    }
}
