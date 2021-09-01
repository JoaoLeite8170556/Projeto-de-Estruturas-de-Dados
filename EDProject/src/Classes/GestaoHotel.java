/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.DoubleLinkedOrderedList;
import Colecoes.UnorderedDoubleLinkedList;
import Enumerações.Tipo;
import Enumerações.TipoSala;
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
            if (pessoaDesconhecida(escolha) != -1) {
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
            if (escolha.equals("1")) {
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

    public void modoManual() throws EmptyExcpetion, ElementNonComparable {

        Scanner scanner = new Scanner(System.in);
        String escolha = "vazia";
        Pessoa pessoaAux = escolhePessoa();

        Divisao divisaoPessoa = hotel.encontraPessoaDivisao(pessoaAux);

        if (divisaoPessoa == null) {
            while (!escolha.equals("valido")) {

                System.out.println("Pretende entrar no " + hotel.getEntrada().getNome()
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

                            if (conecao.getNumeroPessoas() == conecao.getCapacidadeMaxima()) {
                                System.out.println("ALERTA !!!!!" + "\n" + "Atingiu a Capacidade máxima da Divisão!!!!");
                            } else if (conecao.getTipoSala() == TipoSala.RESERVADO && pessoaAux.getTipo() == Tipo.HOSPEDE) {
                                System.out.println("ALERTA !!!!!" + "\n" + "Entrou numa Divisão para Funcionários!!!!");
                                confirmacaoMovimento(pessoaAux, divisaoPessoa, conecao);
                                escolha = "valido";
                            } else if (conecao.getTipoSala() == TipoSala.QUARENTENA) {
                                System.out.println("ALERTA !!!!!" + "\n" + "Entrou numa Divisão de Quarentena!!!!");
                                confirmacaoMovimento(pessoaAux, divisaoPessoa, conecao);
                                escolha = "valido";
                            } else {
                                movePessoa(pessoaAux, divisaoPessoa, conecao);
                                escolha = "valido";
                            }
                        }
                        countAux2++;
                    }

                }
            }

        }
    }

    private void confirmacaoMovimento(Pessoa pessoa, Divisao divisaoInicial, Divisao divisaoDestino)
            throws ElementNonComparable, EmptyExcpetion {
        Scanner scanner = new Scanner(System.in);
        String escolha = "vazia";
        while (!escolha.equals("valido")) {

            System.out.println("Pretende entrar na mesma?!\n");
            System.out.println("1->Sim\n");
            System.out.println("2->Não\n");
            escolha = scanner.nextLine();
            if (escolha.equals("1")) {
                movePessoa(pessoa, divisaoInicial, divisaoDestino);

                escolha = "valido";
            } else if (escolha.equals("2")) {
                System.out.println("Passou a sua vez....");
                escolha = "valido";
            } else {
                System.out.println("!!Escolha invalida!! \n");
            }
        }
    }

    private void movePessoa(Pessoa pessoa, Divisao divisaoInicial, Divisao divisaoDestino)
            throws ElementNonComparable, EmptyExcpetion {
        this.hotel.addPessoaEmDivisao(divisaoDestino, pessoa);
        this.hotel.removePessoaEmDivisao(divisaoInicial, pessoa);
        adicionaMovimento(divisaoDestino, pessoa);
        this.hotel.atualizaPesos(divisaoInicial, divisaoDestino);
    }

    public Hotel getHotel() {
        return hotel;
    }

    private void adicionaMovimento(Divisao divisao, Pessoa pessoa)
            throws ElementNonComparable {
        this.hotel.getMovimentosHotel().add(new Movimentos(pessoa.getId(),
                divisao.getNome()));
    }

    public void imprimeDivisaoAdjacentes(String divisao) {

        Divisao divisaoAux = this.hotel.findDivision(divisao);

        int itr = this.hotel.imprimeDivisoesAdjacentes(divisaoAux);

        System.out.println(itr);
    }
    
    public void caminhoMaisCurtoSalaQuarentena() 
            throws EmptyExcpetion{
        Pessoa pessoa = escolhePessoa();
        Divisao divisaoAtual = this.hotel.encontraPessoaDivisao(pessoa); 
        Divisao divisaoQuarentena = this.hotel.getSalaQuarentena();
        Iterator itr = hotel.getDivisoes().iteratorShortestPath(divisaoAtual, divisaoQuarentena);
        
        Divisao divAux = null;
        if(pessoa.getTipo() == Tipo.FUNCIONARIO){
            itr.next();
            System.out.println("Deve seguir a seguinte sequencia: \n");
            while(itr.hasNext()){
                divAux= (Divisao) itr.next();
                System.out.println("->" + divAux.getNome() + "\n");
            }
        }else{
            System.out.println("Deve seguir a seguinte sequencia: \n");
            
            caminhoMaisCurtoSalaQuarentenaHospede(itr, divisaoQuarentena);
        }
    }
    
    private void caminhoMaisCurtoSalaQuarentenaHospede(Iterator itr, Divisao divisaoQuarentena) {
        
        Divisao divAtual = (Divisao) itr.next();
        Divisao divAux = (Divisao) itr.next();
        if (itr.hasNext()==true) {
            if (divAux.getTipoSala() == TipoSala.RESERVADO) {
                Iterator itrAdjacente = this.hotel.getDivisoes().getVerticesAdjacentes(divAtual);
                caminhoMaisCurtoSalaQuarentenaHospede(
                        hotel.getDivisoes().iteratorShortestPath(divisaoMenosPessoas(itrAdjacente), divisaoQuarentena), 
                        divisaoQuarentena);
            } else {
                
                System.out.println("->" + divAtual.getNome() + "\n");
                
                caminhoMaisCurtoSalaQuarentenaHospede(
                        hotel.getDivisoes().iteratorShortestPath(divAtual, divisaoQuarentena), 
                        divisaoQuarentena);
            }
        }else{
                 System.out.println("->" + divAux.getNome() + "\n");
            }

    }
    
    private Divisao divisaoMenosPessoas(Iterator itr){
        Divisao divAux = null;
        Divisao divisaoMenosPessoas = null;
        while(itr.hasNext()){
            divAux = (Divisao)itr.next();
            if(divisaoMenosPessoas==null){
                divisaoMenosPessoas=divAux;
            }else{
                if(divisaoMenosPessoas.getListaDePessoas().size()>divAux.getListaDePessoas().size()
                        && divAux.getTipoSala()!= TipoSala.RESERVADO){
                    divisaoMenosPessoas=divAux;
                }
            }
            
        }
        System.out.println("->" + divisaoMenosPessoas.getNome() + "\n");
        return divisaoMenosPessoas;
    }

    

}
