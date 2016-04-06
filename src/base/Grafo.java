package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import auxiliar.Pair;

/**
 * Classe Grafo
 *
 * @author Maicon Rafael Zatelli
 *
 */
public abstract class Grafo implements Cloneable {

    protected List<Vertice> vertices = new ArrayList<Vertice>();
    protected Map<Integer, Vertice> idVertices = new HashMap<Integer, Vertice>();
    protected Map<Integer, Aresta> idArestas = new HashMap<Integer, Aresta>();
    private final Map<Pair<Vertice, Vertice>, Boolean> mapAdjacente = new HashMap<Pair<Vertice, Vertice>, Boolean>();

    /**
     * Adiciona um vértice ao grafo
     *
     * @param v
     *            Vertice
     * @throws Exception
     *             caso existe um vértice com esta identificação
     */
    public void addVertice(Vertice v) throws Exception {
        if (getVerticeById(v.getId()) != null) {
            throw new Exception("Existe um vertice com este identificador");
        }
        idVertices.put(v.getId(), v);
        vertices.add(v);
    }

    /**
     * Retorna um vértice pelo seu identificador
     *
     * @param id
     *            Identificador do vértice
     * @return Vertice ou null
     */
    public Vertice getVerticeById(int id) {
        return idVertices.get(id);
    }

    /**
     * Retorna uma aresta pelo seu identificador
     *
     * @param id
     *            Identificador da aresta
     * @return Aresta ou null
     */
    public Aresta getArestaById(int id) {
        return idArestas.get(id);
    }

    /**
     * Apaga um vértice por seu identificador
     *
     * @param id
     *            Identificador do vértice
     */
    public abstract void delVertice(int id);

    /**
     * Retorna o tamanho do grafo
     *
     * @return Quantidade de vértices
     */
    public int getTamanho() {
        return vertices.size();
    }

    /**
     * Retorna a quantidade de arestas do grafo incluindo as geradas pelo próprio.
     *
     * @return Quantidade de arestas
     */
    public abstract int getQtdeArestas();

    /**
     * Retorna um vértice por sua posição
     *
     * @param i
     *            Posição
     * @return Vertice
     */
    public Vertice getVertice(int i) {
        return vertices.get(i);
    }

    /**
     * Adiciona uma conexão u -> v
     *
     * @param u Vértice de origem
     * @param v Vértice de destino
     */
    protected void addConexao(Vertice u, Vertice v) {
        Pair<Vertice, Vertice> par = new Pair<Vertice, Vertice>(u, v);
        mapAdjacente.put(par, true);
    }

    /**
     * Apaga uma conexão u -> v
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     */
    protected void delConexao(Vertice u, Vertice v) {
        Pair<Vertice, Vertice> par = new Pair<Vertice, Vertice>(u, v);
        mapAdjacente.remove(par);
    }

    /**
     * Retorna se existe uma conexão u -> v
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return true ou false
     */
    public boolean ehAdjacente(Vertice u, Vertice v) {
        Pair<Vertice, Vertice> par = new Pair<Vertice, Vertice>(u, v);
        return mapAdjacente.get(par) != null;
    }

    /**
     * Retorna uma aresta por sua posição
     *
     * @param i
     *            Posição
     * @return Aresta
     */
    public abstract Aresta getAresta(int i);

    /**
     * Retorna a lista de vértices adjacentes ao vértice
     *
     * @param v
     *            Vertice
     * @return Conjunto de vértices adjacentes
     */
    public abstract List<Vertice> getAdjacentes(Vertice v);

    /**
     * Retorna se o grafo é trivial.<br>
     * Um grafo é trivial se contém apenas um vértice e nenhuma aresta.
     *
     * @return true ou false
     */
    public boolean ehTrivial() {
        return getTamanho() == 1 && getQtdeArestas() == 0;
    }

    /**
     * Retorna se o grafo é nulo.<br>
     * Um grafo é nulo se o conjunto de arestas é vazio.
     *
     * @return true ou false
     */
    public boolean ehNulo() {
        return getQtdeArestas() == 0;
    }

