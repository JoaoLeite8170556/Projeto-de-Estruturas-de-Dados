/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Classes.Pessoa;
import Excepcoes.ElementNonComparable;
import Excepcoes.EmptyExcpetion;

/**
 *
 * @author João
 */
public interface InterfaceGestaoHotel {
    
    public Pessoa escolhePessoa() throws EmptyExcpetion;

    public void modoManual() throws EmptyExcpetion, ElementNonComparable;

    public void caminhoMaisCurtoSalaQuarentena() throws EmptyExcpetion;
    
    
    
}
