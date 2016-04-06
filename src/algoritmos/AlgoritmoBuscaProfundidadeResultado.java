package algoritmos;

import java.util.ArrayList;
import java.util.HashMap;

import base.Vertice;

/**
 * Classe AlgoritmoBuscaProfundidadeResultado
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public class AlgoritmoBuscaProfundidadeResultado {

    private final HashMap<Vertice, Integer> tempoAbertura = new HashMap<Vertice, Integer>();
    private final HashMap<Vertice, Integer> tempoFechamento = new HashMap<Vertice, Integer>();
    private final HashMap<Vertice, Vertice> predecessor = new HashMap<Vertice, Vertice>();
    private final HashMap<Vertice, ArrayList<Vertice>> descendentes = new HashMap<Vertice, ArrayList<Vertice>>();
    private final HashMap<Vertice, Boolean> visitado = new HashMap<Vertice, Boolean>();
    private int qtdeAtingidos = 0;

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
     * Marca o v�rtice como visitado
     * 
     * @param v V�rtice
     * @param visitado
     */
    public void setVisitado(Vertice v, boolean visitado) {
        this.visitado.put(v, visitado);
    }

    /**
     * Retorna se o v�rtice foi visitado
     * 
     * @param v V�rtice
     * @return true ou false
     */
    public boolean getVisitado(Vertice v) {
        return visitado.get(v);
    }

    /**
     * Incrementa a quantidade de v�rtices atingidos pela busca
     */
    public void incrementaAtingidos() {
        qtdeAtingidos++;
    }

    /**
     * Retorna a quantidade de v�rtices atingidos pela busca
     * 
     * @return Quantidade de v�rtices
     */
    public int getQtdeAtingidos() {
        return qtdeAtingidos;
    }
}