    /**
     * Retorna se o grafo é simples.<br>
     * Um grafo é simples se não possui arestas paralelas e laços.
     *
     * @return true ou false
     */
    public boolean ehSimples() {
        return !ehMultigrafo();
    }

    /**
     * Retorna se o grafo é completo.<br>
     * Um grafo é completo se ele é simples e existe uma aresta ligando cada par de vértices distintos.
     *
     * @return true ou false
     */
    public abstract boolean ehCompleto();

    /**
     * Retorna se o grafo é multigrafo.<br>
     * Um grafo é multigrafo se possui arestas paralelas ou laços.
     *
     * @return true ou false
     */
    public abstract boolean ehMultigrafo();

    /**
     * Retorna se existe aresta paralela a u -> v.<br>
     *
     * @param u
     *            Vertice origem
     * @param v
     *            Vertice destino
     * @return true ou false
     */
    public abstract boolean existeParalelismo(Vertice u, Vertice v);

    /**
     * Retorna o custo da aresta paralela u -> v de menor custo
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return Valor do menor custo
     */
    public abstract double getCustoMinimo(Vertice u, Vertice v);

    /**
     * Retorna a aresta paralela u -> v de menor custo
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return Aresta do menor custo
     */
    public abstract Aresta getArestaCustoMinimo(Vertice u, Vertice v);

    /**
     * Retorna o custo da aresta paralela u -> v de menor custo
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return Valor do maior custo
     */
    public abstract double getCustoMaximo(Vertice u, Vertice v);

    /**
     * Retorna a aresta paralela u -> v de menor custo
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return Aresta do maior custo
     */
    public abstract Aresta getArestaCustoMaximo(Vertice u, Vertice v);

    /**
     * Retorna uma aresta por vértice inicial e final
     *
     * @param u
     *            Vertice origem
     * @param v
     *            Vertice destino
     * @return Aresta ou null
     */
    public abstract Aresta getArestaByVertices(Vertice u, Vertice v);

    protected abstract HashMap<Vertice, Integer> calcBipartido();

    /**
     * Retorna se o grafo é bipartido.<br>
     * Um grafo é bipartido se seu conjunto de vértices pode ser dividido em dois conjunetos <b>X</b> e <b>Y</b> tal que
     * cada aresta tem uma ponta em <b>X</b> e outra em <b>Y</b> e/ou vice-versa.
     *
     * @return true ou false
     */
    public boolean ehBipartido() {
        return calcBipartido() != null;
    }

    /**
     * Retorna se o grafo é bipartido completo.<br>
     * Um grafo é bipartido completo se para cada vértice <b>u</b> pertencente a <b>X</b> e para todo <b>v</b>
     * pertencente a <b>Y</b> existe uma aresta.
     *
     * @return true ou false
     */
    public boolean ehBipartidoCompleto() {
        HashMap<Vertice, Integer> cor = calcBipartido();
        if (cor == null) {
            return false;
        }

        Vector<ArrayList<Vertice>> conjuntos = new Vector<ArrayList<Vertice>>();

        ArrayList<Vertice> temp = new ArrayList<Vertice>();
        conjuntos.add(temp);
        temp = new ArrayList<Vertice>();
        conjuntos.add(temp);

        int tamanhoGrafo = getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = getVertice(i);

            conjuntos.get(cor.get(v)).add(v);
            // System.out.println(v.getDado() + " " + cor.get(v));
        }

        int qtdeCor0 = conjuntos.get(0).size();
        int qtdeCor1 = conjuntos.get(1).size();

        if (qtdeCor0 == 0 || qtdeCor1 == 0) {
            return false;
        }

