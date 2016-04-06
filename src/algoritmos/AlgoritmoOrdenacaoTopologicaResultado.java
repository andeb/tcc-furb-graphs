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
     * Adiciona um v�rtice
     * 
     * @param v V�rtice
     */
    public void addVertice(Vertice v) {
        ordem.add(v);
    }

    /**
     * Retorna a quantidade de v�rtices
     * 
     * @return Quantidade de v�rtices
     */
    public int getTamanho() {
        return ordem.size();
    }

    /**
     * Retorna um v�rtice pela sua posi��o
     * 
     * @param i Posi��o
     * @return V�rtice
     */
    public Vertice getVertice(int i) {
        return ordem.get(i);
    }

    /**
     * Retorna o conjunto de v�rtices
     * 
     * @return Conjunto de v�rtices
     */
    public ArrayList<Vertice> getLista() {
        return ordem;
    }

    /**
     * Atribui um tempo de abertura para o v�rtice
     * 
     * @param v V�rtice
     * @param t Tempo de abertura
     */
    public void setTempoAbertura(Vertice v, int t) {
        tempoAbertura.put(v, t);
    }

    /**
     * Retorna o tempo de abertura do v�rtice
     * 
     * @param v V�rtice
     * @return Tempo de abertura
     */
    public int getTempoAbertura(Vertice v) {
        return tempoAbertura.get(v);
    }

    /**
     * Atribui um tempo de fechamento para o v�rtice
     * 
     * @param v V�rtice
     * @param t Tempo de fechamento
     */
    public void setTempoFechamento(Vertice v, int t) {
        tempoFechamento.put(v, t);
    }

    /**
     * Retorna o tempo de fechamento do v�rtice
     * 
     * @param v V�rtice
     * @return Tempo de fechamento
     */
    public int getTempoFechamento(Vertice v) {
        return tempoFechamento.get(v);
    }
}
