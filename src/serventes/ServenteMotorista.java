package serventes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import interfaces.InterfaceMotorista;
import interfaces.InterfaceServMotorista;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import motorista.MotoristaManager;
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
        if (mensagem.contains(";")) {
            System.out.println("Notificação recebida: " + mensagem.substring(0, mensagem.indexOf(";")) + "\n");
        } else {
            System.out.println("Notificação recebida: " + mensagem + "\n");
        }

        //Verifica se é uma notificação sobre uma cotação de um cliente
        if (mensagem.contains("um cliente realizou uma cota")) {
            //Corta a string para separar o id do transfer cotado e o id do cliente
            String info = mensagem.substring(mensagem.indexOf("número") + 7, mensagem.length());
            String parts[] = info.split(";");

            //Obtem o id do transfer que foi cotado
            int transferCotadoId = Integer.parseInt(parts[0]);

            //Obtem o id do cliente
            int clienteId = Integer.parseInt(parts[1]);

            //Armazena o transfer que possui interessados
            MotoristaManager.adicionaInteresse(transferCotadoId, clienteId);
        } else if (mensagem.contains("foi reservado")) {
            int transferId = Integer.parseInt(mensagem.replace("o transfer de número ", "").replace(" foi reservado por um cliente", ""));
            MotoristaManager.marcaTransferReservado(transferId);
        }
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

            Gson gson = new Gson();

            if (mensagem.contains("foi adicionado com sucesso")) {
                //Converte o json em transfer e adiciona na lista dos transfers do motorista
                MotoristaManager.adicionaTransfer(gson.fromJson(mensagem.substring(38), TransferModel.class));
            } else if (mensagem.contains("foi atualizado com sucesso")) {
                MotoristaManager.alteraTransfer(gson.fromJson(mensagem.substring(38), TransferModel.class));
            }

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

    public void enviarProposta(int transferId, double novoPreco, int clienteId) throws RemoteException {
        this.interfaceServer.realizarProposta(transferId, novoPreco, clienteId);
    }
}
