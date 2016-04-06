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
     * Adiciona uma articulação
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
     * Retorna a quantidade de articulaçÃµes
     * 
     * @return Quantidade de articulaçÃµes
     */
    public int getQtdeArticulacoes() {
        return articulacoes.size();
    }

    /**
     * Retorna uma articulação pela posição
     * 
     * @param i
     *            Posição
     * @return Vertice
     */
    public Vertice getArticulacao(int i) {
        return articulacoes.get(i);
    }

    /**
     * Retorna o conjunto de articulaçÃµes
     * 
     * @return Conjunto de articulaçÃµes
     */
    public ArrayList<Vertice> getArticulacoes() {
        return articulacoes;
    }

}
