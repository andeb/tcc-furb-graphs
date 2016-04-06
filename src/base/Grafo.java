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
     * Adiciona um v�rtice ao grafo
     *
     * @param v
     *            Vertice
     * @throws Exception
     *             caso existe um v�rtice com esta identifica��o
     */
    public void addVertice(Vertice v) throws Exception {
        if (getVerticeById(v.getId()) != null) {
            throw new Exception("Existe um vertice com este identificador");
        }
        idVertices.put(v.getId(), v);
        vertices.add(v);
    }

    /**
     * Retorna um v�rtice pelo seu identificador
     *
     * @param id
     *            Identificador do v�rtice
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
     * Apaga um v�rtice por seu identificador
     *
     * @param id
     *            Identificador do v�rtice
     */
    public abstract void delVertice(int id);

    /**
     * Retorna o tamanho do grafo
     *
     * @return Quantidade de v�rtices
     */
    public int getTamanho() {
        return vertices.size();
    }

    /**
     * Retorna a quantidade de arestas do grafo incluindo as geradas pelo pr�prio.
     *
     * @return Quantidade de arestas
     */
    public abstract int getQtdeArestas();

    /**
     * Retorna um v�rtice por sua posi��o
     *
     * @param i
     *            Posi��o
     * @return Vertice
     */
    public Vertice getVertice(int i) {
        return vertices.get(i);
    }

    /**
     * Adiciona uma conex�o u -> v
     *
     * @param u V�rtice de origem
     * @param v V�rtice de destino
     */
    protected void addConexao(Vertice u, Vertice v) {
        Pair<Vertice, Vertice> par = new Pair<Vertice, Vertice>(u, v);
        mapAdjacente.put(par, true);
    }

    /**
     * Apaga uma conex�o u -> v
     *
     * @param u
     *            V�rtice de origem
     * @param v
     *            V�rtice de destino
     */
    protected void delConexao(Vertice u, Vertice v) {
        Pair<Vertice, Vertice> par = new Pair<Vertice, Vertice>(u, v);
        mapAdjacente.remove(par);
    }

    /**
     * Retorna se existe uma conex�o u -> v
     *
     * @param u
     *            V�rtice de origem
     * @param v
     *            V�rtice de destino
     * @return true ou false
     */
    public boolean ehAdjacente(Vertice u, Vertice v) {
        Pair<Vertice, Vertice> par = new Pair<Vertice, Vertice>(u, v);
        return mapAdjacente.get(par) != null;
    }

    /**
     * Retorna uma aresta por sua posi��o
     *
     * @param i
     *            Posi��o
     * @return Aresta
     */
    public abstract Aresta getAresta(int i);

    /**
     * Retorna a lista de v�rtices adjacentes ao v�rtice
     *
     * @param v
     *            Vertice
     * @return Conjunto de v�rtices adjacentes
     */
    public abstract List<Vertice> getAdjacentes(Vertice v);

    /**
     * Retorna se o grafo � trivial.<br>
     * Um grafo � trivial se cont�m apenas um v�rtice e nenhuma aresta.
     *
     * @return true ou false
     */
    public boolean ehTrivial() {
        return getTamanho() == 1 && getQtdeArestas() == 0;
    }

    /**
     * Retorna se o grafo � nulo.<br>
     * Um grafo � nulo se o conjunto de arestas � vazio.
     *
     * @return true ou false
     */
    public boolean ehNulo() {
        return getQtdeArestas() == 0;
    }

    /**
     * Retorna se o grafo � simples.<br>
     * Um grafo � simples se n�o possui arestas paralelas e la�os.
     *
     * @return true ou false
     */
    public boolean ehSimples() {
        return !ehMultigrafo();
    }

    /**
     * Retorna se o grafo � completo.<br>
     * Um grafo � completo se ele � simples e existe uma aresta ligando cada par de v�rtices distintos.
     *
     * @return true ou false
     */
    public abstract boolean ehCompleto();

    /**
     * Retorna se o grafo � multigrafo.<br>
     * Um grafo � multigrafo se possui arestas paralelas ou la�os.
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
     *            V�rtice de origem
     * @param v
     *            V�rtice de destino
     * @return Valor do menor custo
     */
    public abstract double getCustoMinimo(Vertice u, Vertice v);

    /**
     * Retorna a aresta paralela u -> v de menor custo
     *
     * @param u
     *            V�rtice de origem
     * @param v
     *            V�rtice de destino
     * @return Aresta do menor custo
     */
    public abstract Aresta getArestaCustoMinimo(Vertice u, Vertice v);

    /**
     * Retorna o custo da aresta paralela u -> v de menor custo
     *
     * @param u
     *            V�rtice de origem
     * @param v
     *            V�rtice de destino
     * @return Valor do maior custo
     */
    public abstract double getCustoMaximo(Vertice u, Vertice v);

    /**
     * Retorna a aresta paralela u -> v de menor custo
     *
     * @param u
     *            V�rtice de origem
     * @param v
     *            V�rtice de destino
     * @return Aresta do maior custo
     */
    public abstract Aresta getArestaCustoMaximo(Vertice u, Vertice v);

    /**
     * Retorna uma aresta por v�rtice inicial e final
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
     * Retorna se o grafo � bipartido.<br>
     * Um grafo � bipartido se seu conjunto de v�rtices pode ser dividido em dois conjunetos <b>X</b> e <b>Y</b> tal que
     * cada aresta tem uma ponta em <b>X</b> e outra em <b>Y</b> e/ou vice-versa.
     *
     * @return true ou false
     */
    public boolean ehBipartido() {
        return calcBipartido() != null;
    }

    /**
     * Retorna se o grafo � bipartido completo.<br>
     * Um grafo � bipartido completo se para cada v�rtice <b>u</b> pertencente a <b>X</b> e para todo <b>v</b>
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
     * Retorna se o grafo � denso.<br>
     * Um grafo � denso se o total de arestas � maior ou igual ao quadrado da quantidade de v�rtices.
     *
     * @return true ou false
     */
    public boolean ehDenso() {
        int m = getQtdeArestas();
        int n = getTamanho();

        return m >= (n * n);
    }

    /**
     * Retorna se o grafo � esparso.<br>
     * Um grafo � esparso se o total de arestas � menor que o quadrado da quantidade de v�rtices.
     *
     * @return true ou false
     */
    public boolean ehEsparso() {
        return !ehDenso();
    }

    /**
     * Retorna se o grafo � desconexo.<br>
     * Um grafo � desconexo se n�o for poss�vel visitar qualquer v�rtice, partindo de um outro e passando por arestas.
     *
     * @return true ou false
     */
    public boolean ehDesconexo() {
        return !ehConexo();
    }

    /**
     * Retorna se o grafo � dirigido
     *
     * @return true ou false
     */
    public boolean ehDirigido() {
        return getTipo() == 1;
    }

    /**
     * Retorna se o grafo � conexo.<br>
     * Um grafo � conexo se for poss�vel visitar qualquer v�rtice, partindo de um outro e passando por arestas.
     *
     * @return true ou false
     */
    public abstract boolean ehConexo();

    /**
     * Retorna se o grafo � regular.<br>
     * Um grafo � regular se todos os v�rtices possuem o mesmo grau.
     *
     * @return true ou false
     */
    public abstract boolean ehRegular();

    /**
     * Retorna o tipo do grafo
     *
     * @return 1 se dirigido e 2 se n�o dirigido
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
     * Retorna um v�rtice qualquer
     *
     * @return V�rtice
     */
    public Vertice getUmVertice() {
        return getVertice(0);
    }

    /**
     * Retorna se o grafo � ciclo.<br>
     * Um grafo � ciclo quando possui um �nico v�rtice com um la�o ou<br>
     * � um grafo simples e conexo onde todos os v�rtices e arestas formam um circuito.<br>
     *
     * @return true ou false
     */
    public abstract boolean ehCiclo();

    /**
     * Retorna se o grafo � ac�clico.<br>
     * Um grafo � ac�clico se n�o possui nenhum ciclo
     *
     * @return true ou false
     */
    public abstract boolean ehAciclico();

    /**
     * Retorna se o grafo � floresta.<br>
     * Um grafo � uma floresta se tamb�m � ac�clico
     *
     * @return true ou false
     */
    public abstract boolean ehFloresta();

    /**
     * Retorna um grafo simples
     *
     * @param pblnMaximo
     *            O peso de uma aresta � o peso da aresta paralela de maior valor
     * @return Grafo
     */
    public abstract Grafo getGrafoSimplificado(boolean pblnMaximo);

    /**
     * Retorna um mapa com uma cor associada para cada v�rtice.
     * <p>
     * O algoritmo utilizado � baseado em uma solu��o gulosa que n�o traz o resultado �timo, por�m resultados
     * aproximados.
     *
     * @return um mapa com uma cor associada para cada v�rtice
     */
    public Map<Vertice, Integer> getNumeroCromatico() {
        if (getTamanho() == 0) {
            return Collections.emptyMap();
        }

        Map<Vertice, Integer> cores = new LinkedHashMap<>();
        cores.put(getVertice(0), 0); // atribui a cor inicial

        boolean[] coresUtilizadas = new boolean[getTamanho()];

        for (int u = 1; u < getTamanho(); u++) {
            // encontra todas as cores dos v�rtices adjacentes j� coloridos
            List<Vertice> adjacentes = getAdjacentes(getVertice(u));
            for (Vertice adjacente : adjacentes) {
                if (cores.containsKey(adjacente)) {
                    coresUtilizadas[cores.get(adjacente)] = true;
                }
            }

            // encontra a primeira cor dispon�vel
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
