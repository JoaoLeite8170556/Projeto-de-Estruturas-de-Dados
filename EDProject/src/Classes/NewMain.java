/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Excepcoes.ElementNonComparable;
import Excepcoes.EmptyExcpetion;
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
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException, java.text.ParseException, EmptyExcpetion, ElementNonComparable{
        
       GestaoHotel hote = new GestaoHotel("D://Users//joao-//OneDrive - Instituto Politécnico do Porto//Ambiente de Trabalho//ED_EpocaEspecial//Hoteis/mapa.json");
       
       hote.loadMapaHotel();
       
       
       Iterator itr = hote.getDivisoes().getTodasDivisoes().iterator();
       
       while(itr.hasNext()){
           System.out.println(itr.next().toString());
       }
        
    }
    
}
