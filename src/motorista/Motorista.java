package motorista;

import com.google.gson.Gson;
import interfaces.InterfaceServMotorista;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
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

            Scanner input = new Scanner(System.in);
            Gson gson = new Gson();

            //Mostar menu de opções
            while (true) {
                System.out.println("-- Opções --");
                System.out.println(
                        "Escolha:\n"
                        + "  1) Cadastrar transfer\n"
                        + "  2) Alterar transfer\n"
                        + "  3) Enviar proposta\n"
                        + "  4) Ver meus transfers\n"
                );

                int opcao = input.nextInt();
                input.nextLine();

                switch (opcao) {
                    case 1:
                        //Coleta os dados
                        System.out.println("Insira o tipo de veículo: ");
                        String tipoVeiculo = input.nextLine();
                        input.nextLine();

                        System.out.println("Insira o itinerario: ");
                        String itinerario = input.nextLine();
                        input.nextLine();

                        System.out.println("Insira a data (formato dd/MM/yyyy HH:mm): ");
                        String data = input.nextLine();
                        input.nextLine();

                        System.out.println("Insira o número de passageiros: ");
                        int numPassageiros = input.nextInt();
                        input.nextLine();

                        System.out.println("Insira o preço: ");
                        Double preco = input.nextDouble();
                        input.nextLine();

                        //Cria um transfer com esses dados
                        TransferModel tm = new TransferModel(
                                preco,
                                numPassageiros,
                                tipoVeiculo,
                                data,
                                itinerario
                        );

                        //Converte em json e envia para o servidor
                        serventeMotorista.cadastrarTransfer(gson.toJson(tm));
                        break;
                    case 2:
                        //Mostra a lista de transfers para escolher qual alterar
                        MotoristaManager.listaTransfersLocais();

                        System.out.println("Digite o numero do transfer para alterar: ");
                        int numTransfer = input.nextInt();
                        input.nextLine();

                        //Encontra o transfer a partir da lista local (reflete os dados de
                        //transfer deste motorista no servidor)
                        TransferModel tmAlt = MotoristaManager.getTransferPorId(numTransfer);

                        //Coleta os dados
                        System.out.println("Digite o novo tipo de veiculo (digite 'x' para manter o mesmo): ");
                        String tipoVeiculoAlt = input.nextLine();
                        input.nextLine();

                        System.out.println("Insira o itinerario (digite 'x' para manter o mesmo): ");
                        String itinerarioAlt = input.nextLine();
                        input.nextLine();

                        System.out.println("Insira a data (formato dd/MM/yyyy HH:mm)(digite 'x' para manter o mesmo): ");
                        String dataAlt = input.nextLine();
                        input.nextLine();

                        System.out.println("Insira o número de passageiros (digite 'x' para manter o mesmo): ");
                        String numPassageirosAlt = input.nextLine();
                        input.nextLine();

                        System.out.println("Insira o preço (digite 'x' para manter o mesmo): ");
                        String precoAlt = input.nextLine();
                        input.nextLine();

                        //Altera no objeto, os valores que sofreram alteracao
                        if (!tipoVeiculoAlt.equalsIgnoreCase("x")) {
                            tmAlt.setTipoVeiculo(tipoVeiculoAlt);
                        }
                        if (!itinerarioAlt.equalsIgnoreCase("x")) {
                            tmAlt.setItinerario(itinerarioAlt);
                        }
                        if (!dataAlt.equalsIgnoreCase("x")) {
                            tmAlt.setDataHora(dataAlt);
                        }
                        if (!numPassageirosAlt.equalsIgnoreCase("x")) {
                            tmAlt.setNumPassageiros(Integer.parseInt(numPassageirosAlt));
                        }
                        if (!precoAlt.equalsIgnoreCase("x")) {
                            tmAlt.setPreco(Double.parseDouble(precoAlt));
                        }

                        //Envia alteracao para o servidor
                        serventeMotorista.alterarTransfer(gson.toJson(tmAlt));
                        break;
                    case 3:
                        //Mostra lista de transfers com clientes interessados
                        MotoristaManager.listaTransfersComInteressados();

                        System.out.println("Insira o número do transfer: ");
                        int numTransferProposta = input.nextInt();
                        input.nextLine();

                        System.out.println("Insira o preço da proposta: ");
                        double novoPreco = input.nextDouble();
                        input.nextLine();

                        //Enviar proposta para todos os interessados ou apenas um cliente
                        System.out.println("Digite o id do cliente para qual deseja enviar a proposta: ");
                        int idCliente = input.nextInt();
                        input.nextLine();
                        serventeMotorista.enviarProposta(numTransferProposta, novoPreco, idCliente);
                        break;
                    case 4:
                        MotoristaManager.listaTransfersLocais();
                        break;
                    default:
                        System.out.println("Opção inválida\n");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
