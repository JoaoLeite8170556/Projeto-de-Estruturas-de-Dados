/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.DoubleLinkedOrderedList;
import Colecoes.GraphWeight;
import Colecoes.UnorderedDoubleLinkedList;
import Enumerações.Tipo;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Esta classe vai permitir fazer a gestão de utilizadores e seu posicionamento no Hotel.
 * @author João Leite Nº 8170556 
 * @author Celio Macedo Nº 8170358
 */ 
public class GestaoHotel {
    
    private final UnorderedDoubleLinkedList<Pessoa> listaDePessoas;
    private int versao;
    private String nomeHotel;
    private DoubleLinkedOrderedList<Movimentos> movimentosPessoas;
    private GraphWeight<Divisao> divisoes;
    private String pathFile;
    

    public GestaoHotel(String pathFile){
        this.versao=0;
        this.nomeHotel=null;
        this.pathFile= pathFile;
        this.listaDePessoas = new UnorderedDoubleLinkedList<Pessoa>();
        this.movimentosPessoas = new DoubleLinkedOrderedList<Movimentos>();
        this.divisoes = new GraphWeight<Divisao>();
    }
    
    public void inserirHospede(){
        System.out.println("Insira um Hospede no Sistema!!!");
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Introduza o nome:");
        String nomePessoa = scanner.nextLine();
        
        this.listaDePessoas.addToRear(new Pessoa(nomePessoa,Tipo.HOSPEDE));
        
    }
    
    public void inserirFuncionario(){
        System.out.println("Insira um Funcionario no Sistema!!!");
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Introduza o nome: ");
        String nomePessoa = scanner.nextLine();
        
        this.listaDePessoas.addToRear(new Pessoa(nomePessoa,Tipo.FUNCIONARIO));
        
    }
    
    public GestaoHotel loadMapaHotel() throws FileNotFoundException, IOException, ParseException{
        
        GestaoHotel hotel = new GestaoHotel(this.pathFile);
        
        JSONParser JSONParser = new JSONParser();
        FileReader reader = new FileReader(this.pathFile);
        Object obj = JSONParser.parse(reader);

        JSONObject jsonObject = (JSONObject) obj;
        
        /**
         * Nome do Hotel 
         */
        this.nomeHotel = (String) jsonObject.get("hotel");
        
        /**
         * Versão do Hotel
         */
        this.versao = (int) (long) jsonObject.get("versao");
        
        JSONArray jsonDivisoes = (JSONArray) jsonObject.get("divisoes");
        
        for(Object divisoes : jsonDivisoes){
            
            String nomeDivisao = ((JSONObject)divisoes).get("nome").toString();
            Integer lotacao = Integer.parseInt(((JSONObject)divisoes).get("lotacao").toString());
            
            if(jsonObject.containsKey("quarentena")){
                boolean quarentena = Boolean.parseBoolean(((JSONObject)divisoes).get("quarentena").toString());
                this.divisoes.addVertex(new Divisao(nomeDivisao, quarentena, lotacao));
            }else if(jsonObject.containsKey("reservado")){
                boolean reservado = Boolean.parseBoolean(((JSONObject)divisoes).get("reservado").toString());
                this.divisoes.addVertex(new Divisao(nomeDivisao,lotacao,reservado));
            }else{
                this.divisoes.addVertex(new Divisao(nomeDivisao,lotacao));
            } 
        }
        return hotel;
    }

    public UnorderedDoubleLinkedList<Pessoa> getListaDePessoas() {
        return listaDePessoas;
    }

    public int getVersao() {
        return versao;
    }

    public String getNomeHotel() {
        return nomeHotel;
    }

    public DoubleLinkedOrderedList<Movimentos> getMovimentosPessoas() {
        return movimentosPessoas;
    }

    public GraphWeight<Divisao> getDivisoes() {
        return divisoes;
    } 
}
