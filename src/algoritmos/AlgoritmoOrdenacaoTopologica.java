package algoritmos;

import java.util.ArrayList;
import java.util.HashMap;

import auxiliar.Constante;
import base.GrafoDirigido;
import base.Vertice;

/**
 * Classe AlgoritmoOrdenacaoTopologica<br>
 * <br>
 * Este algoritmo serve para resolver o problema de encontrar uma ordenação topológica
 * em um grafo acíclico dirigido.<br>
 * Uma ordenação topológica é uma ordenação de todos os vértices de um grafo acíclico
 * dirigido, sendo que, se o grafo contém a aresta <b>(u, v)</b> então <b>u</b> aparece antes de <b>v</b>.<br>
 * <br>
 * <b>Referências</b><br>
 * CORMEN, Thomas H. et al. Introduction to algorithms. 2nd ed. Cambridge: MIT, 2001. <br>
 * 
 * @author Maicon Rafael Zatelli
 */
public class AlgoritmoOrdenacaoTopologica {

    AlgoritmoOrdenacaoTopologicaResultado resultado;
    private HashMap<Vertice, Boolean> branco;
    int tempo;
    private GrafoDirigido g;

    /**
     * Retorna o resultado do algoritmo
     * 
     * @return AlgoritmoOrdenacaoTopologicaResultado
     */
    public AlgoritmoOrdenacaoTopologicaResultado getResultado() {
        return resultado;
    }

    private void dfs(Vertice pred, Vertice u) {
        branco.put(u, false);
        resultado.setTempoAbertura(u, tempo);
        tempo++;

        ArrayList<Vertice> adjU = g.getAdjacentes(u);
        for (Vertice v : adjU) {
            if (branco.get(v)) {
                dfs(u, v);
            }
        }

        resultado.addVertice(u);
        resultado.setTempoFechamento(u, tempo);
        tempo++;
    }

    /**
     * Método que executa o algoritmo sobre um grafo dirigido acíclico
     * 
     * @param g Grafo dirigido acíclico
     * @throws Exception
     */
    public void executar(GrafoDirigido g) throws Exception {
        if (!g.ehAciclico()) {
            throw new Exception("O grafo precisa ser acíclico");
        }

        this.g = g;
        resultado = new AlgoritmoOrdenacaoTopologicaResultado();
        branco = new HashMap<Vertice, Boolean>();
        int tamanhoGrafo = g.getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            branco.put(v, true);
            resultado.setTempoAbertura(v, Constante.INFTEMPONEGATIVO);
            resultado.setTempoFechamento(v, Constante.INFTEMPONEGATIVO);
        }

        tempo = 1;

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);
            if (branco.get(v)) {
                dfs(null, v);
            }
        }
    }
}
