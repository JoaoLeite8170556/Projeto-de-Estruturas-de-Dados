/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Classes.JSONMovimentos;
import Colecoes.DoubleLinkedOrderedList;
import Excepcoes.ElementNonComparable;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author João
 */
public interface InterfaceImport {
    
    public DoubleLinkedOrderedList<JSONMovimentos> readFileMovimentos() throws FileNotFoundException, IOException, ParseException, ElementNonComparable;
    
    public String escolheHotel(String escolhaHotel) throws FileNotFoundException, ParseException;
    
     public int mostraHoteis() throws FileNotFoundException, ParseException;
    
}
