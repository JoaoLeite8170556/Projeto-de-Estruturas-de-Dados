/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.*;
import Enumerações.Tipo;
import Enumerações.TipoSala;
import Excepcoes.EmptyExcpetion;
import Grafo.GrafoHotel;
import Interfaces.InterfaceHotel;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Esta classe vai permitir fazer a gestão de utilizadores e seu posicionamento
 * nas divisões do Hotel.
 *
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class Hotel implements InterfaceHotel{

    private final UnorderedDoubleLinkedList<Pessoa> listaDePessoas;
    //private String pathFile;
    private int versao;
    private String nomeHotel;
    private DoubleLinkedOrderedList<Movimentos> movimentosHotel;
    private GrafoHotel<Divisao> divisoesHotel;
    private JSONMovimentos jsonMovimentos;
    private DoubleLinkedOrderedList<JSONMovimentos> listaMovimentos;

    /**
     * Método construtor para criar uma GestaoHotel
     *
     * @param pathFile o caminho onde vão estar os ficheiros
     */
    public Hotel(String pathFile) {
        this.versao = 0;
        this.nomeHotel = null;
        this.divisoesHotel = new GrafoHotel<Divisao>(getNumeroDeDivisoes(this.nomeHotel));
        this.loadMapaHotel(pathFile);
        this.listaDePessoas = new UnorderedDoubleLinkedList<Pessoa>();
        this.movimentosHotel = new DoubleLinkedOrderedList<>();
        this.jsonMovimentos = new JSONMovimentos(this.nomeHotel, this.versao, this.movimentosHotel);
        this.listaMovimentos = new DoubleLinkedOrderedList<JSONMovimentos>();
    }

    /**
     * Este método vai possibilitar estruturar o grafo conforme o ficheiro JSON
     */
    public void loadMapaHotel(String pathFile){

        FileReader reader = null;
        
        try {
           
            JSONParser JSONParser = new JSONParser();
            reader = new FileReader(pathFile);
            Object obj = JSONParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            /**
             * Guardar o nome da Missão
             */
            this.nomeHotel = (String) jsonObject.get("hotel");

            /**
             * Guarda a versão do Hotel
             */
            this.versao = (int) (long) jsonObject.get("versao");

            /**
             * Array das Divisões
             */
            JSONArray jsonDivisoes = (JSONArray) jsonObject.get("divisoes");

            String nomeDivisao = null;
            Integer lotacao = null;
            Divisao divisao = null;

            /**
             * Divisões para existentes no hotel
             */
            for (Object divisoes : jsonDivisoes) {

                nomeDivisao = ((JSONObject) divisoes).get("nome").toString();

                lotacao = Integer.parseInt(((JSONObject) divisoes).get("lotacao").toString());

                if (((JSONObject) divisoes).containsKey("quarentena")){
                    this.divisoesHotel.addVertex(divisao = new Divisao(nomeDivisao, TipoSala.QUARENTENA));
                    divisao.setCapacidadeMaxima(lotacao);
                } else if (((JSONObject) divisoes).containsKey("reservado")){
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
     * Método para calcular o número de Divisões que estão no file JSON
     *
     * @param nomeDivisao nome da Divisão
     * @return retorna o número de Divisões
     */
    private int getNumeroDeDivisoes(String nomeHotel) {

        int numeroDeDivisoes = 0;

        try {
            
            JSONParser parser = new JSONParser();
            
            JSONObject jSONObject = (JSONObject) parser.parse("../mapa.json");
            
            JSONArray mapArray = (JSONArray) jSONObject.get("divisoes");

            numeroDeDivisoes = mapArray.size();

        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        return numeroDeDivisoes;
    }

    /**
     * Este método vai permitir atualizar os pesos entre duas divisões
     *
     * @param divisaoInicial divisao inicial
     * @param divisaoDestino divisão destino
     */
    public void atualizaPesos(Divisao divisaoInicial, Divisao divisaoDestino) {

        Iterator itr_divisaoDestino
                = this.divisoesHotel.getVerticesAdjacentes(divisaoDestino);
        Iterator itr_divisaoInicial
                = this.divisoesHotel.getVerticesAdjacentes(divisaoInicial);

        while (itr_divisaoDestino.hasNext()) {

            Divisao aux_divisaoDestino = (Divisao) itr_divisaoDestino.next();

            if (aux_divisaoDestino != divisaoInicial) {

                this.divisoesHotel.setEdgeWeight(aux_divisaoDestino,
                        divisaoDestino,
                        this.divisoesHotel.getEdgeWeight(aux_divisaoDestino,
                                divisaoDestino) + 1);

            }
        }
        while (itr_divisaoInicial.hasNext()) {

            Divisao aux_divisaoInicial = (Divisao) itr_divisaoInicial.next();

            if (aux_divisaoInicial != divisaoDestino) {

                this.divisoesHotel.setEdgeWeight(aux_divisaoInicial,
                        divisaoInicial,
                        this.divisoesHotel.getEdgeWeight(aux_divisaoInicial,
                                divisaoInicial) - 1);
            }
        }
    }
    
    /**
     * Este método vai permitir atualizar os pesos quando uma pessoa entra do hotels
     *
     * @param entrada divisao entrada
     */
    public void atualizaPesosEntrada(Divisao entrada) {

        Iterator itr_divisaoAdjacentes
                = this.divisoesHotel.getVerticesAdjacentes(entrada);

        while (itr_divisaoAdjacentes.hasNext()) {

            Divisao aux_divisaoAdjacente = (Divisao) itr_divisaoAdjacentes.next();

            this.divisoesHotel.setEdgeWeight(entrada,
                    aux_divisaoAdjacente,
                    this.divisoesHotel.getEdgeWeight(entrada,
                            aux_divisaoAdjacente) + 1);

        }
    }
    
    
    /**
     * Este método vai permitir atualizar os pesos quando uma pessoa sai do hotel
     *
     * @param saida divisao saida
     */
    public void atualizaPesosSaida(Divisao saida) {

        Iterator itr_divisaoAdjacentes
                = this.divisoesHotel.getVerticesAdjacentes(saida);

        while (itr_divisaoAdjacentes.hasNext()) {

            Divisao aux_divisaoAdjacente = (Divisao) itr_divisaoAdjacentes.next();

            this.divisoesHotel.setEdgeWeight(saida,
                    aux_divisaoAdjacente,
                    this.divisoesHotel.getEdgeWeight(saida,
                            aux_divisaoAdjacente) -1);

        }
    }
    
    public UnorderedDoubleLinkedList<Pessoa> getListaDePessoas() {
        return listaDePessoas;
    }

    public int getVersao(){
        return versao;
    }

    public String getNomeHotel(){
        return nomeHotel;
    }

    

    public GrafoHotel<Divisao> getDivisoes() {
        return divisoesHotel;
    }

    public DoubleLinkedOrderedList<Movimentos> getMovimentosHotel() {
        return movimentosHotel;
    }


    
    /**
     * Este metodo vai procurar uma pessoa inscritos no Hotel
     *
     * @param idPessoa id da pessoa
     * @return a Pessoa caso seja encontrado, se não for encontrada retorna null
     */
    private Pessoa findPersonInHotel(int idPessoa) {
        Iterator itr = this.listaDePessoas.iterator();

        while (itr.hasNext()) {
            Pessoa aux = (Pessoa) itr.next();
            if (aux.getId() == idPessoa) {
                return aux;
            }
        }
        return null;
    }
    
    /**
     * Este metodo vai possibilitar a adição de Hospede
     */
    @Override
    public void inserirHospede() throws EmptyExcpetion {


        this.listaDePessoas.addToRear(new Pessoa(Tipo.HOSPEDE));
        System.out.println("Hospede Criado com sucesso!! \n");
        System.out.println("Id do Hospede -> " + this.listaDePessoas.last().toString());
    }

    /**
     * Este metodo vai possibilitar a adição de Funcionario
     */
    @Override
    public void inserirFuncionario() throws EmptyExcpetion {

        this.listaDePessoas.addToRear(new Pessoa(Tipo.FUNCIONARIO));
        System.out.println("Funcionario Criado com sucesso!! \n");
        System.out.println("Id do Funcionario -> " + listaDePessoas.last().toString());
    }
    
    /**
     * Este método vai imprimir todas as Pessoas inseridos no Hotel
     */
    @Override
    public void imprimePessoas(){
        Iterator<Pessoa> itr = this.listaDePessoas.iterator();
        System.out.println("Lista de Pessoas: ");
        while(itr.hasNext()){
                System.out.println("-> " + itr.next().toString() + "\n");
        }
        
    }
    /**
     * Este método vai adicionar uma pessoa na Divisao para qual ela se moveu
     *
     * @param Divisao a divisao onde ela vai
     * @param pessoa a pessoa que se moveu para lá
     */
    @Override
    public void addPessoaEmDivisao(Divisao divisao, Pessoa pessoa) {
        divisao.getListaDePessoas().addToRear(pessoa);
    }

    /**
     * Este método vai remover uma pessoa quando ele sair da divisão.
     *
     * @param Divisao Divisão
     * @param pessoa pessoa
     */
    @Override
    public void removePessoaEmDivisao(Divisao divisao, Pessoa pessoa) throws EmptyExcpetion {
      
        divisao.getListaDePessoas().remove(pessoa);
       
    }
    
     /**
     * Este método vai remover uma pessoa quando ele sair do Hotel.
     *
     * @param pessoa pessoa
     */
    public void removePessoaHotel(Pessoa pessoa) throws EmptyExcpetion {
      
        this.listaDePessoas.remove(pessoa);
       
    }

    /**
     * Este metodo vai procurar o objeto divisao pelo nome
     *
     * @param divisao string da divisao que se pretende procurar
     * @return a Divisao caso seja encontrado, se não for encontrada retorna
     * null
     */
    @Override
    public Divisao findDivision(String divisao) {
        Iterator itr = this.divisoesHotel.getTodasDivisoes().iterator();
        Divisao aux = null;
        while (itr.hasNext()) {
            aux = (Divisao) itr.next();
            if (aux!= null && aux.getNome().equals(divisao)) {
                return aux;
            }
        }
        
        return null;
    }

   

     /**
     * verificar se a pessoa existe 
     * @param id id que se pretende procurar
     * @return retorna a pessoa caso ela seja encontrada
     */
    public Pessoa encontraPessoa(String id){
        int idAux = Integer.parseInt(id);  
         for (Pessoa pessoa: listaDePessoas){
             if(idAux == pessoa.getId()){
                 return pessoa;
             }
         }
        return null;
    }
    
    /**
     * Este método vai possibilitar mostrar as divisões adjacentes a determinada Divisão
     *
     * @param divisao a divisão na qual queremos obter as divisões adjacentes
     */
    @Override
    public int imprimeDivisoesAdjacentes(Divisao divisao) {
        Iterator<Divisao> itr_divisoes
                = this.divisoesHotel.getVerticesAdjacentes(divisao);
        int contador = 0;
        System.out.println("Divisoes Adjacentes: " + divisao.getNome() + "\n");
        while (itr_divisoes.hasNext()) {
            contador++;
            System.out.println(  contador + "-> " + itr_divisoes.next().getNome() + "\n");
        }
        System.out.println("0 -> Sair\n");
        return contador;
    }
    
    public Divisao getEntrada(){
        return findDivision("Hall de entrada");
    }
    
    @Override
    public Divisao getSalaQuarentena(){
        Iterator itr = this.divisoesHotel.getTodasDivisoes().iterator();

        while(itr.hasNext()){
            Divisao divisao = (Divisao) itr.next();
            if(divisao.getTipoSala()==TipoSala.QUARENTENA){
                return divisao;
            }
        }
        return null;
    }
    
    /**
     * Método que vai procurar a pessoa no hotel 
     * @param pessoa pessoa que pretende procurar
     * @return divisao que a pessoa se encontra 
     */
    @Override
   public Divisao encontraPessoaDivisao(Pessoa pessoa){
        Iterator<Divisao> itr = this.divisoesHotel.getTodasDivisoes().iterator();
        Divisao divisaoAux;
        while(itr.hasNext()){
            divisaoAux=itr.next();
             if( divisaoAux.findPessoaInDivision(pessoa)!= null){
                return divisaoAux;
            }
        }
        return null;
    } 
    
    
   

}
