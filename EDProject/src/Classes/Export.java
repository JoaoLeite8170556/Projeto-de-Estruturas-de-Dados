/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import Colecoes.*;
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
public class Export {
        
        
    private Hotel hotel;


    public Export(){
        
    }
    
    
    public void escreveMovimentosJSON(DoubleLinkedOrderedList<JSONMovimentos> listaDeMovimentos) throws IOException{
        
        JSONObject jsonObject = new JSONObject();
        
        JSONArray array = new JSONArray();
        
        Iterator<JSONMovimentos> itr = listaDeMovimentos.iterator();
        
        while(itr.hasNext()){
            array.add((jsonMovimentos(itr.next())));
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

        Iterator<Movimentos> itr = movimentos.getMovimentos().iterator();

        while (itr.hasNext()) {
            JSONObject informacao = new JSONObject();
            informacao.put("idPessoa", itr.next().getIdPessoa());
            informacao.put("divisao", itr.next().getNomeDivisao());

            Date date = itr.next().getDataHoraAtual();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            informacao.put("dataHora", formatter.format(date));
            jsonListaMovimentos.add(informacao);
        }

        jsonMovimentos.put("movimentos", jsonListaMovimentos);

        return jsonMovimentos;
    }
     
    
}
