
package Classes;

import Colecoes.UnorderedDoubleLinkedList;
import Enumerações.Reservado;
import Enumerações.SalaQuarentena;

/**
 * Classe para fazer gestão da Divisão.
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class Divisao {
    private String nome;
    private SalaQuarentena salaQuarentena;
    private Reservado reservado;
    private int capacidadeMaxima;
    private UnorderedDoubleLinkedList<Pessoa> listaDePessoas;

    public Divisao(String nome, SalaQuarentena salaQuarentena, Reservado reservado,int capacidadeMaxima) {
        this.nome = nome;
        this.salaQuarentena = salaQuarentena;
        this.reservado = reservado;
        this.capacidadeMaxima = capacidadeMaxima;
        this.listaDePessoas = new UnorderedDoubleLinkedList<Pessoa>();
    }
    
    public String getNome() {
        return nome;
    }

    public SalaQuarentena getSalaQuarentena() {
        return salaQuarentena;
    }

    public Reservado getReservado() {
        return reservado;
    }

    
    
}
