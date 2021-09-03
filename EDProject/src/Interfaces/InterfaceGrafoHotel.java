/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Colecoes.UnorderedDoubleLinkedList;
import java.util.Iterator;

/**
 *
 * @author João
 */
public interface InterfaceGrafoHotel<T> {
    
     public Iterator<T> getVerticesAdjacentes(T vertex);
     
     public int numeroDeVerticesAdjacentes(T vertice);
     
     public void mostraVerticesAdjacentes(T vertex);
     
     public void setEdgeWeight(T startVertex, T targetVertex, double weight);
     
     public boolean estaConectado(T vertice1, T vertice2);
     
     public T getVerticeWithElement(T element);
     
    public UnorderedDoubleLinkedList<T> getTodasDivisoes();
}
