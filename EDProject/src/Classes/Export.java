/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

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
    
    
    public void escreveMovimentosJSON(){
        String movimentosPath = "movimentos.json";
        
        File file = new File(movimentosPath);
        
        JSONObject hotel = new JSONObject();
        JSONArray movimentosArray = new JSONArray();
        
        hotel.put("nomeHotel", this.hotel.getNomeHotel());
        hotel.put("versao",this.hotel.getVersao());
        
        Iterator itr = this.hotel.getMovimentosHotel().iterator();
        
        while(itr.hasNext()){
            JSONObject movimentosJSON = new JSONObject();
            
            Movimentos movimentos = (Movimentos) itr.next();
            
            movimentosJSON.put("idPessoa",movimentos.getIdPessoa());
            movimentosJSON.put("divisao", movimentos.getNomeDivisao());
            movimentosJSON.put("dataHora",movimentos.getDataHoraAtual());
            
            movimentosArray.add(movimentosJSON);
        }
        
        hotel.put("movimentos",movimentosArray);
        
        
    }
     
    
}
