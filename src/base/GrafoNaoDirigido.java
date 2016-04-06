package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import algoritmos.AlgoritmoBuscaLargura;
import algoritmos.AlgoritmoBuscaLarguraResultado;
import auxiliar.Constante;
import auxiliar.Pair;

/**
 * Classe GrafoNaoDirigido
 *
 * @author Maicon Rafael Zatelli
 *
 */
public class GrafoNaoDirigido extends Grafo {

    private final List<ArestaNaoDirigida> arestas = new ArrayList<ArestaNaoDirigida>();

    /**
     * Adiciona uma aresta ao grafo
     *
     * @param a
     *            Aresta
     * @throws Exception
     *             caso existe uma aresta com esta identificação
     */
    public void addAresta(ArestaNaoDirigida a) throws Exception {
        if (super.getArestaById(a.getId()) != null) {
            throw new Exception("Existe uma aresta com este identificador");
        }

        a.setGrafo(this);
        idArestas.put(a.getId(), a);

        super.addConexao(a.getVi(), a.getVj(a.getVi()));
        super.addConexao(a.getVj(a.getVi()), a.getVi());
        a.getVi().addAresta(a);
        a.getVj(a.getVi()).addAresta(a);
        arestas.add(a);
    }

    /**
     * Retorna a quantidade de arestas do grafo incluindo as geradas pelo próprio.
     *
     * @return Quantidade de arestas
     */
    @Override
    public int getQtdeArestas() {
        return arestas.size();
    }

    /**
     * Retorna uma aresta por sua posição
     *
     * @param i
     *            Posição
     * @return ArestaNaoDirigida
     */
    @Override
    public ArestaNaoDirigida getAresta(int i) {
        return arestas.get(i);
    }

