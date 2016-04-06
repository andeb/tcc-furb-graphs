package algoritmos;

import java.util.HashMap;

import base.Vertice;

/**
 * Classe AlgoritmoDijkstraResultado
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public class AlgoritmoDijkstraResultado {

    private final HashMap<Vertice, Vertice> predecessor = new HashMap<Vertice, Vertice>();
    private final HashMap<Vertice, Double> custo = new HashMap<Vertice, Double>();

    /**
     * Atribui um predecessor para um vértice
     * 
     * @param v Vértice
     * @param pai Vértice pai
     */
    public void setPredecessor(Vertice v, Vertice pai) {
        predecessor.put(v, pai);
    }

    /**
     * Retorna o predecessor de um vértice
     * 
     * @param v Vértice
     * @return Predecessor do vértice
     */
    public Vertice getPredecessor(Vertice v) {
        return predecessor.get(v);
    }

    /**
     * Retorna o custo para alcançar o vértice
     * 
     * @param v Vértice
     * @return Custo do vértice
     */
    public double getCusto(Vertice v) {
        return custo.get(v);
    }

    /**
     * Atribui um custo para alcançar o vértice
     * 
     * @param v Vértice
     * @param custo Custo do vértice
     */
    public void setCusto(Vertice v, double custo) {
        this.custo.put(v, custo);
    }

    /**
     * Retorna se existe caminho
     * 
     * @return true ou false
     */
    public boolean getSemCaminho(Vertice v) {
        return getPredecessor(v) == null;
    }

}
