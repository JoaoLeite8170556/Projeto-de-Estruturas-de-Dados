
package Classes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Esta classe é usada para ler o ficheiro JSON com o mapa e retorna alguns detalhes sobre 
 * o mapa
 * @author João Leite Nº 8170556
 * @author Celio Macedo Nº 8170358
 */
public class JSONHotel {
    
    /**
     * Método para calcular o número de Divisões no array
     * @param nomeDivisao nome da Divisão
     * @return retorna o número de Divisões
     */
    public static int getNumeroDeDivisoes(String nomeDivisao){
        
        int numeroDeDivisoes = 0;
       
        try {
            JSONParser parser = new JSONParser();
            JSONObject jSONObject = (JSONObject) parser.parse("../mapa.json");
            JSONArray mapArray = (JSONArray) jSONObject.get("divisoes");
            
            numeroDeDivisoes = mapArray.size();
            
        } catch (ParseException ex){
            System.out.println(ex.getMessage());
        }
        
        return numeroDeDivisoes;
    }
    
}
