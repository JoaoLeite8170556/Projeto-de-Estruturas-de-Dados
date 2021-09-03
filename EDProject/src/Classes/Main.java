/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Excepcoes.ElementNonComparable;
import Excepcoes.EmptyExcpetion;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.parser.ParseException;

/**
 * Main do Projeto
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            new Menu();
        } catch (IOException ex) {}
    }
    
}
