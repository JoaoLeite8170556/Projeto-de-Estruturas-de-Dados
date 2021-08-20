/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.GraphWeight;
import Colecoes.UnorderedArrayList;
import java.util.Iterator;

/**
 * Esta classe é uma extensão da classe com metodos especificos para o nosso problema.
 * @author Celio Macedo Nº 8170358
 * @author João Leite Nº 8170556
 */
public class GrafoHotel<T> extends GraphWeight<T>{

    public GrafoHotel() {
        super();
    }
    
    public Iterator<T> getVerticesAdjacentes(T vertex){
        UnorderedArrayList<T> tempList = new UnorderedArrayList<>();
        
        int index = this.getIndex(vertex);
        
       
        return tempList.iterator();
    }
    
}
