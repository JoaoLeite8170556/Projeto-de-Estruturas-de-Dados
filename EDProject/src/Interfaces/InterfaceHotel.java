/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Classes.Divisao;
import Classes.Pessoa;
import Excepcoes.EmptyExcpetion;

/**
 *
 * @author João
 */
public interface InterfaceHotel {
    
     public void inserirHospede() throws EmptyExcpetion;

    public void inserirFuncionario() throws EmptyExcpetion;

    public void imprimePessoas();

    public int imprimeDivisoesAdjacentes(Divisao divisao);

    public Divisao encontraPessoaDivisao(Pessoa pessoa);

    public Divisao findDivision(String divisao);

    public void addPessoaEmDivisao(Divisao divisao, Pessoa pessoa);

    public void removePessoaEmDivisao(Divisao divisao, Pessoa pessoa) throws EmptyExcpetion;
    
    public Pessoa encontraPessoa(String id);
    
    public Divisao getSalaQuarentena();
}
