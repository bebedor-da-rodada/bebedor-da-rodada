package br.com.catossi.bebedor_da_rodada.model;

public class UserResponse {
    private String status;
    private String message;
    private DataUsuario data;

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

    public DataUsuario getData() {
        return data;
    }

    public void setData(DataUsuario data) {
        this.data = data;
    }
}
