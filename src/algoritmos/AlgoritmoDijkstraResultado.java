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
     * Atribui um predecessor para um v�rtice
     * 
     * @param v V�rtice
     * @param pai V�rtice pai
     */
    public void setPredecessor(Vertice v, Vertice pai) {
        predecessor.put(v, pai);
    }

    /**
     * Retorna o predecessor de um v�rtice
     * 
     * @param v V�rtice
     * @return Predecessor do v�rtice
     */
    public Vertice getPredecessor(Vertice v) {
        return predecessor.get(v);
    }

    /**
     * Retorna o custo para alcan�ar o v�rtice
     * 
     * @param v V�rtice
     * @return Custo do v�rtice
     */
    public double getCusto(Vertice v) {
        return custo.get(v);
    }

    /**
     * Atribui um custo para alcan�ar o v�rtice
     * 
     * @param v V�rtice
     * @param custo Custo do v�rtice
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
