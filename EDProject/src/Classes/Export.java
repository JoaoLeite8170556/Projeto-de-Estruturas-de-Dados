/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import Colecoes.*;
import Interfaces.InterfaceExport;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe que vai possibilitar fazer o export para ficheiro JSON de alguma informacao
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class Export implements InterfaceExport{
        
    public Export(){
        
    }
    
    /**
     * Este método vai possibilitar escrever a Lista de JSONMovimentos no ficheiro JSON
     * @param listaDeMovimentos a lista de JSONMovimentos
     * @throws IOException 
     */
    @Override
   public void escreveMovimentosJSON(DoubleLinkedOrderedList<JSONMovimentos> listaDeMovimentos) throws IOException{
        
        JSONObject jsonObject = new JSONObject();
        
        JSONArray array = new JSONArray();
        
        for (JSONMovimentos jsonMovimentosTemp : listaDeMovimentos) {
            array.add((jsonMovimentos(jsonMovimentosTemp)));
        }
        
        jsonObject.put("movimentosHoteis",array);
        
        try(FileWriter file = new FileWriter("../movimentos.json")){
            file.write(jsonObject.toString());
        }
    }
    
    
    /**
     * Método que estrutura JSONObject
     * @param movimentos
     * @return 
     */
   private JSONObject jsonMovimentos(JSONMovimentos movimentos){

        JSONObject jsonMovimentos = new JSONObject();

        jsonMovimentos.put("nome", movimentos.getNomeHotel());

        jsonMovimentos.put("versao", movimentos.getVersao());

        JSONArray jsonListaMovimentos = new JSONArray();

        Iterator itr = movimentos.getMovimentos().iterator();

        while (itr.hasNext()) {
            Movimentos movimentosAux = (Movimentos) itr.next();
            
            JSONObject informacao = new JSONObject();
            
            informacao.put("idPessoa", movimentosAux.getIdPessoa());
            informacao.put("divisao", movimentosAux.getNomeDivisao());

            Date date = movimentosAux.getDataHoraAtual();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            informacao.put("dataHora", formatter.format(date));
            jsonListaMovimentos.add(informacao);
        }

        jsonMovimentos.put("movimentos", jsonListaMovimentos);

        return jsonMovimentos;
    }
     
    
}
