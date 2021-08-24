/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafo;


import Classes.Divisao;
import Colecoes.GraphWeight;
import Colecoes.UnorderedArrayList;
import Colecoes.UnorderedDoubleLinkedList;
import Excepcoes.EmptyExcpetion;
import java.util.Iterator;


/**
 * Esta classe é uma extensão da classe com metodos especificos para o nosso problema.
 * @author Celio Macedo Nº 8170358
 * @author João Leite Nº 8170556
 */
public class GrafoHotel<T> extends GraphWeight<T>{

    public GrafoHotel(int n) {
        super();
    }
    
    
    
    
    
    /**
     * Este método permite obter todos os vertices adjacentes de determinado
     * vertice.
     * @param vertex vertice na qual queremos obter os vertices vizinhos
     * @return lista de vertices adjacentes
     */
    public Iterator<T> getVerticesAdjacentes(T vertex){
        UnorderedArrayList<T> tempList = new UnorderedArrayList<T>();
        
        int index = this.getIndex(vertex);
        
       for (int i=0;i<this.numVertices; i++){
           if(this.weights[i][index] != Double.POSITIVE_INFINITY){
               tempList.addToRear(this.vertices[i]);
           }
       }
       for(int i=0;i<this.numVertices;i++){
            try {
                if(this.weights[index][i] != Double.POSITIVE_INFINITY && !tempList.contains(this.vertices[i])){
                    tempList.addToRear(this.vertices[i]);
                }} catch (EmptyExcpetion ex){}
       }
        return tempList.iterator();
    }
    
    
    
    /**
     * Este metodo vai indicar qual o número de vertices adjacentes tem
     * @param vertice vertice na qual queremos saber o número de vertices 
     * @return o número de vertices
     */
    public int numeroDeVerticesAdjacentes(T vertice){
        Iterator itr = getVerticesAdjacentes(vertice);
        int contador = 0;
        
        while(itr.hasNext()){
            itr.next();
            contador++;
        }
        return contador;
    }
    
    /**
     * Este metodo vai imprimir o todos os vertices adjacentes de determinado vertice
     * @param vertex vertice na qual queremos obter os vertices adjacentes.
     */
    public void mostraVerticesAdjacentes(T vertex) {
        Iterator itr = getVerticesAdjacentes(vertex);
        System.out.println("----------------------------------------");
        while (itr.hasNext()) {

            System.out.println("|->" + itr.next()
                    + "                             |");
        }
        System.out.println("|->Sair                                |");
        System.out.println("----------------------------------------");
    }
    
    /**
     * Método para alterar o peso da ligação entre dois vertices.
     *
     * @param startVertex vertice inicial
     * @param targetVertex vertice adjacente
     * @param weight novo peso a inserir
     */
    @Override
    public void setEdgeWeight(T startVertex, T targetVertex, double weight) {
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);
        for (int i = 0; i < this.vertices.length; i++) {
            this.weights[startIndex][targetIndex] = weight;
            this.weights[targetIndex][startIndex] = weight;

        }
    }
    
    /**
     * Este metodo vai permitir verificar se os vertices estao conectados entre si
     * @param vertice1 
     * @param vertice2
     * @return true se estiver conectado, false se não.
     */
    public boolean estaConectado(T vertice1, T vertice2){
        int index1 = this.getIndex(vertice1);
        int index2 = this.getIndex(vertice2);
        
        if((this.validIndex(index1) == 1 && this.validIndex(index2) == 1) && 
                this.adjMatrix[index1][index2] && this.adjMatrix[index2][index1]){
            return true;
        }
        return false;
    }
    
    /**
     * Metodo para retornar a divisao que esta em determinado vertice
     * @param element o elemento que queremos encontrar
     * @return null se nao for encontrado, caso contrario retorna o vertice
     */
    public T getVerticeWithElement(T element){
        int i=0;
        while(i<this.numVertices && !this.vertices[i].equals(element)){
            i++;
        }
        return i==this.numVertices ? null : this.vertices[i];
    }
    
    
    /**
     * Metodo que vai guardar num estrutura todos os vertices presentes no Grafo
     * @return todas as divisões do Hotel
     */
    public UnorderedDoubleLinkedList<T> getTodasDivisoes(){
        UnorderedDoubleLinkedList<T> tempList = new UnorderedDoubleLinkedList<T>();
        
        for(int i=0;i<this.numVertices;i++){
            tempList.addToRear(this.vertices[i]);
        }
        return tempList;
    }
    
    
    /**
    
     * Metodo que vai retornar uma string onde vai indicar uma matriz com as
     * ligações e os vertices com as suas ligações correspondentes.
     *
     * @return a string com os vertices e as suas ligaçoes
     
    @Override
    public String toString() {
        if (numVertices == 0) {
            return "Graph is empty";
        }

        String result = new String("");

        /* Print the adjacency Matrix 
        result += "----------------------------------Matriz de adjacência----------------------------------\n";
        result += "----------------------------------------------------------------------------------------\n";
        result += "Divisões\t\t";

        for (int i = 0; i < numVertices; i++) {
            result += this.vertices[i] + " \t";

        }
        result += "\n";

        for (int i = 0; i < numVertices; i++) {
            Divisao temp = (Divisao) this.vertices[i];
            if (temp.length() < 8) {
                result += this.vertices[i] + "\t\t\t";
            } else if (temp.length() > 15) {
                result += this.vertices[i] + "\t";
            } else {
                result += this.vertices[i] + "\t\t";
            }

            for (int j = 0; j < numVertices; j++) {
                temp = (String) this.vertices[j];

                if (weights[i][j] < Double.POSITIVE_INFINITY) {

                    if (temp.length() > 13) {

                        result += "   True " + "\t\t";

                    } else if (temp.equals("Escada 4") || temp.equals("Segurança")) {
                        result += "   True   " + " ";
                    } else {

                        result += "   True " + "\t";
                    }

                } else {

                    if (temp.length() > 13) {

                        result += "  False  " + "\t\t";

                    } else if (temp.equals("Escada 4") || temp.equals("Segurança")) {
                        result += "   False  " + "  ";
                    } else {

                        result += "  False  " + "\t";

                    }
                }
            }
            result += "\n";
        }
        result += "\n\n------------------Peso das Arestas------------------";
        result += "\n-----------------------------------------------------\n";
        result += "Aresta \t\t\t\t\t\tPeso\n\n";

        for (int i = 0; i < numVertices; i++) {
            for (int j = numVertices - 1; j > i; j--) {
                if (this.weights[i][j] < Double.POSITIVE_INFINITY) {
                    String tmp = "";
                    result += this.vertices[i] + " para " + this.vertices[j];
                    tmp = this.vertices[i] + " para " + this.vertices[j];

                    if (tmp.length() < 40 && tmp.length() > 31) {
                        result += "\t\t";
                    } else if (tmp.length() < 32 && tmp.length() > 23) {
                        result += "\t\t\t";
                    } else {
                        result += "\t\t\t\t";
                    }

                    result += this.weights[i][j] + "\n";
                    result += this.vertices[j] + " para " + this.vertices[i];

                    if (tmp.length() < 40 && tmp.length() > 31) {
                        result += "\t\t";
                    } else if (tmp.length() < 33 && tmp.length() >= 24) {
                        result += "\t\t\t";
                    } else {
                        result += "\t\t\t\t";
                    }

                    result += this.weights[j][i] + "\n";
                }

            }

        }

        result += "\n";
        return result;
    }**/
}
