/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.*;
import Excepcoes.*;
import Interfaces.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Métodos 
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class Import implements InterfaceImport{

     /**
     * Este metodo vai ler o ficheiro json e guardar a informação numa lista.
     *
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException
     * @throws java.text.ParseException
     */
    @Override
    public DoubleLinkedOrderedList<JSONMovimentos> readFileMovimentos() throws FileNotFoundException, IOException, ParseException, ElementNonComparable {
        
        DoubleLinkedOrderedList<JSONMovimentos> tempList = new DoubleLinkedOrderedList<JSONMovimentos>();
        DoubleLinkedOrderedList<Movimentos> listaMovimentosTemp = new DoubleLinkedOrderedList<>();
        String nomeHotel = "";
        Integer versao = 0;
        File file = new File("../movimentos.json");

        if (file.exists()) {
            JSONObject movimentosJSON = (JSONObject) new JSONParser().parse(new FileReader(file));
            JSONArray movimentosArray = (JSONArray) movimentosJSON.get("movimentosHoteis");

            for (Object obj : movimentosArray) {
                JSONObject movimentosHotel = (JSONObject) obj;
                versao = Integer.parseInt((String) movimentosHotel.get("versao").toString());
                nomeHotel = (String) movimentosHotel.get("nome");

                JSONArray jsonMovimentos = (JSONArray) movimentosHotel.get("movimentos");



            for (int i = 0; i < jsonMovimentos.size(); i++) {

                JSONObject objMovimentos = (JSONObject) jsonMovimentos.get(i);
                
                Integer idPessoa = Integer.parseInt((String) objMovimentos.get("idPessoa").toString());
                String divisao = (String) objMovimentos.get("divisão");
                String dataHora = (String) objMovimentos.get("dataHora");
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date date = formatter.parse(dataHora);
                    listaMovimentosTemp.add(new Movimentos(idPessoa,divisao,date));
                } catch (java.text.ParseException | ElementNonComparable ex) {
                    Logger.getLogger(JSONMovimentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            }
            tempList.add(new JSONMovimentos(nomeHotel, versao, listaMovimentosTemp));
        }
        return tempList;
    }
    
    /**
     * Obtem todos os mapa que estão no directorio 
     * @return iterador com a coleção com todos os mapas
     */
    private Iterator obtemMapasHoteis(){
        UnorderedArrayList<Mapa> listaDeMapas = new UnorderedArrayList<>();
        
        File file = new File("../Hoteis");
        File listOfFiles[] = file.listFiles();
        
        int i=0;
        
        for(int j=listOfFiles.length; i<j; i++){
            File arquivos = listOfFiles[i];
            Mapa mapa = new Mapa(arquivos.getName(),arquivos.getAbsolutePath());
            listaDeMapas.addToRear(mapa);
        }
        return listaDeMapas.iterator();
    }
       
    /**
     * Método que vai permitir escolher o hotel para a nossa aplicação
     * @param escolhaHotel o numero a que o hotel corresponde
     * @return o nome do hotel escolhido
     */
    @Override
    public String escolheHotel(String escolhaHotel) throws FileNotFoundException, ParseException{
        int count =1;
        
        boolean found = false;
        
        Iterator itr = this.obtemMapasHoteis();
        
        while(itr.hasNext() && !found){
            
            Mapa hotel = (Mapa) itr.next();
            
            if (Integer.parseInt(escolhaHotel) == count) {
                found = true;
                return hotel.getPath();
            } else {
                count++;
            }
        }
        return null;
    }
    
    /**
     * Este método vai imprimir todos os hoteis e retornar o seu número
     * @return número de hoteis
     */
    @Override
    public int mostraHoteis() throws FileNotFoundException, ParseException{
        Iterator itr = obtemMapasHoteis();
        int num = 1;
        
        while(itr.hasNext()){
            Mapa hotel = (Mapa) itr.next();
            System.out.println(num + ") "+ hotel.getFileName());
            
            if(itr.hasNext()){
                num++;
            }
        }
        return num;
    }
    
    
    
}
