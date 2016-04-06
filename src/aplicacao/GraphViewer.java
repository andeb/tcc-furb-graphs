package aplicacao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import persistencia.DefinicaoGrafoVisual;
import persistencia.PersistenciaVisual;
import algoritmos.AlgoritmoBellmanFord;
import algoritmos.AlgoritmoBellmanFordResultado;
import algoritmos.AlgoritmoBuscaLargura;
import algoritmos.AlgoritmoBuscaLarguraResultado;
import algoritmos.AlgoritmoBuscaProfundidade;
import algoritmos.AlgoritmoBuscaProfundidadeResultado;
import algoritmos.AlgoritmoCaminhoEuleriano;
import algoritmos.AlgoritmoCaminhoEulerianoResultado;
import algoritmos.AlgoritmoCicloHamiltoniano;
import algoritmos.AlgoritmoCicloHamiltonianoResultado;
import algoritmos.AlgoritmoDijkstra;
import algoritmos.AlgoritmoDijkstraResultado;
import algoritmos.AlgoritmoFloydWarshall;
import algoritmos.AlgoritmoFloydWarshallResultado;
import algoritmos.AlgoritmoFordFulkerson;
import algoritmos.AlgoritmoFordFulkersonResultado;
import algoritmos.AlgoritmoHopcroftTarjan;
import algoritmos.AlgoritmoHopcroftTarjanResultado;
import algoritmos.AlgoritmoKruskal;
import algoritmos.AlgoritmoKruskalResultado;
import algoritmos.AlgoritmoOrdenacaoTopologica;
import algoritmos.AlgoritmoOrdenacaoTopologicaResultado;
import algoritmos.AlgoritmoPontes;
import algoritmos.AlgoritmoPontesResultado;
import algoritmos.AlgoritmoPrim;
import algoritmos.AlgoritmoPrimResultado;
import base.Aresta;
import base.ArestaDirigida;
import base.ArestaNaoDirigida;
import base.Grafo;
import base.GrafoDirigido;
import base.GrafoNaoDirigido;
import base.GrafoNaoDirigido.PropriedadeIsomorfismoResultado;
import base.Vertice;

public class GraphViewer extends JComponent {

    private static final int WIDTH = 900;
    private static final int HIGH = 480;
    private static final int RADIUS = 18;
    private final ControlPanel control = new ControlPanel();
    private int radius = RADIUS;
    private List<VerticeVisual> vertexs = new ArrayList<VerticeVisual>();
    private final List<VerticeVisual> selected = new ArrayList<VerticeVisual>();
    private List<ArestaVisual> edges = new ArrayList<ArestaVisual>();
    private Point mousePoint = new Point(WIDTH / 2, HIGH / 2); // FIXME desnecessário inicializar?
    private final Rectangle mouseRect = new Rectangle();
    private boolean selecting = false;

    private JButton btnDeleteEdge;
    private JButton btnDeleteVertex;
    private JButton btnConectar;
    private JLabel labelValorVertice;
    private JLabel labelValorAresta;
    private JLabel labelDirecao;
    private JScrollPane scrollPane;
    private JLabel vertexNameLabel;
    private JLabel labelNameAresta;
    private JTextField vertexValueField;
    private JTextField vertexNameField;
    private JTextField nameArestaField;
    private JTextField edgeValueField;
    private JComboBox<String> direcaoComboField;
    private JLabel statusLabel;
    private JSpinner jspinner;

    private static AlgoritmoDesenho algoritmoDesenho = new AlgoritmoDesenho();
    private static IAlgoritmoExecutor algoritmoExecutor;

    private static class AlgoritmoDesenho {

        Set<VerticeVisual> verticesMarcados = new LinkedHashSet<VerticeVisual>();
        Color corVerticesMarcados;
        Map<VerticeVisual, Color> coresVertices = new HashMap<VerticeVisual, Color>();

        Set<ArestaVisual> arestasMarcadas = new LinkedHashSet<ArestaVisual>();
        Color corArestasMarcadas;
        Map<ArestaVisual, Color> coresArestas = new HashMap<ArestaVisual, Color>();

        public AlgoritmoDesenho() {
            reset();
        }

        public void reset() {
            verticesMarcados.clear();
            coresVertices.clear();
            arestasMarcadas.clear();
            coresArestas.clear();
            corVerticesMarcados = Color.green;
            corArestasMarcadas = Color.green;
        }

    }

    private static class ResultadoFrame extends JFrame {

