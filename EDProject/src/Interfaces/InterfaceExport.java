/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Classes.JSONMovimentos;
import Colecoes.DoubleLinkedOrderedList;
import java.io.IOException;

/**
 *
 * @author João
 */
public interface InterfaceExport {
     public void escreveMovimentosJSON(DoubleLinkedOrderedList<JSONMovimentos> listaDeMovimentos) throws IOException;
}
