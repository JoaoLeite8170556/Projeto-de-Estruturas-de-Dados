/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import static Classes.JSONHotel.getNumeroDeDivisoes;

import Colecoes.*;
import Enumerações.Tipo;
import Enumerações.TipoSala;
import Excepcoes.ElementNonComparable;
import Excepcoes.EmptyExcpetion;
import Grafo.GrafoHotel;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private JSONMovimentos movimentos;
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
        this.divisoesHotel = new GrafoHotel<Divisao>(getNumeroDeDivisoes(this.nomeHotel));
        this.movimentos = new JSONMovimentos("../movimentos.json");    
        this.movimentosPessoas=this.movimentos.readFileMovimentos();
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
     * Este metodo vai permitir retornar a listagem de todas as pessoas que estão no hotel;
     * @return a losta com todas as pessoas
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ParseException
     * @throws java.text.ParseException
     * @throws EmptyExcpetion 
     */
    public UnorderedDoubleLinkedList<Integer> listagemDePessoas() throws IOException, FileNotFoundException, ParseException, java.text.ParseException, EmptyExcpetion{
        
        
        UnorderedDoubleLinkedList<Integer> lista = new UnorderedDoubleLinkedList<Integer>();
        JSONMovimentos jsonMovimentos = new JSONMovimentos("../movimentos.json");
        
        DoubleLinkedOrderedList<Movimentos> allMovimentos = jsonMovimentos.readFileMovimentos();
        
        
        Iterator itr = allMovimentos.iterator();
        
        while(itr.hasNext()){
            
            Movimentos move = (Movimentos) itr.next();
            
           if(!lista.contains(move.getIdPessoa())){
               lista.addToRear(move.getIdPessoa());
           }
                   
        }
        
        return lista;
    }
    /**
     * Este método vai possibilitar estruturar o grafo conforme o ficheiro JSON
     */
    public void loadMapaHotel(){

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
 

                if (((JSONObject) divisoes).containsKey("quarentena")){
                    this.divisoesHotel.addVertex(divisao = new Divisao(nomeDivisao, TipoSala.QUARENTENA));
                    divisao.setCapacidadeMaxima(lotacao);
                } else if (((JSONObject) divisoes).containsKey("reservado")) {
                    this.divisoesHotel.addVertex(divisao = new Divisao(nomeDivisao, TipoSala.RESERVADO));
                    divisao.setCapacidadeMaxima(lotacao);
                } else {
                    this.divisoesHotel.addVertex(divisao = new Divisao(nomeDivisao));
                    divisao.setCapacidadeMaxima(lotacao);
                }
            }
            
            JSONArray jsonLigacoes = (JSONArray) jsonObject.get("ligacoes");

            for (Object ligacoes : jsonLigacoes) {
                JSONArray edge = (JSONArray) ligacoes;
                this.divisoesHotel.addEdge(findDivision(edge.get(0).toString()),
                        findDivision(edge.get(1).toString()), 1);
            }
            
        } catch (FileNotFoundException ex) {
        } catch (IOException | ParseException ex) {
        } 

    }
    
    /**
     * Este metodo vai permitir atualizar os pesos entre duas divisões
     * @param divisaoInicial divisao inicial
     * @param divisaoDestino divisão destino
     */
    private void atualizaPesos(Divisao divisaoInicial, Divisao divisaoDestino){
        
        Iterator itr_divisaoDestino = 
                this.divisoesHotel.getVerticesAdjacentes(divisaoDestino);
        Iterator itr_divisaoInicial = 
                this.divisoesHotel.getVerticesAdjacentes(divisaoInicial); 
        
        while(itr_divisaoDestino.hasNext()){
            
            Divisao aux_divisaoDestino = (Divisao)itr_divisaoDestino.next();
           
            if(aux_divisaoDestino!=divisaoInicial){
               
                this.divisoesHotel.setEdgeWeight(aux_divisaoDestino, 
                        divisaoDestino,
                        this.divisoesHotel.getEdgeWeight(aux_divisaoDestino,
                                divisaoDestino)+1);
                
            }
        }
        
        while(itr_divisaoInicial.hasNext()){
            
            Divisao aux_divisaoInicial = (Divisao)itr_divisaoInicial.next();
           
            if(aux_divisaoInicial!=divisaoDestino){
               
                this.divisoesHotel.setEdgeWeight(aux_divisaoInicial, 
                        divisaoDestino,
                        this.divisoesHotel.getEdgeWeight(aux_divisaoInicial,
                                divisaoDestino)-1);
                
            }
        }
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

    public GrafoHotel<Divisao> getDivisoes() {
        return divisoesHotel;
    }
    
    /**
     * Este método vai permitir retornar todos os movimentos efetuados nas ultimas 
     * X horas
     * @param date o número de horas na qual queremos ter os movimentos
     * @return uma listas com todos os movimentos.
     */
    public DoubleLinkedOrderedList<Movimentos> listaDeContactos(int date) throws IOException, FileNotFoundException, ParseException, java.text.ParseException, ElementNonComparable{
        
        DoubleLinkedOrderedList<Movimentos> listaDeMovimentos = new DoubleLinkedOrderedList<Movimentos>();
       
        Calendar c = Calendar.getInstance(); // data e hora atual
       
        c.add(Calendar.HOUR, -date); // vou retirar as X horas
            
        Date updateDate = c.getTime(); // Guardar num objeto Date a subtração feita
       
        for (Movimentos movimentos : this.movimentosPessoas) {

            if(movimentos.getDataHoraAtual().compareTo(updateDate)==1){
                listaDeMovimentos.add(movimentos);
            }
        }
        return listaDeMovimentos;
    }
    
    
    
    
    
    /*public int escolheDataHora(){
        
        int hora = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduza a Hora na qual quer pesquisar!!!!"+"\n\n");
       
        /*System.out.println("Introduza o ano: "+"\n");
        String yyyy = scanner.nextLine();
        System.out.println("Ano Introduzido: "+yyyy+"\n");
        System.out.println("Introduza o Mes: "+"\n");
        String MM = scanner.nextLine();
        System.out.println("Mês introduzido: "+MM+"\n");
        System.out.println("Introduza o Dia: "+"\n");
        String dd = scanner.nextLine();
        System.out.println("Dia introduzido: "+dd+"\n");
        System.out.println("Introduza a Hora: "+"\n");
        int HH = scanner.nextInt();
        System.out.println("Hora introduzido: "+HH+"\n");
        /*System.out.println("Introduza o Minutos: "+"\n");
        String mm = scanner.nextLine();
        System.out.println("Hora introduzido: "+mm+"\n");

        return hora = HH;
    }*/
    
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
    
     /**
     * Este metodo vai procurar o objeto divisao pelo nome
     * @param divisao string da divisao que se pretende procurar
     * @return a Divisao caso seja encontrado, se não for encontrada retorna null
     */
    public Divisao findDivision(String divisao){
        
        Iterator itr = this.divisoesHotel.getTodasDivisoes().iterator();
        
        while(itr.hasNext()){
            Divisao aux = (Divisao)itr.next();
            if(aux.getNome().equals(divisao) ){
                return aux;
            }
        }
        return null;
    }
    /**
     * Este método vai permitir guardar pessoas nas divisão analisando certas variaveis
     * como tipo de sala que esta a entrar ou tipo de pessoa que é.
     * @param pessoa pessoa que vai ser mover para a divisão.
     * @param divisao a divisao.
     */
    public void verificaTipoPessoa(Pessoa pessoa, Divisao divisao){

        Iterator itr = divisao.getListaDePessoas().iterator();

        while(itr.hasNext()){
            if(divisao.getTipoSala().equals("RESERVADO") && pessoa.getTipo().equals("FUNCIONARIO") && verificaLotacao(divisao)==0){
                divisao.getListaDePessoas().addToRear(pessoa);
            }else if(divisao.getListaDePessoas().equals("QUARENTENA") && pessoa.getTipo().equals("HOSPEDE") && verificaLotacao(divisao)==0){
                divisao.getListaDePessoas().addToRear(pessoa);
            }else if(!divisao.getListaDePessoas().equals("QUARENTENA") && !divisao.getListaDePessoas().equals("RESERVADO") && verificaLotacao(divisao)==1){
                divisao.getListaDePessoas().addToRear(pessoa);
            }else{
                System.out.println("IMPOSSÍVEL!!!! Não pode entrar!!!!");
            }
        }
    }
    
    /**
     * Este método vai possibilitar a lotação da divisão chegar ao limite apresenta a seguinte mensagem
     * @param divisao a divisão em causa.
     */
    public void alertaLotacao(Divisao divisao){
        if(verificaLotacao(divisao)==0){
            System.out.println("Atingiu limite da Divisão, não pode entrar.");
        }
    }
    /**
     * Metodo que verifica se a lotacao atual da divisao ja atingiu a lotacao máxima
     * @param divisao a divisao na qual vamos fazer a verificação
     * @return retorna 1 se a lotação estiver OK, 0 se atingir o máximo
     */
    private int verificaLotacao(Divisao divisao){

        if(divisao.getListaDePessoas().size() < divisao.getNumeroPessoas()){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public String toString() {
        return "GestaoHotel{" + "versao=" + versao + ", nomeHotel=" + nomeHotel + ", divisoesHotel=" + divisoesHotel + '}';
    }
    
    
}
