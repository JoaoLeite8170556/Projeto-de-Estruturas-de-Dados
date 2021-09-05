/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.DoubleLinkedOrderedList;
import Colecoes.UnorderedArrayList;
import Colecoes.UnorderedDoubleLinkedList;
import Enumerações.Tipo;
import Enumerações.TipoSala;
import Excepcoes.ElementNonComparable;
import Excepcoes.EmptyExcpetion;
import Interfaces.InterfaceGestaoHotel;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Esta classe Gestão do Hotel
 *
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class GestaoHotel implements InterfaceGestaoHotel {

    private Hotel hotel;
    private int aux;

    public GestaoHotel(Hotel hotel) {
        this.hotel = hotel;

    }

    /**
     * Metodo para escolher pessoa 
     * @return return da pessoa escolhida 
     * @throws EmptyExcpetion 
     */
    @Override
    public Pessoa escolhePessoa() throws EmptyExcpetion {

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

    /**
     * Metodo que insere pessoa caso ela ainda nao esteja criada
     * @param escolha
     * @return id a pessoa 
     * @throws EmptyExcpetion 
     */
    
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
                System.out.println("Nao se registou não vai ser premitido entrar no hotel!! \n");
                escolha = "valido";
            } else {
                System.out.println("!!Escolha invalida!! \n");
            }
        }
        return -1;
    }

    /**
     * Este metodo vai permitir mover as poessoas dentro do hotel 
     * @throws EmptyExcpetion
     * @throws ElementNonComparable 
     */
    @Override
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
                if (!escolha.matches("[0-" + contAux + "]")) {
                    System.out.println("Invalid option");
                } else if (escolha.equals("0")) {
                    System.out.println("Passou a sua vez....");
                    escolha = "valido";
                } else {
                    Iterator itr
                            = this.hotel.getDivisoes().getVerticesAdjacentes(divisaoPessoa);
                    int countAux2 = 1;
                    while (itr.hasNext()) {
                        Divisao conecao = (Divisao) itr.next();

                        if (countAux2 == auxEscolha) {

                            escolha = validacoes(pessoaAux, conecao, escolha);
                        }
                        countAux2++;
                    }

                }
            }

        }
    }

    /**
     * Este metodo vai fazer as validaçoes cada vez que a pessoa enbtras numa divisao 
     * @param pessoa pessoa que se esta a mvover 
     * @param divisao divisao que a pessoa esta 
     * @param escolha divisao que pretende ir 
     * @return valido se a escolha for valida 
     * @throws ElementNonComparable
     * @throws EmptyExcpetion 
     */
    private String validacoes(Pessoa pessoa, Divisao divisao, String escolha) 
            throws ElementNonComparable, EmptyExcpetion {
        Divisao divisaoPessoa = hotel.encontraPessoaDivisao(pessoa);
        boolean querSair = false;
        if (divisao.getNome().equals(hotel.getEntrada().getNome())) {
            querSair = desejaSair(escolha, pessoa);
        }
        int lugaresVagos = divisao.getCapacidadeMaxima() - divisao.getNumeroPessoas();

        if (querSair != true) {
            if (lugaresVagos == 0) {
                System.out.println("ALERTA !!!!!" + "\n" + "Capacidade máxima "
                        + "da Divisão já atingida!!!!");
            } else if (divisao.getTipoSala() == TipoSala.RESERVADO && pessoa.getTipo() == Tipo.HOSPEDE) {
                verificaPertoLotacaoMax(lugaresVagos);
                System.out.println("ALERTA !!!!!" + "\n" + "Entrou numa Divisão para Funcionários!!!!");
                confirmacaoMovimento(pessoa, divisaoPessoa, divisao);
                escolha = "valido";
            } else if (divisao.getTipoSala() == TipoSala.QUARENTENA) {
                verificaPertoLotacaoMax(lugaresVagos);
                System.out.println("ALERTA !!!!!" + "\n" + "Entrou numa Divisão de Quarentena!!!!");
                confirmacaoMovimento(pessoa, divisaoPessoa, divisao);
                escolha = "valido";
            } else {
                verificaPertoLotacaoMax(lugaresVagos);
                movePessoa(pessoa, divisaoPessoa, divisao);
                escolha = "valido";
            }
        } else {
            escolha = "valido";
        }

        return escolha;
    }

    /**
     * Metodo verifica se divisao esta perto da lotação maxima 
     * @param lugaresVagos numero de lugares vagos na divisão 
     * @throws EmptyExcpetion 
     */
    private void verificaPertoLotacaoMax(Integer lugaresVagos) throws EmptyExcpetion {
        UnorderedArrayList<Integer> arrayList = new UnorderedArrayList<>();
        arrayList.addToRear(2);
        arrayList.addToRear(3);

        if (lugaresVagos == 1) {
            System.out.println("ALERTA !!!!!" + "\n" + "Atingiu a Capacidade "
                    + "máxima da Divisão!!!! \n");
        } else if (arrayList.contains(lugaresVagos)) {
            System.out.println("ALERTA !!!!!" + "\n" + "A divisao esta próxima "
                    + "da lotação máxima!!\n");
        }
    }

    /**
     * Metodo vai perguntar a pessoa se deseja sair sempre que a mesma passa volta 
     * a entrada
     * @param escolha variavel aux para o ciclo
     * @param pessoa pessoa que se esta a mover 
     * @return true se quer sair e false se nao pretende sair 
     * @throws EmptyExcpetion
     * @throws ElementNonComparable 
     */
    private boolean desejaSair(String escolha, Pessoa pessoa) throws EmptyExcpetion,
            ElementNonComparable {
        Scanner scanner = new Scanner(System.in);

        while (!escolha.equals("valido")) {

            System.out.println("Pretende Sair do Hotel \n");
            System.out.println("1->Sim\n");
            System.out.println("2->Não\n");
            escolha = scanner.nextLine();
            if (escolha.equals("1")) {
                System.out.println("Muito obrigado pela sua estadia, volte sempre!!\n");
                this.hotel.addPessoaEmDivisao(hotel.getEntrada(), pessoa);
                this.hotel.atualizaPesos(hotel.encontraPessoaDivisao(pessoa),
                        hotel.getEntrada());
                adicionaMovimento(hotel.getEntrada(), pessoa);
                this.hotel.removePessoaEmDivisao(hotel.getEntrada(), pessoa);
                this.hotel.atualizaPesosSaida(hotel.getEntrada());
                this.hotel.removePessoaHotel(pessoa);
                return true;
            } else if (escolha.equals("2")) {
                System.out.println("Bom proveito da sua estadia!!\n");
                return false;
            } else {
                System.out.println("!!Escolha invalida!! \n");
            }
        }
        return false;

    }
    
    /**
     * Método que confirma a movimentação da pessoa 
     * @param pessoa pessoa que se pretende mover 
     * @param divisaoInicial divisao que esta 
     * @param divisaoDestino divisão que pretende ir 
     * @throws ElementNonComparable
     * @throws EmptyExcpetion 
     */

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

    /**
     * Metodo que vai mover a pessoa 
     * @param pessoa pessoa que se pretende mover 
     * @param divisaoInicial divisao que esta 
     * @param divisaoDestino divisão que pretende ir 
     * @throws ElementNonComparable
     * @throws EmptyExcpetion 
     */
    private void movePessoa(Pessoa pessoa, Divisao divisaoInicial, Divisao divisaoDestino)
            throws ElementNonComparable, EmptyExcpetion {
        this.hotel.addPessoaEmDivisao(divisaoDestino, pessoa);
        this.hotel.removePessoaEmDivisao(divisaoInicial, pessoa);
        adicionaMovimento(divisaoDestino, pessoa);
        this.hotel.atualizaPesos(divisaoInicial, divisaoDestino);
    }

    /**
     * Metodo que adiciona movimento ao hotel
     * @param divisao divisao do movimento 
     * @param pessoa pessoa que se esta a mover 
     * @throws ElementNonComparable 
     */
    private void adicionaMovimento(Divisao divisao, Pessoa pessoa)
            throws ElementNonComparable {
        this.hotel.getMovimentosHotel().add(new Movimentos(pessoa.getId(),
                divisao.getNome()));
    }
    
    /**
     * imprime divisoes adjacentes 
     * @param divisao divisao que se pretende obter divisões adjacentes 
     */
    public void imprimeDivisaoAdjacentes(String divisao) {

        Divisao divisaoAux = this.hotel.findDivision(divisao);

        int itr = this.hotel.imprimeDivisoesAdjacentes(divisaoAux);

        System.out.println(itr);
    }

    /**
     * Metodo vai apresentar o caminho mais curto para a sala de quarentena, 
     * passando pelo menor numero de pessoas 
     * @throws EmptyExcpetion
     */
    @Override
    public void caminhoMaisCurtoSalaQuarentena()
            throws EmptyExcpetion {
        Pessoa pessoa = escolhePessoa();
        if (this.hotel.encontraPessoaDivisao(pessoa) != null) {
            Divisao divisaoAtual = this.hotel.encontraPessoaDivisao(pessoa);
            Divisao divisaoQuarentena = this.hotel.getSalaQuarentena();
            Iterator itr = hotel.getDivisoes().iteratorShortestPath(divisaoAtual, divisaoQuarentena);

            Divisao divAux = null;
            if (pessoa.getTipo() == Tipo.FUNCIONARIO) {
                itr.next();
                System.out.println("Deve seguir a seguinte sequencia: \n");
                while (itr.hasNext()) {
                    divAux = (Divisao) itr.next();
                    System.out.println("->" + divAux.getNome() + "\n");
                }
            } else {
                System.out.println("Deve seguir a seguinte sequencia: \n");

                caminhoMaisCurtoSalaQuarentenaHospede(itr, divisaoQuarentena);
            }
        } else {
            System.out.println("A pessoa não está em nenhuma divisao!!");
        }
    }

    /**
     * Metodo vai escrever o caminho mais curto para a sala de quarentena 
     * @param itr itr da lista de divisoes
     * @param divisaoQuarentena divisao do tipo quarentena 
     */
    private void caminhoMaisCurtoSalaQuarentenaHospede(Iterator itr, Divisao divisaoQuarentena) {

        Divisao divAtual = (Divisao) itr.next();
        Divisao divAux = (Divisao) itr.next();
        if (itr.hasNext() == true) {
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
        } else {
            System.out.println("->" + divAux.getNome() + "\n");
        }

    }

    /**
     * Metodo procura a divisao com menos pessoas 
     * @param itr itr de divisoes   
     * @return divisao com menos pessoas
     */
    private Divisao divisaoMenosPessoas(Iterator itr) {
        Divisao divAux = null;
        Divisao divisaoMenosPessoas = null;
        while (itr.hasNext()) {
            divAux = (Divisao) itr.next();
            if (divisaoMenosPessoas == null) {
                divisaoMenosPessoas = divAux;
            } else {
                if (divisaoMenosPessoas.getListaDePessoas().size() > divAux.getListaDePessoas().size()
                        && divAux.getTipoSala() != TipoSala.RESERVADO) {
                    divisaoMenosPessoas = divAux;
                }
            }

        }
        System.out.println("->" + divisaoMenosPessoas.getNome() + "\n");
        return divisaoMenosPessoas;
    }

    /**
     * Metodo que vai possibilitar retornar a localização atual de uma pessoa
     */
    public void escolhePessoaParaEncontrar() {
        if (this.hotel.getListaDePessoas().isEmpty()) {
            System.out.println("Ainda nao tem pessoas associadas ao hotel...\n");
        } else {
            System.out.println("Escolha qual a Pessoa que pretende obter localização atual!!!!\n");

            for (Pessoa pessoa : this.hotel.getListaDePessoas()) {
                System.out.println("-> ");
                System.out.println(pessoa.getId());
                System.out.println("\n");
            }

            System.out.println("Escolha a opção: " + "\n");

            Scanner scanner = new Scanner(System.in);

            String escolha = scanner.nextLine();

            Pessoa pessoaEscolhida = this.hotel.encontraPessoa(escolha);

            Divisao div = this.hotel.encontraPessoaDivisao(pessoaEscolhida);

            System.out.println("A divisão atual da pessoa: " + pessoaEscolhida.getId() + "\n"
                    + "é " + div.getNome());
        }
    }


    /**
     * Método que permite obter todos os movimentos realizados em determinado
     * periodo de tempo
     *
     * @param date a diferença no horario
     * @return
     * @throws ElementNonComparable
     */
    private DoubleLinkedOrderedList<Movimentos> movimentosNoIntervalo(int date, Iterator itr) throws ElementNonComparable {

        DoubleLinkedOrderedList<Movimentos> listaDeMovimentos = new DoubleLinkedOrderedList<Movimentos>();

        Calendar c = Calendar.getInstance();

        c.add(Calendar.HOUR, -date);

        Date updateDate = c.getTime();
        Movimentos movimentos = null;
        while (itr.hasNext()) {
            movimentos = (Movimentos) itr.next();
            if (movimentos.getDataHoraAtual().compareTo(updateDate) == 1) {
                listaDeMovimentos.add(movimentos);
            }
        }
        return listaDeMovimentos;
    }

    /**
     * Metodo que vai possibilitar todos os movimentos realizado por determinada
     * pessoa.
     *
     * @return
     * @throws EmptyExcpetion
     * @throws ElementNonComparable
     */
    private DoubleLinkedOrderedList<Movimentos> movimentosPessoaDiferente(Pessoa pessoa) throws EmptyExcpetion, ElementNonComparable {

        DoubleLinkedOrderedList<Movimentos> listaDeMovimentos = new DoubleLinkedOrderedList<>();

        Iterator itr = movimentosNoIntervalo(this.aux, this.hotel.getMovimentosHotel().iterator()).iterator();

        while (itr.hasNext()) {
            Movimentos move = (Movimentos) itr.next();
            if (move.getIdPessoa() != pessoa.getId()) {
                listaDeMovimentos.add(move);
            }
        }
        return listaDeMovimentos;
    }

    /**
     * Metodo que vai possibilitar todos os movimentos realizado por determinada
     * pessoa.
     *
     * @return
     * @throws EmptyExcpetion
     * @throws ElementNonComparable
     */
    private DoubleLinkedOrderedList<Movimentos> movimentosPessoa(Pessoa pessoa) throws EmptyExcpetion, ElementNonComparable {

        DoubleLinkedOrderedList<Movimentos> listaDeMovimentos = new DoubleLinkedOrderedList<>();

        Iterator itr = movimentosNoIntervalo(this.aux, this.hotel.getMovimentosHotel().iterator()).iterator();

        while (itr.hasNext()) {
            Movimentos move = (Movimentos) itr.next();
            if (move.getIdPessoa() == pessoa.getId()) {
                listaDeMovimentos.add(move);
            }
        }
        return listaDeMovimentos;
    }

    /**
     * Método que vai possibilitar retornar todas as divisoes
     *
     * @return
     * @throws EmptyExcpetion
     * @throws ElementNonComparable
     */
    private void divisoesPartilhadas() throws EmptyExcpetion, ElementNonComparable {
        if (this.hotel.getListaDePessoas().isEmpty() || this.hotel.getMovimentosHotel().isEmpty()) {
            System.out.println("Ainda não tem pessoas ou movimentos associadas ao hotel...");
        } else {
            Pessoa pessoa = escolhePessoa();

            Iterator itrMovPessoa = movimentosPessoa(pessoa).iterator();

            DoubleLinkedOrderedList<Movimentos> listaFinal = new DoubleLinkedOrderedList<>();

            Movimentos movPessoaInical = (Movimentos) itrMovPessoa.next();

            System.out.println("Esteve em contacto com :\n");

            while (itrMovPessoa.hasNext()) {
                Movimentos movPessoaFinal = (Movimentos) itrMovPessoa.next();
                Iterator itrMovPessoasDiferente = movimentosPessoaDiferente(pessoa).iterator();
                verificaSeTemMovimento(movPessoaInical, movPessoaFinal, itrMovPessoasDiferente);
                movPessoaInical = movPessoaFinal;
            }

        }
    }

    /**
     * Verifica se tem movimento para salas em comum e caso tenha escreve
     *
     * @param movimentoInicial Movimento que ele entrou na divisão
     * @param movimentoFinal Movimento que ele saiu da divisão
     * @param itr iterador com todos os movimentos das outras pessoas
     * @throws ElementNonComparable
     */
    private void verificaSeTemMovimento(Movimentos movimentoInicial, Movimentos movimentoFinal, Iterator itr) throws ElementNonComparable {
        Date dateInical = movimentoInicial.getDataHoraAtual();
        Date dateFinal = movimentoFinal.getDataHoraAtual();

        Movimentos moveAux = (Movimentos) itr.next();

        while (itr.hasNext()) {
            Movimentos moveItr2 = (Movimentos) itr.next();
            Date dateMov = moveItr2.getDataHoraAtual();

            if (!dateInical.after(moveAux.getDataHoraAtual())
                    && !dateFinal.before(moveAux.getDataHoraAtual())) {
                if (movimentoInicial.getNomeDivisao().equals(moveAux.getNomeDivisao())) {
                    System.out.println("Estva em contacto com a pessoa no:\n");
                    System.out.println("->" + moveAux.toString() + "\n");
                }
            } else if (!moveAux.getDataHoraAtual().after(dateInical)
                    && !dateMov.before(dateInical)) {
                if (moveAux.getNomeDivisao().equals(movimentoInicial.getNomeDivisao())) {
                    System.out.println("Estva em contacto com a pessoa no:\n");
                    System.out.println("->" + moveAux.toString() + "\n");
                }
            }
            moveAux = moveItr2;
        }
    }

    public void imprimeDivisoesDasPessoas() throws EmptyExcpetion, ElementNonComparable {
        this.aux = definaIntrevaloDeTempo();
        divisoesPartilhadas();
    }

    /**
     * Metodo que vai intoduzir o intervalo de tempo na qual queremos os
     * movimentos
     *
     * @return
     */
    private int definaIntrevaloDeTempo() {
        int tempoTemp = 0;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduza o Intrevalo de Tempo:");
        tempoTemp = scanner.nextInt();

        return tempoTemp;
    }
}
