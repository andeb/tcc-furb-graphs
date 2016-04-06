package algoritmos;

import java.util.ArrayList;
import java.util.HashMap;

import base.Aresta;
import base.Vertice;

/**
 * Classe AlgoritmoKruskalResultado
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public class AlgoritmoKruskalResultado {

    private final HashMap<Vertice, Vertice> predecessor = new HashMap<Vertice, Vertice>();
    private final HashMap<Vertice, ArrayList<Vertice>> descendentes = new HashMap<Vertice, ArrayList<Vertice>>();
    private final HashMap<Vertice, Double> custo = new HashMap<Vertice, Double>();
    private final ArrayList<Aresta> arestas = new ArrayList<Aresta>();
    private double custoTotal = 0;

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
     * Adiciona um valor ao custo total
     * 
     * @param valor Valor adicional
     */
    public void addCustoTotal(double valor) {
        custoTotal += valor;
    }

    /**
     * Retorna o custo total
     * 
     * @return Custo total
     */
    public double getCustoTotal() {
        return custoTotal;
    }

    /**
     * Adiciona um descendente de um v�rtice
     * 
     * @param v V�rtice
     * @param filho Descendente do v�rtice
     */
    public void addDescendente(Vertice v, Vertice filho) {
        ArrayList<Vertice> lista = descendentes.get(v);

        if (lista == null) {
            lista = new ArrayList<Vertice>();
        }

        lista.add(filho);

        descendentes.put(v, lista);
    }

    /**
     * Retorna o conjunto de descendentes de um v�rtice
     * 
     * @param v V�rtice
     * @return Conjunto de descendentes do v�rtice
     */
    public ArrayList<Vertice> getDescendentes(Vertice v) {
        return descendentes.get(v);
    }

    /**
     * Adiciona uma aresta
     * 
     * @param a aresta
     */
    public void addAresta(Aresta a) {
        arestas.add(a);
    }

    /**
     * Retorna o conjunto de arestas
     * 
     * @return Conjunto de arestas
     */
    public ArrayList<Aresta> getArestas() {
        return arestas;
    }

    /**
     * Atribui um custo para um v�rtice
     * 
     * @param v V�rtice
     * @param custo Custo
     */
    public void setCusto(Vertice v, double custo) {
        this.custo.put(v, custo);
    }

    /**
     * Retorna o custo para um v�rtice
     * 
     * @param v V�rtice
     * @return Custo do v�rtice
     */
    public double getCusto(Vertice v) {
        return custo.get(v);
    }
}
