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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        
       GestaoHotel hote = new GestaoHotel("C://Users//celio//OneDrive//Ambiente de Trabalho//universidade//2ºano//1º Semestre//Estruturas de Dados//trabalho final época especial//ED_EpocaEspecial//Hoteis//mapa.json");
       
       hote.loadMapaHotel();
       
       //Iterator itr1 = hote.getMovimentosPessoas().iterator();
       
        
       Iterator itr = hote.listaDeContactos(5).iterator();
       
        
       while(itr.hasNext()){
           System.out.println(itr.next().toString());
       }
       
       
       /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Current Date " + dateFormat.format(date));

        // Convert Date to Calendar
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // Perform addition/subtraction
        //c.add(Calendar.YEAR, 2);
        //c.add(Calendar.MONTH, 1);
        //c.add(Calendar.DATE, -10);
        c.add(Calendar.HOUR, -4);
        //c.add(Calendar.MINUTE, 30);
        //c.add(Calendar.SECOND, 50);

        // Convert calendar back to Date
        Date currentDatePlusOne = c.getTime();

        System.out.println("Updated Date " + dateFormat.format(currentDatePlusOne));*/
       
        
    }
    
}
