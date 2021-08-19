
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
    private boolean salaQuarentena;
    private boolean reservado;
    private int capacidadeMaxima;
    private UnorderedDoubleLinkedList<Pessoa> listaDePessoas;

    public Divisao(String nome, int capacidadeMaxima, boolean reservado) {
        this.nome = nome;
        this.reservado = reservado;
        this.capacidadeMaxima = capacidadeMaxima;
        
    }

    public Divisao(String nome, boolean salaQuarentena, int capacidadeMaxima) {
        this.nome = nome;
        this.salaQuarentena = salaQuarentena;
        this.capacidadeMaxima = capacidadeMaxima;
        this.listaDePessoas = new UnorderedDoubleLinkedList<Pessoa>();
    }

    public Divisao(String nome, int capacidadeMaxima) {
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.listaDePessoas = new UnorderedDoubleLinkedList<Pessoa>();
    }
    
    
    
    public String getNome() {
        return nome;
    }

    public boolean getSalaQuarentena() {
        return salaQuarentena;
    }

    public boolean getReservado() {
        return reservado;
    }

    public void setSalaQuarentena(boolean salaQuarentena) {
        this.salaQuarentena = salaQuarentena;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }
    
    
    
}
