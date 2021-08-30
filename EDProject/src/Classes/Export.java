/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import Colecoes.*;
import java.io.File;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author João
 */
public class Export {
        
        
    private Hotel hotel;


    public Export(){
        
    }
    
    
    public void escreveMovimentosJSON(DoubleLinkedOrderedList<JSONMovimentos> listaDeMovimentos){
        
        JSONObject jsonObject = new JSONObject();
        
        JSONArray array = new JSONArray();
        
        Iterator<JSONMovimentos> itr = listaDeMovimentos.iterator();
        
        while(itr.hasNext()){
            array.add((jsonMovimentos(itr.next())));
        }
        
        jsonObject.put("movimentosHoteis",array);
        
        try{
            FileW
        }
    }
    
    
    /**
     * Metodo que estrutura 
     * @param movimentos
     * @return 
     */
    private JSONObject jsonMovimentos(JSONMovimentos movimentos){
        
        JSONObject jsonMovimentos = new JSONObject();
        
        jsonMovimentos.put("nome", movimentos.getNomeHotel());
        
        jsonMovimentos.put("versao", movimentos.getVersao());
        
        JSONArray jsonListaMovimentos = new JSONArray();
        
        Iterator itr = movimentos.getMovimentos().iterator();
        
        while(itr.hasNext()){
            jsonListaMovimentos.add(itr.next());
        }
        
        jsonMovimentos.put("movimentos", jsonListaMovimentos);
        
        return jsonMovimentos;
    }
     
    
}
