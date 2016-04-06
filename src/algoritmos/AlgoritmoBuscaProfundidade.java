package algoritmos;

import java.util.HashMap;
import java.util.List;

import auxiliar.Constante;
import base.Grafo;
import base.Vertice;

/**
 * Classe AlgoritmoBuscaProfundidade<br>
 * <br>
 * Este algoritmo executa uma sequ�ncia de visita�ões iniciando em um v�rtice qualquer <b>p</b>.<br>
 * O m�todo consiste em visitar algum v�rtice, ainda n�o visitado, adjacente a <b>p</b> e
 * colocá-lo em uma pilha.<br>
 * Depois de terminar de examinar <b>p</b> um novo v�rtice � retirado da pilha e processado
 * da mesma forma.<br>
 * Este procedimento � feito at� que a pilha se torne vazia.<br>
 * <br>
 * <b>Refer�ncias</b><br>
 * LAU, Hang T. A Java library of graph algorithms and optimization. Boca Raton: Chapman & Hall/CR, 2007. <br>
 * 
 * @author Maicon Rafael Zatelli
 */
public class AlgoritmoBuscaProfundidade {

    private AlgoritmoBuscaProfundidadeResultado resultado;
    private HashMap<Vertice, Boolean> branco;
    private int tempo;
    private Vertice verticeDestino;
    private Grafo g;

    /**
     * Retorna o resultado do algoritmo
     * 
     * @return AlgoritmoBuscaProfundidadeResultado
     */
    public AlgoritmoBuscaProfundidadeResultado getResultado() {
        return resultado;
    }

    private boolean dfs(Vertice pred, Vertice u) {
        branco.put(u, false);
        resultado.setTempoAbertura(u, tempo);
        resultado.addDescendente(pred, u);
        resultado.setPredecessor(u, pred);
        resultado.setVisitado(u, true);
        resultado.incrementaAtingidos();
        tempo++;

        if (verticeDestino == null || (verticeDestino != null && !verticeDestino.equals(u))) {
            List<Vertice> adjU = g.getAdjacentes(u);
            for (Vertice v : adjU) {
                if (branco.get(v)) {
                    if (dfs(u, v)) {
                        resultado.setTempoFechamento(u, tempo);
                        tempo++;
                        return true;
                    }
                }
            }
        }

        resultado.setTempoFechamento(u, tempo);
        tempo++;

        if (verticeDestino != null && verticeDestino.equals(u)) {
            return true; //fechou o vertice de destino	
        }
        return false;
    }

    /**
     * M�todo que executa o algoritmo sobre um grafo
     * 
     * @param g Grafo
     */
    public void executar(Grafo g) {
        this.g = g;
        resultado = new AlgoritmoBuscaProfundidadeResultado();
        branco = new HashMap<Vertice, Boolean>();
        int tamanhoGrafo = g.getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            branco.put(v, true);
            resultado.setPredecessor(v, null);
            resultado.setVisitado(v, false);
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

    /**
     * M�todo que executa o algoritmo sobre um grafo a partir de um v�rtice de origem
     * 
     * @param g Grafo
     * @param s V�rtice de origem
     */
    public void executar(Grafo g, Vertice s) {
        this.g = g;
        resultado = new AlgoritmoBuscaProfundidadeResultado();
        branco = new HashMap<Vertice, Boolean>();
        int tamanhoGrafo = g.getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            branco.put(v, true);
            resultado.setPredecessor(v, null);
            resultado.setVisitado(v, false);
            resultado.setTempoAbertura(v, Constante.INFTEMPONEGATIVO);
            resultado.setTempoFechamento(v, Constante.INFTEMPONEGATIVO);
        }

        tempo = 1;

        dfs(null, s);
    }

    /**
     * M�todo que executa o algoritmo sobre um grafo a partir de um v�rtice de origem<br>
     * at� um v�rtice de destino
     * 
     * @param g Grafo
     * @param s V�rtice de origem
     * @param d V�rtice de destino
     */
    public void executar(Grafo g, Vertice s, Vertice d) {
        this.g = g;
        verticeDestino = d;
        executar(g, s);
    }
}