        public ResultadoFrame(String titulo, String resultado) {
            setTitle(titulo);
            setSize(500, 300);
            add(new JScrollPane(new JTextArea(resultado)));
            //  pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }

    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame f = new JFrame("FURB Graphs: uma aplicação para teoria dos grafos");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GraphViewer gp = new GraphViewer();
                f.add(gp.control, BorderLayout.NORTH);
                gp.scrollPane = new JScrollPane(gp);
                gp.addKeyShortcut();

                f.add(gp.scrollPane, BorderLayout.CENTER);

                // create the status bar panel and shove it down the bottom of the frame
                JPanel statusPanel = new JPanel();
                statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                f.add(statusPanel, BorderLayout.SOUTH);
                statusPanel.setPreferredSize(new Dimension(f.getWidth(), 16));
                statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
                gp.statusLabel = new JLabel("");
                gp.statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
                gp.updateFooter();
                statusPanel.add(gp.statusLabel);

                JMenuBar menuBar = new JMenuBar();
                {
                    //Build the first menu.
                    JMenu menu = new JMenu("Arquivo");
                    //    menu.setMnemonic(KeyEvent.VK_A);
                    //    menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");

                    JMenuItem menuItemSalvarGrafo = new JMenuItem("Salvar grafo...", KeyEvent.VK_S);
                    //    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
                    //    menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
                    menuItemSalvarGrafo.addActionListener(gp.new SalvarGrafoAction());
                    JMenuItem menuItemCarregarGrafo = new JMenuItem("Carregar grafo...", KeyEvent.VK_C);
                    //    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
                    //    menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
                    menuItemCarregarGrafo.addActionListener(gp.new CarregarGrafoAction());

                    menu.add(menuItemSalvarGrafo);
                    menu.add(menuItemCarregarGrafo);

                    menuBar.add(menu);
                }
                {
                    gp.menuPropriedades(menuBar);
                    gp.menuAlgoritmos(menuBar);
                    gp.menuSobre(menuBar);
                }
                f.setJMenuBar(menuBar);

                f.pack();
                // f.setLocationByPlatform(true);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }

        });
    }

    /**
     * @param menuBar
     */
    private void menuSobre(JMenuBar menuBar) {
        /*
         * Menu Propriedades
         */
        JMenu mnuAlgoritmos = new JMenu("Ajuda");

        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Sobre");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //1. Create the frame.
                    JFrame frame = new JFrame("Sobre Furb Graphs");
                    frame.setSize(400, 300);

                    String texto =
                            /**/"FURB Graphs" +
                                    /**/"\r\n" +
                                    /**/"\r\n" +
                                    /**/"Trabalho de Conclusão de Curso desenvolvido para obtenção do grau de " +
                                    /**/"\r\nbacharelado do curso de Ciência da Computação do Centro de Ciências " +
                                    /**/"\r\nExatas e Naturais da Universidade Regional de Blumenau." +
                                    /**/"\r\n" +
                                    /**/"\r\n" +
                                    /**/"Trabalho inicialmente desenvolvido por Maicon Rafael Zatelli (FURB, 2010)." +
                                    /**/"\r\nPosteriomente complementado por Anderson de Borba (FURB, 2014).";
                    JTextArea emptyLabel = new JTextArea(texto);
                    emptyLabel.setEditable(false);
                    //3. Create components and put them in the frame.
                    //...create emptyLabel...
                    frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

                    //4. Size the frame.
                    frame.pack();
                    frame.setLocationRelativeTo(null);

                    //5. Show it.
                    frame.setVisible(true);
                }

            });
        }

        menuBar.add(mnuAlgoritmos);
    }

    private void menuAlgoritmos(JMenuBar menuBar) {
        /*
         * Menu Propriedades
         */
        JMenu mnuAlgoritmos = new JMenu("Algoritmos");

        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Limpar Execução");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoDesenho.reset();
                    GraphViewer.this.repaint();
                    algoritmoExecutor = null;
                }

            });
        }
        {
            mnuAlgoritmos.add(new JSeparator());
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Bellman-Ford");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = new IAlgoritmoExecutor() {

                        boolean messageV1 = false;

                        VerticeVisual v1, v2;

                        @Override
                        public void nextStep(VerticeVisual... vertices) {
                            if (!messageV1) {
                                info("Selecione o vértice inicial.");
                                messageV1 = true;
                                return;
                            }

                            if (vertices != null && vertices.length == 1) {
                                if (v1 == null) {
                                    v1 = vertices[0];
                                    info("Selecione o vértice final.");

                                    algoritmoDesenho.coresVertices.put(v1, Color.MAGENTA);
                                    GraphViewer.this.repaint();

                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                } else {
                                    if (v1 == vertices[0]) {
                                        info("Vértice inicial e final devem ser diferentes");
                                        return;
                                    }
                                    v2 = vertices[0];

                                    Grafo g = g();

                                    Vertice _v1 = g.getVerticeById(v1.id);
                                    Vertice _v2 = g.getVerticeById(v2.id);

                                    AlgoritmoBellmanFord bellman = new AlgoritmoBellmanFord();
                                    bellman.executar(g, _v1);

                                    AlgoritmoBellmanFordResultado resultado = bellman.getResultado();

                                    List<Vertice> result_vertex = new ArrayList<>();

                                    Vertice predecessor = _v2;
                                    algoritmoDesenho.verticesMarcados.add(findVerticeVisual(_v1));
                                    algoritmoDesenho.verticesMarcados.add(findVerticeVisual(_v2));

                                    boolean existeCaminho = true;
                                    while (predecessor != _v1) {
                                        result_vertex.add(predecessor);

                                        algoritmoDesenho.verticesMarcados.add(findVerticeVisual(predecessor));
                                        Vertice predecessorTemp = resultado.getPredecessor(predecessor);

                                        Aresta aresta = g.getArestaByVertices(predecessor, predecessorTemp);
                                        if (aresta == null) {
                                            try {
                                                aresta = g.getArestaByVertices(predecessorTemp, predecessor);
                                            } catch (NullPointerException npe) {
                                                info("Não existe o caminho.");
                                                existeCaminho = false;
                                                break;
                                            }
                                        }

                                        algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                                        predecessor = predecessorTemp;
                                    }
                                    result_vertex.add(_v1);
                                    Collections.reverse(result_vertex);

                                    if (existeCaminho) {
                                        // frame
                                        String str = "Resultado Bellman-Ford:\n";
                                        for (Vertice vertice : result_vertex) {
                                            str += (vertice.getDado() == null ? "(não nomeado)" : vertice.getDado()) + "\n";
                                        }
                                        new ResultadoFrame("Resultado Bellman-Ford", str);
                                    }

                                    algoritmoExecutor = null;
                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                }
                            }
                        }

                    };

                    algoritmoDesenho.reset();
                    VerticeVisual.selectNone(vertexs);
                    GraphViewer.this.repaint();
                    algoritmoExecutor.nextStep();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Busca Largura");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = new IAlgoritmoExecutor() {

                        boolean messageV1 = false;

                        VerticeVisual v1;

                        @Override
                        public void nextStep(VerticeVisual... vertices) {
                            if (!messageV1) {
                                info("Selecione o vértice inicial.");
                                messageV1 = true;
                                return;
                            }

                            if (vertices != null && vertices.length == 1) {
                                if (v1 == null) {
                                    v1 = vertices[0];

                                    Grafo g = g();

                                    Vertice _v1 = g.getVerticeById(v1.id);

                                    AlgoritmoBuscaLargura dfs = new AlgoritmoBuscaLargura();
                                    dfs.executar(g, _v1);

                                    AlgoritmoBuscaLarguraResultado resultado = dfs.getResultado();

                                    // frame
                                    String str = "Resultado Busca Largura:\n";

                                    for (int i = 0; i < g.getTamanho(); i++) {
                                        Vertice vertice = g.getVertice(i);
                                        if (resultado.getVisitado(vertice)) {
                                            algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vertice));

                                            for (int j = 0; j < vertice.getQtdeArestas(); j++) {
                                                Aresta aresta = vertice.getAresta(j);
                                                algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                                            }

                                            str += vertice.getDado() + //
                                                    " Tempo abertura: " + resultado.getTempoAbertura(vertice) + //
                                                    " Tempo fechamento: " + resultado.getTempoFechamento(vertice) + //
                                                    "\n";
                                        }
                                    }

                                    new ResultadoFrame("Resultado Busca Largura", str);

                                    algoritmoExecutor = null;
                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                }
                            }
                        }

                    };

                    algoritmoDesenho.reset();
                    VerticeVisual.selectNone(vertexs);
                    GraphViewer.this.repaint();
                    algoritmoExecutor.nextStep();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Busca Profundidade");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = new IAlgoritmoExecutor() {

                        boolean messageV1 = false;

                        VerticeVisual v1;

                        @Override
                        public void nextStep(VerticeVisual... vertices) {
                            if (!messageV1) {
                                info("Selecione o vértice inicial.");
                                messageV1 = true;
                                return;
                            }

                            if (vertices != null && vertices.length == 1) {
                                if (v1 == null) {
                                    v1 = vertices[0];

                                    Grafo g = g();

                                    Vertice _v1 = g.getVerticeById(v1.id);

                                    AlgoritmoBuscaProfundidade dfs = new AlgoritmoBuscaProfundidade();
                                    dfs.executar(g, _v1);

                                    AlgoritmoBuscaProfundidadeResultado resultado = dfs.getResultado();

                                    // frame
                                    String str = "Resultado Busca Profundidade:\n";

                                    for (int i = 0; i < g.getTamanho(); i++) {
                                        Vertice vertice = g.getVertice(i);
                                        if (resultado.getVisitado(vertice)) {
                                            algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vertice));

                                            for (int j = 0; j < vertice.getQtdeArestas(); j++) {
                                                Aresta aresta = vertice.getAresta(j);
                                                algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                                            }

                                            str += vertice.getDado() + //
                                                    " Tempo abertura: " + resultado.getTempoAbertura(vertice) + //
                                                    " Tempo fechamento: " + resultado.getTempoFechamento(vertice) + //
                                                    "\n";
                                        }
                                    }

                                    new ResultadoFrame("Resultado Busca Profundidade", str);

                                    algoritmoExecutor = null;
                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                }
                            }
                        }

                    };

                    algoritmoDesenho.reset();
                    VerticeVisual.selectNone(vertexs);
                    GraphViewer.this.repaint();
                    algoritmoExecutor.nextStep();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Caminho Euleriano");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    AlgoritmoCaminhoEuleriano euleriano = new AlgoritmoCaminhoEuleriano();
                    AlgoritmoCaminhoEulerianoResultado resultado = euleriano.fleury(g());

                    algoritmoDesenho.reset();
                    if (resultado == null) {
                        JOptionPane.showMessageDialog(null, "Não encontrou caminho euleriano.");
                    } else {
                        for (Aresta aresta : resultado.getCaminho()) {
                            algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                            algoritmoDesenho.verticesMarcados.add(findVerticeVisual(aresta.getVi()));
                            algoritmoDesenho.verticesMarcados.add(findVerticeVisual(aresta.getVj()));
                        }
                        new ResultadoFrame("Resultado Caminho Euleriano", resultado.toString());
                    }
                    GraphViewer.this.repaint();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Ciclo Hamiltoniano");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (hasDirection()) {
                        JOptionPane.showMessageDialog(null, "Algoritmo de ciclo hamiltoniano aplica-se somente para grafos não dirigidos.");
                    } else {
                        GrafoNaoDirigido g = (GrafoNaoDirigido) g();

                        AlgoritmoCicloHamiltoniano h = new AlgoritmoCicloHamiltoniano();
                        AlgoritmoCicloHamiltonianoResultado resultado = h.backtracking(g);

                        algoritmoDesenho.reset();
                        if (resultado == null) {
                            JOptionPane.showMessageDialog(null, "Ciclo hamiltoniano não encontrado");
                        } else {
                            List<Vertice> caminho = resultado.getCaminho();
                            for (int i = 0; i < caminho.size(); i++) {
                                Vertice vi = caminho.get(i);
                                algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vi));

                                Vertice vj = (i > 0) ? caminho.get(i - 1) : caminho.get(caminho.size() - 1);
                                Aresta aresta = g.getArestaByVertices(vi, vj);
                                if (aresta == null) {
                                    System.out.println("não encontrou a aresta: " + vi + " " + vj);
                                } else {
                                    VerticeVisual v1 = findVerticeVisual(vi);
                                    VerticeVisual v2 = findVerticeVisual(vj);
                                    algoritmoDesenho.arestasMarcadas.add(getEdge(v1, v2));
                                }
                            }

                            new ResultadoFrame("Resultado Ciclo Hamiltoniano", resultado.toString());
                        }
                        GraphViewer.this.repaint();
                    }
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Dijkstra");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = new IAlgoritmoExecutor() {

                        boolean messageV1 = false;

                        VerticeVisual v1, v2;

                        @Override
                        public void nextStep(VerticeVisual... vertices) {
                            if (!messageV1) {
                                info("Selecione o vértice inicial.");
                                messageV1 = true;
                                return;
                            }

                            if (vertices != null && vertices.length == 1) {
                                if (v1 == null) {
                                    v1 = vertices[0];
                                    info("Selecione o vértice final.");

                                    algoritmoDesenho.coresVertices.put(v1, Color.MAGENTA);
                                    GraphViewer.this.repaint();

                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                } else {
                                    if (v1 == vertices[0]) {
                                        info("Vértice inicial e final devem ser diferentes");
                                        return;
                                    }
                                    v2 = vertices[0];

                                    Grafo g = g();

                                    Vertice _v1 = g.getVerticeById(v1.id);
                                    Vertice _v2 = g.getVerticeById(v2.id);

                                    AlgoritmoDijkstra dijkstra = new AlgoritmoDijkstra();
                                    dijkstra.executar(g, _v1);

                                    AlgoritmoDijkstraResultado resultado = dijkstra.getResultado();

                                    List<Vertice> result_vertex = new ArrayList<>();

                                    Vertice predecessor = _v2;
                                    algoritmoDesenho.verticesMarcados.add(findVerticeVisual(_v1));
                                    algoritmoDesenho.verticesMarcados.add(findVerticeVisual(_v2));

                                    boolean existeCaminho = true;
                                    while (predecessor != _v1) {
                                        result_vertex.add(predecessor);

                                        algoritmoDesenho.verticesMarcados.add(findVerticeVisual(predecessor));
                                        Vertice predecessorTemp = resultado.getPredecessor(predecessor);
                                        if (predecessorTemp == null) { // não encontrou resultado
                                            info("Não encontrou o caminho.");
                                            existeCaminho = false;
                                            break;
                                        }

                                        Aresta aresta = g.getArestaByVertices(predecessor, predecessorTemp);
                                        algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                                        predecessor = predecessorTemp;
                                    }
                                    result_vertex.add(_v1);
                                    Collections.reverse(result_vertex);

                                    // frame
                                    if (existeCaminho) {
                                        String str = "Resultado Dijkstra:\n";
                                        for (Vertice vertice : result_vertex) {
                                            str += vertice.getDado() + "\n";
                                        }
                                        new ResultadoFrame("Resultado Dijkstra", str);
                                    }

                                    algoritmoExecutor = null;
                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                }
                            }
                        }

                    };

                    algoritmoDesenho.reset();
                    VerticeVisual.selectNone(vertexs);
                    GraphViewer.this.repaint();
                    algoritmoExecutor.nextStep();
                }

            });
        }
        //        {
        //            JMenuItem mnuAlgoritmo = new JMenuItem("Emparelhamento Perfeito Máximo");
        //            mnuAlgoritmos.add(mnuAlgoritmo);
        //            mnuAlgoritmo.addActionListener(new ActionListener() {
        //
        //                @Override
        //                public void actionPerformed(ActionEvent e) {
        //                    JOptionPane.showMessageDialog(null, "Não implementado");
        //                }
        //
        //            });
        //        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Floyd-Warshall");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = new IAlgoritmoExecutor() {

                        boolean messageV1 = false;

                        VerticeVisual v1;

                        @Override
                        public void nextStep(VerticeVisual... vertices) {
                            if (!messageV1) {
                                info("Selecione o vértice inicial.");
                                messageV1 = true;
                                return;
                            }

                            if (vertices != null && vertices.length == 1) {
                                if (v1 == null) {
                                    v1 = vertices[0];

                                    Grafo g = g();

                                    Vertice _v1 = g.getVerticeById(v1.id);

                                    AlgoritmoFloydWarshall dfs = new AlgoritmoFloydWarshall();
                                    dfs.executar(g);

                                    AlgoritmoFloydWarshallResultado resultado = dfs.getResultado();

                                    // frame
                                    String str = "Resultado Floyd-Warshall:\n";

                                    for (int i = 0; i < g.getTamanho(); i++) {
                                        Vertice vertice = g.getVertice(i);
                                        if (vertice.getId() == _v1.getId()) {
                                            continue;
                                        }

                                        str += "Menor caminho para o vértice " + vertice.getDado() + " até o vértice " + _v1.getDado() + "\n";

                                        double total = 0.0;
                                        Vertice a = vertice;
                                        Vertice anterior = null;
                                        while (a.getId() != _v1.getId()) {
                                            str += " " + a.getDado();
                                            anterior = a;
                                            a = resultado.getPredecessor(_v1, a);

                                            Aresta aresta;
                                            try {
                                                aresta = g.getArestaByVertices(a, anterior);
                                            } catch (NullPointerException e) {
                                                aresta = g.getArestaByVertices(anterior, a);
                                            }
                                            str += " Valor: " + aresta.getValor() + "\n";

                                            total += aresta.getValor();
                                        }

                                        str += "Valor total: " + total + "\n\n";
                                    }

                                    new ResultadoFrame("Resultado Floyd-Warshall", str);

                                    algoritmoExecutor = null;
                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                }
                            }
                        }

                    };

                    algoritmoDesenho.reset();
                    VerticeVisual.selectNone(vertexs);
                    GraphViewer.this.repaint();
                    algoritmoExecutor.nextStep();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Ford-Fulkerson");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = new IAlgoritmoExecutor() {

                        boolean messageV1 = false;

                        VerticeVisual v1, v2;

                        @Override
                        public void nextStep(VerticeVisual... vertices) {
                            if (!messageV1) {
                                info("Selecione o vértice inicial.");
                                messageV1 = true;
                                return;
                            }

                            if (vertices != null && vertices.length == 1) {
                                if (v1 == null) {
                                    v1 = vertices[0];
                                    info("Selecione o vértice final.");

                                    algoritmoDesenho.coresVertices.put(v1, Color.MAGENTA);
                                    GraphViewer.this.repaint();

                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                } else {
                                    if (v1 == vertices[0]) {
                                        info("Vértice inicial e final devem ser diferentes");
                                        return;
                                    }
                                    v2 = vertices[0];

                                    Grafo g = g();

                                    Vertice _v1 = g.getVerticeById(v1.id);
                                    Vertice _v2 = g.getVerticeById(v2.id);

                                    AlgoritmoFordFulkerson fordFulkerson = new AlgoritmoFordFulkerson();
                                    fordFulkerson.executar(g, _v1, _v2);

                                    AlgoritmoFordFulkersonResultado resultado = fordFulkerson.getResultado();

                                    // frame
                                    String str = "Resultado Ford-Fulkerson:\n";
                                    //        str += "Custo Fluxo: " + resultado.getCustoFluxo() + "\n";
                                    str += "Fluxo Máximo: " + resultado.getFluxoMaximo() + "\n\n";

                                    //        for (Entry<Pair, Double> entry : resultado.getFluxo().entrySet()) {
                                    //            Vertice vi = (Vertice) entry.getKey().getA();
                                    //            Vertice vj = (Vertice) entry.getKey().getB();
                                    //
                                    //            str += vi.getDado();
                                    //            str += " -> ";
                                    //            str += vj.getDado();
                                    //            Aresta aresta = g.getArestaByVertices(vi, vj);
                                    //            if (aresta == null) {
                                    //                aresta = g.getArestaByVertices(vj, vi);
                                    //            }
                                    //            str += ": " + aresta.getCapacidade() + " (";
                                    //            if (entry.getValue() >= 0) {
                                    //                str += entry.getValue();
                                    //            } else {
                                    //                str += entry.getValue();
                                    //            }
                                    //            str += ")\n";
                                    //        }

                                    //for (Vertice vertice : result_vertex) {
                                    //    str += vertice.getDado() + "\n";
                                    //}
                                    new ResultadoFrame("Resultado Ford-Fulkerson", str);

                                    algoritmoExecutor = null;
                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                }
                            }
                        }

                    };

                    algoritmoDesenho.reset();
                    VerticeVisual.selectNone(vertexs);
                    GraphViewer.this.repaint();
                    algoritmoExecutor.nextStep();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Hopcroft-Tarjan");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoDesenho.reset();
                    algoritmoExecutor = null;

                    Grafo g = g();

                    AlgoritmoHopcroftTarjan hopcroftTarjan = new AlgoritmoHopcroftTarjan();
                    hopcroftTarjan.executar(g);
                    AlgoritmoHopcroftTarjanResultado resultado = hopcroftTarjan.getResultado();
                    if (resultado.getQtdeComponentes() <= NumeroCromaticoCores.values().length) {

                        for (int i = 0; i < resultado.getQtdeComponentes(); i++) {
                            ArrayList<Vertice> componente = resultado.getComponente(i);
                            Color cor = NumeroCromaticoCores.values()[i].cor;

                            for (Vertice vertice : componente) {
                                algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vertice));
                                algoritmoDesenho.coresVertices.put(findVerticeVisual(vertice), cor);

                                for (int j = 0; j < vertice.getQtdeArestas(); j++) {
                                    Aresta aresta = vertice.getAresta(j);

                                    algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                                    algoritmoDesenho.coresArestas.put(findArestaVisual(aresta), cor);
                                }
                            }
                        }
                    }

                    GraphViewer.this.repaint();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Kruskal");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = null;
                    algoritmoDesenho.reset();

                    if (hasDirection()) {
                        info("Algoritmo de Kruskal executa somente sobre grafo não dirigidos.");
                        return;
                    }
                    GrafoNaoDirigido g = (GrafoNaoDirigido) g();

                    AlgoritmoKruskal kruskal = new AlgoritmoKruskal();
                    kruskal.executar(g);

                    AlgoritmoKruskalResultado resultado = kruskal.getResultado();

                    String str = "Resultado Kruskal\n";
                    str += "Custo total: " + resultado.getCustoTotal() + "\n";
                    str += "Arestas:\n";
                    for (Aresta aresta : resultado.getArestas()) {
                        Vertice vi = aresta.getVi();
                        Vertice vj = aresta.getVj();

                        str += aresta + "\n";
                        algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vi));
                        algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vj));
                        algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                    }

                    new ResultadoFrame("Resultado Kruskal", str);

                    GraphViewer.this.repaint();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Ordenação Topológica");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    Grafo g = g();
                    if (!hasDirection()) {
                        info("Ordenação topológica executa somente sobre grafos dirigidos");
                        return;
                    }

                    AlgoritmoOrdenacaoTopologica topologica = new AlgoritmoOrdenacaoTopologica();
                    try {
                        topologica.executar((GrafoDirigido) g);
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        info(e1.getMessage());
                        return;
                    }

                    AlgoritmoOrdenacaoTopologicaResultado resultado = topologica.getResultado();

                    String str = "Ordenação topológica:\n";
                    for (Vertice v : resultado.getLista()) {
                        str += (v.getDado() == null ? "(vértice não nomeado)" : v.getDado().toString());
                        str += " Tempo de abertura: " + resultado.getTempoAbertura(v);
                        str += " Tempo de fechamento: " + resultado.getTempoFechamento(v);
                        str += "\n";
                    }

                    new ResultadoFrame("Resultado Ordenação Topológica", str);
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Pontes");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = null;
                    algoritmoDesenho.reset();

                    Grafo g = g();

                    AlgoritmoPontes pontes = new AlgoritmoPontes();
                    pontes.executar(g);

                    AlgoritmoPontesResultado resultado = pontes.getResultado();
                    if (resultado.getPontes().isEmpty()) {
                        info("Nenhuma ponte encontrada.");
                        return;
                    }

                    // frame
                    String str = "Resultado Pontes:\n";
                    for (Aresta aresta : resultado.getPontes()) {
                        str += aresta.getDado() + "\n";
                        algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                    }

                    new ResultadoFrame("Resultado Pontes", str);

                    VerticeVisual.selectNone(vertexs);
                    GraphViewer.this.repaint();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Prim");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = null;
                    algoritmoDesenho.reset();

                    if (hasDirection()) {
                        info("Algoritmo de Prim executa somente sobre grafo não dirigidos.");
                        return;
                    }
                    GrafoNaoDirigido g = (GrafoNaoDirigido) g();

                    AlgoritmoPrim prim = new AlgoritmoPrim();
                    prim.executar(g);

                    AlgoritmoPrimResultado resultado = prim.getResultado();

                    String str = "Resultado Prim\n";
                    str += "Custo total: " + resultado.getCustoTotal() + "\n";
                    str += "Arestas:\n";
                    for (Aresta aresta : resultado.getArestas()) {
                        Vertice vi = aresta.getVi();
                        Vertice vj = aresta.getVj();

                        str += aresta + "\n";
                        algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vi));
                        algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vj));
                        algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                    }

                    new ResultadoFrame("Resultado Prim", str);

                    GraphViewer.this.repaint();
                }

            });
        }

        menuBar.add(mnuAlgoritmos);
    }

    private void menuPropriedades(JMenuBar menuBar) {
        /*
         * Menu Propriedades
         */
        JMenu mnuPropriedades = new JMenu("Propriedades");

        JMenuItem mnuPropriedadeConexo = new JMenuItem("Conexo");
        mnuPropriedadeConexo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Conexo: " + g().ehConexo());
            }

        });
        mnuPropriedades.add(mnuPropriedadeConexo);

        JMenuItem mnuPropriedadeDesconexo = new JMenuItem("Desconexo");
        mnuPropriedadeDesconexo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Desconexo: " + g().ehDesconexo());
            }

        });
        mnuPropriedades.add(mnuPropriedadeDesconexo);

        JMenuItem mnuPropriedadeNulo = new JMenuItem("Nulo");
        mnuPropriedadeNulo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Nulo: " + g().ehNulo());
            }

        });
        mnuPropriedades.add(mnuPropriedadeNulo);

        JMenuItem mnuPropriedadeTrivial = new JMenuItem("Trivial");
        mnuPropriedadeTrivial.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Trivial: " + g().ehTrivial());
            }

        });
        mnuPropriedades.add(mnuPropriedadeTrivial);

        JMenuItem mnuPropriedadeRegular = new JMenuItem("Regular");
        mnuPropriedadeRegular.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Regular: " + g().ehRegular());
            }

        });
        mnuPropriedades.add(mnuPropriedadeRegular);

        JMenuItem mnuPropriedadeDenso = new JMenuItem("Denso");
        mnuPropriedadeDenso.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Denso: " + g().ehDenso());
            }

        });
        mnuPropriedades.add(mnuPropriedadeDenso);

        JMenuItem mnuPropriedadeEsparso = new JMenuItem("Esparso");
        mnuPropriedadeEsparso.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Esparso: " + g().ehEsparso());
            }

        });
        mnuPropriedades.add(mnuPropriedadeEsparso);

        JMenuItem mnuPropriedadeSimples = new JMenuItem("Simples");
        mnuPropriedadeSimples.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Simples: " + g().ehSimples());
            }

        });
        mnuPropriedades.add(mnuPropriedadeSimples);

        JMenuItem mnuPropriedadeMultigrafo = new JMenuItem("Multigrafo");
        mnuPropriedadeMultigrafo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Multigrafo: " + g().ehMultigrafo());
            }

        });
        mnuPropriedades.add(mnuPropriedadeMultigrafo);

        JMenuItem mnuPropriedadeCompleto = new JMenuItem("Completo");
        mnuPropriedadeCompleto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Completo: " + g().ehCompleto());
            }

        });
        mnuPropriedades.add(mnuPropriedadeCompleto);

        JMenuItem mnuPropriedadeBipartido = new JMenuItem("Bipartido");
        mnuPropriedadeBipartido.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Bipartido: " + g().ehBipartido());
            }

        });
        mnuPropriedades.add(mnuPropriedadeBipartido);

        JMenuItem mnuPropriedadeBipartidoCompleto = new JMenuItem("Bipartido completo");
        mnuPropriedadeBipartidoCompleto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Bipartido completo: " + g().ehBipartidoCompleto());
            }

        });
        mnuPropriedades.add(mnuPropriedadeBipartidoCompleto);

        JMenuItem mnuPropriedadeCiclo = new JMenuItem("Ciclo");
        mnuPropriedadeCiclo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Ciclo: " + g().ehCiclo());
            }

        });
        mnuPropriedades.add(mnuPropriedadeCiclo);

        JMenuItem mnuPropriedadeAciclico = new JMenuItem("Aciclico");
        mnuPropriedadeAciclico.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Aciclico: " + g().ehAciclico());
            }

        });
        mnuPropriedades.add(mnuPropriedadeAciclico);

        JMenuItem mnuPropriedadeArvore = new JMenuItem("Arvore/Arborescencia");
        mnuPropriedadeArvore.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (g().ehDirigido()) {
                    JOptionPane.showMessageDialog(null, "Arborescencia: " + ((GrafoDirigido) g()).ehArborescencia());
                } else {
                    JOptionPane.showMessageDialog(null, "Arvore: " + ((GrafoNaoDirigido) g()).ehArvore());
                }

            }

        });
        mnuPropriedades.add(mnuPropriedadeArvore);

        JMenuItem mnuPropriedadeFloresta = new JMenuItem("Floresta");
        mnuPropriedadeFloresta.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Floresta: " + g().ehFloresta());
            }

        });
        mnuPropriedades.add(mnuPropriedadeFloresta);

        // ====================================================================
        JMenuItem mnuPropriedadeNumeroCromatico = new JMenuItem("Número Cromático");
        mnuPropriedadeNumeroCromatico.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Grafo g = g();
                Map<Vertice, Integer> numeroCromatico = g.getNumeroCromatico();

                int max = 0;
                for (Integer i : numeroCromatico.values()) {
                    max = Math.max(max, i);
                }
                max++; // inicia em zero.
                if (g.getTamanho() == 0) {
                    max = 0;
                }

                algoritmoDesenho.reset();
                if (max <= NumeroCromaticoCores.values().length) {
                    for (Entry<Vertice, Integer> entry : numeroCromatico.entrySet()) {
                        algoritmoDesenho.coresVertices.put(findVerticeVisual(entry.getKey()), NumeroCromaticoCores.values()[entry.getValue()].cor);
                    }
                }
                GraphViewer.this.repaint();
                JOptionPane.showMessageDialog(null, "Número Cromático: " + max);
            }

        });
        mnuPropriedades.add(mnuPropriedadeNumeroCromatico);

        JMenuItem mnuPropriedadeHipercubo = new JMenuItem("Hipercubo");
        mnuPropriedadeHipercubo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Grafo g = g();
                if (g instanceof GrafoNaoDirigido) {
                    boolean ehHipercubo = ((GrafoNaoDirigido) g()).ehHipercubo();
                    JOptionPane.showMessageDialog(null, "O grafo " + (ehHipercubo ? "" : "não ") + "apresenta a propriedade hipercubo.");
                } else {
                    JOptionPane.showMessageDialog(null, "Hipercubos se aplicam somente para grafos não dirigidos.");
                }
            }

        });
        mnuPropriedades.add(mnuPropriedadeHipercubo);

        JMenuItem mnuPropriedadeIsomorfismo = new JMenuItem("Isomorfismo");
        mnuPropriedadeIsomorfismo.addActionListener(new ActionListener() {

            final JFileChooser chooser = new JFileChooser();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (hasDirection()) {
                    JOptionPane.showMessageDialog(null, "Isomorfismo implementado aplica-se somente para grafos não dirigidos.");
                    return;
                }

                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setMultiSelectionEnabled(false);
                chooser.setDialogType(JFileChooser.OPEN_DIALOG);
                chooser.setDialogTitle("Carregar");

                //In response to a button click:
                int returnVal = chooser.showDialog(GraphViewer.this, "Carregar");
                if (returnVal != JFileChooser.APPROVE_OPTION) {
                    return;
                }

                System.out.println("You choose to open this file: " + chooser.getSelectedFile().getName());
                File file = chooser.getSelectedFile();

                DefinicaoGrafoVisual definicaoGrafoVisual = PersistenciaVisual.carregarGrafo(file);

                if (hasDirection(definicaoGrafoVisual.arestas)) {
                    JOptionPane.showMessageDialog(null, "Isomorfismo implementado aplica-se somente para grafos não dirigidos.");
                    return;
                }

                GrafoNaoDirigido g1 = (GrafoNaoDirigido) buildGraph(vertexs, edges);
                GrafoNaoDirigido g2 = (GrafoNaoDirigido) buildGraph(definicaoGrafoVisual.vertices, definicaoGrafoVisual.arestas);

                PropriedadeIsomorfismoResultado isomorfismo = g1.ehIsomorfico(g2);
                if (isomorfismo == null) {
                    JOptionPane.showMessageDialog(null, "Os grafos não são isomorfos.");
                } else {
                    new ResultadoFrame("Resultado Isomorfismo", isomorfismo.toString());
                }
            }

        });
        mnuPropriedades.add(mnuPropriedadeIsomorfismo);

        //        JMenuItem mnuPropriedadePlanar = new JMenuItem("Planar");
        //        mnuPropriedadePlanar.addActionListener(new ActionListener() {
        //
        //            @Override
        //            public void actionPerformed(ActionEvent e) {
        //                JOptionPane.showMessageDialog(null, "Não implementado");
        //            }
        //
        //        });
        //        mnuPropriedades.add(mnuPropriedadePlanar);

        menuBar.add(mnuPropriedades);
    }

    private Grafo g() {
        return buildGraph(vertexs, edges);
    }

    public GraphViewer() {
        setOpaque(true);
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
    }

    private void addKeyShortcut() {
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("GraphViewer.addKeyShortcut().new KeyAdapter() {...}.keyPressed()");
                // TODO Auto-generated method stub
                super.keyPressed(e);
            }

        });
        scrollPane.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("GraphViewer.addKeyShortcut().new KeyAdapter() {...}.keyPressed()");
                // TODO Auto-generated method stub
                super.keyPressed(e);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    VerticeVisual.getSelected(vertexs, selected);

                    // FIXME: refatorar, mesmo código que o DeleteAction
                    ListIterator<VerticeVisual> iter = selected.listIterator();
                    while (iter.hasNext()) {
                        VerticeVisual n = iter.next();
                        if (n.isSelected()) {
                            deleteEdges(n);
                            iter.remove();
                        }
                    }

                    updateFooter();
                    repaint();
                }
            }

            // FIXME: código duplicado
            private void deleteEdges(VerticeVisual n) {
                ListIterator<ArestaVisual> iter = edges.listIterator();
                while (iter.hasNext()) {
                    ArestaVisual e = iter.next();
                    if (e.n1 == n || e.n2 == n) {
                        iter.remove();
                    }
                }
            }

        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HIGH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0x00f0f0f0));
        g.fillRect(0, 0, getWidth(), getHeight());

        for (VerticeVisual n : vertexs) {
            n.drawAlgoritmo(g);
        }
        for (ArestaVisual e : edges) {
            e.draw(g);
        }
        for (VerticeVisual n : vertexs) {
            n.draw(g);
        }
        if (selecting) {
            g.setColor(Color.darkGray);
            g.drawRect(mouseRect.x, mouseRect.y, mouseRect.width, mouseRect.height);
        }
    }

    private ArestaVisual getEdge(VerticeVisual v1, VerticeVisual v2) {
        ArestaVisual edge = null;
        for (ArestaVisual e : edges) {
            if (e.n1 == v1 && e.n2 == v2) {
                edge = e;
                break;
            }
            if (e.n1 == v2 && e.n2 == v1) {
                edge = e;
                break;
            }
        }
        return edge;
    }

    /**
     * @author ande
     */
    private final class CarregarGrafoAction implements ActionListener {

        final JFileChooser chooser = new JFileChooser();

        @Override
        public void actionPerformed(ActionEvent e) {
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setMultiSelectionEnabled(false);
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            chooser.setDialogTitle("Carregar");

            //In response to a button click:
            int returnVal = chooser.showDialog(GraphViewer.this, "Carregar");
            if (returnVal != JFileChooser.APPROVE_OPTION) {
                return;
            }

            System.out.println("You choose to open this file: " + chooser.getSelectedFile().getName());
            File file = chooser.getSelectedFile();

            DefinicaoGrafoVisual definicaoGrafoVisual = PersistenciaVisual.carregarGrafo(file);
            vertexs = definicaoGrafoVisual.vertices;
            edges = definicaoGrafoVisual.arestas;
            _id = definicaoGrafoVisual.ultimoIdVertice;

            GraphViewer.this.repaint();
            updateFooter();
        }
    }

    /**
     * @author ande
     */
    private final class SalvarGrafoAction implements ActionListener {

        final JFileChooser chooser = new JFileChooser();

        @Override
        public void actionPerformed(ActionEvent e) {
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setMultiSelectionEnabled(false);
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            chooser.setDialogTitle("Salvar");

            //In response to a button click:
            int returnVal = chooser.showDialog(GraphViewer.this, "Salvar");
            if (returnVal != JFileChooser.APPROVE_OPTION) {
                return;
            }

            System.out.println("You choose to open this file: " + chooser.getSelectedFile().getName());

            DefinicaoGrafoVisual definicaoGrafoVisual = new DefinicaoGrafoVisual(vertexs, edges, _id);
            PersistenciaVisual.salvarGrafo(definicaoGrafoVisual, chooser.getSelectedFile());
        }

    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            selecting = false;
            mouseRect.setBounds(0, 0, 0, 0);

            // Verifica se houve seleção, se não tiver, cria o vértice.
            if (e.getPoint().equals(mousePoint)) {
                if (!VerticeVisual.hasNodeAtPosition(vertexs, mousePoint)) {
                    // só cria, caso não possui nenhum selecionado
                    if (VerticeVisual.getQuantitySelected(vertexs) >= 1) {
                        VerticeVisual.selectNone(vertexs);
                    } else {
                        // se o modo é algoritmo, então não cria
                        if (algoritmoExecutor == null) {
                            AddVertexAction action = new AddVertexAction(null);
                            action.actionPerformed(null);
                        }
                    }
                }
            }

            VerticeVisual.getSelected(vertexs, selected);
            validateEditFields();

            if (algoritmoExecutor != null) {
                algoritmoExecutor.nextStep(selected.toArray(new VerticeVisual[selected.size()]));
            }

            if (e.isPopupTrigger()) {
                showPopup(e);
            }
            // updateFooter();
            e.getComponent().repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePoint = e.getPoint();

            if (e.isShiftDown()) {
                VerticeVisual.selectToggle(vertexs, mousePoint);
            } else if (e.isPopupTrigger()) {
                VerticeVisual.selectOne(vertexs, mousePoint);
                showPopup(e);
            } else if (VerticeVisual.selectOne(vertexs, mousePoint)) {
                selecting = false;
            } else {
                selecting = true;
            }

            // updateFooter();
            e.getComponent().repaint();
        }

        private void showPopup(MouseEvent e) {
            control.popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private class MouseMotionHandler extends MouseMotionAdapter {

        Point delta = new Point();

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selecting) {
                mouseRect.setBounds(Math.min(mousePoint.x, e.getX()), Math.min(mousePoint.y, e.getY()), Math.abs(mousePoint.x - e.getX()), Math.abs(mousePoint.y - e.getY()));
                VerticeVisual.selectRect(vertexs, mouseRect);
            } else {
                delta.setLocation(e.getX() - mousePoint.x, e.getY() - mousePoint.y);
                VerticeVisual.updatePosition(vertexs, delta);
                mousePoint = e.getPoint();
            }
            // updateFooter();
            e.getComponent().repaint();
        }
    }

    public JToolBar getControlPanel() {
        return control;
    }

    private void updateFooter() {
        algoritmoDesenho.reset();

        statusLabel.setText(
                            /**/"Quantidade de vértices: " + vertexs.size() + ". " +
                                    /**/"Quantidade de arestas: " + edges.size() + ". " +
                                    /**/"Tipo do grafo: " + getGraphType());
    }

    /**
     * @return
     */
    private String getGraphType() {
        return hasDirection() ? "Grafo Dirigido" : "Grafo Não Dirigido";
    }

    public void validateEditFields() {
        // algoritmoDesenho.reset();

        VerticeVisual.getSelected(vertexs, selected);

        if (selected.size() == 1) {
            vertexNameLabel.setEnabled(true);
            vertexNameField.setEnabled(true);
            vertexNameField.setText(selected.get(0).name);

            labelValorVertice.setEnabled(true);
            vertexValueField.setEnabled(true);
            vertexValueField.setText(selected.get(0).value);
        } else {
            vertexNameLabel.setEnabled(false);
            vertexNameField.setEnabled(false);
            vertexNameField.setText("");

            labelValorVertice.setEnabled(false);
            vertexValueField.setEnabled(false);
            vertexValueField.setText("");
        }

        btnConectar.setEnabled(selected.size() > 1);
        btnDeleteVertex.setEnabled(selected.size() > 0);

        boolean hasAnyEdge = false;
        a: for (VerticeVisual n1 : selected) {
            for (VerticeVisual n2 : selected) {
                if (n1 != n2) {
                    ArestaVisual edge = getEdge(n1, n2);
                    if (edge != null) {
                        hasAnyEdge = true;
                        break a;
                    }
                }
            }
        }
        btnDeleteEdge.setEnabled(hasAnyEdge);

        if (selected.size() == 2) {
            ArestaVisual edge = getEdge(selected.get(0), selected.get(1));
            if (edge != null) {
                labelDirecao.setEnabled(true);
                direcaoComboField.setEnabled(true);
                direcaoComboField.setSelectedIndex(edge.direction.ordinal());

                labelValorAresta.setEnabled(true);
                labelNameAresta.setEnabled(true);

                nameArestaField.setEnabled(true);
                nameArestaField.setText(edge.name);

                edgeValueField.setEnabled(true);
                edgeValueField.setText(edge.value);
            } else {
                labelDirecao.setEnabled(false);
                direcaoComboField.setEnabled(false);
                direcaoComboField.setSelectedIndex(0);

                labelValorAresta.setEnabled(false);
                labelNameAresta.setEnabled(false);

                nameArestaField.setEnabled(false);
                nameArestaField.setText("");

                edgeValueField.setEnabled(false);
                edgeValueField.setText("");
            }
        } else {
            labelDirecao.setEnabled(false);
            direcaoComboField.setEnabled(false);
            direcaoComboField.setSelectedIndex(0);

            labelValorAresta.setEnabled(false);
            labelNameAresta.setEnabled(false);

            nameArestaField.setEnabled(false);
            nameArestaField.setText("");

            edgeValueField.setEnabled(false);
            edgeValueField.setText("");
        }
    }

    private class ControlPanel extends JToolBar {

        private final Action clearAll = new ClearAction("Limpar");
        private final Action color = new ColorAction("Cor");
        private final Action connect = new ConnectAction("Conectar");
        private final Action deleteVertex = new DeleteVertexAction("Apagar vértice");
        private final Action deleteEdge = new DeleteEdgeAction("Apagar aresta");
        private final ColorIcon hueIcon = new ColorIcon(/* Color.blue */new Color(99, 190, 245));
        private final JPopupMenu popup = new JPopupMenu();

        ControlPanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            //            setBackground(Color.lightGray);
            setFloatable(false);

            this.add(new JButton(clearAll));
            this.add(new JButton(color));
            this.add(new JLabel(hueIcon));
            jspinner = new JSpinner();
            jspinner.setModel(new SpinnerNumberModel(RADIUS, 5, 100, 5));
            jspinner.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    JSpinner s = (JSpinner) e.getSource();
                    radius = (Integer) s.getValue();
                    VerticeVisual.updateRadius(vertexs, radius);
                    GraphViewer.this.repaint();
                }

            });
            this.add(new JLabel("Tamanho:"));
            this.add(jspinner);

            _addSeparator();

            btnConectar = new JButton(connect);
            btnConectar.setEnabled(false);

            this.add(btnConectar);

            _addSeparator();

            vertexNameLabel = new JLabel("Nome Vértice:");
            vertexNameLabel.setEnabled(false);
            this.add(vertexNameLabel);

            vertexNameField = new JTextField(7);
            vertexNameField.setEnabled(false);
            vertexNameField.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void changedUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    warn();
                }

                public void warn() {
                    if (selected.size() == 1) {
                        selected.get(0).setName(vertexNameField.getText());
                        GraphViewer.this.repaint();
                    }
                }

            });
            this.add(vertexNameField);

            labelValorVertice = new JLabel("Valor Vértice:");
            labelValorVertice.setEnabled(false);
            this.add(labelValorVertice);

            vertexValueField = new JTextField(7);
            vertexValueField.setEnabled(false);
            vertexValueField.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void changedUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    warn();
                }

                public void warn() {
                    if (selected.size() == 1) {
                        selected.get(0).setValue(vertexValueField.getText());
                        GraphViewer.this.repaint();
                    }
                }

            });
            this.add(vertexValueField);

            btnDeleteVertex = new JButton(deleteVertex);
            btnDeleteVertex.setEnabled(false);
            this.add(btnDeleteVertex);

            _addSeparator();

            labelNameAresta = new JLabel("Nome Aresta:");
            labelNameAresta.setEnabled(false);
            this.add(labelNameAresta);

            nameArestaField = new JTextField(7);
            nameArestaField.setEnabled(false);
            nameArestaField.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void changedUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    warn();
                }

                public void warn() {
                    if (selected.size() == 2) {
                        VerticeVisual v1 = selected.get(0);
                        VerticeVisual v2 = selected.get(1);

                        ArestaVisual edge = getEdge(v1, v2);
                        if (edge != null) {
                            edge.setName(nameArestaField.getText());
                            GraphViewer.this.repaint();
                        }
                    }
                }

            });
            this.add(nameArestaField);

            labelValorAresta = new JLabel("Valor Aresta:");
            labelValorAresta.setEnabled(false);

            this.add(labelValorAresta);
            edgeValueField = new JTextField(7);
            edgeValueField.setEnabled(false);
            edgeValueField.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void changedUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    warn();
                }

                public void warn() {
                    if (selected.size() == 2) {
                        VerticeVisual v1 = selected.get(0);
                        VerticeVisual v2 = selected.get(1);

                        ArestaVisual edge = getEdge(v1, v2);
                        if (edge != null) {
                            edge.setValue(edgeValueField.getText());
                            GraphViewer.this.repaint();
                        }
                    }
                }

            });
            this.add(edgeValueField);

            labelDirecao = new JLabel("Direção:");
            labelDirecao.setEnabled(false);
            this.add(labelDirecao);

            direcaoComboField = new JComboBox<String>();
            direcaoComboField.setEnabled(false);
            direcaoComboField.addItem("");
            direcaoComboField.addItem("->");
            direcaoComboField.addItem("<-");
            direcaoComboField.addItem("<->");
            direcaoComboField.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    String item = (String) ((JComboBox<?>) e.getSource()).getSelectedItem();

                    ArestaVisual edge = getInternalEdge();
                    if (edge == null) {
                        return;
                    }

                    if (item.equals("->")) {
                        edge.direction = ArestaVisual.Direction.RIGHT;
                    } else if (item.equals("<-")) {
                        edge.direction = ArestaVisual.Direction.LEFT;
                    } else if (item.equals("<->")) {
                        edge.direction = ArestaVisual.Direction.BI;
                    } else {
                        edge.direction = ArestaVisual.Direction.NONE;
                    }

                    updateFooter();
                    GraphViewer.this.repaint();
                }

                ArestaVisual getInternalEdge() {
                    if (selected.size() == 2) {
                        VerticeVisual v1 = selected.get(0);
                        VerticeVisual v2 = selected.get(1);

                        ArestaVisual edge = getEdge(v1, v2);
                        return edge;
                    }
                    return null;
                }

            });

            this.add(direcaoComboField);

            btnDeleteEdge = new JButton(deleteEdge);
            btnDeleteEdge.setEnabled(false);

            this.add(btnDeleteEdge);

            //    this.add(new JButton(new AbstractAction("___") {
            //
            //        @Override
            //        public void actionPerformed(ActionEvent e) {
            //            buildGraph();
            //        }
            //
            //    }));

            popup.add(new JMenuItem(color));
            popup.add(new JMenuItem(connect));
            popup.add(new JMenuItem(deleteVertex));
        }

        private void _addSeparator() {
            addSeparator(new Dimension(4, 20));
        }
    }

    private class ClearAction extends AbstractAction {

        public ClearAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            vertexs.clear();
            edges.clear();

            updateFooter();
            repaint();
        }
    }

    private class ColorAction extends AbstractAction {

        public ColorAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Color color = control.hueIcon.getColor();
            color = JColorChooser.showDialog(GraphViewer.this, "Choose a color", color);
            if (color != null) {
                VerticeVisual.updateColor(vertexs, color);
                control.hueIcon.setColor(color);
                control.repaint();
                repaint();
            }
        }
    }

    private class ConnectAction extends AbstractAction {

        public ConnectAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            VerticeVisual.getSelected(vertexs, selected);

            if (selected.size() > 1) {
                for (VerticeVisual e1 : selected) {
                    for (VerticeVisual e2 : selected) {
                        if (e1 != e2) {
                            boolean has = false;
                            for (ArestaVisual edge : edges) {
                                if (edge.n1 == e1 && edge.n2 == e2 //
                                        || edge.n1 == e2 && edge.n2 == e1) {
                                    has = true;
                                }
                            }
                            if (!has) {
                                edges.add(new ArestaVisual(e1, e2));
                            }
                        }
                    }
                }
            }

            validateEditFields();
            updateFooter();
            repaint();
        }
    }

    private class DeleteVertexAction extends AbstractAction {

        public DeleteVertexAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ListIterator<VerticeVisual> iter = vertexs.listIterator();
            while (iter.hasNext()) {
                VerticeVisual n = iter.next();
                if (n.isSelected()) {
                    deleteEdges(n);
                    iter.remove();
                }
            }

            validateEditFields();
            updateFooter();
            repaint();
        }

        private void deleteEdges(VerticeVisual n) {
            ListIterator<ArestaVisual> iter = edges.listIterator();
            while (iter.hasNext()) {
                ArestaVisual e = iter.next();
                if (e.n1 == n || e.n2 == n) {
                    iter.remove();
                }
            }
        }
    }

    private class DeleteEdgeAction extends AbstractAction {

        public DeleteEdgeAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            VerticeVisual.getSelected(vertexs, selected);

            for (VerticeVisual v1 : selected) {
                for (VerticeVisual v2 : selected) {
                    if (v1 != v2) {
                        deleteEdges(v1, v2);
                    }
                }
            }
            updateFooter();
            validateEditFields();
            repaint();
        }

        private void deleteEdges(VerticeVisual n1, VerticeVisual n2) {
            ListIterator<ArestaVisual> iter = edges.listIterator();
            while (iter.hasNext()) {
                ArestaVisual e = iter.next();
                if (e.n1 == n1 && e.n2 == n2) {
                    iter.remove();
                }
            }
        }
    }

    private class AddVertexAction extends AbstractAction {

        public AddVertexAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            VerticeVisual.selectNone(vertexs);
            Point p = mousePoint.getLocation();
            Color color = control.hueIcon.getColor();
            VerticeVisual n = new VerticeVisual(p, radius, color);
            n.setSelected(true);
            vertexs.add(n);

            validateEditFields();
            updateFooter();
            repaint();
        }
    }

    /**
     * An Edge is a pair of Nodes.
     */
    public static class ArestaVisual {

        public enum Direction {

            NONE, RIGHT, LEFT, BI

        }

        public Direction direction = Direction.NONE;
        public final VerticeVisual n1;
        public final VerticeVisual n2;
        public String value;
        public String name;

        public ArestaVisual(VerticeVisual n1, VerticeVisual n2) {
            this.n1 = n1;
            this.n2 = n2;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static Path2D createArrowForLine(Point2D fromPoint, double rotationDeg, double length, double wingsAngleDeg) {
            double ax = fromPoint.getX();
            double ay = fromPoint.getY();

            double radB = Math.toRadians(-rotationDeg + wingsAngleDeg);
            double radC = Math.toRadians(-rotationDeg - wingsAngleDeg);

            Path2D resultPath = new Path2D.Double();
            resultPath.moveTo(length * Math.cos(radB) + ax, length * Math.sin(radB) + ay);
            resultPath.lineTo(ax, ay);
            resultPath.lineTo(length * Math.cos(radC) + ax, length * Math.sin(radC) + ay);
            return resultPath;
        }

        public void draw(Graphics g) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Point _p1 = n1.getLocation();
            Point _p2 = n2.getLocation();

            Point p1 = _p1; // calc(_p1, _p2, n1.r);
            Point p2 = _p2; // calc(_p2, _p1, n2.r);

            if (algoritmoDesenho.arestasMarcadas.contains(this)) {
                Color color = algoritmoDesenho.coresArestas.get(this);
                if (color == null) {
                    color = algoritmoDesenho.corArestasMarcadas;
                }

                g.setColor(color);

                int size = 2;
                for (int i = -size; i <= size; i++) {
                    g.drawLine(p1.x - i, p1.y - i, p2.x - i, p2.y - i);
                }
                for (int i = -size; i <= size; i++) {
                    g.drawLine(p1.x + i, p1.y + i, p2.x + i, p2.y + i);
                }
                for (int i = -size; i <= size; i++) {
                    g.drawLine(p1.x - i, p1.y + i, p2.x - i, p2.y + i);
                }
                for (int i = -size; i <= size; i++) {
                    g.drawLine(p1.x + i, p1.y - i, p2.x + i, p2.y - i);
                }

                drawArrows(g, p1, p2, color, 4);
            }

            if (value != null || name != null) {
                g.setColor(Color.BLACK);

                String _name = buildName(name, value);
                int str = g.getFontMetrics().stringWidth(_name);
                g.drawString(_name, ((p1.x + p2.x) / 2) //
                             - str / 2, //
                             (p1.y + p2.y) / 2 - 3);
            }

            g.setColor(Color.darkGray);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);

            drawArrows(g, p1, p2, Color.BLACK, 0);
        }

        private void drawArrows(Graphics g, Point p1, Point p2, Color color, int offset) {
            switch (direction) {
                case RIGHT:
                    drawArrowHead((Graphics2D) g, p2, p1, n2, color, offset);
                    break;
                case LEFT:
                    drawArrowHead((Graphics2D) g, p1, p2, n1, color, offset);
                    break;
                case BI:
                    drawArrowHead((Graphics2D) g, p1, p2, n1, color, offset);
                    drawArrowHead((Graphics2D) g, p2, p1, n2, color, offset);
                    break;
                default:
                    break;
            }
        }

        private Point calc(Point tip, Point tail, int raio) {
            double dy = tip.y - tail.y;
            double dx = tip.x - tail.x;
            double theta = Math.atan2(dy, dx);

            // --
            double n_y = tip.y - raio * Math.sin(theta);
            double n_x = tip.x - raio * Math.cos(theta);

            //System.out.println("theta = " + Math.toDegrees(theta));
            int x1 = (int) n_x;
            int y1 = (int) n_y;

            return new Point(x1, y1);
        }

        static double phi;
        static int barb;
        static {
            phi = Math.toRadians(40);
            barb = 10;
        }

        private void drawArrowHead(Graphics2D g2, Point tip, Point tail, VerticeVisual tipNode, Color color, int offset) {
            g2.setPaint(color);
            double dy = tip.y - tail.y;
            double dx = tip.x - tail.x;
            double theta = Math.atan2(dy, dx);

            // --
            double n_y = tip.y - (tipNode.r - offset) * Math.sin(theta);
            double n_x = tip.x - (tipNode.r - offset) * Math.cos(theta);

            //System.out.println("theta = " + Math.toDegrees(theta));
            int x1 = (int) n_x;
            int y1 = (int) n_y;

            double rho = theta + phi;

            double desloc = (barb + offset * 2);
            int x2 = (int) (x1 - desloc * Math.cos(rho));
            int y2 = (int) (y1 - desloc * Math.sin(rho));

            rho = theta - phi;
            int x3 = (int) (x1 - desloc * Math.cos(rho));
            int y3 = (int) (y1 - desloc * Math.sin(rho));

            triangle(g2, x1, y1, x2, y2, x3, y3);
        }

        void triangle(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3) {
            // Left both the lines and the fill so you could play with color or something.
            //            g.drawLine(x1, y1, x2, y2);
            //            g.drawLine(x2, y2, x3, y3);
            //            g.drawLine(x3, y3, x1, y1);
            g.fillPolygon(new int[] { x1, x2, x3 }, new int[] { y1, y2, y3 }, 3);
        }

    }

    private static Grafo buildGraph(List<VerticeVisual> _nodes, List<ArestaVisual> _edges) {
        // Se tiver alguma aresta dirigida, cria um GrafoDirigido, senão usa GrafoNaoDirigido.
        boolean hasDirection = hasDirection(_edges);

        // cria o grafo.
        Grafo grafo;
        if (hasDirection) {
            grafo = new GrafoDirigido();
        } else {
            grafo = new GrafoNaoDirigido();
        }

        // cria as arestas.
        Map<VerticeVisual, Vertice> mapVertex = new HashMap<VerticeVisual, Vertice>();
        for (VerticeVisual node : _nodes) {
            Vertice vertice = new Vertice(node.id);
            if (isPeso(node.value)) {
                vertice.setDado(node.value);
            }
            vertice.setDado(node.name);

            try {
                grafo.addVertice(vertice);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            mapVertex.put(node, vertice);
        }

        // popula de acordo com as arestas.
        int id_arestas = 1;
        for (ArestaVisual edge : _edges) {
            // ================================================================
            // Grafo Dirigido
            // ================================================================
            if (hasDirection) {
                ArestaDirigida aresta1 = null;
                ArestaDirigida aresta2 = null;

                switch (edge.direction) {
                    case LEFT:
                        aresta1 = new ArestaDirigida(id_arestas++, mapVertex.get(edge.n2), mapVertex.get(edge.n1));
                        if (edge.name != null && !edge.name.isEmpty()) {
                            aresta1.setDado(edge.name);
                        }
                        if (isPeso(edge.value)) {
                            aresta1.setValor(Double.parseDouble(edge.value));
                            aresta1.setCapacidade(Double.parseDouble(edge.value));
                        }
                        break;
                    case RIGHT:
                        aresta2 = new ArestaDirigida(id_arestas++, mapVertex.get(edge.n1), mapVertex.get(edge.n2));
                        if (edge.name != null && !edge.name.isEmpty()) {
                            aresta2.setDado(edge.name);
                        }
                        if (isPeso(edge.value)) {
                            aresta2.setValor(Double.parseDouble(edge.value));
                            aresta2.setCapacidade(Double.parseDouble(edge.value));
                        }
                        break;
                    case BI:
                    case NONE:
                        aresta1 = new ArestaDirigida(id_arestas++, mapVertex.get(edge.n2), mapVertex.get(edge.n1));
                        aresta2 = new ArestaDirigida(id_arestas++, mapVertex.get(edge.n1), mapVertex.get(edge.n2));
                        if (edge.name != null && !edge.name.isEmpty()) {
                            aresta1.setDado(edge.name);
                            aresta2.setDado(edge.name);
                        }

                        if (isPeso(edge.value)) {
                            aresta1.setValor(Double.parseDouble(edge.value));
                            aresta1.setCapacidade(Double.parseDouble(edge.value));
                            aresta2.setValor(Double.parseDouble(edge.value));
                            aresta2.setCapacidade(Double.parseDouble(edge.value));
                        }
                        break;
                }
                try {
                    if (aresta1 != null) {
                        ((GrafoDirigido) grafo).addAresta(aresta1);
                    }
                    if (aresta2 != null) {
                        ((GrafoDirigido) grafo).addAresta(aresta2);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                // ================================================================
                // Grafo Não-Dirigido
                // ================================================================
                ArestaNaoDirigida aresta = new ArestaNaoDirigida(id_arestas++, mapVertex.get(edge.n1), mapVertex.get(edge.n2));
                try {
                    if (edge.name != null && !edge.name.isEmpty()) {
                        aresta.setDado(edge.name);
                    }

                    if (isPeso(edge.value)) {
                        aresta.setValor(Double.parseDouble(edge.value));
                        aresta.setCapacidade(Double.parseDouble(edge.value));
                    }
                    ((GrafoNaoDirigido) grafo).addAresta(aresta);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return grafo;
    }

    private static boolean isPeso(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean hasDirection() {
        return hasDirection(edges);
    }

    private static boolean hasDirection(List<ArestaVisual> _edges) {
        boolean hasDirection = false;
        for (ArestaVisual edge : _edges) {
            if (edge.direction != ArestaVisual.Direction.NONE) {
                hasDirection = true;
                break;
            }
        }
        return hasDirection;
    }

    private VerticeVisual findVerticeVisual(Vertice v) {
        for (VerticeVisual visual : vertexs) {
            if (visual.id == v.getId()) {
                return visual;
            }
        }
        // TODO Auto-generated method stub
        System.out.println(" NOT FOUND!! " + v);
        return null;
    }

    private ArestaVisual findArestaVisual(Aresta a) {
        Vertice vi = a.getVi();
        Vertice vj = a.getVj();

        for (ArestaVisual visual : edges) {
            if (visual.n1.id == vi.getId() && visual.n2.id == vj.getId()) {
                return visual;
            }
            if (visual.n2.id == vi.getId() && visual.n1.id == vj.getId()) {
                return visual;
            }
        }
        System.out.println("NOT FOUND!! " + a);
        return null;
    }

    static int _id;

    static int nextId() {
        return ++_id;
    }

    private static String buildName(String name, String value) {
        boolean n = name == null || name.isEmpty();
        boolean v = value == null || value.isEmpty();

        if (n && v) {
            return "";
        }
        if (n) {
            return "(" + value + ")";
        }
        if (v) {
            return name;
        }
        return name + " (" + value + ")";
    }

    /**
     * A Node represents a node in a graph.
     */
    public static class VerticeVisual {

        private static final Color COLOR_MIDDLE_BLACK = new Color(51, 51, 51);

        public int id;

        private final Point p;
        private int r;
        private Color color;
        private transient boolean selected = false;
        private final Rectangle b = new Rectangle();
        private String name;
        private String value;

        /**
         * Construct a new node.
         */
        public VerticeVisual(Point p, int r, Color color) {
            this.p = p;
            this.r = r;
            this.color = color;
            setBoundary(b);
            id = nextId();
        }

        /**
         * @param text
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @param g
         */
        public void drawAlgoritmo(Graphics g) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (algoritmoDesenho.verticesMarcados.contains(this)) {
                g.setColor(algoritmoDesenho.corVerticesMarcados);

                g.fillOval(b.x - 3, b.y - 3, b.width + 6, b.height + 6);
            }
        }

        /**
         * @param value the value to set
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Calculate this node's rectangular boundary.
         */
        private void setBoundary(Rectangle b) {
            b.setBounds(p.x - r, p.y - r, 2 * r, 2 * r);
        }

        /**
         * Draw this node.
         */
        public void draw(Graphics g) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (name != null || value != null) {
                g.setColor(Color.BLACK);

                String _name = buildName(name, value);
                int str = g.getFontMetrics().stringWidth(_name);
                g.drawString(_name, b.x // início
                             + b.width / 2 // centraliza o texto
                             - str / 2, // divide metado do texto para cada lado
                             b.y - 4);
            }

            g.setColor(COLOR_MIDDLE_BLACK);
            // if (this.kind == Kind.Circular) {
            g.fillOval(b.x, b.y, b.width, b.height);

            Color corVertice = algoritmoDesenho.coresVertices.containsKey(this) ? algoritmoDesenho.coresVertices.get(this) : color;
            g.setColor(corVertice);
            // if (this.kind == Kind.Circular) {
            g.fillOval(b.x + 1, b.y + 1, b.width - 2, b.height - 2);
            // } else if (this.kind == Kind.Rounded) {
            // g.fillRoundRect(b.x, b.y, b.width, b.height, r, r);
            // } else if (this.kind == Kind.Square) {
            // g.fillRect(b.x, b.y, b.width, b.height);
            // }
            if (selected) {
                g.setColor(Color.darkGray);
                g.drawRect(b.x, b.y, b.width, b.height);
            }
        }

        /**
         * Return this node's location.
         */
        public Point getLocation() {
            return p;
        }

        /**
         * Return true if this node contains p.
         */
        public boolean contains(Point p) {
            return b.contains(p);
        }

        /**
         * Return true if this node is selected.
         */
        public boolean isSelected() {
            return selected;
        }

        /**
         * Mark this node as selected.
         */
        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        /**
         * Collected all the selected nodes in list.
         */
        public static void getSelected(List<VerticeVisual> list, List<VerticeVisual> selected) {
            selected.clear();
            for (VerticeVisual n : list) {
                if (n.isSelected()) {
                    selected.add(n);
                }
            }
        }

        /**
         * Select no nodes.
         */
        public static void selectNone(List<VerticeVisual> list) {
            for (VerticeVisual n : list) {
                n.setSelected(false);
            }
        }

        /**
         * Select a single node; return true if not already selected.
         */
        public static boolean selectOne(List<VerticeVisual> list, Point p) {
            for (VerticeVisual n : list) {
                if (n.contains(p)) {
                    if (!n.isSelected()) {
                        VerticeVisual.selectNone(list);
                        n.setSelected(true);
                    }
                    return true;
                }
            }
            return false;
        }

        public static int getQuantitySelected(List<VerticeVisual> list) {
            int selected = 0;
            for (VerticeVisual n : list) {
                if (n.isSelected()) {
                    selected++;
                }
            }
            return selected;
        }

        public static boolean hasNodeAtPosition(List<VerticeVisual> list, Point p) {
            for (VerticeVisual n : list) {
                if (n.contains(p)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Select each node in r.
         */
        public static void selectRect(List<VerticeVisual> list, Rectangle r) {
            for (VerticeVisual n : list) {
                n.setSelected(r.contains(n.p));
            }
        }

        /**
         * Toggle selected state of each node containing p.
         */
        public static void selectToggle(List<VerticeVisual> list, Point p) {
            for (VerticeVisual n : list) {
                if (n.contains(p)) {
                    n.setSelected(!n.isSelected());
                }
            }
        }

        /**
         * Update each node's position by d (delta).
         */
        public static void updatePosition(List<VerticeVisual> list, Point d) {
            for (VerticeVisual n : list) {
                if (n.isSelected()) {
                    n.p.x += d.x;
                    n.p.y += d.y;
                    n.setBoundary(n.b);
                }
            }
        }

        /**
         * Update each node's radius r.
         */
        public static void updateRadius(List<VerticeVisual> list, int r) {
            for (VerticeVisual n : list) {
                if (n.isSelected()) {
                    n.r = r;
                    n.setBoundary(n.b);
                }
            }
        }

        /**
         * Update each node's color.
         */
        public static void updateColor(List<VerticeVisual> list, Color color) {
            for (VerticeVisual n : list) {
                if (n.isSelected()) {
                    n.color = color;
                }
            }
        }
    }

    private static class ColorIcon implements Icon {

        private static final int WIDE = 20;
        private static final int HIGH = 20;
        private Color color;

        public ColorIcon(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(color);

            // g.fillRect(x, y, WIDE, HIGH);

            g.fillRoundRect(x, y, WIDE, HIGH, 6, 6);

            // g.fillRect(x, y, WIDE, HIGH);
        }

        @Override
        public int getIconWidth() {
            return WIDE;
        }

        @Override
        public int getIconHeight() {
            return HIGH;
        }
    }

    enum NumeroCromaticoCores {

        _1(new Color(153, 255, 153)), // verde

        _2(new Color(153, 153, 255)), // azul

        _3(new Color(255, 153, 153)), // vermelho

        _4(new Color(255, 158, 95)), // laranja

        _5(new Color(255, 255, 153)), // amarelo

        _6(new Color(255, 102, 255)), // rosa

        _7(new Color(51, 51, 51)), // preto

        _8(Color.WHITE), // branco

        _9(Color.CYAN), // ciano

        _10(Color.LIGHT_GRAY), // cinza claro

        _11(Color.DARK_GRAY); // cinza escuro

        private Color cor;

        NumeroCromaticoCores(Color cor) {
            this.cor = cor;
        }

    }

    private interface IAlgoritmoExecutor {

        void nextStep(VerticeVisual... vertices);

    }

    private void info(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

}
