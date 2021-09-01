/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.DoubleLinkedOrderedList;
import Colecoes.UnorderedDoubleLinkedList;
import Excepcoes.ElementNonComparable;
import Excepcoes.EmptyExcpetion;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Esta classe Gestão do Hotel
 * 
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class GestaoHotel {

    private Hotel hotel;

    public GestaoHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    private Pessoa escolhePessoa() throws EmptyExcpetion {
        
        
        UnorderedDoubleLinkedList<Pessoa> listaTemp = hotel.getListaDePessoas();
        
        if (listaTemp.isEmpty()) {
            
            System.out.println("Ainda nao existe Pessoas cridas!! \n");
            System.out.println("Crie pessoas!! \n");
            //----------------------metodo menu para criar pessoas-------------//
            listaTemp = hotel.getListaDePessoas();
        }

        System.out.println("Escolha qual a Pessoa que pretende Mover");
        for (Pessoa pessoa : listaTemp) {
            System.out.println("-> ");
            System.out.println(pessoa.getId());
            System.out.println("\n");
        }

        System.out.println("Escolha a opção: " + "\n");

        Scanner scanner = new Scanner(System.in);

        String escolha = scanner.nextLine();

        Pessoa pessoaEscolhida = this.hotel.encontraPessoa(escolha);

        if (pessoaEscolhida != null) {
            return pessoaEscolhida;
        } else {
            if (pessoaDesconhecida(escolha)!=-1){
                return hotel.getListaDePessoas().last();
            }
            
        }
        return null;
    }

    private int pessoaDesconhecida(String escolha) throws EmptyExcpetion {
        
        Scanner scanner = new Scanner(System.in);
        
        while (!escolha.equals("valido")) {
            System.out.println("!!!!!Pessoa desconhecida pelo sistema!!!!! \n");
            System.out.println("Pretende criar registar-se ? \n");
            System.out.println("1->Sim \n");
            System.out.println("2->Não \n");
            escolha = scanner.nextLine();
            if (escolha.equals("1")){
                hotel.inserirHospede();
                System.out.println("Você está no sistema com o id: "
                        + hotel.getListaDePessoas().last().getId() + "\n");
                escolha = "valido";
                return hotel.getListaDePessoas().last().getId();
            } else if (escolha.equals("2")) {
                System.out.println("Nao se registou nao vai ser premitido entrar no hotel!! \n");
                escolha = "valido";
            } else {
                System.out.println("!!Escolha invalida!! \n");
            }
        }
        return -1;
    }

    public void modoManual() throws EmptyExcpetion, ElementNonComparable{

        Scanner scanner = new Scanner(System.in, "ISO-8859-1");
        String escolha = "vazia";
        DoubleLinkedOrderedList<Movimentos> listaMovimentos 
                = hotel.getMovimentosHotel();
        Pessoa pessoaAux = escolhePessoa();
        
        Divisao divisaoPessoa = hotel.encontraPessoaDivisao(pessoaAux);
        
        if (divisaoPessoa == null) {
            while (!escolha.equals("valido")) {

                    System.out.println("Pretende entrar no" + hotel.getEntrada().getNome()
                        + "\n");
                System.out.println("1->Sim\n");
                System.out.println("2->Não\n");
                escolha = scanner.nextLine();
                if (escolha.equals("1")) {
                    
                    this.hotel.addPessoaEmDivisao(hotel.getEntrada(), pessoaAux);
                    adicionaMovimento(hotel.getEntrada(), pessoaAux);
                    this.hotel.atualizaPesosEntrada(hotel.getEntrada());
                    escolha = "valido";
                } else if (escolha.equals("2")) {
                    System.out.println("Passou a sua vez....");
                    escolha = "valido";
                } else {
                    System.out.println("!!Escolha invalida!! \n");
                }
            }
            escolha = "vazia";
        } else {
            while (!escolha.equals("valido")) {

                System.out.println("Escolha a divisao para "
                        + "a qual se pretende mover:\n");
                int contAux = this.hotel.imprimeDivisoesAdjacentes(divisaoPessoa);
                escolha = scanner.nextLine();
                int auxEscolha = Integer.parseInt(escolha);  
                if (!escolha.matches("[1-" + contAux + "]")) {
                    System.out.println("Invalid option");
                } else {
                    Iterator itr
                            = this.hotel.getDivisoes().getVerticesAdjacentes(divisaoPessoa);
                    int countAux2 = 1;
                    while (itr.hasNext()) {
                        Divisao conecao = (Divisao) itr.next();
                        if (countAux2 == auxEscolha) {
                            hotel.addPessoaEmDivisao(conecao, pessoaAux);
                            hotel.removePessoaEmDivisao(divisaoPessoa, pessoaAux);
                            adicionaMovimento(divisaoPessoa, pessoaAux);
                            hotel.atualizaPesos(divisaoPessoa, conecao);
                            escolha = "valido";
                        }
                        countAux2++;
                    }

                }
            }

        }
    }
    
    public Hotel getHotel(){
        return hotel;
    }


    private void adicionaMovimento(Divisao divisao, Pessoa pessoa)
            throws ElementNonComparable {
        this.hotel.getMovimentosHotel().add(new Movimentos(pessoa.getId(),
                divisao.getNome()));
    }
    
    public void imprimeDivisaoAdjacentes(String divisao){
        
        Divisao divisaoAux = this.hotel.findDivision(divisao);
        
        int itr = this.hotel.imprimeDivisoesAdjacentes(divisaoAux);
        
        
        System.out.println(itr);
    }

}
