
package Classes;

import Colecoes.UnorderedDoubleLinkedList;
import java.util.Iterator;
import java.util.Objects;

/**
 * Classe para fazer gestão da Divisão
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class Divisao {
    private String nome;
    private boolean salaQuarentena;
    private boolean reservado;
    /**
     * Lotação Máxima de cada Divisão
     */
    private int capacidadeMaxima;
    private UnorderedDoubleLinkedList<Pessoa> listaDePessoas;

    public Divisao(String nome,boolean reservado) {
        this.nome = nome;
        this.reservado = reservado;
        this.capacidadeMaxima = 0;
        this.listaDePessoas = new UnorderedDoubleLinkedList<Pessoa>();
        
    }

    public Divisao(String nome) {
        this.nome = nome;
        this.capacidadeMaxima = 0;
        this.listaDePessoas = new UnorderedDoubleLinkedList<Pessoa>();
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
    
    
    /**
     * Metodo para encontrar determinada pessoa na Divisao
     * @param Pessoa A pessoa a procurar
     * @return returna a Pessoa se a encontrar 
     */
    public Pessoa findPessoaInDivision(Pessoa Pessoa){
        
        Iterator itr = this.listaDePessoas.iterator();
        
        while(itr.hasNext()){
            Pessoa auxPessoa = (Pessoa) itr.next();
            if(auxPessoa.equals(auxPessoa)){
                return auxPessoa;
            }
        }
        return null;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Divisao other = (Divisao) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }

    public UnorderedDoubleLinkedList<Pessoa> getListaDePessoas() {
        return listaDePessoas;
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
    
    public int getNumeroPessoas(){
        return this.listaDePessoas.size();
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }
    
    
   
    
    
}
