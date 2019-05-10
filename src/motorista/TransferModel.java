package motorista;

import java.io.Serializable;

/**
 *
 * @author cnmoro
 */
public class TransferModel implements Serializable {

    int id;
    double preco;
    int numPassageiros;
    String tipoVeiculo;
    String dataHora;
    String itinerario;
    boolean reservado;

    public TransferModel() {
    }

    public TransferModel(int id, double preco, int numPassageiros, String tipoVeiculo, String dataHora, String intinerario, boolean reservado) {
        this.id = id;
        this.preco = preco;
        this.numPassageiros = numPassageiros;
        this.tipoVeiculo = tipoVeiculo;
        this.dataHora = dataHora;
        this.itinerario = intinerario;
        this.reservado = reservado;
    }

    public TransferModel(double preco, int numPassageiros, String tipoVeiculo, String dataHora, String intinerario) {
        this.preco = preco;
        this.numPassageiros = numPassageiros;
        this.tipoVeiculo = tipoVeiculo;
        this.dataHora = dataHora;
        this.itinerario = intinerario;
        this.reservado = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getNumPassageiros() {
        return numPassageiros;
    }

    public void setNumPassageiros(int numPassageiros) {
        this.numPassageiros = numPassageiros;
    }

    public String getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(String tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getItinerario() {
        return itinerario;
    }

    public void setItinerario(String intinerario) {
        this.itinerario = intinerario;
    }

    public void change(TransferModel tm) {
        this.dataHora = tm.getDataHora();
        this.itinerario = tm.getItinerario();
        this.numPassageiros = tm.getNumPassageiros();
        this.preco = tm.getPreco();
        this.tipoVeiculo = tm.getTipoVeiculo();
    }

    public boolean isReservado() {
        return this.reservado;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }

}
