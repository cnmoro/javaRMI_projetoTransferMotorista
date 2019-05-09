package serventes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import interfaces.InterfaceMotorista;
import interfaces.InterfaceServMotorista;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import motorista.Storage;
import motorista.TransferModel;

/**
 *
 * @author cnmoro
 */
public class ServenteMotorista extends UnicastRemoteObject implements InterfaceMotorista {

    InterfaceServMotorista interfaceServer;

    public ServenteMotorista(InterfaceServMotorista interfaceServer) throws RemoteException {
        this.interfaceServer = interfaceServer;
    }

    @Override
    public void receberNotificacao(String mensagem) throws RemoteException {
        System.out.println("Notificação recebida: " + mensagem + "\n");
    }

    @Override
    public void receberConfirmacao(String mensagem) throws RemoteException {
        //Realiza operacoes somente se nao houve erro
        if (!mensagem.contains("Não foi possível encontrar este transf")) {
            //Imprime a resposta com o transfer descrito
            System.out.println("Confirmação: " + mensagem.substring(0, 38));

            Gson gsonPrettyPrinter = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(mensagem.substring(38));
            System.out.println("Transfer: " + gsonPrettyPrinter.toJson(je) + "\n");

            //Converte o json em transfer e adiciona na lista dos transfers do motorista
            Gson gson = new Gson();
            Storage.meusTransfers.add(gson.fromJson(mensagem.substring(38), TransferModel.class));
        } else {
            //Formato da resposta: confirmação + json transfer, qd nao ha erros
            System.out.println("Confirmação: " + mensagem);
        }
    }

    public void cadastrarTransfer(String tm) throws RemoteException {
        this.interfaceServer.cadastrarTransfer(tm, this);
    }

    public void alterarTransfer(String tm) throws RemoteException {
        this.interfaceServer.alterarTransfer(tm, this);
    }
}
