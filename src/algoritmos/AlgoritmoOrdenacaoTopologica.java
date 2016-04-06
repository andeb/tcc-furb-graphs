package algoritmos;

import java.util.ArrayList;
import java.util.HashMap;

import auxiliar.Constante;
import base.GrafoDirigido;
import base.Vertice;

/**
 * Classe AlgoritmoOrdenacaoTopologica<br>
 * <br>
 * Este algoritmo serve para resolver o problema de encontrar uma ordena��o topol�gica
 * em um grafo ac�clico dirigido.<br>
 * Uma ordena��o topol�gica � uma ordena��o de todos os v�rtices de um grafo ac�clico
 * dirigido, sendo que, se o grafo cont�m a aresta <b>(u, v)</b> ent�o <b>u</b> aparece antes de <b>v</b>.<br>
 * <br>
 * <b>Refer�ncias</b><br>
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
     * M�todo que executa o algoritmo sobre um grafo dirigido ac�clico
     * 
     * @param g Grafo dirigido ac�clico
     * @throws Exception
     */
    public void executar(GrafoDirigido g) throws Exception {
        if (!g.ehAciclico()) {
            throw new Exception("O grafo precisa ser ac�clico");
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
