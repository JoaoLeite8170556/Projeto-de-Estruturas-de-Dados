/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Enumerações.Reservado;
import Enumerações.SalaQuarentena;

/**
 *
 * @author João
 */
public class Divisao {
    private String nome;
    private SalaQuarentena salaQuarentena;
    private Reservado reservado;

    public Divisao(String nome, SalaQuarentena salaQuarentena, Reservado reservado) {
        this.nome = nome;
        this.salaQuarentena = salaQuarentena;
        this.reservado = reservado;
    }

    public String getNome() {
        return nome;
    }

    public SalaQuarentena getSalaQuarentena() {
        return salaQuarentena;
    }

    public Reservado getReservado() {
        return reservado;
    }

    
    
}
