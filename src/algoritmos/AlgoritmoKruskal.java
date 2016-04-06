package algoritmos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import auxiliar.PairPriority;
import base.Aresta;
import base.ArestaNaoDirigida;
import base.GrafoNaoDirigido;
import base.Vertice;

/**
 * Classe AlgoritmoKruskal<br>
 * <br>
 * Este algoritmo serve para resolver o problema de encontrar Ã¡rvores geradoras de custo
 * mínimo em grafos desconexos.<br>
 * <br>
 * <b>Referências</b><br>
 * KOCAY, William; KREHER, Donald L. Graphs, algorithms, and optimization. Boca Raton: Chapman & Hall/CRC, 2005. <br>
 * 
 * @author Maicon Rafael Zatelli
 */
public class AlgoritmoKruskal {

    private AlgoritmoKruskalResultado resultado;
    private HashMap<Vertice, Integer> tamanho;

    /**
     * Retorna o resultado do algoritmo
     * 
     * @return AlgoritmoKruskalResultado
     */
    public AlgoritmoKruskalResultado getResultado() {
        return resultado;
    }

    //Devolve a raiz do union-find tree que contem o vertice v
    private Vertice find(Vertice v) {
        Vertice i;
        for (i = v; !i.equals(resultado.getPredecessor(i)); i = resultado.getPredecessor(i)) {
            resultado.setPredecessor(i, resultado.getPredecessor(resultado.getPredecessor(i))); //path compression
        }
        return i;
    }

    private void ufUnion(Vertice v, Vertice w, Vertice i, Vertice j) {
        //if (i.equals(j)) return;

        int tamanhoI = tamanho.get(i);
        int tamanhoJ = tamanho.get(j);

        if (tamanhoI < tamanhoJ) {
            resultado.setPredecessor(i, j);
            tamanho.put(j, tamanhoJ + tamanhoI);
        } else {
            resultado.setPredecessor(j, i);
            tamanho.put(i, tamanhoJ + tamanhoI);
        }
    }

    /**
     * Método que executa o algoritmo sobre um grafo
     * 
     * @param g GrafoNaoDirigido
     */
    public void executar(GrafoNaoDirigido g) {
        resultado = new AlgoritmoKruskalResultado();
        tamanho = new HashMap<Vertice, Integer>();
        PriorityQueue<PairPriority<ArestaNaoDirigida>> fila = new PriorityQueue<PairPriority<ArestaNaoDirigida>>();

        //UFinit
        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            resultado.setPredecessor(v, v);
            tamanho.put(v, 1);
        }

        //Inicializa fila das arestas
        int qtdeArestas = g.getQtdeArestas();
        for (int i = 0; i < qtdeArestas; i++) {
            ArestaNaoDirigida a = g.getAresta(i);
            PairPriority<ArestaNaoDirigida> pAresta = new PairPriority<ArestaNaoDirigida>(a, a.getValor());

            fila.offer(pAresta);
        }

        int k = 0;
        while (fila.size() > 0 && k < tamanhoGrafo) {
            PairPriority<ArestaNaoDirigida> pAresta = fila.peek();
            fila.poll();

            ArestaNaoDirigida a = pAresta.getDado();

            Vertice findI = find(a.getVi());
            Vertice findJ = find(a.getVj(a.getVi()));

            if (!findI.equals(findJ)) {
                ufUnion(a.getVi(), a.getVj(a.getVi()), findI, findJ);

                resultado.addAresta(a);

                k++;

                resultado.addCustoTotal(pAresta.getCusto());
            }
        }

        //gera floresta
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            resultado.setPredecessor(v, null);
            resultado.setCusto(v, 0);
        }

        ArrayList<Aresta> arestas = resultado.getArestas();
        for (int i = 0; i < arestas.size(); i++) {
            ArestaNaoDirigida a = (ArestaNaoDirigida) arestas.get(i);
            if (resultado.getPredecessor(a.getVi()) == null) {
                resultado.setPredecessor(a.getVi(), a.getVj(a.getVi()));
                resultado.setCusto(a.getVi(), a.getValor());
                resultado.addDescendente(a.getVj(a.getVi()), a.getVi());
            } else {
                resultado.setPredecessor(a.getVj(a.getVi()), a.getVi());
                resultado.setCusto(a.getVj(a.getVi()), a.getValor());
                resultado.addDescendente(a.getVi(), a.getVj(a.getVi()));
            }
        }
    }
}
