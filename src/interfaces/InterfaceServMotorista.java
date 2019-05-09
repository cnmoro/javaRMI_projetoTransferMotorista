package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author cnmoro
 */
public interface InterfaceServMotorista extends Remote {

    //Cadastrar transfers
    public void cadastrarTransfer(String tm, InterfaceMotorista motorista) throws RemoteException;

    //Alteração de transfer
    public void alterarTransfer(String tm, InterfaceMotorista motorista) throws RemoteException;

    //Proposta - redução de preço para um cliente específico
    public void realizarProposta() throws RemoteException;
}
