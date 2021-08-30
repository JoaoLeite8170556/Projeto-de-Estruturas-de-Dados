/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author João
 */
public class Mapa {
    
    private String fileName;
    private String nomeMapa;
    
    
    public Mapa(String file, String nomeMapa){
        this.fileName=file;
        this.nomeMapa=nomeMapa;
    }

    public String getFileName() {
        return fileName;
    }

    public String getNomeMapa() {
        return nomeMapa;
    }
    
}
