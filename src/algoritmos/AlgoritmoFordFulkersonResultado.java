package algoritmos;

import java.util.HashMap;
import java.util.Map;

import auxiliar.Pair;
import base.Grafo;
import base.Vertice;

/**
 * Classe AlgoritmoFordFulkersonResultado
 *
 * @author Maicon Rafael Zatelli
 *
 */
public class AlgoritmoFordFulkersonResultado {

    private final HashMap<Pair, Double> fluxo = new HashMap<Pair, Double>();
    private final HashMap<Pair, Double> capacidade = new HashMap<Pair, Double>();
    private double fluxoMaximo = 0;
    private Grafo g;

    /**
     * Atribui um fluxo para a aresta u -> v
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @param fluxo
     *            Fluxo da aresta
     */
    public void setFluxo(Vertice u, Vertice v, double fluxo) {
        Pair par = new Pair(u, v);

        this.fluxo.put(par, fluxo);
    }

    /**
     * Incrementa o fluxo para a aresta u -> v
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @param fluxo
     *            Fluxo adicional
     */
    public void addFluxo(Vertice u, Vertice v, double fluxo) {
        Pair par = new Pair(u, v);

        double atual = (this.fluxo.get(par) == null ? 0.0 : this.fluxo.get(par));

        this.fluxo.put(par, atual + fluxo);
    }

    /**
     * Retorna o fluxo da aresta u -> v
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return Fluxo da aresta
     */
    public double getFluxo(Vertice u, Vertice v) {
        Pair par = new Pair(u, v);

        return (fluxo.get(par) == null ? 0.0 : fluxo.get(par));
    }

    /**
     * Atribui uma capacidade para a aresta u -> v
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @param capacidade
     *            Capacidade da aresta
     */
    public void setCapacidade(Vertice u, Vertice v, double capacidade) {
        Pair par = new Pair(u, v);

        this.capacidade.put(par, capacidade);
    }

    /**
     * Incrementa a capacidade da aresta u -> v
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @param capacidade
     *            Capacidade adicional
     */
    public void addCapacidade(Vertice u, Vertice v, double capacidade) {
        Pair par = new Pair(u, v);

        double atual = (this.capacidade.get(par) == null ? 0.0 : this.capacidade.get(par));

        this.capacidade.put(par, atual + capacidade);
    }

    /**
     * Retorna a capacidade da aresta u -> v
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return Capacidade da aresta
     */
    public double getCapacidade(Vertice u, Vertice v) {
        Pair par = new Pair(u, v);

        return (capacidade.get(par) == null ? 0.0 : capacidade.get(par));
    }

    /**
     * Retorna a capacidade da aresta u -> v
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return Capacidade da aresta
     */
    public Map<Pair, Double> getCapacidade() {
        return capacidade;
    }

    /**
     * Retorna a capacidade da aresta u -> v
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return Capacidade da aresta
     */
    public Map<Pair, Double> getFluxo() {
        return fluxo;
    }

    /**
     * Atribui o fluxo mÃ¡ximo
     *
     * @param fluxoMaximo
     */
    public void setFluxoMaximo(double fluxoMaximo) {
        this.fluxoMaximo = fluxoMaximo;
    }

    /**
     * Retorna o fluxo mÃ¡ximo
     *
     * @return Fluxo mÃ¡ximo
     */
    public double getFluxoMaximo() {
        return fluxoMaximo;
    }

    /**
     * Retorna o custo do fluxo mÃ¡ximo
     *
     * @return Custo do fluxo mÃ¡ximo
     */
    public double getCustoFluxo() {
        int tamanhoGrafo = g.getTamanho();
        double total = 0;
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice u = g.getVertice(i);
            for (int j = 0; j < tamanhoGrafo; j++) {
                Vertice v = g.getVertice(j);
                if (getFluxo(u, v) > 0.0) {
                    total += getFluxo(u, v) * g.getCustoMinimo(u, v);
                }
            }
        }
        return total;
    }

    /**
     * Atribui o grafo que estÃ¡ sendo trabalhado
     *
     * @param g
     *            Grafo
     */
    public void setGrafo(Grafo g) {
        this.g = g;
    }
}
