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
import java.util.Scanner;

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
    

    public GestaoHotel(){
        this.versao=0;
        this.nomeHotel="";
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

    public void setMovimentosPessoas(DoubleLinkedOrderedList<Movimentos> movimentosPessoas) {
        this.movimentosPessoas = movimentosPessoas;
    }

    public void setDivisoes(GraphWeight<Divisao> divisoes) {
        this.divisoes = divisoes;
    }
    
    
    
}
