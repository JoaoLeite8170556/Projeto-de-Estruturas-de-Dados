/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Enumerações.Reservado;
import Enumerações.SalaQuarentena;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Esta classe vai possibilitar o tratamento dos dados do mapa do hotel do e para 
 * um ficheiro JSON.
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class MapaHotel {
    
    private String pathFile;
    private GestaoHotel hotel;
    
    public MapaHotel(String pathFile){
        this.pathFile = pathFile;
    }
    
    public void loadMapaHotel() throws FileNotFoundException, IOException, ParseException{
        
        JSONParser jSONParser = new JSONParser();
        FileReader reader = new FileReader(this.pathFile);
        Object obj = jSONParser.parse(reader);

        JSONObject jsonObject = (JSONObject) obj;
        
        /**
         * Nome do Hotel 
         */
        String divisao = (String) jsonObject.get("hotel");
        
        /**
         * Versão do Hotel
         */
        Integer versao = (int) jsonObject.get("versao");
        
        JSONArray jsonDivisoes = (JSONArray) jsonObject.get("divisoes");
        
        for(Object divisoes : jsonDivisoes){
            
            String nomeDivisao = ((JSONObject)divisoes).get("nome").toString();
            Integer lotacao = Integer.parseInt(((JSONObject)divisoes).get("lotacao").toString());
            boolean reservado = Boolean.parseBoolean(((JSONObject)divisoes).get("reservado").toString());
            boolean quarentena = Boolean.parseBoolean(((JSONObject)divisoes).get("quarentena").toString());
            
            if(quarentena == true){
                this.hotel.getDivisoes().addVertex(new Divisao(nomeDivisao, quarentena, lotacao));
            }else if(reservado == true){
                this.hotel.getDivisoes().addVertex(new Divisao(nomeDivisao, lotacao, reservado));
            }else{
                
            }
        }
    }
    
    
    
    
    
    
}
