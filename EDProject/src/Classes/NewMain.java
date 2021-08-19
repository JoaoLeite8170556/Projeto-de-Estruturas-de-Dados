/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Enumerações.Tipo;
import java.time.Instant;
import java.util.Date;

/**
 *
 * @author João
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Pessoa pessoa1 = new Pessoa("Zé", Tipo.HOSPEDE);
        Pessoa pessoa2 = new Pessoa("Zé", Tipo.HOSPEDE);
        Movimentos movimentos = new Movimentos(pessoa1.getId(),"Quarto");
        
        System.out.println(movimentos.getDataFormater());
        System.out.println(pessoa1.getId());
        System.out.println(pessoa2.getId());
        System.out.println("Ola E ADeus");
    }
    
}
