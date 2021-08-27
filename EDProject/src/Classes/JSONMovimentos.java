/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.*;
import Excepcoes.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
/**
 * Classe para fazer import e export dos dados dos movimentos efetuados.
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class JSONMovimentos {
    private String filePath;

    public JSONMovimentos(String filePath){
        this.filePath = filePath;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    /**
     * Este metodo vai ler o ficheiro json e guardar a informação numa lista.
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException
     * @throws java.text.ParseException 
     */
    public DoubleLinkedOrderedList<Movimentos> readFileMovimentos(){
        FileReader reader = null;
        try {
            JSONParser JSONParser = new JSONParser();
            reader = new FileReader(this.filePath);
            DoubleLinkedOrderedList<Movimentos> tempList = new DoubleLinkedOrderedList<Movimentos>();
            Object obj;
            obj = JSONParser.parse(reader);
            JSONObject jsonObject= (JSONObject) obj;
            JSONArray jsonMovimentos = (JSONArray) jsonObject.get("Movimentos");
            for (int i = 0; i < jsonMovimentos.size(); i++) {
                
                JSONObject objMovimentos = (JSONObject) jsonMovimentos.get(i);
                Integer idPessoa = Integer.parseInt((String) objMovimentos.get("idPessoa").toString());
                String divisao = (String) objMovimentos.get("Divisão");
                String dataHora = (String) objMovimentos.get("DataHora");
                
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    tempList.add(new Movimentos(idPessoa, divisao, formatter.parse(dataHora)));
                } catch (java.text.ParseException ex) {
                    Logger.getLogger(JSONMovimentos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ElementNonComparable ex) {
                    Logger.getLogger(JSONMovimentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   return tempList;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JSONMovimentos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JSONMovimentos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(JSONMovimentos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(JSONMovimentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
        
    }
    
    /**
     * Este método para encontrar os movimentos de determinada pessoa no hotel
     * @param idPessoa da pessoa
     * @return a lista com todos os movimentos
     */
    private OrderedArrayList<Movimentos> movimentosPessoa(int idPessoa){
        
        OrderedArrayList<Movimentos> lista = new OrderedArrayList<>();
        
        Iterator itr = readFileMovimentos().iterator();
        
        while(itr.hasNext()){
            Movimentos move = (Movimentos) itr.next();
            
            if(move.getIdPessoa() == idPessoa){
                try {
                    lista.add(move);
                } catch (ElementNonComparable ex) {
                    Logger.getLogger(JSONMovimentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        return lista;
    }
    
    
    public void escreveMovimentosJson(DoubleLinkedOrderedList<Movimentos> listaDeMovimentos){
        
    }
   
}
