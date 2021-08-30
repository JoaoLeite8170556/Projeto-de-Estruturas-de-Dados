/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.*;
import Enumerações.Tipo;
import Enumerações.TipoSala;
import Excepcoes.ElementNonComparable;
import Excepcoes.EmptyExcpetion;
import Grafo.GrafoHotel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Hotel {

    private final UnorderedDoubleLinkedList<Pessoa> listaDePessoas;
    private int versao;
    private String nomeHotel;
    private DoubleLinkedOrderedList<Movimentos> movimentosHotel;
    private GrafoHotel<Divisao> divisoesHotel;
    private String pathFile;
    //private JSONMovimentos movimentos;

    /**
     * Método construtor para criar uma GestaoHotel
     *
     * @param pathFile o caminho onde vão estar os ficheiros
     */
    public Hotel(String pathFile) {
        this.versao = 0;
        this.nomeHotel = null;
        this.listaDePessoas = new UnorderedDoubleLinkedList<Pessoa>();
        this.divisoesHotel = new GrafoHotel<Divisao>(getNumeroDeDivisoes(this.nomeHotel));
        this.movimentosHotel = new DoubleLinkedOrderedList<>();
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
    private void atualizaPesos(Divisao divisaoInicial, Divisao divisaoDestino) {

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
                        divisaoDestino,
                        this.divisoesHotel.getEdgeWeight(aux_divisaoInicial,
                                divisaoDestino) - 1);
            }
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
     * Este método vai adicionar uma pessoa na Divisao para qual ela se moveu
     *
     * @param Divisao a divisao onde ela vai
     * @param pessoa a pessoa que se moveu para lá
     */
    public void addPessoaEmDivisao(String Divisao, Pessoa pessoa) {
        Divisao auxDivisao = findDivision(Divisao);
        auxDivisao.getListaDePessoas().addToRear(pessoa);
    }

    /**
     * Este método vai remover uma pessoa quando ele sair da divisão.
     *
     * @param Divisao Divisão
     * @param pessoa pessoa
     */
    public void removePessoaEmDivisao(String Divisao, Pessoa pessoa) {
        Divisao auxDivisao = findDivision(Divisao);

        try {
            auxDivisao.getListaDePessoas().remove(pessoa);
        } catch (EmptyExcpetion ex) {
        }
    }

    /**
     * Este metodo vai procurar o objeto divisao pelo nome
     *
     * @param divisao string da divisao que se pretende procurar
     * @return a Divisao caso seja encontrado, se não for encontrada retorna
     * null
     */
    public Divisao findDivision(String divisao) {
        Iterator itr = this.divisoesHotel.getTodasDivisoes().iterator();

        while (itr.hasNext()) {
            Divisao aux = (Divisao) itr.next();
            if (aux.getNome().equals(divisao)) {
                return aux;
            }
        }
        return null;
    }

    /**
     * Este método vai verificar a lotação atual de uma determinada Divisao.
     *
     * @param divisao a divisão, na qual queremos aceder
     * @return true se o número de pessoas for menor que a lotação máxima
     * permitida
     */
    private boolean verificaLotacaoAtual(Divisao divisao) {
        return (divisao.getListaDePessoas().size() <= divisao.getNumeroPessoas());
    }

    /**
     * Este método vai permitir guardar pessoas nas divisão analisando certas
     * variaveis como tipo de sala que esta a entrar ou tipo de pessoa que é.
     *
     * @param pessoa pessoa que vai ser mover para a divisão.
     * @param divisao a divisão.
     */
    private void verificaTipoPessoa(Pessoa pessoa, Divisao divisao) {

        Iterator itr = divisao.getListaDePessoas().iterator();

        while (itr.hasNext()) {
            if (verificaSalaReservado(divisao.getNome()) && pessoa.getTipo().equals("FUNCIONARIO") && verificaLotacao(divisao) == 0) {
                divisao.getListaDePessoas().addToRear(pessoa);
            } else if (verificaSalaQuarentena(divisao.getNome()) && pessoa.getTipo().equals("HOSPEDE") && verificaLotacao(divisao) == 0) {
                divisao.getListaDePessoas().addToRear(pessoa);
            } else if (!verificaSalaReservado(divisao.getNome()) && !verificaSalaQuarentena(divisao.getNome()) && verificaLotacao(divisao) == 1) {
                divisao.getListaDePessoas().addToRear(pessoa);
            } else {
                System.out.println("IMPOSSÍVEL!!!! Não pode entrar!!!!");
            }
        }
    }

    /**
     * Este método vai possibilitar a lotação da divisão chegar ao limite
     * apresenta a seguinte mensagem
     *
     * @param divisao a divisão em causa.
     */
    private void alertaLotacao(Divisao divisao) {
        if (verificaLotacao(divisao) == 0) {
            System.out.println("Atingiu limite da Divisão, não pode entrar!!!!");
        }
    }

    /**
     * Verifica se a sala atual corresponde a sala de quarentena
     *
     * @param divisao na qual queremos pesquisar
     * @return true se a divisao for do tipo "Quarentena"
     */
    private boolean verificaSalaQuarentena(String divisao) {
        Divisao auxDivisao = findDivision(divisao);
        return auxDivisao.getTipoSala().equals("QUARENTENA");
    }

    /**
     * Verifica se a sala atual corresponde a sala reservada
     *
     * @param divisao a divisao queremos verificar
     * @return true se a divisão for do tipo "reservada"
     */
    private boolean verificaSalaReservado(String divisao) {
        Divisao auxDivisao = findDivision(divisao);

        return auxDivisao.getTipoSala().equals("RESERVADO");
    }

    /**
     * Metodo que verifica se a lotacao atual da divisao ja atingiu a lotacao
     * máxima
     *
     * @param divisao a divisao na qual vamos fazer a verificação
     * @return retorna 1 se a lotação estiver OK, 0 se atingir o máximo
     */
    private int verificaLotacao(Divisao divisao) {

        if (divisao.getListaDePessoas().size() <= divisao.getNumeroPessoas()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Este método vai possibilitar mostrar as divisões adjacentes
     *
     * @param divisao a divisão na qual queremos obter as divisões adjacentes
     */
    public void imprimeDivisoesAdjacentes(Divisao divisao) {
        Iterator itr_divisoes
                = this.divisoesHotel.getVerticesAdjacentes(divisao);
        int contador = 1;
        System.out.println("Divisoes Adjacentes: " + divisao.getNome() + "\n");
        while (itr_divisoes.hasNext()) {

            Divisao aux_divisao = (Divisao) itr_divisoes.next();

            System.out.println("->" + " " + contador++ + " -" + " " + aux_divisao.getNome() + "\n");
        }
    }

}
