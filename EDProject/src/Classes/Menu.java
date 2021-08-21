/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.UnorderedArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Classe para tratarmos dos menus do Programa.
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class Menu {
    
    
    public Menu(){
        
    }
    
    /**
     * Este método vai retornar todos os mapas da pasta Mapas
     * @return iterador com a lista de mapas presentes na pasta "./Mapas"
     */
    public Iterator arrayDeMapas(){
        UnorderedArrayList<Hotel> hoteis = new UnorderedArrayList<>();
        
        File[] listaDeFicheiros = new File("./Mapas").listFiles(new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name) {
                if(name.toLowerCase().endsWith(".json")){
                    return true;
                }else{
                    return false;
                }
            }
            
        });
        
        for(File fileName: listaDeFicheiros){
            try {
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("Mapas/"+fileName.getName()));
                
                String[] nomesDosHoteis = fileName.getName().split(".json");
                
                Hotel hotel = new Hotel(nomesDosHoteis[0], jsonObject.get("nome").toString());
                hoteis.addToFront(hotel);
            } catch (FileNotFoundException ex) {
            } catch (IOException ex) {} 
            catch (ParseException ex) {}
        }
        return hoteis.iterator();
    }
    
    /**
     * Metodo que vai permitir escolher o hotel para a nossa aplicação
     * @param escolhaHotel o numero a que o hotel corresponde
     * @return o nome do hotel escolhido.
     */
    public String escolheHotel(String escolhaHotel){
        int count =1;
        boolean found = false;
        Iterator itr = this.arrayDeMapas();
        
        while(itr.hasNext() && !found){
            Hotel hotel = (Hotel) itr.next();
            
            if(Integer.parseInt(escolhaHotel)==count){
                found=true;
                return hotel.getNome();
            }else{
                count++;
            }
        }
        return null;
    }
    
}
