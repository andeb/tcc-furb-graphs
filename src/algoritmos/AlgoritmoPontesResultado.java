package algoritmos;

import java.util.ArrayList;

import base.Aresta;

/**
 * Classe AlgoritmoPontesResultado
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public class AlgoritmoPontesResultado {

    private final ArrayList<Aresta> pontes = new ArrayList<Aresta>();

    /**
     * Adiciona uma ponte
     * 
     * @param a Aresta
     */
    public void addPonte(Aresta a) {
        if (!pontes.contains(a)) {
            pontes.add(a);
        }
    }

    /**
     * Retorna a quantidade de pontes
     * 
     * @return Quantidade de pontes
     */
    public int getQtdePontes() {
        return pontes.size();
    }

    /**
     * Retorna uma ponte pela posição
     * 
     * @param i Posição
     * @return Aresta
     */
    public Aresta getPonte(int i) {
        return pontes.get(i);
    }

    /**
     * Retorna o conjunto de pontes
     * 
     * @return Conjunto de pontes
     */
    public ArrayList<Aresta> getPontes() {
        return pontes;
    }
}
