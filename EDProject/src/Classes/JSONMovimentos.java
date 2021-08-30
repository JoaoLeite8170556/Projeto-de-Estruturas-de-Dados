/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.*;

/**
 * Classe para fazer import e export dos dados dos movimentos efetuados.
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class JSONMovimentos {
       
    private String nomeHotel;
    private int versao;
    private DoubleLinkedOrderedList<Movimentos> movimentos;
    
    public JSONMovimentos(String nomeHotel, int versao){
       this.setNomeHotel(nomeHotel);
       this.setVersao(versao);
       this.movimentos = new DoubleLinkedOrderedList<Movimentos>();
    }

    public String getNomeHotel() {
        return nomeHotel;
    }

    public void setNomeHotel(String nomeHotel) {
        this.nomeHotel = nomeHotel;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }

    public DoubleLinkedOrderedList<Movimentos> getMovimentos() {
        return movimentos;
    }
    
    

    /*public void escreveMovimentosJson(DoubleLinkedOrderedList<Movimentos> listaDeMovimentos){
        String pathJsonMovimentos = "../movimentos.json";
        
        JSONObject jsonObject = new JSONObject();

        JSONArray array = new JSONArray();

        Iterator itr = listaDeMovimentos.iterator();

        while (itr.hasNext()) {
            array.add(jsonMovimentos(itr.next()));
        }

        jsonObject.put("Movimentos", array);

        try (FileWriter file = new FileWriter(pathJsonMovimentos)) {
            file.write(jsonObject.toString());
        }

        
        
        
    }*/
    
    /**
     * Método que vai escrever o objeto Movimento no JSON
     * @param hotel hotel onde ocorreram os movimentos
     * @return objeto JSONObject
     */
   /* private JSONObject jsonMovimentos(Movimentos movimentos){
        
        JSONObject jsonMovimentos = new JSONObject();
        
        JSONArray jsonArrayMovimentos = new JSONArray();
        
        Iterator itr = movimentos.
        
        while(itr.hasNext()){
            jsonArrayMovimentos.add(itr.next());
        }
        
        jsonMovimentos.put("Movimento", jsonArrayMovimentos);
        
        return jsonMovimentos;
        
    }*/
   
}
