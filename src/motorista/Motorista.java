package motorista;

import com.google.gson.Gson;
import interfaces.InterfaceServMotorista;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import serventes.ServenteMotorista;

/**
 *
 * @author cnmoro
 */
public class Motorista {

    public static void main(String[] args) {
        try {
            Registry servicoNomes = LocateRegistry.getRegistry(1099);

            //Vincula a referencia do servidor
            InterfaceServMotorista interfaceServidor = (InterfaceServMotorista) servicoNomes.lookup("ReferenciaServenteServidorMot");

            //Cria servente do motorista com a referencia do servidor
            ServenteMotorista serventeMotorista = new ServenteMotorista(interfaceServidor);

            System.out.println("Motorista Rodando\n");

            TransferModel tm1 = new TransferModel(
                    155,
                    3,
                    "Micro onibus",
                    new Date(),
                    "Shopping Estação - Shopping Mueller - Rodoviaria"
            );

            //
            System.out.println("Pedindo cadastro de transfer");

            Gson gson = new Gson();
            serventeMotorista.cadastrarTransfer(gson.toJson(tm1));
            //

            TimeUnit.SECONDS.sleep(10);

            //
            System.out.println("Pedindo alteração de transfer");
            TransferModel transferParaModificar = MotoristaManager.meusTransfers.get(0).getTransfer();
            transferParaModificar.setPreco(78);

            serventeMotorista.alterarTransfer(gson.toJson(transferParaModificar));
            //

            TimeUnit.SECONDS.sleep(2);

            //
            int clienteId = MotoristaManager.meusTransfers.get(0).getInteressados().get(0);

            System.out.println("Enviando proposta, novo valor: 50 reais p/ cliente " + clienteId);
            serventeMotorista.enviarProposta(1, 50, clienteId);

            MotoristaManager.listaTransfersLocais();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
