/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import Enumerações.Tipo;

/**
 * Esta classe vai identificar as pessoas que vão poder estar nas divisões do 
 * Hotel.
 * @author João Leite Nº8170556
 * @author Celio Macedo Nº8170358
 */


public class Pessoa {
    
    private static int incremento = 1;
    private int id;
    private Tipo tipo;

    public Pessoa(Tipo tipo) {
        this.id= incremento++;
        this.tipo = tipo;
    }
    
    public Pessoa(){
        this.id = incremento++;
    }


    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ID Pessoa: " + this.id +"\n" +"Tipo: " + this.tipo + "\n";
    }
    
    
    
}
