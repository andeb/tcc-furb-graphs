package algoritmos;

import java.util.HashMap;
import java.util.List;

import base.Grafo;
import base.Vertice;

/**
 * Classe AlgoritmoArticulacoes<br>
 * <br>
 * Um nó de articulação é um vértique que, se retirado do grafo, torna o grafo desconexo.<br>
 * <br>
 * <b>Referências</b><br>
 * CORMEN, Thomas H. et al. Introduction to algorithms. 2nd ed. Cambridge: MIT, 2001. <br>
 * 
 * @author Maicon Rafael Zatelli
 */
public class AlgoritmoArticulacoes {

    private final byte BRANCA = 0;
    private final byte CINZA = 1;
    private final byte PRETA = 2;

    private AlgoritmoArticulacoesResultado resultado;
    private HashMap<Vertice, Byte> cor;
    private HashMap<Vertice, Integer> low;
    private HashMap<Vertice, Integer> num;
    private HashMap<Vertice, Integer> filhos;
    private Grafo g;

    /**
     * Retorna o resultado do algoritmo
     * 
     * @return AlgoritmoArticulacoesResultado
     */
    public AlgoritmoArticulacoesResultado getResultado() {
        return resultado;
    }

    private void dfs(Vertice u, int niv) {
        cor.put(u, CINZA);
        low.put(u, niv);
        num.put(u, niv);

        List<Vertice> adjU = g.getAdjacentes(u);
        for (Vertice v : adjU) {
            if (cor.get(v) == BRANCA) {
                dfs(v, niv + 1);

                if (niv > 1 && low.get(v) >= num.get(u)) {
                    resultado.addArticulacao(u); //articulacao
                }

                if (low.get(v) < low.get(u)) {
                    low.put(u, low.get(v));
                }

                filhos.put(u, filhos.get(u) + 1);
            } else if (cor.get(v) == CINZA) {
                if (num.get(v) < low.get(u)) {
                    low.put(u, num.get(v));
                }
            }
        }

        if (niv == 1 && filhos.get(u) > 1) {
            resultado.addArticulacao(u); //articulacao
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
        resultado = new AlgoritmoArticulacoesResultado();
        cor = new HashMap<Vertice, Byte>();
        low = new HashMap<Vertice, Integer>();
        num = new HashMap<Vertice, Integer>();
        filhos = new HashMap<Vertice, Integer>();

        int tamanhoGrafo = g.getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            cor.put(v, BRANCA);
            filhos.put(v, 0);
        }

        dfs(g.getUmVertice(), 1);
    }
}
