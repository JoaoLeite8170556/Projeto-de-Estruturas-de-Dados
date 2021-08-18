
package Classes;

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

    public Divisao(String nome, SalaQuarentena salaQuarentena, Reservado reservado) {
        this.nome = nome;
        this.salaQuarentena = salaQuarentena;
        this.reservado = reservado;
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
