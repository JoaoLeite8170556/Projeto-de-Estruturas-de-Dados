package Classes;

import Colecoes.UnorderedDoubleLinkedList;
import Enumerações.TipoSala;
import java.util.Iterator;
import java.util.Objects;

/**
 * Classe para fazer gestão da Divisão
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class Divisao {
    private String nome;
    private TipoSala tipoSala;
    
    /**
     * Lotação Máxima de cada Divisão
     */
    private int capacidadeMaxima;
    private UnorderedDoubleLinkedList<Pessoa> listaDePessoas;

    public Divisao(String nome,TipoSala tipoSala) {
        this.nome = nome;
        this.tipoSala = tipoSala;
        this.capacidadeMaxima = 0;
        this.listaDePessoas = new UnorderedDoubleLinkedList<Pessoa>();
        
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }
    
    
    public Divisao(String nome) {
        this.nome = nome;
        this.capacidadeMaxima = 0;
        this.listaDePessoas = new UnorderedDoubleLinkedList<Pessoa>();
    }

    public Divisao() {}
    
    
    
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
    public Pessoa findPessoaInDivision(Pessoa pessoa){
        
        Iterator itr = this.listaDePessoas.iterator();
        
        while(itr.hasNext()){
            Pessoa auxPessoa = (Pessoa) itr.next();
            if(auxPessoa != null && auxPessoa.equals(pessoa)){
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

    public TipoSala getTipoSala() {
        return tipoSala;
    }

    public void setTipoSala(TipoSala tipoSala) {
        this.tipoSala = tipoSala;
    }

    public int getNumeroPessoas(){
        return this.listaDePessoas.size();
    }

    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    @Override
    public String toString() {
        
        String msm = "";

        if(getTipoSala()==this.tipoSala.QUARENTENA || getTipoSala()==this.tipoSala.RESERVADO){
            msm = "Divisão: " + this.nome + "\t" +"Lotação Máxima: " + this.capacidadeMaxima + "\n"+
                        "Lotação Atual: "+this.listaDePessoas.size()+"\n"+
                "Tipo de Divisão: "+ this.tipoSala+"\n";
        }else{
             msm = "Divisão: " + this.nome + "\t" +"Lotação Máxima: " + this.capacidadeMaxima + "\n"+
                        "Lotação Atual: "+this.listaDePessoas.size()+"\n";
        }
        return msm;
    }
    
    
   
    
    
}