/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.GraphWeight;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.parser.ParseException;

/**
 *
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException{
        
        
        GestaoHotel mapa = new GestaoHotel("../mapa.json");
        
        mapa.loadMapaHotel();
        
        Iterator itr = (Iterator) mapa.getDivisoes();
        
        
        GraphWeight<Integer> grafo = new GrafoHotel<>();
        
     
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
        
    }
    
}
