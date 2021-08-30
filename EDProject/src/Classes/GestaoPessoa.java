/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;


import Colecoes.*;
import Enumerações.Tipo;
import Excepcoes.EmptyExcpetion;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import org.json.simple.parser.ParseException;

/**
 * Esta classe vai permitir fazer a gestão de utilizadores 
 *
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class GestaoPessoa {
    
    private UnorderedArrayList<Pessoa> listaDePessoas;

    public GestaoPessoa(String pathFile) {
        this.listaDePessoas = new UnorderedArrayList<Pessoa>();
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
    /*public UnorderedDoubleLinkedList<Integer> listagemDePessoas() throws IOException, FileNotFoundException, ParseException, java.text.ParseException, EmptyExcpetion{
        
        
        UnorderedDoubleLinkedList<Integer> lista = new UnorderedDoubleLinkedList<Integer>();
        //JSONMovimentos jsonMovimentos = new JSONMovimentos("../movimentos.json");
        
        DoubleLinkedOrderedList<Movimentos> allMovimentos = jsonMovimentos.readFileMovimentos();
        
        
        Iterator itr = allMovimentos.iterator();
        
        while(itr.hasNext()){
            
            Movimentos move = (Movimentos) itr.next();
            
           if(!lista.contains(move.getIdPessoa())){
               lista.addToRear(move.getIdPessoa());
           }
                   
        }
        
        return lista;
    }*/

    }
