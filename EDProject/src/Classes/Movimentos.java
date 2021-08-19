/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;



import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe para fazermos a gestão dos movimentos das pessoas no Hotel.
 * 
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class Movimentos implements Comparable{
    private int idPessoa;
    private String nomeDivisao;
    private SimpleDateFormat dataFormater;
    private Date dataHora;

    public Movimentos(int idPessoa, String nomeDivisao) {
        this.idPessoa = idPessoa;
        this.nomeDivisao = nomeDivisao;
        this.dataFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.dataHora = new Date();
        this.dataFormater.format(dataHora).toString();
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

    public SimpleDateFormat getDataFormater() {
        return dataFormater;
    }

    @Override
    public int compareTo(Object obj) {
        
        Movimentos movimentos = (Movimentos) obj;
        
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