    /**
     * Retorna se o grafo é regular.<br>
     * Um grafo é regular se todos os vértices possuem o mesmo grau.
     *
     * @return true ou false
     */
    @Override
    public boolean ehRegular() {
        int grau = getGrau(getUmVertice());
        int tamanhoGrafo = getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            if (grau != getGrau(getVertice(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Retorna se o grafo é conexo.<br>
     * Um grafo é conexo se for possível visitar qualquer vértice, partindo de um outro e passando por arestas.
     *
     * @return true ou false
     */
    @Override
    public boolean ehConexo() {
        AlgoritmoBuscaLargura alg = new AlgoritmoBuscaLargura();

        alg.executar(this, getUmVertice());

        AlgoritmoBuscaLarguraResultado res = alg.getResultado();

        int tamanhoGrafo = getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = getVertice(i);
            if (res.getVisitado(v) != true) {
                return false;
            }
        }
        return true;
    }

    protected int dfsBipartido(HashMap<Vertice, Integer> cor, Vertice u, int c) {
        cor.put(u, 1 - c);

        ArrayList<Vertice> adjU = getAdjacentes(u);
        for (Vertice v : adjU) {
            if (cor.get(v) == -1) {
                if (dfsBipartido(cor, v, 1 - c) == 0) {
                    return 0;
                }
            } else if (cor.get(v) == 1 - c) {
                return 0;
            }
        }
        return 1;
    }

    @Override
    protected HashMap<Vertice, Integer> calcBipartido() {
        HashMap<Vertice, Integer> cor = new HashMap<Vertice, Integer>();
        int tamanhoGrafo = getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = getVertice(i);
            cor.put(v, -1);
        }

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = getVertice(i);
            if (cor.get(v) == -1) {
                if (dfsBipartido(cor, v, 0) == 0) {
                    return null;
                }
            }
        }

        return cor;
    }

    /**
     * Apaga uma aresta por seu identificador
     *
     * @param id
     *            Identificador da aresta
     * @return quantidade de arestas apagadas
     */
    @Override
    public int delAresta(int id) {
        ArestaNaoDirigida a = (ArestaNaoDirigida) getArestaById(id);

        // remove todas as arestas do mesmo id
        int qtde = 0;
        while (arestas.contains(a)) {
            arestas.remove(a);
            qtde++;
        }

        // apaga aresta dos id
        idArestas.remove(a.getId());

        // apaga a aresta dos vertices
        a.getVi().delAresta(a);
        a.getVj(a.getVi()).delAresta(a);

        // verifica se encontra ainda alguma aresta u -> v
        boolean achou = false;
        int grauVertice = getGrau(a.getVi());
        for (int i = 0; i < grauVertice; i++) {
            ArestaNaoDirigida b = (ArestaNaoDirigida) a.getVi().getAresta(i);

            if (b.getVj(a.getVi()).equals(a.getVj(a.getVi()))) {
                achou = true;
                break;
            }
        }
        if (!achou) {
            delConexao(a.getVi(), a.getVj(a.getVi()));
            delConexao(a.getVj(a.getVi()), a.getVi());
        }

        return qtde;
    }

    /**
     * Retorna o tipo do grafo
     *
     * @return 2
     */
    @Override
    public int getTipo() {
        return 2;
    }

    /**
     * Retorna se o grafo é ciclo.<br>
     * Um grafo é ciclo quando possui um único vértice com um laço ou<br>
     * é um grafo simples e conexo onde todos os vértices e arestas formam um circuito.<br>
     *
     * @return true ou false
     */
    @Override
    public boolean ehCiclo() {
        if (ehRegular() && getGrau(getUmVertice()) == 2 && ehConexo()) {
            return true;
        }
        return false;
    }

    private boolean dfsCiclo(HashMap<Vertice, Integer> cor, HashMap<Vertice, Vertice> pred, Vertice u) {
        cor.put(u, 2);

        ArrayList<Vertice> adjU = getAdjacentes(u);
        for (Vertice v : adjU) {
            if (cor.get(v) == 1) {
                pred.put(v, u);
                if (dfsCiclo(cor, pred, v)) {
                    return true;
                }
            } else if (!v.equals(pred.get(u)) || cor.get(v) == 2 && v.equals(u)) {
                return true;
            }
        }

        cor.put(u, 3);
        return false;
    }

    /**
     * Retorna se o grafo é acíclico.<br>
     * Um grafo é acíclico se não possui nenhum ciclo
     *
     * @return true ou false
     */
    @Override
    public boolean ehAciclico() {
        HashMap<Vertice, Integer> cor = new HashMap<Vertice, Integer>();
        HashMap<Vertice, Vertice> pred = new HashMap<Vertice, Vertice>();
        int tamanhoGrafo = getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = getVertice(i);
            cor.put(v, 1);
            pred.put(v, v);
        }

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = getVertice(i);
            if (cor.get(v) == 1) {
                if (dfsCiclo(cor, pred, v)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Retorna se o grafo é Ã¡rvore.<br>
     * Um grafo é uma Ã¡rvore se não possui nenhum ciclo e é conexo
     *
     * @return true ou false
     */
    public boolean ehArvore() {
        if (ehConexo() && ehAciclico()) {
            return true;
        }
        return false;
    }

    /**
     * Retorna se o grafo é floresta.<br>
     * Um grafo é uma floresta se também é acíclico
     *
     * @return true ou false
     */
    @Override
    public boolean ehFloresta() {
        return ehAciclico();
    }

    /**
     * Retorna o grau
     *
     * @param v
     *            Vertice
     * @return grau
     */
    public int getGrau(Vertice v) {
        return v.getQtdeArestas();
    }

    /**
     * Retorna uma aresta por vértice inicial e final
     *
     * @param u
     *            Vertice origem
     * @param v
     *            Vertice destino
     * @return Aresta ou null
     */
    @Override
    public Aresta getArestaByVertices(Vertice u, Vertice v) {
        int qtdeArestas = getGrau(u);
        for (int j = 0; j < qtdeArestas; j++) {
            ArestaNaoDirigida a = (ArestaNaoDirigida) u.getAresta(j);

            if (a.getVj(u).equals(v)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Retorna o custo da aresta paralela u -> v de menor custo
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return Valor do menor custo
     */
    @Override
    public double getCustoMinimo(Vertice u, Vertice v) {
        double menor = Constante.INF;
        int i = u.getQtdeArestas() - 1;
        for (; i >= 0; i--) {
            ArestaNaoDirigida temp = (ArestaNaoDirigida) u.getAresta(i);
            if (temp.getVj(u).equals(v)) {
                if (temp.getValor() < menor) {
                    menor = temp.getValor();
                }
            }
        }
        return menor;
    }

    /**
     * Retorna a aresta paralela u -> v de menor custo
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return ArestaNaoDirigida do menor custo
     */
    @Override
    public ArestaNaoDirigida getArestaCustoMinimo(Vertice u, Vertice v) {
        double menor = Constante.INF;
        ArestaNaoDirigida aMenor = null;

        int i = u.getQtdeArestas() - 1;
        for (; i >= 0; i--) {
            ArestaNaoDirigida temp = (ArestaNaoDirigida) u.getAresta(i);
            if (temp.getVj(u).equals(v)) {
                if (temp.getValor() < menor) {
                    menor = temp.getValor();
                    aMenor = temp;
                }
            }
        }
        return aMenor;
    }

    /**
     * Retorna o custo da aresta paralela u -> v de menor custo
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return Valor do maior custo
     */
    @Override
    public double getCustoMaximo(Vertice u, Vertice v) {
        double maior = -Constante.INF;
        int i = u.getQtdeArestas() - 1;
        for (; i >= 0; i--) {
            ArestaNaoDirigida temp = (ArestaNaoDirigida) u.getAresta(i);
            if (temp.getVj(u).equals(v)) {
                if (temp.getValor() > maior) {
                    maior = temp.getValor();
                }
            }
        }
        return maior;
    }

    /**
     * Retorna a aresta paralela u -> v de menor custo
     *
     * @param u
     *            Vértice de origem
     * @param v
     *            Vértice de destino
     * @return ArestaNaoDirigida do maior custo
     */
    @Override
    public ArestaNaoDirigida getArestaCustoMaximo(Vertice u, Vertice v) {
        double maior = -Constante.INF;
        ArestaNaoDirigida aMaior = null;

        int i = u.getQtdeArestas() - 1;
        for (; i >= 0; i--) {
            ArestaNaoDirigida temp = (ArestaNaoDirigida) u.getAresta(i);
            if (temp.getVj(u).equals(v)) {
                if (temp.getValor() > maior) {
                    maior = temp.getValor();
                    aMaior = temp;
                }
            }
        }
        return aMaior;
    }

    /**
     * Retorna se existe aresta paralela a u -> v.<br>
     *
     * @param u
     *            Vertice origem
     * @param v
     *            Vertice destino
     * @return true ou false
     */
    @Override
    public boolean existeParalelismo(Vertice u, Vertice v) {
        HashMap<Vertice, Boolean> atingiu = new HashMap<Vertice, Boolean>();
        int qtdeArestas = getGrau(u);
        for (int j = 0; j < qtdeArestas; j++) {
            ArestaNaoDirigida a = (ArestaNaoDirigida) u.getAresta(j);

            if (atingiu.get(a.getVj(u)) != null && v.equals(a.getVj(u))) {
                return true;
            }

            atingiu.put(a.getVj(u), true);
        }

        return false;
    }

    /**
     * Retorna se o grafo é multigrafo.<br>
     * Um grafo é multigrafo se possui arestas paralelas ou laços.
     *
     * @return true ou false
     */
    @Override
    public boolean ehMultigrafo() {
        int tamanhoGrafo = getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice u = getVertice(i);

            HashMap<Vertice, Boolean> atingiu = new HashMap<Vertice, Boolean>();
            int qtdeArestas = getGrau(u);
            for (int j = 0; j < qtdeArestas; j++) {
                ArestaNaoDirigida a = (ArestaNaoDirigida) u.getAresta(j);

                if (atingiu.get(a.getVj(u)) != null) {
                    return true;
                }
                if (a.getVj(u).equals(u)) {
                    return true;
                }

                atingiu.put(a.getVj(u), true);
            }
        }

        return false;
    }

    /**
     * Retorna se o grafo é completo.<br>
     * Um grafo é completo se ele é simples e existe uma aresta ligando cada par de vértices distintos.
     *
     * @return true ou false
     */
    @Override
    public boolean ehCompleto() {
        if (ehMultigrafo()) {
            return false;
        }

        int tamanhoGrafo = getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice u = getVertice(i);

            // o grau de saida dos vertices eh o numero de vertices -1
            if (getGrau(u) != tamanhoGrafo - 1) {
                return false;
            }
        }

        return true;
    }

    /**
     * Retorna a lista de vértices adjacentes ao vértice
     *
     * @param v
     *            Vertice
     * @return Conjunto de vértices adjacentes
     */
    @Override
    public ArrayList<Vertice> getAdjacentes(Vertice v) {
        ArrayList<Vertice> adj = new ArrayList<Vertice>();
        HashMap<Vertice, Boolean> visitado = new HashMap<Vertice, Boolean>();

        int qtdeArestas = v.getQtdeArestas();
        for (int i = 0; i < qtdeArestas; i++) {
            ArestaNaoDirigida a = (ArestaNaoDirigida) v.getAresta(i);
            if (visitado.get(a.getVj(v)) == null) {
                visitado.put(a.getVj(v), true);
                adj.add(a.getVj(v));
            }
        }
        return adj;
    }

    /**
     * Apaga um vértice por seu identificador
     *
     * @param id
     *            Identificador do vértice
     */
    @Override
    public void delVertice(int id) {
        Vertice v = getVerticeById(id);

        while (vertices.contains(v)) {
            vertices.remove(v);
        }
        idVertices.remove(v.getId());

        int qtdeArestas = getQtdeArestas();
        for (int i = 0; i < qtdeArestas; i++) {
            ArestaNaoDirigida a = getAresta(i);
            int del = 0;
            if (a.getVi().equals(v) || a.getVj(a.getVi()).equals(v)) {
                del = delAresta(a.getId());
                qtdeArestas -= del;
            }
            i -= del;
            if (i < -1) {
                i = -1;
            }
        }
        v.limpaArestas();
    }

    /**
     * Retorna um grafo simples
     *
     * @param pblnMaximo
     *            O peso de uma aresta é o peso da aresta paralela de maior valor
     * @return Grafo
     */
    @Override
    public GrafoNaoDirigido getGrafoSimplificado(boolean pblnMaximo) {
        GrafoNaoDirigido g = new GrafoNaoDirigido();
        int tamanhoGrafo = getTamanho();

        for (int i = 0; i < tamanhoGrafo; i++) {
            try {
                g.addVertice(getVertice(i).clone());
            } catch (Exception e) {}
        }

        HashMap<Pair, Integer> pares = new HashMap<Pair, Integer>();
        int qtdeArestas = getQtdeArestas();
        int cAresta = 1;
        for (int i = 0; i < qtdeArestas; i++) {
            ArestaNaoDirigida aAnt = getAresta(i);

            Vertice viNovo = g.getVerticeById(aAnt.getVi().getId());
            Vertice vjNovo = g.getVerticeById(aAnt.getVj(aAnt.getVi()).getId());

            if (viNovo.equals(vjNovo)) {
                continue;
            }

            Pair par = new Pair(viNovo, vjNovo);
            if (pares.get(par) != null) {
                int idAresta = pares.get(par);

                ArestaNaoDirigida aAtual = (ArestaNaoDirigida) g.getArestaById(idAresta);

                aAtual.setCapacidade(aAtual.getCapacidade() + aAnt.getCapacidade());
                if (pblnMaximo) {
                    if (aAnt.getValor() > aAtual.getValor()) {
                        aAtual.setValor(aAnt.getValor());
                    }
                } else {
                    if (aAnt.getValor() < aAtual.getValor()) {
                        aAtual.setValor(aAnt.getValor());
                    }
                }

            } else {
                ArestaNaoDirigida aNova = new ArestaNaoDirigida(cAresta, viNovo, vjNovo);

                aNova.setCapacidade(aAnt.getCapacidade());
                aNova.setDado(aAnt.getDado());
                aNova.setValor(aAnt.getValor());
                aNova.setGrafo(g);

                try {
                    g.addAresta(aNova);
                    pares.put(par, cAresta);

                    par = new Pair(vjNovo, viNovo);
                    pares.put(par, cAresta);
                } catch (Exception e) {}

                cAresta++;
            }
        }

        return g;
    }

    /**
     * Retorna <code>true</code> se um grafo é hipercubo e <code>false</code> caso contrário.
     *
     * @return <code>true</code> se um grafo é hipercubo e <code>false</code> caso contrário
     */
    public boolean ehHipercubo() {
        int tamanho = getTamanho(), n;
        for (n = 0;; n++) {
            double pow = Math.pow(2, n);
            if (pow == tamanho) {
                break;
            }
            if (pow > tamanho) { // não é uma potência de n.
                return false;
            }
        }

        for (int i = 0; i < tamanho; i++) {
            Vertice vertice = getVertice(i);
            if (vertice.getQtdeArestas() != n) {
                return false;
            }

            // verifica a ausência de arestas paralelas e laços entre os vértices
            Set<Integer> set = new HashSet<Integer>();
            for (int j = 0; j < vertice.getQtdeArestas(); j++) {
                Aresta aresta = vertice.getAresta(j);
                Vertice verticeOposto = aresta.getVi().getId() == vertice.getId() ? aresta.getVj() : aresta.getVi();

                if (verticeOposto.getId() == vertice.getId()) {
                    return false;
                }
                if (set.contains(aresta.getVj().getId())) {
                    return false;
                }
            }
        }
        return true;
    }

    public PropriedadeIsomorfismoResultado ehIsomorfico(GrafoNaoDirigido g) {
        PropriedadeIsomorfismo isomorfismo = new PropriedadeIsomorfismo();
        return isomorfismo.ehIsomorfico(this, g);
    }

    public static class PropriedadeIsomorfismoResultado {

        private Map<Aresta, Aresta> relacao_a;
        private Map<Vertice, Vertice> relacao_v;

        public PropriedadeIsomorfismoResultado(Map<Aresta, Aresta> relacao_a, Map<Vertice, Vertice> relacao_v) {
            this.relacao_a = relacao_a;
            this.relacao_v = relacao_v;
        }

        public Map<Aresta, Aresta> getRelacaoArestas() {
            return relacao_a;
        }

        public Map<Vertice, Vertice> getRelacaoVertices() {
            return relacao_v;
        }

        @Override
        public String toString() {
            String s = "";
            s += "Relação dos vértices:\n";
            for (Entry<Vertice, Vertice> entry : relacao_v.entrySet()) {
                Vertice v1 = entry.getKey();
                Vertice v2 = entry.getValue();

                s += " ";
                s += v1.getDado() == null ? "(vértice não nomeado)" : v1.getDado();
                s += " <-> ";
                s += v2.getDado() == null ? "(vértice não nomeado)" : v2.getDado();
                s += "\n";
            }
            s += "Relação das arestas:\n";
            for (Entry<Aresta, Aresta> entry : relacao_a.entrySet()) {
                Aresta a1 = entry.getKey();
                Aresta a2 = entry.getValue();

                s += " ";
                s += a1.getDado() == null ? "(aresta não nomeado)" : a1.getDado();
                s += " <-> ";
                s += a2.getDado() == null ? "(aresta não nomeado)" : a2.getDado();
                s += "\n";
            }
            return s;
        }

    }

    private static class PropriedadeIsomorfismo {

        private Comparator<Vertice> ARESTAS_COMPARATOR = new Comparator<Vertice>() {

            @Override
            public int compare(Vertice v1, Vertice v2) {
                return v2.getQtdeArestas() - v1.getQtdeArestas();
            }

        };

        public PropriedadeIsomorfismoResultado ehIsomorfico(GrafoNaoDirigido g1, GrafoNaoDirigido g2) {
            // a) compara o tamanho
            if (g1.getTamanho() != g2.getTamanho()) {
                return null;
            }

            // b) compara as arestas
            if (g1.getQtdeArestas() != g2.getQtdeArestas()) {
                return null;
            }

            // c) compara o grau de cada aresta
            Vertice[] grau1 = new Vertice[g1.getTamanho()];
            for (int i = 0; i < grau1.length; i++) {
                grau1[i] = g1.getVertice(i);
            }
            Arrays.sort(grau1, ARESTAS_COMPARATOR);

            Vertice[] grau2 = new Vertice[g2.getTamanho()];
            for (int i = 0; i < grau2.length; i++) {
                grau2[i] = g2.getVertice(i);
            }
            Arrays.sort(grau2, ARESTAS_COMPARATOR);

            for (int i = 0; i < grau1.length; i++) {
                if (grau1[i].getQtdeArestas() != grau2[i].getQtdeArestas()) {
                    return null;
                }
            }

            // d) backtracking
            // para todos que tem o mesmo grau, verifica isomorfismo
            Vertice exemplo = grau1[0];
            for (int i = 0; exemplo.getQtdeArestas() == grau2[i].getQtdeArestas(); i++) {
                if (backtracking(g1, g2, exemplo, grau2[i], new ArrayList<Vertice>(), new ArrayList<Aresta>())) {
                    return new PropriedadeIsomorfismoResultado(relacao_a, relacao_v);
                }
            }
            return null;
        }

        // nota: se estiver no mapa, significa que já visitou ela

        // relação do vertice do grafo 1 para o vertice do grafo 2
        private Map<Vertice, Vertice> relacao_v = new HashMap<Vertice, Vertice>();

        // relação do vertice do grafo 1 para o vertice do grafo 2
        private Map<Aresta, Aresta> relacao_a = new HashMap<Aresta, Aresta>();

        private boolean backtracking(GrafoNaoDirigido g1, GrafoNaoDirigido g2, Vertice v1, Vertice v2, //
                                     List<Vertice> verticesAdicionados, List<Aresta> arestasAdicionadas) {
            // se já foi visitado, a relação deve ser a mesma
            if (relacao_v.containsKey(v1)) {
                return relacao_v.get(v1) == v2;
            }
            if (relacao_v.containsValue(v2)) {
                return false; // já existe relação para tal vértice
            }
            if (v1.getQtdeArestas() != v2.getQtdeArestas()) {
                return false;
            }

            // adicionamos ambos na mesma relação
            relacao_v.put(v1, v2);
            verticesAdicionados.add(v1);

            List<Vertice> _verticesAdicionados_1 = new ArrayList<>();
            List<Aresta> _arestasAdicionadas_1 = new ArrayList<>();

            // comparamos todas as arestas agora que ainda não foram visitadas
            out: for (int i = 0; i < v1.getQtdeArestas(); i++) {
                Aresta aresta1 = v1.getAresta(i);
                if (relacao_a.containsKey(aresta1)) {
                    continue; // aresta já visitada, então continua
                }

                // procura por todas as arestas não visitadas
                for (int j = 0; j < v2.getQtdeArestas(); j++) {
                    Aresta aresta2 = v2.getAresta(j);
                    if (relacao_a.containsValue(aresta2)) {
                        continue; // aresta já visitada, então continua
                    }

                    // se ambos possuem o mesmo grau, continua a busca
                    Vertice v1_j = otherside(aresta1, v1);
                    Vertice v2_j = otherside(aresta2, v2);
                    if (v1_j.getQtdeArestas() != v2_j.getQtdeArestas()) {
                        continue;
                    }

                    List<Vertice> _verticesAdicionados_2 = new ArrayList<>();
                    List<Aresta> _arestasAdicionadas_2 = new ArrayList<>();

                    relacao_a.put(aresta1, aresta2);
                    arestasAdicionadas.add(aresta1);

                    if (backtracking(g1, g2, v1_j, v2_j, _verticesAdicionados_2, _arestasAdicionadas_2)) {
                        _verticesAdicionados_1.addAll(_verticesAdicionados_2);
                        _arestasAdicionadas_1.addAll(_arestasAdicionadas_2);
                        continue out;
                    }

                    // desfaz o backtracking
                    for (Vertice vertice : _verticesAdicionados_2) {
                        relacao_v.remove(vertice);
                    }
                    for (Aresta aresta : _arestasAdicionadas_2) {
                        relacao_a.remove(aresta);
                    }
                }

                // se chegar aqui, então não encontrou caminho para válido
                // para a aresta, desfaz e continua
                for (Vertice vertice : _verticesAdicionados_1) {
                    relacao_v.remove(vertice);
                }
                for (Aresta aresta : _arestasAdicionadas_1) {
                    relacao_a.remove(aresta);
                }
                return false;
            }

            // se exauriu suas possiblidades, retorna true
            for (int i = 0; i < v1.getQtdeArestas(); i++) {
                Aresta a1 = v1.getAresta(i);
                if (!relacao_a.containsKey(a1)) {
                    return false;
                }
                Aresta a2 = v2.getAresta(i);
                if (!relacao_a.containsValue(a2)) {
                    return false;
                }
            }

            // fim da execução. Todos as relações preenchidas
            return true;
        }

        private static Vertice otherside(Aresta aresta, Vertice base) {
            return (aresta.getVi() == base) ? aresta.getVj() : aresta.getVi();
        }

    }

    // TODO criado
    @Override
    public GrafoNaoDirigido clone() {
        // TODO Auto-generated method stub
        return (GrafoNaoDirigido) super.clone();
    }

}
