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
    private String nome;
    private Tipo tipo;

    public Pessoa(String nome, Tipo tipo) {
        this.id= incremento++;
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
    
    
}
