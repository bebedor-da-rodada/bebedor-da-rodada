package br.com.catossi.bebedor_da_rodada.model;

public class DrinkResponse {

    private String status;
    private String message;
    private DataDrink data;

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

    public DataDrink getData() {
        return data;
    }

    public void setData(DataDrink data) {
        this.data = data;
    }
}
