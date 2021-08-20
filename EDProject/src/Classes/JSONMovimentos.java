/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.DoubleLinkedOrderedList;
import Excepcoes.ElementNonComparable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
/**
 * Classe para fazer import e export dos dados dos movimentos efetuados
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class JSONMovimentos {
    private String filePath;

    public JSONMovimentos(String filePath) {
        this.filePath = filePath;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public DoubleLinkedOrderedList<Movimentos> readFileMovimentos() throws FileNotFoundException, IOException, ParseException, java.text.ParseException{
        JSONParser JSONParser = new JSONParser();
        FileReader reader = new FileReader(this.filePath);
        
        DoubleLinkedOrderedList<Movimentos> tempList = new DoubleLinkedOrderedList<Movimentos>();
        
        Object obj;
        
        obj = JSONParser.parse(reader);
        
        JSONObject jsonObject= (JSONObject) obj;
        
        JSONArray jsonMovimentos = (JSONArray) jsonObject.get("Movimentos");
        
        for(int i=0;i<jsonMovimentos.size();i++){
            
            JSONObject objMovimentos = (JSONObject) jsonMovimentos.get(i);
            Integer idPessoa = Integer.parseInt((String) objMovimentos.get("idPessoa").toString());
            String divisao = (String) objMovimentos.get("Divisão");
            String dataHora = (String) objMovimentos.get("DataHora");
             try {
                tempList.add(new Movimentos(idPessoa,divisao,dataHora));
            } catch (ElementNonComparable ex){}
            
        }
        return tempList;
        
    }
}
