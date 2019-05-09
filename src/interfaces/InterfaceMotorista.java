package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author cnmoro
 */
public interface InterfaceMotorista extends Remote {

    public void receberNotificacao(String mensagem) throws RemoteException;

    public void receberConfirmacao(String mensagem) throws RemoteException;
}
