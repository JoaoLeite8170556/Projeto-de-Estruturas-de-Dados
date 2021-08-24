/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.UnorderedArrayList;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
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
public final class Menu {
    
    
   
    
    /**
     * Este método vai retornar todos os Hoteis da pasta Hoteis
     * @return iterador com a lista de mapas presentes na pasta "./Hoteis"
     */
    private Iterator arrayDeHoteis() {

        UnorderedArrayList<Hotel> hoteis = new UnorderedArrayList<Hotel>();

        File directoryPath = new File("D://Users//joao-//OneDrive - Instituto Politécnico do Porto//Ambiente de Trabalho//ED_EpocaEspecial//Hoteis");

        FileFilter textFileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                boolean isFile = file.isFile();
                
                if (isFile) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        File listaDeFicheiros[] = directoryPath.listFiles(textFileFilter);

        for (File fileName : listaDeFicheiros) {

            Hotel hotel = new Hotel(fileName.getAbsolutePath(),fileName.getName());
            hoteis.addToFront(hotel);

        }
        return hoteis.iterator();
    }
    
   
    Menu(){
        
        String choise = "";
        
        boolean nomeDoMapa = false;
        boolean validoPath = false;
        String nomeHotel = "";
        
        while(!nomeDoMapa || !validoPath){
            int number = mostraHoteis();
            
            System.out.println("Escolha o hotel para usar:");
            
            Scanner scanner = new Scanner(System.in);
            
            choise = scanner.nextLine();
            
            if(choise.matches("[1-"+number+"]")){
                if(escolheHotel(choise)!= null){
                    nomeHotel = escolheHotel(choise);
                }
            }
            
        }
        
        
        
        
        
    }
 
    /**
     * Metodo que vai permitir escolher o hotel para a nossa aplicação
     * @param escolhaHotel o numero a que o hotel corresponde
     * @return o nome do hotel escolhido
     */
    public String escolheHotel(String escolhaHotel){
        int count =1;
        boolean found = false;
        Iterator itr = this.arrayDeHoteis();
        
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
    
    
    /**
     * Este metodo vai imprimir todos os hoteis e retornar o seu número
     * @return numero de hoteis
     */
    public int mostraHoteis(){
        Iterator itr = arrayDeHoteis();
        int num = 1;
        
        while(itr.hasNext()){
            Hotel hotel = (Hotel) itr.next();
            System.out.println(num + ") "+ hotel.getNome());
            
            if(itr.hasNext()){
                num++;
            }
        }
        return num;
    }
    
    
    
}
