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
    private String path;
    
    
    public Mapa(String fileName, String path){
        this.fileName=fileName;
        this.path=path;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }
    
}
