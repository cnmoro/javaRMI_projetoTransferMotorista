package motorista;

import com.google.gson.Gson;
import interfaces.InterfaceServMotorista;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
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

            //
            System.out.println("Pedindo alteração de transfer");
            Storage.meusTransfers.get(0).setPreco(78);
            serventeMotorista.alterarTransfer(gson.toJson(Storage.meusTransfers.get(0)));
            //

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
