package br.com.catossi.bebedor_da_rodada.model;

public class RoundResponse {
    private String status;
    private String message;
    private DataRound data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataRound getData() {
        return data;
    }

    public void setData(DataRound data) {
        this.data = data;
    }
}
