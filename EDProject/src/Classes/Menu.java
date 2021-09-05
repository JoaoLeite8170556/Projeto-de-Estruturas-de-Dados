/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.DoubleLinkedOrderedList;
import Colecoes.UnorderedArrayList;
import Colecoes.UnorderedDoubleLinkedList;
import Excepcoes.ElementNonComparable;
import Excepcoes.EmptyExcpetion;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Classe para tratarmos dos menus do Programa.
 *
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class Menu {

    private Hotel hotel;
    private GestaoHotel gestaoHotel;
    private Import importTemp;
    private Export export;
    private DoubleLinkedOrderedList<JSONMovimentos> listaJsonMovimentos;
    private int aux;

    public Menu() throws IOException {
        try {

            this.export = new Export();
            this.importTemp = new Import();

            this.hotel = new Hotel(this.escolheMapa());

            File file = new File("../movimentos.json");

            if (file.exists()) {
                this.listaJsonMovimentos = this.importTemp.readFileMovimentos();
            } else {
                this.listaJsonMovimentos = new DoubleLinkedOrderedList<JSONMovimentos>();
            }
            this.gestaoHotel = new GestaoHotel(this.hotel);

        } catch (FileNotFoundException | ParseException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ElementNonComparable ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            this.menuHotel();
        } catch (EmptyExcpetion | ElementNonComparable ex) {
        }
    }

    public UnorderedDoubleLinkedList<Divisao> retornaDivisoes() {
        return this.hotel.getDivisoes().getTodasDivisoes();
    }

    /**
     * Metodo que é o menu com as opções para usar no Programa
     *
     * @throws EmptyExcpetion
     * @throws ElementNonComparable
     */
    private void menuHotel() throws EmptyExcpetion, ElementNonComparable {
        int opcao = 0;
        Scanner scanner = new Scanner(System.in);
        while (opcao != -1) {
            System.out.println("---------------------------");
            System.out.println("1 -> Mover Pessoas");
            System.out.println("2 -> Criar Hospede");
            System.out.println("3 -> Criar Funcionario");
            System.out.println("4 -> Ver localização Pessoa");
            System.out.println("5 -> Ver Lista de Pessoas");
            System.out.println("6 -> Ver Mapa de Hotel");
            System.out.println("7 -> Lista de Movimentos do Hotel");
            System.out.println("8 -> Caminho mais apropriado sala quarentena");
            System.out.println("9 -> Contactos com a pessoa");
            System.out.println("0 -> Sair");
            System.out.println("---------------------------");
            int escolha = scanner.nextInt();
            switch (escolha) {
                case 1:
                    gestaoHotel.modoManual();
                    break;
                case 2:
                    hotel.inserirHospede();
                    break;
                case 3:
                    hotel.inserirFuncionario();
                    break;
                case 4:
                    this.gestaoHotel.escolhePessoaParaEncontrar();
                    break;
                case 5:
                    this.hotel.imprimePessoas();
                    break;
                case 6:
                    System.out.println(hotel.getDivisoes().toString());
                    this.hotel.getDivisoes().getTodasDivisoes();
                    break;
                case 7:
                    System.out.println(hotel.getMovimentosHotel().toString());
                    break;
                case 8:
                    gestaoHotel.caminhoMaisCurtoSalaQuarentena();
                    break;
                case 9:
                    this.gestaoHotel.imprimeDivisoesDasPessoas();
                    break;
                case 0:
                    this.listaJsonMovimentos.add(new JSONMovimentos(this.hotel.getNomeHotel(), this.hotel.getVersao(), this.hotel.getMovimentosHotel()));
                     {
                        try {
                            this.export.escreveMovimentosJSON(this.listaJsonMovimentos);
                        } catch (IOException ex) {
                        }
                    }
                    opcao = -1;
                    break;

            }
        }
    }

    /**
     * Este metodo vai pedir ao utilizador para escolher o mapa do hotel para
     * usar no programa
     *
     * @return
     * @throws FileNotFoundException
     * @throws ParseException
     */
    private String escolheMapa() throws FileNotFoundException, ParseException {

        String escolha = "";
        String fileMapa = "";
        boolean validPath = false;

        while (!validPath) {
            int number = importTemp.mostraHoteis();

            System.out.println("Escolha o mapa que quer jogar!!!!!" + " \n");

            Scanner scanner = new Scanner(System.in);

            escolha = scanner.nextLine();

            if (escolha.matches("[1" + number + "]")) {
                if (importTemp.escolheHotel(escolha) != null) {
                    fileMapa = importTemp.escolheHotel(escolha);
                    validPath = true;
                }
            }
        }

        return fileMapa;
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
