/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import static Classes.JSONHotel.getNumeroDeDivisoes;

import Colecoes.*;
import Enumerações.Tipo;
import Grafo.GrafoHotel;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Esta classe vai permitir fazer a gestão de utilizadores e seu posicionamento
 * no Hotel.
 *
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class GestaoHotel {

    private final UnorderedArrayList<Pessoa> listaDePessoas;
    private int versao;
    private String nomeHotel;
    private DoubleLinkedOrderedList<Movimentos> movimentosPessoas;
    private GrafoHotel<Divisao> divisoesHotel;
    private String pathFile;
    private Pessoa pessoa;

    /**
     * Metodo construtor para criar uma GestaoHotel
     *
     * @param pathFile O caminho onde vão estar os ficheiros
     */
    public GestaoHotel(String pathFile) {
        this.versao = 0;
        this.nomeHotel = null;
        this.pathFile = pathFile;
        this.listaDePessoas = new UnorderedArrayList<Pessoa>();
        this.movimentosPessoas = new DoubleLinkedOrderedList<Movimentos>();
        this.divisoesHotel = new GrafoHotel<Divisao>(getNumeroDeDivisoes(this.nomeHotel));
        this.pessoa = new Pessoa();
    }

    /**
     * Este metodo vai possibilitar a adição de Hospede
     */
    public void inserirHospede() {
        System.out.println("Insira um Hospede no Sistema!!!");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduza o nome:");
        String nomePessoa = scanner.nextLine();

        this.listaDePessoas.addToRear(new Pessoa(nomePessoa, Tipo.HOSPEDE));

    }

    /**
     * Este metodo vai possibilitar a adição de Funcionario
     */
    public void inserirFuncionario() {
        System.out.println("Insira um Funcionario no Sistema!!!");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduza o nome: ");
        String nomePessoa = scanner.nextLine();

        this.listaDePessoas.addToRear(new Pessoa(nomePessoa, Tipo.FUNCIONARIO));

    }
    
    /**
     * Este método vai imprimir todos os Hospedes inseridos no Hotel
     */
    public void imprimeHospedes(){
        Iterator<Pessoa> itr = this.listaDePessoas.iterator();
        
        while(itr.hasNext()){
            if(itr.next().getTipo()== Tipo.HOSPEDE){
                System.out.println(itr.next().toString());
            }
        }
        
    }
    
    /**
     * Este método vai imprimir todos os Funcionarios no Hotel
     */
    public void imprimeFuncionarios(){
        Iterator<Pessoa> itr = this.listaDePessoas.iterator();
        
        while(itr.hasNext()){
            if(itr.next().getTipo()== Tipo.FUNCIONARIO){
                System.out.println(itr.next().toString());
            }
        }
        
    }
    
    /**
     * Este método vai possibilitar estruturar o grafo conforme o ficheiro JSON
     */
    public void loadMapaHotel() {

        FileReader reader = null;
        try {
            JSONParser JSONParser = new JSONParser();
            reader = new FileReader(this.pathFile);
            Object obj = JSONParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            /**
             * Guardar o nome da missão
             */
            this.nomeHotel = (String) jsonObject.get("hotel");
            /**
             * Guarda a versão do hotel
             */
            this.versao = (int) (long) jsonObject.get("versao");
            /**
             * Array das Divisões
             */
            JSONArray jsonDivisoes = (JSONArray) jsonObject.get("divisoes");
            String nomeDivisao = null;
            Integer lotacao = null;
            Divisao divisao = new Divisao(this.nomeHotel);

            /**
             * Divisões para existentes no hotel
             */
            for (Object divisoes : jsonDivisoes) {

                nomeDivisao = ((JSONObject) divisoes).get("nome").toString();
                lotacao = Integer.parseInt(((JSONObject) divisoes).get("lotacao").toString());
                if (jsonObject.containsKey("quarentena")) {
                    boolean quarentena = Boolean.parseBoolean(((JSONObject) divisoes).get("quarentena").toString());
                    this.divisoesHotel.addVertex(divisao = new Divisao(nomeDivisao ,quarentena));
                    divisao.setCapacidadeMaxima(lotacao);
                } else if (jsonObject.containsKey("reservado")) {
                    boolean reservado = Boolean.parseBoolean(((JSONObject) divisoes).get("reservado").toString());
                    this.divisoesHotel.addVertex(divisao = new Divisao(nomeDivisao,reservado));
                    divisao.setCapacidadeMaxima(lotacao);
                } else {
                    this.divisoesHotel.addVertex(divisao = new Divisao(nomeDivisao));
                    divisao.setCapacidadeMaxima(lotacao);
                }
            }
            
            JSONArray jsonLigacoes = (JSONArray) jsonObject.get("ligacoes");

            for (Object ligacoes : jsonLigacoes) {

                Divisao coneccao = new Divisao((String) ligacoes);
                this.divisoesHotel.addVertex(coneccao);
                this.atualizaPesos(divisao, coneccao);
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } catch (ParseException ex) {
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
            }
        }

    }
    
    /**
     * Este metodo vai permitir atualizar os pesos entre duas divisões
     * @param divisaoInicial divisao inicial
     * @param divisaoDestino divisão destino
     */
    private void atualizaPesos(Divisao divisaoInicial, Divisao divisaoDestino){
        if(!this.divisoesHotel.estaConectado(divisaoInicial, divisaoDestino)){
            this.divisoesHotel.addEdge(divisaoInicial, divisaoDestino, 0);
        }
        
        this.divisoesHotel.addEdge(divisaoInicial, divisaoDestino, divisaoInicial.getNumeroPessoas());
    }

    public UnorderedArrayList<Pessoa> getListaDePessoas() {
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
        return divisoesHotel;
    }
    
    /**
     * Este metodo vai procurar uma pessoa inscritos no Hotel 
     * @param idPessoa id da pessoa
     * @return a Pessoa caso seja encontrado, se não for encontrada retorna null
     */
    public Pessoa findPersonInHotel(int idPessoa){
        Iterator itr = this.listaDePessoas.iterator();
        
        while(itr.hasNext()){
            Pessoa aux = (Pessoa)itr.next();
            if(aux.getId()== idPessoa){
                return aux;
            }
        }
        return null;
    }
    
    
}
