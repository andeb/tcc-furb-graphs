package algoritmos;

import java.util.ArrayList;
import java.util.HashMap;

import base.Vertice;

/**
 * Classe AlgoritmoOrdenacaoTopologicaResultado
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public class AlgoritmoOrdenacaoTopologicaResultado {

    private final ArrayList<Vertice> ordem = new ArrayList<Vertice>();
    private final HashMap<Vertice, Integer> tempoAbertura = new HashMap<Vertice, Integer>();
    private final HashMap<Vertice, Integer> tempoFechamento = new HashMap<Vertice, Integer>();

    /**
     * Adiciona um vértice
     * 
     * @param v Vértice
     */
    public void addVertice(Vertice v) {
        ordem.add(v);
    }

    /**
     * Retorna a quantidade de vértices
     * 
     * @return Quantidade de vértices
     */
    public int getTamanho() {
        return ordem.size();
    }

    /**
     * Retorna um vértice pela sua posição
     * 
     * @param i Posição
     * @return Vértice
     */
    public Vertice getVertice(int i) {
        return ordem.get(i);
    }

    /**
     * Retorna o conjunto de vértices
     * 
     * @return Conjunto de vértices
     */
    public ArrayList<Vertice> getLista() {
        return ordem;
    }

    /**
     * Atribui um tempo de abertura para o vértice
     * 
     * @param v Vértice
     * @param t Tempo de abertura
     */
    public void setTempoAbertura(Vertice v, int t) {
        tempoAbertura.put(v, t);
    }

    /**
     * Retorna o tempo de abertura do vértice
     * 
     * @param v Vértice
     * @return Tempo de abertura
     */
    public int getTempoAbertura(Vertice v) {
        return tempoAbertura.get(v);
    }

    /**
     * Atribui um tempo de fechamento para o vértice
     * 
     * @param v Vértice
     * @param t Tempo de fechamento
     */
    public void setTempoFechamento(Vertice v, int t) {
        tempoFechamento.put(v, t);
    }

    /**
     * Retorna o tempo de fechamento do vértice
     * 
     * @param v Vértice
     * @return Tempo de fechamento
     */
    public int getTempoFechamento(Vertice v) {
        return tempoFechamento.get(v);
    }
}
