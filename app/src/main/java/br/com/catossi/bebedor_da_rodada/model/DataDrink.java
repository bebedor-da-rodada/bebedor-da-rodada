package br.com.catossi.bebedor_da_rodada.model;

import java.util.List;

public class DataDrink {

    private List<DrinkRequest> bebidas;

    public List<DrinkRequest> getBebidas() {
        return bebidas;
    }

    public void setBebidas(List<DrinkRequest> bebidas) {
        this.bebidas = bebidas;
    }
}
