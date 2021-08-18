/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;


import java.util.Date;

/**
 * Classe para fazermos a gestão dos movimentos das pessoas no Hotel.
 * 
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class Movimentos {
    private int idPessoa;
    private String nomeDivisao;
    private Date dataHora;

    public Movimentos(int idPessoa, String nomeDivisao, Date dataHora) {
        this.idPessoa = idPessoa;
        this.nomeDivisao = nomeDivisao;
        this.dataHora = dataHora;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public String getNomeDivisao() {
        return nomeDivisao;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public int compareTo(Movimentos movimentos) {
        if (getDataHora().before(movimentos.getDataHora()) && getIdPessoa()>movimentos.getIdPessoa()){
            return 1;
        } else {
            if (getDataHora().after(movimentos.getDataHora()) && getIdPessoa() < movimentos.getIdPessoa()){
                return -1;
            } else {
                return 0;
            }
        }

    }
    
}
