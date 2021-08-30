/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.DoubleLinkedOrderedList;
import Enumerações.TipoSala;
import Excepcoes.ElementNonComparable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author João
 */
public class Import {
    
     /**
     * Este metodo vai ler o ficheiro json e guardar a informação numa lista.
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException
     * @throws java.text.ParseException 
     */
    public DoubleLinkedOrderedList<JSONMovimentos> readFileMovimentos(Hotel hotel) throws FileNotFoundException, IOException, ParseException{
        DoubleLinkedOrderedList<JSONMovimentos> tempList = new DoubleLinkedOrderedList<JSONMovimentos>();
        File file = new File("../movimentos.json");

        if(file.exists()){
            JSONObject movimentosJSON = (JSONObject) new JSONParser().parse(new FileReader(file));
            JSONArray movimentosArray = (JSONArray) movimentosJSON.get("movimentosHoteis");
            
            
            for(Object obj : movimentosArray){
                JSONObject movimentosHotel = (JSONObject) obj;
                String versao = String.valueOf(movimentosHotel.get("versao"));
                String nomeHotel = (String) movimentosHotel.get("nome");
                
                
                JSONArray movimentosRealizados = (JSONArray) movimentosHotel.get("movimentos");
                
                for(Object move : movimentosRealizados){
                    
                }
                
            }
        }
        return null;
    }
    
    
}
