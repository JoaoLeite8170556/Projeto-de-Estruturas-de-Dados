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
public class JSONMovimentos implements Comparable{
       
    private String nomeHotel;
    private int versao;
    private DoubleLinkedOrderedList<Movimentos> movimentos;
    
    public JSONMovimentos(String nomeHotel, int versao, DoubleLinkedOrderedList<Movimentos> listaMovimentos){
       this.setNomeHotel(nomeHotel);
       this.setVersao(versao);
       this.movimentos = listaMovimentos;
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
    
    @Override
    public int compareTo(Object o) {
        JSONMovimentos json = (JSONMovimentos) o;
        
        
        if(getVersao() < json.getVersao()){
            return 1;
        }else if(getVersao()>json.getVersao()){
            return -1;
        }else{
            return 0;
        }
    }
     
}
