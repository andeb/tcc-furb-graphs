package algoritmos;

import java.util.HashMap;

import auxiliar.Pair;
import base.Vertice;

/**
 * Classe AlgoritmoFloydWarshallResultado
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public class AlgoritmoFloydWarshallResultado {

    private final HashMap<Pair, Double> custo = new HashMap<Pair, Double>();
    private final HashMap<Pair, Vertice> predecessor = new HashMap<Pair, Vertice>();

    /**
     * Atribui um custo para um vértice
     * 
     * @param i Vértice de origem
     * @param j Vértice de destino
     * @param custo Custo do vértice de destino
     */
    public void setCusto(Vertice i, Vertice j, double custo) {
        Pair par = new Pair(i, j);
        this.custo.put(par, custo);
    }

    /**
     * Retorna o custo para um vértice
     * 
     * @param i Vértice de origem
     * @param j Vértice de destino
     * @return Custo do vértice de destino
     */
    public double getCusto(Vertice i, Vertice j) {
        Pair par = new Pair(i, j);
        return custo.get(par);
    }

    /**
     * Atribui um predecessor para um vértice
     * 
     * @param i Vértice de origem
     * @param j Vértice de destino
     * @param pai Vértice pai do vértice de destino
     */
    public void setPredecessor(Vertice i, Vertice j, Vertice pai) {
        Pair par = new Pair(i, j);
        predecessor.put(par, pai);
    }

    /**
     * Retorna o predecessor de um vértice
     * 
     * @param i Vértice de origem
     * @param j Vértice de destino
     * @return Predecessor do vértice de destino
     */
    public Vertice getPredecessor(Vertice i, Vertice j) {
        Pair par = new Pair(i, j);
        return predecessor.get(par);
    }
}
