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
    private Date dataHoraAtual;
    private String dataHora;

    public Movimentos(int idPessoa, String nomeDivisao) {
        this.idPessoa = idPessoa;
        this.nomeDivisao = nomeDivisao;
        this.dataFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.dataHoraAtual = new Date();
        this.dataHora = this.dataFormater.format(this.dataHoraAtual);
    }

    public Movimentos(int idPessoa, String nomeDivisao, Date dataHoraAtual) {
        this.idPessoa = idPessoa;
        this.nomeDivisao = nomeDivisao;
        this.dataHoraAtual = dataHoraAtual;
    }
    
    public int getIdPessoa() {
        return idPessoa;
    }

    public String getNomeDivisao() {
        return nomeDivisao;
    }

    public String getDataHora() {
        return dataHora;
    }

    public Date getDataHoraAtual(){
        return dataHoraAtual;
    }
    

    @Override
    public int compareTo(Object obj) {
        Movimentos movimentos = (Movimentos) obj;

        if (getDataHoraAtual().after(movimentos.getDataHoraAtual()) ) {
            return 1;
        } else {
            if (getDataHoraAtual().before(movimentos.getDataHoraAtual())) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public String toString() {
        return "idPessoa:" + this.idPessoa + "\n" + "Divisão:" + this.nomeDivisao + "\n" +"DataHora:" + this.dataHoraAtual + "\n";
    }
    
    
}
