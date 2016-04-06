package algoritmos;

import java.util.ArrayList;

import base.Vertice;

/**
 * Classe AlgoritmoArticulacoesResultado
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public class AlgoritmoArticulacoesResultado {

    private final ArrayList<Vertice> articulacoes = new ArrayList<Vertice>();

    /**
     * Adiciona uma articula��o
     * 
     * @param v
     *            Vertice
     */
    public void addArticulacao(Vertice v) {
        if (!articulacoes.contains(v)) {
            articulacoes.add(v);
        }
    }

    /**
     * Retorna a quantidade de articula�ões
     * 
     * @return Quantidade de articula�ões
     */
    public int getQtdeArticulacoes() {
        return articulacoes.size();
    }

    /**
     * Retorna uma articula��o pela posi��o
     * 
     * @param i
     *            Posi��o
     * @return Vertice
     */
    public Vertice getArticulacao(int i) {
        return articulacoes.get(i);
    }

    /**
     * Retorna o conjunto de articula�ões
     * 
     * @return Conjunto de articula�ões
     */
    public ArrayList<Vertice> getArticulacoes() {
        return articulacoes;
    }

}
