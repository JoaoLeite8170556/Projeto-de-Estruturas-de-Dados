/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.UnorderedArrayList;
import Excepcoes.ElementNonComparable;
import Excepcoes.EmptyExcpetion;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.Iterator;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Classe para tratarmos dos menus do Programa.
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class Menu {
    
    
    
    private Hotel hotel;
    private GestaoHotel gestaoHotel;
    
    
    public Menu() {
    }
    
    
    
    
    
   
    
    /**
     * Este método vai retornar todos os Hoteis da pasta Hoteis
     * @return iterador com a lista de mapas presentes na pasta "./Hoteis"
     */
    private Iterator arrayDeHoteis() throws FileNotFoundException, ParseException {

        UnorderedArrayList<Mapa> hoteis = new UnorderedArrayList<Mapa>();

        File[] listaDeFicheiros = new File("../Hoteis").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.toLowerCase().endsWith(".json")){
                    return true;
                }else{
                    return false;
                }
            }
        });
        
        
        for(File fileName : listaDeFicheiros){
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("../Hoteis/")+fileName.getName());
            
            String[] mapaNameSlited = fileName.getName().split(".json");
            
            Mapa mapa = new Mapa(mapaNameSlited[0],jsonObject.get("nome").toString());
            hoteis.addToFront(mapa);
        }
        return hoteis.iterator();
    }
    
 
    /**
     * Método que vai permitir escolher o hotel para a nossa aplicação
     * @param escolhaHotel o numero a que o hotel corresponde
     * @return o nome do hotel escolhido
     */
    public String escolheHotel(String escolhaHotel) throws FileNotFoundException, ParseException{
        int count =1;
        boolean found = false;
        Iterator itr = this.arrayDeHoteis();
        
        while(itr.hasNext() && !found){
            Mapa hotel = (Mapa) itr.next();
            
            if(Integer.parseInt(escolhaHotel)==count){
                found=true;
                return hotel.getFileName();
            }else{
                count++;
            }
        }
        return null;
    }
    
    
    /**
     * Este metodo vai imprimir todos os hoteis e retornar o seu número
     * @return número de hoteis
     */
    private int mostraHoteis() throws FileNotFoundException, ParseException{
        Iterator itr = arrayDeHoteis();
        int num = 1;
        
        while(itr.hasNext()){
            Mapa hotel = (Mapa) itr.next();
            System.out.println(num + ") "+ hotel.getNomeMapa());
            
            if(itr.hasNext()){
                num++;
            }
        }
        return num;
    }
    
    public void menuHotel() throws FileNotFoundException, ParseException{
        String escolha = "";
        
        
        boolean nomeDoMapa = false;
        boolean caminhoValido = false;
        
        
        String nomeMapa = "";
        
        System.out.println("---------------------------------------"+ "\n");
        System.out.println("Bem vindo ao Hotel: Toca"+"\n");
        
        while(!nomeDoMapa || !caminhoValido){
            int number = mostraHoteis();
            
            System.out.println("Escolha a opção: "+"\n");

            Scanner scanner = new Scanner(System.in);
            
            escolha = scanner.nextLine();
            
            if(escolha.matches("1-"+ number+"]")){
                if(escolheHotel(escolha) != null){
                    nomeMapa = escolheHotel(escolha);
                    this.hotel = new Hotel(nomeMapa);
                    caminhoValido = true;
                }
            }else{
                System.out.println("Opção inválida");
            }
        }
    }
    
     public void modoDeJogo() throws EmptyExcpetion, ElementNonComparable{
        int opcao = 0;
        while(opcao != -1){
            System.out.println("---------------------------");
            System.out.println("1 -> Modo Manual");
            System.out.println("2 -> Modo Automatico");
            System.out.println("0 -> Sair");
            System.out.println("---------------------------");
            
            switch(opcao){
                case 1:
                    modoManual();
                    break;
                case 2:
                    //gestaoHotel.modoAutomatico();
                    break;
                case 0:
                    opcao=-1;
                    break;
            }
        }
    }
     
     
    public void modoManual() throws EmptyExcpetion, ElementNonComparable {
        int opcao = 0;
        Scanner scanner = new Scanner(System.in);
        while (opcao != -1) {
            System.out.println("---------------------------");
            System.out.println("1 -> Mover Pessoas");
            System.out.println("2 -> Criar Hospede");
            System.out.println("3 -> Criar Funcionario");
            System.out.println("4 -> Ver Lista de Pessoas");
            System.out.println("5 -> Ver Mapa de Hotel");
            System.out.println("0 -> Sair");
            System.out.println("---------------------------");
            int escolha = scanner.nextInt();
            switch (escolha) {
                case 1:
                    gestaoHotel.modoManual();
                    break;
                case 2:
                    hotel.inserirHospede();
                    break;
                case 3:
                    hotel.inserirFuncionario();
                    break;
                case 4:
                    hotel.imprimePessoas();
                    break;
                case 5:

                    break;
                case 0:
                    opcao = -1;
                    break;
            }
        }
    }

}
