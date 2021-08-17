/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.Calendar;
import java.util.Date;

/**
 * Esta classe vai-nos permitir guardar os movimentos das pessoas dentro do Hotel
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class Sensor_Movimentos {
    private Pessoa idPessoa;
    private Divisao divisao;
    private Date data;
    private Calendar hora;

    public Sensor_Movimentos(Divisao nomeDivisao, Date data, Calendar hora) {
        this.divisao = nomeDivisao;
        this.data = data;
        this.hora = hora;
    }

    public Pessoa getIdPessoa() {
        return idPessoa;
    }

    public Divisao getNomeDivisao() {
        return divisao;
    }

    public Date getData() {
        return data;
    }

    public Calendar getHora() {
        return hora;
    }
    
    
    
    
}
