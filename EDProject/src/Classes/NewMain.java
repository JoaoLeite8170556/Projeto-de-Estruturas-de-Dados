/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.UnorderedDoubleLinkedList;
import Enumerações.Tipo;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import org.json.simple.parser.ParseException;

/**
 *
 * @author João
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException, java.text.ParseException {
        
        
        JSONMovimentos jsonMovimentos = new JSONMovimentos("../movimentos.json");
        Iterator itr = jsonMovimentos.readFileMovimentos().iterator();
        while(itr.hasNext()){
            System.out.println(itr.next().toString());
        }
        UnorderedDoubleLinkedList<Integer> ko = new UnorderedDoubleLinkedList<>();
        
        ko.
    }
    
}