        for (int i = 0; i < qtdeCor0; i++) {
            Vertice u = conjuntos.get(0).get(i);
            for (int j = 0; j < qtdeCor1; j++) {
                Vertice v = conjuntos.get(1).get(j);

                if (!ehAdjacente(u, v)) {
                    return false;
                }
                if (!ehAdjacente(v, u)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Retorna se o grafo é denso.<br>
     * Um grafo é denso se o total de arestas é maior ou igual ao quadrado da quantidade de vértices.
     *
     * @return true ou false
     */
    public boolean ehDenso() {
        int m = getQtdeArestas();
        int n = getTamanho();

        return m >= (n * n);
    }

    /**
     * Retorna se o grafo é esparso.<br>
     * Um grafo é esparso se o total de arestas é menor que o quadrado da quantidade de vértices.
     *
     * @return true ou false
     */
    public boolean ehEsparso() {
        return !ehDenso();
    }

    /**
     * Retorna se o grafo é desconexo.<br>
     * Um grafo é desconexo se não for possível visitar qualquer vértice, partindo de um outro e passando por arestas.
     *
     * @return true ou false
     */
    public boolean ehDesconexo() {
        return !ehConexo();
    }

    /**
     * Retorna se o grafo é dirigido
     *
     * @return true ou false
     */
    public boolean ehDirigido() {
        return getTipo() == 1;
    }

    /**
     * Retorna se o grafo é conexo.<br>
     * Um grafo é conexo se for possível visitar qualquer vértice, partindo de um outro e passando por arestas.
     *
     * @return true ou false
     */
    public abstract boolean ehConexo();

    /**
     * Retorna se o grafo é regular.<br>
     * Um grafo é regular se todos os vértices possuem o mesmo grau.
     *
     * @return true ou false
     */
    public abstract boolean ehRegular();

    /**
     * Retorna o tipo do grafo
     *
     * @return 1 se dirigido e 2 se não dirigido
     */
    public abstract int getTipo();

    /**
     * Apaga uma aresta por seu identificador
     *
     * @param id
     *            Identificador da aresta
     * @return quantidade de arestas apagadas
     */
    public abstract int delAresta(int id);

    /**
     * Retorna um vértice qualquer
     *
     * @return Vértice
     */
    public Vertice getUmVertice() {
        return getVertice(0);
    }

    /**
     * Retorna se o grafo é ciclo.<br>
     * Um grafo é ciclo quando possui um único vértice com um laço ou<br>
     * é um grafo simples e conexo onde todos os vértices e arestas formam um circuito.<br>
     *
     * @return true ou false
     */
    public abstract boolean ehCiclo();

    /**
     * Retorna se o grafo é acíclico.<br>
     * Um grafo é acíclico se não possui nenhum ciclo
     *
     * @return true ou false
     */
    public abstract boolean ehAciclico();

    /**
     * Retorna se o grafo é floresta.<br>
     * Um grafo é uma floresta se também é acíclico
     *
     * @return true ou false
     */
    public abstract boolean ehFloresta();

    /**
     * Retorna um grafo simples
     *
     * @param pblnMaximo
     *            O peso de uma aresta é o peso da aresta paralela de maior valor
     * @return Grafo
     */
    public abstract Grafo getGrafoSimplificado(boolean pblnMaximo);

    /**
     * Retorna um mapa com uma cor associada para cada vértice.
     * <p>
     * O algoritmo utilizado é baseado em uma solução gulosa que não traz o resultado ótimo, porém resultados
     * aproximados.
     *
     * @return um mapa com uma cor associada para cada vértice
     */
    public Map<Vertice, Integer> getNumeroCromatico() {
        if (getTamanho() == 0) {
            return Collections.emptyMap();
        }

        Map<Vertice, Integer> cores = new LinkedHashMap<>();
        cores.put(getVertice(0), 0); // atribui a cor inicial

        boolean[] coresUtilizadas = new boolean[getTamanho()];

        for (int u = 1; u < getTamanho(); u++) {
            // encontra todas as cores dos vértices adjacentes já coloridos
            List<Vertice> adjacentes = getAdjacentes(getVertice(u));
            for (Vertice adjacente : adjacentes) {
                if (cores.containsKey(adjacente)) {
                    coresUtilizadas[cores.get(adjacente)] = true;
                }
            }

            // encontra a primeira cor disponível
            int cor;
            for (cor = 0; cor < getTamanho(); cor++) {
                if (!coresUtilizadas[cor]) {
                    break;
                }
            }
            cores.put(getVertice(u), cor); // atribui a cor encontrada

            Arrays.fill(coresUtilizadas, false);
        }
        return cores;
    }

    @Override
    public Grafo clone() {
        try {
            return (Grafo) super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

}
