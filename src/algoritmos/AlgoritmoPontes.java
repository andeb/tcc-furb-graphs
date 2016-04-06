package algoritmos;

import java.util.HashMap;
import java.util.List;

import auxiliar.Constante;
import base.Grafo;
import base.Vertice;

/**
 * Classe AlgoritmoPontes<br>
 * <br>
 * Uma ponte é uma aresta que, se desconectada, torna o grafo desconexo.<br>
 * <br>
 * <b>Referências</b><br>
 * CORMEN, Thomas H. et al. Introduction to algorithms. 2nd ed. Cambridge: MIT, 2001. <br>
 * 
 * @author Maicon Rafael Zatelli
 */
public class AlgoritmoPontes {

    private final byte BRANCA = 0;
    private final byte CINZA = 1;
    private final byte PRETA = 2;

    private AlgoritmoPontesResultado resultado;
    private HashMap<Vertice, Byte> cor;
    private HashMap<Vertice, Integer> num;
    private HashMap<Vertice, Integer> low;
    private HashMap<Vertice, Vertice> pred;
    private Grafo g;

    /**
     * Retorna o resultado do algoritmo
     * 
     * @return AlgoritmoPontes
     */
    public AlgoritmoPontesResultado getResultado() {
        return resultado;
    }

    private void dfs(Vertice u, int niv) {
        num.put(u, niv);
        low.put(u, niv);
        cor.put(u, CINZA);

        List<Vertice> adjU = g.getAdjacentes(u);
        for (Vertice v : adjU) {
            if (cor.get(v) == BRANCA) {
                pred.put(v, u);
                dfs(v, niv + 1);

                if (low.get(v) < low.get(u)) {
                    low.put(u, low.get(v));
                }

                if (low.get(v) == num.get(v)) {
                    if (!g.existeParalelismo(u, v)) {
                        resultado.addPonte(g.getArestaByVertices(u, v));
                    }

                    if (g.ehDirigido() && !g.existeParalelismo(v, u)) {
                        resultado.addPonte(g.getArestaByVertices(v, u));
                    }
                }
            } else if (!pred.get(u).equals(v) && cor.get(v) == CINZA) {
                if (low.get(v) < low.get(u)) {
                    low.put(u, low.get(v));
                }
            }
        }

        cor.put(u, PRETA);
    }

    /**
     * Método que executa o algoritmo sobre um grafo
     * 
     * @param g Grafo
     */
    public void executar(Grafo g) {
        this.g = g;
        resultado = new AlgoritmoPontesResultado();
        cor = new HashMap<Vertice, Byte>();
        low = new HashMap<Vertice, Integer>();
        num = new HashMap<Vertice, Integer>();
        pred = new HashMap<Vertice, Vertice>();

        int tamanhoGrafo = g.getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            cor.put(v, BRANCA);
            pred.put(v, v);
            low.put(v, Constante.INFTEMPOPOSITIVO);
        }

        dfs(g.getUmVertice(), 1);
    }
}
