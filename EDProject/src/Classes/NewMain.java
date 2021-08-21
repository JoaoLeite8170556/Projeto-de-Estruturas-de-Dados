/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.GraphWeight;
import Grafo.GrafoHotel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.parser.ParseException;

/**
 * Main do Projeto
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException{
        
        
        GestaoHotel hotel = new GestaoHotel("../mapa.json");
        
        hotel.loadMapaHotel();
        
        Iterator itr = hotel.getDivisoes().getTodasDivisoes().iterator();
        
        while(itr.hasNext()){
            System.out.println(itr.next().toString());
        }
        
        
        
        
        
    }
    
}
