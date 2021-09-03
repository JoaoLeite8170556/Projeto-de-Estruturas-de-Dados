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
import Interfaces.InterfaceGrafoHotel;
import java.util.Iterator;


/**
 * Esta classe é uma extensão da classe com metodos especificos para o nosso problema.
 * @author Celio Macedo Nº 8170358
 * @author João Leite Nº 8170556
 */
public class GrafoHotel<T> extends GraphWeight<T> implements InterfaceGrafoHotel<T>{

    public GrafoHotel(int n) {
        super();
    }
    
    
    
    
    
    /**
     * Este método permite obter todos os vertices adjacentes de determinado
     * vertice.
     * @param vertex vertice na qual queremos obter os vertices vizinhos
     * @return lista de vertices adjacentes
     */
    @Override
    public Iterator<T> getVerticesAdjacentes(T vertex){
        UnorderedDoubleLinkedList<T> tempList = new UnorderedDoubleLinkedList<T>();
        
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
     **/
   @Override
    public String toString() {
        if (numVertices == 0)
            return "Graph is empty";

        StringBuilder result = new StringBuilder();

        // Print the adjacency Matrix
        result.append("Adjacency Matrix\n");
        result.append("----------------\n");
        result.append("index\t");

        for (int i = 0; i < numVertices; i++) {
            result.append(i);
            if (i < 10)
                result.append(" ");
        }
        result.append("\n\n");

        for (int i = 0; i < numVertices; i++) {
            result.append(i).append("\t\t");

            for (int j = 0; j < numVertices; j++) {
                if (weights[i][j] < Double.POSITIVE_INFINITY)
                    result.append(weights[i][j]);
                else
                    result.append("0 ");
            }
            result.append("\n");
        }

        /* Print the vertex values /
        result.append("\n\nVertex Values");
        result.append("\n-------------\n");
        result.append("index\tvalue\n\n");

        for (int i = 0; i < numVertices; i++) {
            result.append(i).append("\t\t");
            result.append(vertices[i].toString()).append("\n");
        }

        / Print the weights of the edges /
        /result.append("\n\nWeights of Edges");
        result.append("\n----------------\n");
        result.append("index\tweight\n\n");

        for (int i = 0; i < numVertices; i++) {
            for (int j = numVertices - 1; j > i; j--) {
                if (weights[i][j] < Double.POSITIVE_INFINITY) {
                    //result.append((vertices[i]).getName()).append(" to ").append(((Vertex) vertices[j]).getName()).append("\t");
                    result.append(adjMatrix[i][j]).append("\n");
                }
            }
        }*/

        return result.toString();
    }
}
