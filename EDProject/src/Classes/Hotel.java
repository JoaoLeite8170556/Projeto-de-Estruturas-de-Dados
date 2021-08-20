/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;


/**
 * Esta classe vai possibilitar o tratamento dos dados do mapa do hotel
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class Hotel {
    
    private String pathFile;
    private String nome;
    
    /**
     * Método Construção
     * 
     * 
     * 
     * @param pathFile caminho onde esta o file
     * @param nome nome do mapa
     */
    public Hotel(String pathFile, String nome){
        this.pathFile = pathFile;
        this.nome = nome;
    }

    public String getPathFile(){
        return pathFile;
    }

    public String getNome(){
        return nome;
    }

}
