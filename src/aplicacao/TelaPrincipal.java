package aplicacao;

import geradores.GeradorGrafos;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import persistencia.Persistencia;
import algoritmos.AlgoritmoArticulacoes;
import algoritmos.AlgoritmoArticulacoesResultado;
import algoritmos.AlgoritmoBellmanFord;
import algoritmos.AlgoritmoBellmanFordResultado;
import algoritmos.AlgoritmoBuscaLargura;
import algoritmos.AlgoritmoBuscaLarguraResultado;
import algoritmos.AlgoritmoBuscaProfundidade;
import algoritmos.AlgoritmoBuscaProfundidadeResultado;
import algoritmos.AlgoritmoDijkstra;
import algoritmos.AlgoritmoDijkstraResultado;
import algoritmos.AlgoritmoFloydWarshall;
import algoritmos.AlgoritmoFloydWarshallResultado;
import algoritmos.AlgoritmoFordFulkerson;
import algoritmos.AlgoritmoFordFulkersonDijkstra;
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
import auxiliar.Constante;
import base.Aresta;
import base.ArestaDirigida;
import base.ArestaNaoDirigida;
import base.Grafo;
import base.GrafoDirigido;
import base.GrafoNaoDirigido;
import base.Vertice;

public class TelaPrincipal extends JFrame {

    private Grafo g;

    private final JMenuBar menu = new JMenuBar();
    private final JLabel lblVertice = new JLabel("Vertices: ");
    private final JButton btnAddVertice = new JButton("+");
    private final JButton btnDelVertice = new JButton("-");
    private final JComboBox cboVertices = new JComboBox();
    private final JLabel lblAresta = new JLabel("Arestas: ");
    private final JButton btnAddAresta = new JButton("+");
    private final JButton btnDelAresta = new JButton("-");
    private final JComboBox cboArestas = new JComboBox();

    private JScrollPane scrollSaida;
    private JTextArea txtSaida;

    public static int yesno(String theMessage) {
        int result = JOptionPane.showConfirmDialog((Component) null, theMessage, "alert", JOptionPane.YES_NO_OPTION);
        return result;
    }

    public TelaPrincipal() {
        super("Principal");

        getContentPane().setLayout(new BorderLayout());

        createMenu();
        mostraVerticesArestas();
        mostraCampoSaida();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        this.setSize(new Dimension(900, 420));
        setResizable(true);
        setLocationRelativeTo(null);
    }

    public void mostraCampoSaida() {
        txtSaida = new JTextArea();
        JScrollPane spDescricao = new JScrollPane(txtSaida);

        txtSaida.setLineWrap(true);
        txtSaida.setEditable(true);
        //res.setPreferredSize(new Dimension(200, 130));
        spDescricao.setPreferredSize(new Dimension(200, 300));

        scrollSaida = spDescricao;

        getContentPane().add(scrollSaida, BorderLayout.SOUTH);
    }

    public void mostraVerticesArestas() {
        JPanel pnlTeste = new JPanel();

        pnlTeste.add(lblVertice);
        pnlTeste.add(cboVertices);
        pnlTeste.add(btnAddVertice);
        pnlTeste.add(btnDelVertice);

        pnlTeste.add(lblAresta);
        pnlTeste.add(cboArestas);
        pnlTeste.add(btnAddAresta);
        pnlTeste.add(btnDelAresta);

        btnAddAresta.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addAresta();
            }
        });

        btnDelAresta.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delAresta();
            }
        });

        btnAddVertice.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addVertice();
            }
        });

        btnDelVertice.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                delVertice();
            }
        });

        getContentPane().add(pnlTeste);
    }

    private void addAresta() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo esta criado");
            return;
        }

        String strId = javax.swing.JOptionPane.showInputDialog("Informe um identificador para a aresta...");

        if (strId == null || strId.equals("")) {
            JOptionPane.showMessageDialog(null, "Informe um identificador valido.");
            return;
        }

        int intId = Integer.parseInt(strId);
        if (g.getArestaById(intId) != null) {
            JOptionPane.showMessageDialog(null, "Ja existe uma aresta com este identificador...");
            return;
        }

        String strIdU = javax.swing.JOptionPane.showInputDialog("Informe o identificador do vertice de origem...");
        int intIdU = Integer.parseInt(strIdU);
        if (g.getVerticeById(intIdU) == null) {
            JOptionPane.showMessageDialog(null, "O vertice informado nao foi encontrado...");
            return;
        }

        String strIdV = javax.swing.JOptionPane.showInputDialog("Informe o identificador do vertice de destino...");
        int intIdV = Integer.parseInt(strIdV);
        if (g.getVerticeById(intIdV) == null) {
            JOptionPane.showMessageDialog(null, "O vertice informado nao foi encontrado...");
            return;
        }

        String dado = javax.swing.JOptionPane.showInputDialog("Informe algum dado da aresta...");
        String strValor = javax.swing.JOptionPane.showInputDialog("Informe algum valor (custo/peso) da aresta...");
        String strCapacidade = javax.swing.JOptionPane.showInputDialog("Informe alguma capacidade/fluxo da aresta...");

        double dblValor = 0;
        double dblCapacidade = 0;
        if (strValor != null && !strValor.equals("")) {
            dblValor = Double.valueOf(strValor);
        }
        if (strCapacidade != null && !strCapacidade.equals("")) {
            dblCapacidade = Double.valueOf(strCapacidade);
        }

        if (g.ehDirigido()) {
            Vertice u = g.getVerticeById(intIdU);
            Vertice v = g.getVerticeById(intIdV);
            ArestaDirigida a = new ArestaDirigida(intId, u, v);
            a.setDado(dado);
            a.setValor(dblValor);
            a.setCapacidade(dblCapacidade);

            try {
                ((GrafoDirigido) g).addAresta(a);
                carregaArestas();
            } catch (Exception e) {}

        } else {
            Vertice u = g.getVerticeById(intIdU);
            Vertice v = g.getVerticeById(intIdV);
            ArestaNaoDirigida a = new ArestaNaoDirigida(intId, u, v);
            a.setDado(dado);
            a.setValor(dblValor);
            a.setCapacidade(dblCapacidade);

            try {
                ((GrafoNaoDirigido) g).addAresta(a);
                carregaArestas();
            } catch (Exception e) {}

        }
    }

    private void carregaVertices() {
        cboVertices.removeAllItems();

        if (g == null) {
            return;
        }

        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            cboVertices.addItem(v);
        }
    }

    private void carregaArestas() {
        cboArestas.removeAllItems();

        if (g == null) {
            return;
        }

        HashMap<Integer, Boolean> arestasForam = new HashMap<Integer, Boolean>();
        int qtdeArestas = g.getQtdeArestas();
        for (int i = 0; i < qtdeArestas; i++) {
            Aresta a = g.getAresta(i);

            if (arestasForam.get(a.getId()) == null) {
                arestasForam.put(a.getId(), true);

                cboArestas.addItem(a);
            }
        }
    }

    private void delAresta() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo esta criado");
            return;
        }

        if (cboArestas.getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "Nenhuma aresta esta criada");
            return;
        }

        Aresta a = (Aresta) cboArestas.getSelectedItem();
        g.delAresta(a.getId());
        carregaArestas();
    }

    private void addVertice() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo esta criado");
            return;
        }

        String strId = javax.swing.JOptionPane.showInputDialog("Informe um identificador...");
        if (strId == null || strId.equals("")) {
            JOptionPane.showMessageDialog(null, "Informe um identificador valido.");
            return;
        }

        int id = Integer.parseInt(strId);

        if (g.getVerticeById(id) != null) {
            JOptionPane.showMessageDialog(null, "Ja existe um vertice com este identificador");
            return;
        }

        String dado = javax.swing.JOptionPane.showInputDialog("Informe algum dado do vertice...");

        if (g.ehDirigido()) {
            Vertice v = new Vertice(id);

            v.setDado(dado);
            try {
                ((GrafoDirigido) g).addVertice(v);
                carregaVertices();
            } catch (Exception e) {}

        } else {
            Vertice v = new Vertice(id);

            v.setDado(dado);
            try {
                ((GrafoNaoDirigido) g).addVertice(v);
                carregaVertices();
            } catch (Exception e) {}

        }

    }

    private void delVertice() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo esta criado");
            return;
        }

        if (cboVertices.getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "Nenhum vertice esta criado");
            return;
        }

        Vertice v = (Vertice) cboVertices.getSelectedItem();

        g.delVertice(v.getId());
        carregaVertices();
        carregaArestas();
    }

    private void carregaGrafo() {
        String arquivo = javax.swing.JOptionPane.showInputDialog("Informe o endereco do arquivo do grafo...");

        File file = new File(arquivo);

        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "Arquivo inexistente");
            return;
        }

        cboArestas.removeAllItems();
        cboVertices.removeAllItems();
        g = null;

        try {
            g = Persistencia.carregarGrafo(arquivo);
            carregaVertices();
            carregaArestas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void salvaGrafo() {
        String arquivo = javax.swing.JOptionPane.showInputDialog("Informe o endereco do arquivo do grafo para salvar...");

        try {
            Persistencia.salvarGrafo(g, arquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createMenu() {

        /*
         * Menu Arquivo
         */
        JMenu mnuArquivo = new JMenu("Arquivo");

        JMenu mnuGrafoNovo = new JMenu("Novo");
        mnuGrafoNovo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Novo");
            }

        });
        mnuArquivo.add(mnuGrafoNovo);

        JMenuItem mnuGrafoNovoDirigido = new JMenuItem("Dirigido");
        mnuGrafoNovoDirigido.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                g = null;
                carregaVertices();
                carregaArestas();
                g = new GrafoDirigido();
            }

        });
        mnuGrafoNovo.add(mnuGrafoNovoDirigido);

        JMenuItem mnuGrafoNovoNaoDirigido = new JMenuItem("N�o Dirigido");
        mnuGrafoNovoNaoDirigido.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                g = null;
                carregaVertices();
                carregaArestas();
                g = new GrafoNaoDirigido();
            }

        });
        mnuGrafoNovo.add(mnuGrafoNovoNaoDirigido);

        JMenu mnuGrafoGerar = new JMenu("Gerar grafo");
        mnuArquivo.add(mnuGrafoGerar);

        JMenuItem mnuGrafoAbrir = new JMenuItem("Abrir");
        mnuGrafoAbrir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                carregaGrafo();
            }

        });
        mnuArquivo.add(mnuGrafoAbrir);

        JMenuItem mnuGrafoSalvar = new JMenuItem("Salvar");
        mnuGrafoSalvar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                salvaGrafo();
            }

        });
        mnuArquivo.add(mnuGrafoSalvar);

        JMenuItem mnuGrafoGrafico = new JMenuItem("Tela grafica");
        mnuGrafoGrafico.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarGrafico();
            }

        });
        mnuArquivo.add(mnuGrafoGrafico);

        JMenuItem mnuGrafoSair = new JMenuItem("Sair");
        mnuGrafoSair.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });
        mnuArquivo.add(mnuGrafoSair);

        menu.add(mnuArquivo);

        /*
         * Menu Algoritmos
         */
        JMenu mnuAlgoritmos = new JMenu("Algoritmos");

        JMenuItem mnuAlgoritmoBuscaLargura = new JMenuItem("Busca em largura");
        mnuAlgoritmoBuscaLargura.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                executaBuscaLargura();
                imprimeln("");
            }

        });
        mnuAlgoritmos.add(mnuAlgoritmoBuscaLargura);

        JMenuItem mnuAlgoritmoBuscaProfundidade = new JMenuItem("Busca em profundidade");
        mnuAlgoritmoBuscaProfundidade.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                executaBuscaProfundidade();
                imprimeln("");
            }

        });
        mnuAlgoritmos.add(mnuAlgoritmoBuscaProfundidade);

        JMenuItem mnuAlgoritmoOrdenacaoTopologica = new JMenuItem("Ordenacao topologica");
        mnuAlgoritmoOrdenacaoTopologica.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                executaOrdenacaoTopologica();
                imprimeln("");
            }

        });
        mnuAlgoritmos.add(mnuAlgoritmoOrdenacaoTopologica);

        JMenuItem mnuAlgoritmoBellmanFord = new JMenuItem("Bellman-Ford");
        mnuAlgoritmoBellmanFord.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                executaBellmanFord();
                imprimeln("");
            }

        });
        mnuAlgoritmos.add(mnuAlgoritmoBellmanFord);

        JMenuItem mnuAlgoritmoDijkstra = new JMenuItem("Dijkstra");
        mnuAlgoritmoDijkstra.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                executaDijkstra();
                imprimeln("");
            }

        });
        mnuAlgoritmos.add(mnuAlgoritmoDijkstra);

        JMenuItem mnuAlgoritmoFloydWarshall = new JMenuItem("Floyd-Warshall");
        mnuAlgoritmoFloydWarshall.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                executaFloydWarshall();
                imprimeln("");
            }

        });
        mnuAlgoritmos.add(mnuAlgoritmoFloydWarshall);

        JMenuItem mnuAlgoritmoPrim = new JMenuItem("Prim");
        mnuAlgoritmoPrim.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                executaPrim();
                imprimeln("");
            }

        });
        mnuAlgoritmos.add(mnuAlgoritmoPrim);

        JMenuItem mnuAlgoritmoKruskal = new JMenuItem("Kruskal");
        mnuAlgoritmoKruskal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                executaKruskal();
                imprimeln("");
            }

        });
        mnuAlgoritmos.add(mnuAlgoritmoKruskal);

        JMenuItem mnuAlgoritmoHopcroftTarjan = new JMenuItem("Hopcroft-Tarjan");
        mnuAlgoritmoHopcroftTarjan.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                executaHopcroftTarjan();
                imprimeln("");
            }

        });
        mnuAlgoritmos.add(mnuAlgoritmoHopcroftTarjan);

        JMenuItem mnuAlgoritmoFordFulkerson = new JMenuItem("Ford-Fulkerson");
        mnuAlgoritmoFordFulkerson.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                executaFordFulkerson();
                imprimeln("");
            }

        });
        mnuAlgoritmos.add(mnuAlgoritmoFordFulkerson);

        JMenuItem mnuAlgoritmoFordFulkersonDijkstra = new JMenuItem("Ford-Fulkerson-Dijkstra");
        mnuAlgoritmoFordFulkersonDijkstra.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                executaFordFulkersonDijkstra();
                imprimeln("");
            }

        });
        mnuAlgoritmos.add(mnuAlgoritmoFordFulkersonDijkstra);

        JMenuItem mnuAlgoritmoBase = new JMenuItem("Encontrar Base");
        mnuAlgoritmoBase.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                executaBase();
                imprimeln("");
            }

        });
        mnuAlgoritmos.add(mnuAlgoritmoBase);

        JMenuItem mnuAlgoritmoAntibase = new JMenuItem("Encontrar Antibase");
        mnuAlgoritmoAntibase.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                executaAntibase();
                imprimeln("");
            }

        });
        mnuAlgoritmos.add(mnuAlgoritmoAntibase);

        JMenuItem mnuAlgoritmoArticulacoes = new JMenuItem("Encontrar Articulacoes");
        mnuAlgoritmoArticulacoes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                executaArticulacoes();
                imprimeln("");
            }

        });
        mnuAlgoritmos.add(mnuAlgoritmoArticulacoes);

        JMenuItem mnuAlgoritmoPontes = new JMenuItem("Encontrar Pontes");
        mnuAlgoritmoPontes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                executaPontes();
                imprimeln("");
            }

        });
        mnuAlgoritmos.add(mnuAlgoritmoPontes);

        menu.add(mnuAlgoritmos);

        /*
         * Menu Propriedades
         */
        JMenu mnuPropriedades = new JMenu("Propriedades");

        JMenuItem mnuPropriedadeConexo = new JMenuItem("Conexo");
        mnuPropriedadeConexo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Conexo: " + g.ehConexo());
            }

        });
        mnuPropriedades.add(mnuPropriedadeConexo);

        JMenuItem mnuPropriedadeDesconexo = new JMenuItem("Desconexo");
        mnuPropriedadeDesconexo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Desconexo: " + g.ehDesconexo());
            }

        });
        mnuPropriedades.add(mnuPropriedadeDesconexo);

        JMenuItem mnuPropriedadeNulo = new JMenuItem("Nulo");
        mnuPropriedadeNulo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Nulo: " + g.ehNulo());
            }

        });
        mnuPropriedades.add(mnuPropriedadeNulo);

        JMenuItem mnuPropriedadeTrivial = new JMenuItem("Trivial");
        mnuPropriedadeTrivial.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Trivial: " + g.ehTrivial());
            }

        });
        mnuPropriedades.add(mnuPropriedadeTrivial);

        JMenuItem mnuPropriedadeRegular = new JMenuItem("Regular");
        mnuPropriedadeRegular.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Regular: " + g.ehRegular());
            }

        });
        mnuPropriedades.add(mnuPropriedadeRegular);

        JMenuItem mnuPropriedadeDenso = new JMenuItem("Denso");
        mnuPropriedadeDenso.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Denso: " + g.ehDenso());
            }

        });
        mnuPropriedades.add(mnuPropriedadeDenso);

        JMenuItem mnuPropriedadeEsparso = new JMenuItem("Esparso");
        mnuPropriedadeEsparso.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Esparso: " + g.ehEsparso());
            }

        });
        mnuPropriedades.add(mnuPropriedadeEsparso);

        JMenuItem mnuPropriedadeSimples = new JMenuItem("Simples");
        mnuPropriedadeSimples.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Simples: " + g.ehSimples());
            }

        });
        mnuPropriedades.add(mnuPropriedadeSimples);

        JMenuItem mnuPropriedadeMultigrafo = new JMenuItem("Multigrafo");
        mnuPropriedadeMultigrafo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Multigrafo: " + g.ehMultigrafo());
            }

        });
        mnuPropriedades.add(mnuPropriedadeMultigrafo);

        JMenuItem mnuPropriedadeCompleto = new JMenuItem("Completo");
        mnuPropriedadeCompleto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Completo: " + g.ehCompleto());
            }

        });
        mnuPropriedades.add(mnuPropriedadeCompleto);

        JMenuItem mnuPropriedadeBipartido = new JMenuItem("Bipartido");
        mnuPropriedadeBipartido.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Bipartido: " + g.ehBipartido());
            }

        });
        mnuPropriedades.add(mnuPropriedadeBipartido);

        JMenuItem mnuPropriedadeBipartidoCompleto = new JMenuItem("Bipartido completo");
        mnuPropriedadeBipartidoCompleto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Bipartido completo: " + g.ehBipartidoCompleto());
            }

        });
        mnuPropriedades.add(mnuPropriedadeBipartidoCompleto);

        JMenuItem mnuPropriedadeCiclo = new JMenuItem("Ciclo");
        mnuPropriedadeCiclo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Ciclo: " + g.ehCiclo());
            }

        });
        mnuPropriedades.add(mnuPropriedadeCiclo);

        JMenuItem mnuPropriedadeAciclico = new JMenuItem("Aciclico");
        mnuPropriedadeAciclico.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Aciclico: " + g.ehAciclico());
            }

        });
        mnuPropriedades.add(mnuPropriedadeAciclico);

        JMenuItem mnuPropriedadeArvore = new JMenuItem("Arvore/Arborescencia");
        mnuPropriedadeArvore.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (g.ehDirigido()) {
                    JOptionPane.showMessageDialog(null, "Arborescencia: " + ((GrafoDirigido) g).ehArborescencia());
                } else {
                    JOptionPane.showMessageDialog(null, "Arvore: " + ((GrafoNaoDirigido) g).ehArvore());
                }

            }

        });
        mnuPropriedades.add(mnuPropriedadeArvore);

        JMenuItem mnuPropriedadeFloresta = new JMenuItem("Floresta");
        mnuPropriedadeFloresta.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Floresta: " + g.ehFloresta());
            }

        });
        mnuPropriedades.add(mnuPropriedadeFloresta);

        menu.add(mnuPropriedades);

        /*
         * Gerar grafos 
         */
        JMenu mnuGrafoGerarDirigido = new JMenu("Dirigido");
        mnuGrafoGerar.add(mnuGrafoGerarDirigido);

        JMenu mnuGrafoGerarNaoDirigido = new JMenu("N�o Dirigido");
        mnuGrafoGerar.add(mnuGrafoGerarNaoDirigido);

        /*
         * Menu Dirigido
         */
        JMenuItem mnuGrafoGerarDirigidoCompleto = new JMenuItem("Completo");
        mnuGrafoGerarDirigidoCompleto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoDirigidoCompleto();
            }

        });
        mnuGrafoGerarDirigido.add(mnuGrafoGerarDirigidoCompleto);

        JMenuItem mnuGrafoGerarDirigidoSimples = new JMenuItem("Simples");
        mnuGrafoGerarDirigidoSimples.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoDirigidoSimples();
            }

        });
        mnuGrafoGerarDirigido.add(mnuGrafoGerarDirigidoSimples);

        JMenuItem mnuGrafoGerarDirigidoMultigrafo = new JMenuItem("Multigrafo");
        mnuGrafoGerarDirigidoMultigrafo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoDirigidoMultigrafo();
            }

        });
        mnuGrafoGerarDirigido.add(mnuGrafoGerarDirigidoMultigrafo);

        JMenuItem mnuGrafoGerarDirigidoDenso = new JMenuItem("Denso");
        mnuGrafoGerarDirigidoDenso.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoDirigidoDenso();
            }

        });
        mnuGrafoGerarDirigido.add(mnuGrafoGerarDirigidoDenso);

        JMenuItem mnuGrafoGerarDirigidoEsparso = new JMenuItem("Esparso");
        mnuGrafoGerarDirigidoEsparso.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoDirigidoEsparso();
            }

        });
        mnuGrafoGerarDirigido.add(mnuGrafoGerarDirigidoEsparso);

        JMenuItem mnuGrafoGerarDirigidoConexo = new JMenuItem("Conexo");
        mnuGrafoGerarDirigidoConexo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoDirigidoConexo();
            }

        });
        mnuGrafoGerarDirigido.add(mnuGrafoGerarDirigidoConexo);

        JMenuItem mnuGrafoGerarDirigidoDesconexo = new JMenuItem("Desconexo");
        mnuGrafoGerarDirigidoDesconexo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoDirigidoDesconexo();
            }

        });
        mnuGrafoGerarDirigido.add(mnuGrafoGerarDirigidoDesconexo);

        JMenuItem mnuGrafoGerarDirigidoRegular = new JMenuItem("Regular");
        mnuGrafoGerarDirigidoRegular.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoDirigidoRegular();
            }

        });
        mnuGrafoGerarDirigido.add(mnuGrafoGerarDirigidoRegular);

        JMenuItem mnuGrafoGerarDirigidoBipartido = new JMenuItem("Bipartido");
        mnuGrafoGerarDirigidoBipartido.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoDirigidoBipartido();
            }

        });
        mnuGrafoGerarDirigido.add(mnuGrafoGerarDirigidoBipartido);

        JMenuItem mnuGrafoGerarDirigidoBipartidoCompleto = new JMenuItem("Bipartido completo");
        mnuGrafoGerarDirigidoBipartidoCompleto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoDirigidoBipartidoCompleto();
            }

        });
        mnuGrafoGerarDirigido.add(mnuGrafoGerarDirigidoBipartidoCompleto);

        JMenuItem mnuGrafoGerarDirigidoCiclo = new JMenuItem("Ciclo");
        mnuGrafoGerarDirigidoCiclo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoDirigidoCiclo();
            }

        });
        mnuGrafoGerarDirigido.add(mnuGrafoGerarDirigidoCiclo);

        JMenuItem mnuGrafoGerarDirigidoAciclico = new JMenuItem("Aciclico");
        mnuGrafoGerarDirigidoAciclico.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoDirigidoAciclico();
            }

        });
        mnuGrafoGerarDirigido.add(mnuGrafoGerarDirigidoAciclico);

        JMenuItem mnuGrafoGerarDirigidoRandomico = new JMenuItem("Randomico (Conexo, Simples, Aciclico)");
        mnuGrafoGerarDirigidoRandomico.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoDirigidoRandomico();
            }

        });
        mnuGrafoGerarDirigido.add(mnuGrafoGerarDirigidoRandomico);

        /*
         * Menu N�o NaoDirigido
         */
        JMenuItem mnuGrafoGerarNaoDirigidoCompleto = new JMenuItem("Completo");
        mnuGrafoGerarNaoDirigidoCompleto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoNaoDirigidoCompleto();
            }

        });
        mnuGrafoGerarNaoDirigido.add(mnuGrafoGerarNaoDirigidoCompleto);

        JMenuItem mnuGrafoGerarNaoDirigidoSimples = new JMenuItem("Simples");
        mnuGrafoGerarNaoDirigidoSimples.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoNaoDirigidoSimples();
            }

        });
        mnuGrafoGerarNaoDirigido.add(mnuGrafoGerarNaoDirigidoSimples);

        JMenuItem mnuGrafoGerarNaoDirigidoMultigrafo = new JMenuItem("Multigrafo");
        mnuGrafoGerarNaoDirigidoMultigrafo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoNaoDirigidoMultigrafo();
            }

        });
        mnuGrafoGerarNaoDirigido.add(mnuGrafoGerarNaoDirigidoMultigrafo);

        JMenuItem mnuGrafoGerarNaoDirigidoDenso = new JMenuItem("Denso");
        mnuGrafoGerarNaoDirigidoDenso.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoNaoDirigidoDenso();
            }

        });
        mnuGrafoGerarNaoDirigido.add(mnuGrafoGerarNaoDirigidoDenso);

        JMenuItem mnuGrafoGerarNaoDirigidoEsparso = new JMenuItem("Esparso");
        mnuGrafoGerarNaoDirigidoEsparso.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoNaoDirigidoEsparso();
            }

        });
        mnuGrafoGerarNaoDirigido.add(mnuGrafoGerarNaoDirigidoEsparso);

        JMenuItem mnuGrafoGerarNaoDirigidoConexo = new JMenuItem("Conexo");
        mnuGrafoGerarNaoDirigidoConexo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoNaoDirigidoConexo();
            }

        });
        mnuGrafoGerarNaoDirigido.add(mnuGrafoGerarNaoDirigidoConexo);

        JMenuItem mnuGrafoGerarNaoDirigidoDesconexo = new JMenuItem("Desconexo");
        mnuGrafoGerarNaoDirigidoDesconexo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoNaoDirigidoDesconexo();
            }

        });
        mnuGrafoGerarNaoDirigido.add(mnuGrafoGerarNaoDirigidoDesconexo);

        JMenuItem mnuGrafoGerarNaoDirigidoRegular = new JMenuItem("Regular");
        mnuGrafoGerarNaoDirigidoRegular.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoNaoDirigidoRegular();
            }

        });
        mnuGrafoGerarNaoDirigido.add(mnuGrafoGerarNaoDirigidoRegular);

        JMenuItem mnuGrafoGerarNaoDirigidoBipartido = new JMenuItem("Bipartido");
        mnuGrafoGerarNaoDirigidoBipartido.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoNaoDirigidoBipartido();
            }

        });
        mnuGrafoGerarNaoDirigido.add(mnuGrafoGerarNaoDirigidoBipartido);

        JMenuItem mnuGrafoGerarNaoDirigidoBipartidoCompleto = new JMenuItem("Bipartido completo");
        mnuGrafoGerarNaoDirigidoBipartidoCompleto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoNaoDirigidoBipartidoCompleto();
            }

        });
        mnuGrafoGerarNaoDirigido.add(mnuGrafoGerarNaoDirigidoBipartidoCompleto);

        JMenuItem mnuGrafoGerarNaoDirigidoCiclo = new JMenuItem("Ciclo");
        mnuGrafoGerarNaoDirigidoCiclo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoNaoDirigidoCiclo();
            }

        });
        mnuGrafoGerarNaoDirigido.add(mnuGrafoGerarNaoDirigidoCiclo);

        JMenuItem mnuGrafoGerarNaoDirigidoAciclico = new JMenuItem("Aciclico");
        mnuGrafoGerarNaoDirigidoAciclico.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoNaoDirigidoAciclico();
            }

        });
        mnuGrafoGerarNaoDirigido.add(mnuGrafoGerarNaoDirigidoAciclico);

        JMenuItem mnuGrafoGerarNaoDirigidoRandomico = new JMenuItem("Randomico (Conexo, Simples, Aciclico)");
        mnuGrafoGerarNaoDirigidoRandomico.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gerarGrafoNaoDirigidoRandomico();
            }

        });
        mnuGrafoGerarNaoDirigido.add(mnuGrafoGerarNaoDirigidoRandomico);

        getContentPane().add(menu, BorderLayout.NORTH);
    }

    private void limpar() {
        txtSaida.setText("");
    }

    private void imprime(String texto) {
        if (txtSaida.getText() == null || txtSaida.getText().equals("")) {
            txtSaida.setText(texto);
        } else {
            txtSaida.setText(txtSaida.getText() + texto);
        }
    }

    private void imprimeln(String texto) {
        if (txtSaida.getText() == null || txtSaida.getText().equals("")) {
            txtSaida.setText(texto + "\n");
        } else {
            txtSaida.setText(txtSaida.getText() + texto + "\n");
        }
    }

    private void gerarGrafoDirigidoCompleto() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoCompletoDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoDirigidoSimples() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoSimplesDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoDirigidoMultigrafo() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoMultigrafoDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoDirigidoDenso() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoDensoDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoDirigidoEsparso() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoEsparsoDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoDirigidoConexo() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoConexoDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoDirigidoDesconexo() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = null;
        cboArestas.removeAllItems();
        cboVertices.removeAllItems();

        try {
            g = GeradorGrafos.getGrafoDesconexoDirigido(Integer.parseInt(strQtde));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoDirigidoRegular() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        String strGrau = javax.swing.JOptionPane.showInputDialog("Informe o grau dos vertices...");
        if (strGrau == null || strGrau.equals("")) {
            JOptionPane.showMessageDialog(null, "Grau inválido...");
            return;
        }

        g = null;
        cboArestas.removeAllItems();
        cboVertices.removeAllItems();

        try {
            g = GeradorGrafos.getGrafoRegularDirigido(Integer.parseInt(strQtde), Integer.parseInt(strGrau));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoDirigidoBipartido() {
        String strQtdeX = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices do conjunto X...");
        if (strQtdeX == null || strQtdeX.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        String strQtdeY = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices do conjunto Y...");
        if (strQtdeY == null || strQtdeY.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoBipartidoDirigido(Integer.parseInt(strQtdeX), Integer.parseInt(strQtdeY));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoDirigidoBipartidoCompleto() {
        String strQtdeX = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices do conjunto X...");
        if (strQtdeX == null || strQtdeX.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        String strQtdeY = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices do conjunto Y...");
        if (strQtdeY == null || strQtdeY.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoBipartidoCompletoDirigido(Integer.parseInt(strQtdeX), Integer.parseInt(strQtdeY));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoNaoDirigidoCompleto() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoCompletoNaoDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoNaoDirigidoSimples() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoSimplesNaoDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoNaoDirigidoMultigrafo() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoMultigrafoNaoDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoNaoDirigidoDenso() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoDensoNaoDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoNaoDirigidoEsparso() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoEsparsoNaoDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoNaoDirigidoConexo() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoConexoNaoDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoNaoDirigidoDesconexo() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = null;
        cboArestas.removeAllItems();
        cboVertices.removeAllItems();

        try {
            g = GeradorGrafos.getGrafoDesconexoNaoDirigido(Integer.parseInt(strQtde));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoNaoDirigidoRegular() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        String strGrau = javax.swing.JOptionPane.showInputDialog("Informe o grau dos vertices...");
        if (strGrau == null || strGrau.equals("")) {
            JOptionPane.showMessageDialog(null, "Grau inválido...");
            return;
        }

        g = null;
        cboArestas.removeAllItems();
        cboVertices.removeAllItems();

        try {
            g = GeradorGrafos.getGrafoRegularNaoDirigido(Integer.parseInt(strQtde), Integer.parseInt(strGrau));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoNaoDirigidoBipartido() {
        String strQtdeX = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices do conjunto X...");
        if (strQtdeX == null || strQtdeX.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        String strQtdeY = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices do conjunto Y...");
        if (strQtdeY == null || strQtdeY.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoBipartidoNaoDirigido(Integer.parseInt(strQtdeX), Integer.parseInt(strQtdeY));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoNaoDirigidoBipartidoCompleto() {
        String strQtdeX = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices do conjunto X...");
        if (strQtdeX == null || strQtdeX.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        String strQtdeY = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices do conjunto Y...");
        if (strQtdeY == null || strQtdeY.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoBipartidoCompletoNaoDirigido(Integer.parseInt(strQtdeX), Integer.parseInt(strQtdeY));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoNaoDirigidoCiclo() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoCicloNaoDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoDirigidoCiclo() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoCicloDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoNaoDirigidoRandomico() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        boolean blnConexo = yesno("O grafo deve ser conexo?") != 1;
        boolean blnSimples = yesno("O grafo deve ser simples?") != 1;
        boolean blnAciclico = yesno("O grafo deve ser aciclico?") != 1;

        g = GeradorGrafos.getGrafoRandomicoNaoDirigido(Integer.parseInt(strQtde), blnConexo, blnSimples, blnAciclico);
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoDirigidoRandomico() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        boolean blnConexo = yesno("O grafo deve ser conexo?") != 1;
        boolean blnSimples = yesno("O grafo deve ser simples?") != 1;
        boolean blnAciclico = yesno("O grafo deve ser aciclico?") != 1;

        try {
            g = GeradorGrafos.getGrafoRandomicoDirigido(Integer.parseInt(strQtde), blnConexo, blnSimples, blnAciclico);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoNaoDirigidoAciclico() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoAciclicoNaoDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    private void gerarGrafoDirigidoAciclico() {
        String strQtde = javax.swing.JOptionPane.showInputDialog("Informe a quantidade de vertices...");
        if (strQtde == null || strQtde.equals("")) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida...");
            return;
        }

        g = GeradorGrafos.getGrafoAciclicoDirigido(Integer.parseInt(strQtde));
        carregaVertices();
        carregaArestas();
    }

    public void executaDijkstra() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo criado...");
            return;
        }

        imprimeln("*** Executando algoritmo de Dijkstra ***");

        //parametros
        String strIdU = javax.swing.JOptionPane.showInputDialog("Informe o identificador do vertice de origem...");
        if (strIdU == null || strIdU.equals("")) {
            JOptionPane.showMessageDialog(null, "Identificador invalido...");
            imprimeln("Processo abortado");
            return;
        }
        int intIdU = Integer.parseInt(strIdU);
        if (g.getVerticeById(intIdU) == null) {
            JOptionPane.showMessageDialog(null, "Identificador nao encontrado...");
            imprimeln("Processo abortado");
            return;
        }

        String strIdV = javax.swing.JOptionPane.showInputDialog("Informe o identificador do vertice de destino...");
        if (strIdV == null || strIdV.equals("")) {
            JOptionPane.showMessageDialog(null, "Identificador invalido...");
            imprimeln("Processo abortado");
            return;
        }
        int intIdV = Integer.parseInt(strIdV);
        if (g.getVerticeById(intIdV) == null) {
            JOptionPane.showMessageDialog(null, "Identificador nao encontrado...");
            imprimeln("Processo abortado");
            return;
        }

        Vertice s = g.getVerticeById(intIdU);
        Vertice d = g.getVerticeById(intIdV);

        AlgoritmoDijkstra alg = new AlgoritmoDijkstra();
        alg.executar(g, s, d);

        imprimeln("*** FIM DA EXECUCAO ***");
        imprimeln("*** RESULTADOS ***");

        AlgoritmoDijkstraResultado res = alg.getResultado();
        if (!res.getSemCaminho(d)) {
            imprimeln("Menor custo: " + res.getCusto(d));
            String caminho = "";

            caminho = "" + d.getDado();
            for (Vertice ver = res.getPredecessor(d); ver != null; ver = res.getPredecessor(ver)) {
                caminho = ver.getDado() + " -> " + caminho;
            }
            imprimeln(caminho);
        } else {
            imprimeln("Impossivel");
        }
    }

    public void executaBellmanFord() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo criado...");
            return;
        }

        imprimeln("*** Executando algoritmo de Bellman-Ford ***");

        //parametros
        String strIdU = javax.swing.JOptionPane.showInputDialog("Informe o identificador do vertice de origem...");
        if (strIdU == null || strIdU.equals("")) {
            JOptionPane.showMessageDialog(null, "Identificador invalido...");
            imprimeln("Processo abortado");
            return;
        }
        int intIdU = Integer.parseInt(strIdU);
        if (g.getVerticeById(intIdU) == null) {
            JOptionPane.showMessageDialog(null, "Identificador nao encontrado...");
            imprimeln("Processo abortado");
            return;
        }

        Vertice s = g.getVerticeById(intIdU);

        AlgoritmoBellmanFord alg = new AlgoritmoBellmanFord();
        alg.executar(g, s);

        imprimeln("*** FIM DA EXECUCAO ***");
        imprimeln("*** RESULTADOS ***");

        AlgoritmoBellmanFordResultado res = alg.getResultado();

        for (;;) {
            //parametros
            String strIdV = javax.swing.JOptionPane.showInputDialog("Informe o identificador de um vertice de destino ou nada para abortar...");
            if (strIdV == null || strIdV.equals("")) {
                JOptionPane.showMessageDialog(null, "Identificador invalido...");
                imprimeln("Processo abortado");
                return;
            }
            int intIdV = Integer.parseInt(strIdV);
            if (g.getVerticeById(intIdV) == null) {
                JOptionPane.showMessageDialog(null, "Identificador nao encontrado...");
                return;
            }

            Vertice d = g.getVerticeById(intIdV);

            imprimeln("*** Resultado para " + d.getDado() + " ***");

            if (res.getCusto(d) != Constante.INF) {
                imprimeln("Menor custo: " + +res.getCusto(d));
                String caminho = "";

                caminho = "" + d.getDado();
                for (Vertice ver = res.getPredecessor(d); ver != null; ver = res.getPredecessor(ver)) {
                    caminho = ver.getDado() + " -> " + caminho;
                }
                imprimeln(caminho);
            } else {
                imprimeln("Impossivel");
            }
        }
    }

    public void executaFloydWarshall() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo criado...");
            return;
        }

        imprimeln("*** Executando algoritmo de Floyd-Warshall ***");

        AlgoritmoFloydWarshall alg = new AlgoritmoFloydWarshall();
        alg.executar(g);

        imprimeln("*** FIM DA EXECUCAO ***");
        imprimeln("*** RESULTADOS ***");

        AlgoritmoFloydWarshallResultado res = alg.getResultado();

        for (;;) {
            //parametros
            String strIdU = javax.swing.JOptionPane.showInputDialog("Informe o identificador do vertice de origem ou nada para abortar...");
            if (strIdU == null || strIdU.equals("")) {
                JOptionPane.showMessageDialog(null, "Identificador invalido...");
                imprimeln("Processo abortado");
                return;
            }
            int intIdU = Integer.parseInt(strIdU);
            if (g.getVerticeById(intIdU) == null) {
                JOptionPane.showMessageDialog(null, "Identificador nao encontrado...");
                imprimeln("Processo abortado");
                return;
            }
            Vertice s = g.getVerticeById(intIdU);

            String strIdV = javax.swing.JOptionPane.showInputDialog("Informe o identificador de um vertice de destino ou nada para abortar...");
            if (strIdV == null || strIdV.equals("")) {
                JOptionPane.showMessageDialog(null, "Identificador invalido...");
                imprimeln("Processo abortado");
                return;
            }
            int intIdV = Integer.parseInt(strIdV);
            if (g.getVerticeById(intIdV) == null) {
                JOptionPane.showMessageDialog(null, "Identificador nao encontrado...");
            }
            Vertice d = g.getVerticeById(intIdV);

            imprimeln("*** Resultado para " + s.getDado() + " -> " + d.getDado() + " ***");

            if (res.getCusto(s, d) != Constante.INF) {
                imprimeln("Menor custo: " + res.getCusto(s, d));
                String caminho = "";

                caminho = "" + d.getDado();

                for (Vertice ver = res.getPredecessor(s, d); !ver.equals(s); ver = res.getPredecessor(s, ver)) {
                    caminho = ver.getDado() + " -> " + caminho;
                }
                caminho = s.getDado() + " -> " + caminho;
                imprimeln(caminho);
            } else {
                imprimeln("Impossivel");
            }
        }
    }

    public void executaFordFulkerson() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo criado...");
            return;
        }

        imprimeln("*** Executando algoritmo de Ford-Fulkerson ***");

        //parametros
        String strIdU = javax.swing.JOptionPane.showInputDialog("Informe o identificador do vertice de origem...");
        if (strIdU == null || strIdU.equals("")) {
            JOptionPane.showMessageDialog(null, "Identificador invalido...");
            imprimeln("Processo abortado");
            return;
        }
        int intIdU = Integer.parseInt(strIdU);
        if (g.getVerticeById(intIdU) == null) {
            JOptionPane.showMessageDialog(null, "Identificador nao encontrado...");
            imprimeln("Processo abortado");
            return;
        }

        String strIdV = javax.swing.JOptionPane.showInputDialog("Informe o identificador do vertice de destino...");
        if (strIdV == null || strIdV.equals("")) {
            JOptionPane.showMessageDialog(null, "Identificador invalido...");
            imprimeln("Processo abortado");
            return;
        }
        int intIdV = Integer.parseInt(strIdV);
        if (g.getVerticeById(intIdV) == null) {
            JOptionPane.showMessageDialog(null, "Identificador nao encontrado...");
            imprimeln("Processo abortado");
            return;
        }

        Vertice s = g.getVerticeById(intIdU);
        Vertice d = g.getVerticeById(intIdV);

        AlgoritmoFordFulkerson alg = new AlgoritmoFordFulkerson();
        alg.executar(g, s, d);

        imprimeln("*** FIM DA EXECUCAO ***");
        imprimeln("*** RESULTADOS ***");

        AlgoritmoFordFulkersonResultado res = alg.getResultado();
        imprimeln("Fluxo maximo: " + res.getFluxoMaximo() + " Custo: " + res.getCustoFluxo());
        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice u = g.getVertice(i);
            for (int j = 0; j < tamanhoGrafo; j++) {
                Vertice v = g.getVertice(j);
                if (res.getFluxo(u, v) > 0.0) {
                    imprimeln(u.getDado() + " -> " + v.getDado() + " Fluxo: " + res.getFluxo(u, v));
                }
            }
        }
    }

    public void executaHopcroftTarjan() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo criado...");
            return;
        }

        imprimeln("*** Executando algoritmo de Hopcroft-Tarjan ***");

        AlgoritmoHopcroftTarjan alg = new AlgoritmoHopcroftTarjan();
        alg.executar(g);

        imprimeln("*** FIM DA EXECUCAO ***");
        imprimeln("*** RESULTADOS ***");

        AlgoritmoHopcroftTarjanResultado res = alg.getResultado();
        imprimeln("Total de componentes: " + res.getQtdeComponentes());

        for (ArrayList<Vertice> componente : res.getComponentes()) {
            imprime("Componente: ");
            for (Vertice v : componente) {
                imprime(v.getDado() + " ");
            }
            imprimeln("");
        }
    }

    public void executaOrdenacaoTopologica() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo criado...");
            return;
        }

        if (!g.ehDirigido() || !g.ehAciclico()) {
            JOptionPane.showMessageDialog(null, "O grafo precisa ser dirigido e ac�clico...");
            return;
        }

        imprimeln("*** Executando algoritmo de Ordenacao Topologica ***");

        AlgoritmoOrdenacaoTopologica alg = new AlgoritmoOrdenacaoTopologica();
        try {
            alg.executar((GrafoDirigido) g);
        } catch (Exception e) {
            imprimeln(e.getMessage());
            return;
        }

        imprimeln("*** FIM DA EXECUCAO ***");
        imprimeln("*** RESULTADOS ***");

        AlgoritmoOrdenacaoTopologicaResultado res = alg.getResultado();

        int tamanhoLista = res.getTamanho();
        Vertice v = res.getVertice(0);
        String seq = v.getDado() + "[" + res.getTempoAbertura(v) + "/" + res.getTempoFechamento(v) + "]";
        for (int i = 1; i < tamanhoLista; i++) {
            v = res.getVertice(i);
            seq = v.getDado() + "[" + res.getTempoAbertura(v) + "/" + res.getTempoFechamento(v) + "]" + " -> " + seq;
        }

        imprimeln(seq);
    }

    public void executaKruskal() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo criado...");
            return;
        }

        if (g.ehDirigido()) {
            JOptionPane.showMessageDialog(null, "O algoritmo de Kruskal nao pode ser executado num grafo dirigido...");
            return;
        }

        imprimeln("*** Executando algoritmo de Kruskal ***");

        AlgoritmoKruskal alg = new AlgoritmoKruskal();
        alg.executar((GrafoNaoDirigido) g);

        imprimeln("*** FIM DA EXECUCAO ***");
        imprimeln("*** RESULTADOS ***");

        AlgoritmoKruskalResultado res = alg.getResultado();
        imprimeln("Custo total minimo: " + res.getCustoTotal());

        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            imprimeln("Resumo vertice " + v.getDado());
            Vertice pred = res.getPredecessor(v);
            if (pred == null) {
                imprimeln("Predecessor: null");
            } else {
                imprimeln("Predecessor: " + pred.getDado() + " Custo: " + res.getCusto(v));
            }

            ArrayList<Vertice> descendentes = res.getDescendentes(v);
            imprime("Sucessores: ");
            String desc = "";
            if (descendentes != null) {
                for (Vertice j : descendentes) {
                    desc = desc + " " + j.getDado();
                }
            }
            imprimeln(desc);
            imprimeln("--------");
        }

        if (g.ehDirigido()) {
            ArrayList<Aresta> arestas = res.getArestas();
            imprimeln("Arestas utilizadas");
            for (int i = 0; i < arestas.size(); i++) {
                ArestaDirigida a = (ArestaDirigida) arestas.get(i);
                imprimeln(a.getVi().getDado() + " -> " + a.getVj().getDado() + " Custo: " + a.getValor());
            }
        } else {
            ArrayList<Aresta> arestas = res.getArestas();
            imprimeln("Arestas utilizadas");
            for (int i = 0; i < arestas.size(); i++) {
                ArestaNaoDirigida a = (ArestaNaoDirigida) arestas.get(i);
                imprimeln(a.getVi().getDado() + " <-> " + a.getVj(a.getVi()).getDado() + " Custo: " + a.getValor());
            }
        }
    }

    public void executaPrim() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo criado...");
            return;
        }

        if (g.ehDirigido()) {
            JOptionPane.showMessageDialog(null, "O algoritmo de Prim nao pode ser executado num grafo dirigido...");
            return;
        }

        imprimeln("*** Executando algoritmo de Prim ***");

        AlgoritmoPrim alg = new AlgoritmoPrim();
        alg.executar((GrafoNaoDirigido) g);

        imprimeln("*** FIM DA EXECUCAO ***");
        imprimeln("*** RESULTADOS ***");

        AlgoritmoPrimResultado res = alg.getResultado();
        imprimeln("Custo total minimo: " + res.getCustoTotal());

        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            imprimeln("Resumo vertice " + v.getDado());
            Vertice pred = res.getPredecessor(v);
            if (pred == null) {
                imprimeln("Predecessor: null");
            } else {
                imprimeln("Predecessor: " + pred.getDado() + " Custo: " + res.getCusto(v));
            }

            ArrayList<Vertice> descendentes = res.getDescendentes(v);
            imprime("Sucessores: ");
            String desc = "";
            if (descendentes != null) {
                for (Vertice j : descendentes) {
                    desc = desc + " " + j.getDado();
                }
            }
            imprimeln(desc);
            imprimeln("--------");
        }

        if (g.ehDirigido()) {
            ArrayList<Aresta> arestas = res.getArestas();
            imprimeln("Arestas utilizadas");
            for (int i = 0; i < arestas.size(); i++) {
                ArestaDirigida a = (ArestaDirigida) arestas.get(i);
                imprimeln(a.getVi().getDado() + " -> " + a.getVj().getDado() + " Custo: " + a.getValor());
            }
        } else {
            ArrayList<Aresta> arestas = res.getArestas();
            imprimeln("Arestas utilizadas");
            for (int i = 0; i < arestas.size(); i++) {
                ArestaNaoDirigida a = (ArestaNaoDirigida) arestas.get(i);
                imprimeln(a.getVi().getDado() + " <-> " + a.getVj(a.getVi()).getDado() + " Custo: " + a.getValor());
            }
        }
    }

    public void executaBuscaLargura() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo criado...");
            return;
        }

        imprimeln("*** Executando algoritmo de Busca em Largura ***");

        AlgoritmoBuscaLargura alg;

        //parametros
        String strIdU = javax.swing.JOptionPane.showInputDialog("Informe o identificador do vertice de origem. Se nao houver deixe em branco...");
        if (strIdU == null || strIdU.equals("")) {
            imprimeln("Nenhum vertice de origem escolhido");
            alg = new AlgoritmoBuscaLargura();
            alg.executar(g);
        } else {
            int intIdU = Integer.parseInt(strIdU);
            if (g.getVerticeById(intIdU) == null) {
                JOptionPane.showMessageDialog(null, "Identificador nao encontrado...");
                imprimeln("Processo abortado");
                return;
            }
            Vertice s = g.getVerticeById(intIdU);
            imprimeln("Vertice de origem: " + s.getDado());

            String strIdV = javax.swing.JOptionPane.showInputDialog("Informe o identificador do vertice de destino. Se nao houver deixe em branco...");
            if (strIdV == null || strIdV.equals("")) {
                imprimeln("Nenhum vertice de destino escolhido");
                alg = new AlgoritmoBuscaLargura();
                alg.executar(g, s);
            } else {
                int intIdV = Integer.parseInt(strIdV);
                if (g.getVerticeById(intIdV) == null) {
                    JOptionPane.showMessageDialog(null, "Identificador nao encontrado...");
                    imprimeln("Processo abortado");
                    return;
                }
                Vertice d = g.getVerticeById(intIdV);
                imprimeln("Vertice de destino: " + d.getDado());

                alg = new AlgoritmoBuscaLargura();
                alg.executar(g, s, d);
            }
        }

        imprimeln("*** FIM DA EXECUCAO ***");
        imprimeln("*** RESULTADOS ***");

        AlgoritmoBuscaLarguraResultado res = alg.getResultado();

        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            imprimeln("Resumo vertice " + v.getDado());
            imprimeln("Abertura: " + res.getTempoAbertura(v) + " Fechamento: " + res.getTempoFechamento(v) + " Visitado: " + res.getVisitado(v));
            Vertice pred = res.getPredecessor(v);
            if (pred == null) {
                imprimeln("Predecessor: null");
            } else {
                imprimeln("Predecessor: " + pred.getDado());
            }

            ArrayList<Vertice> descendentes = res.getDescendentes(v);
            imprime("Sucessores: ");
            String desc = "";
            if (descendentes != null) {
                for (Vertice j : descendentes) {
                    desc = desc + " " + j.getDado();
                }
            }
            imprimeln(desc);
            imprimeln("--------");
        }
    }

    public void executaBuscaProfundidade() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo criado...");
            return;
        }

        imprimeln("*** Executando algoritmo de Busca em Profundidade ***");

        AlgoritmoBuscaProfundidade alg;

        //parametros
        String strIdU = javax.swing.JOptionPane.showInputDialog("Informe o identificador do vertice de origem. Se nao houver deixe em branco...");
        if (strIdU == null || strIdU.equals("")) {
            imprimeln("Nenhum vertice de origem escolhido");
            alg = new AlgoritmoBuscaProfundidade();
            alg.executar(g);
        } else {
            int intIdU = Integer.parseInt(strIdU);
            if (g.getVerticeById(intIdU) == null) {
                JOptionPane.showMessageDialog(null, "Identificador nao encontrado...");
                imprimeln("Processo abortado");
                return;
            }
            Vertice s = g.getVerticeById(intIdU);
            imprimeln("Vertice de origem: " + s.getDado());

            String strIdV = javax.swing.JOptionPane.showInputDialog("Informe o identificador do vertice de destino. Se nao houver deixe em branco...");
            if (strIdV == null || strIdV.equals("")) {
                imprimeln("Nenhum vertice de destino escolhido");
                alg = new AlgoritmoBuscaProfundidade();
                alg.executar(g, s);
            } else {
                int intIdV = Integer.parseInt(strIdV);
                if (g.getVerticeById(intIdV) == null) {
                    JOptionPane.showMessageDialog(null, "Identificador nao encontrado...");
                    imprimeln("Processo abortado");
                    return;
                }
                Vertice d = g.getVerticeById(intIdV);
                imprimeln("Vertice de destino: " + d.getDado());

                alg = new AlgoritmoBuscaProfundidade();
                alg.executar(g, s, d);
            }
        }

        imprimeln("*** FIM DA EXECUCAO ***");
        imprimeln("*** RESULTADOS ***");

        AlgoritmoBuscaProfundidadeResultado res = alg.getResultado();

        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            imprimeln("Resumo vertice " + v.getDado());
            imprimeln("Abertura: " + res.getTempoAbertura(v) + " Fechamento: " + res.getTempoFechamento(v) + " Visitado: " + res.getVisitado(v));
            Vertice pred = res.getPredecessor(v);
            if (pred == null) {
                imprimeln("Predecessor: null");
            } else {
                imprimeln("Predecessor: " + pred.getDado());
            }

            ArrayList<Vertice> descendentes = res.getDescendentes(v);
            imprime("Sucessores: ");
            String desc = "";
            if (descendentes != null) {
                for (Vertice j : descendentes) {
                    desc = desc + " " + j.getDado();
                }
            }
            imprimeln(desc);
            imprimeln("--------");
        }
    }

    public void executaFordFulkersonDijkstra() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo criado...");
            return;
        }

        imprimeln("*** Executando algoritmo de Ford-Fulkerson-Dijkstra ***");

        //parametros
        String strIdU = javax.swing.JOptionPane.showInputDialog("Informe o identificador do vertice de origem...");
        if (strIdU == null || strIdU.equals("")) {
            JOptionPane.showMessageDialog(null, "Identificador invalido...");
            imprimeln("Processo abortado");
            return;
        }
        int intIdU = Integer.parseInt(strIdU);
        if (g.getVerticeById(intIdU) == null) {
            JOptionPane.showMessageDialog(null, "Identificador nao encontrado...");
            imprimeln("Processo abortado");
            return;
        }

        String strIdV = javax.swing.JOptionPane.showInputDialog("Informe o identificador do vertice de destino...");
        if (strIdV == null || strIdV.equals("")) {
            JOptionPane.showMessageDialog(null, "Identificador invalido...");
            imprimeln("Processo abortado");
            return;
        }
        int intIdV = Integer.parseInt(strIdV);
        if (g.getVerticeById(intIdV) == null) {
            JOptionPane.showMessageDialog(null, "Identificador nao encontrado...");
            imprimeln("Processo abortado");
            return;
        }

        Vertice s = g.getVerticeById(intIdU);
        Vertice d = g.getVerticeById(intIdV);

        AlgoritmoFordFulkersonDijkstra alg = new AlgoritmoFordFulkersonDijkstra();
        alg.executar(g, s, d);

        imprimeln("*** FIM DA EXECUCAO ***");
        imprimeln("*** RESULTADOS ***");

        AlgoritmoFordFulkersonResultado res = alg.getResultado();
        imprimeln("Fluxo maximo: " + res.getFluxoMaximo() + " Custo: " + res.getCustoFluxo());
        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice u = g.getVertice(i);
            for (int j = 0; j < tamanhoGrafo; j++) {
                Vertice v = g.getVertice(j);
                if (res.getFluxo(u, v) > 0.0) {
                    imprimeln(u.getDado() + " -> " + v.getDado() + " Fluxo: " + res.getFluxo(u, v));
                }
            }
        }
    }

    public void executaBase() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo criado...");
            return;
        }

        if (g.getTipo() == 2) {
            JOptionPane.showMessageDialog(null, "Este algoritmo se aplica apenas para grafos dirigidos");
            return;
        }

        imprimeln("*** Executando algoritmo para encontrar Base ***");

        ArrayList<Vertice> res = ((GrafoDirigido) g).getBase();

        imprimeln("*** FIM DA EXECUCAO ***");
        imprimeln("*** RESULTADOS ***");

        imprimeln("Base: ");

        String texto = "";
        for (int i = 0; i < res.size(); i++) {
            texto += " " + res.get(i).getDado();
        }
        imprimeln(texto);
    }

    public void executaAntibase() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo criado...");
            return;
        }

        if (g.getTipo() == 2) {
            JOptionPane.showMessageDialog(null, "Este algoritmo se aplica apenas para grafos dirigidos");
            return;
        }

        imprimeln("*** Executando algoritmo para encontrar Antibase ***");

        ArrayList<Vertice> res = ((GrafoDirigido) g).getAntibase();

        imprimeln("*** FIM DA EXECUCAO ***");
        imprimeln("*** RESULTADOS ***");

        imprimeln("Antibase: ");

        String texto = "";
        for (int i = 0; i < res.size(); i++) {
            texto += " " + res.get(i).getDado();
        }
        imprimeln(texto);
    }

    public void executaArticulacoes() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo criado...");
            return;
        }

        if (!g.ehConexo()) {
            JOptionPane.showMessageDialog(null, "O grafo precisa ser conexo...");
            return;
        }

        imprimeln("*** Executando algoritmo para encontrar Articulacoes ***");

        AlgoritmoArticulacoes alg = new AlgoritmoArticulacoes();
        alg.executar(g);

        imprimeln("*** FIM DA EXECUCAO ***");
        imprimeln("*** RESULTADOS ***");

        AlgoritmoArticulacoesResultado res = alg.getResultado();

        int tamanhoLista = res.getQtdeArticulacoes();
        String seq = "";
        for (int i = 0; i < tamanhoLista; i++) {
            seq += res.getArticulacao(i).getDado() + " ";
        }

        imprimeln(seq);
    }

    public void executaPontes() {
        if (g == null) {
            JOptionPane.showMessageDialog(null, "Nenhum grafo criado...");
            return;
        }

        if (!g.ehConexo()) {
            JOptionPane.showMessageDialog(null, "O grafo precisa ser conexo...");
            return;
        }

        imprimeln("*** Executando algoritmo para encontrar Pontes ***");

        AlgoritmoPontes alg = new AlgoritmoPontes();
        alg.executar(g);

        imprimeln("*** FIM DA EXECUCAO ***");
        imprimeln("*** RESULTADOS ***");

        AlgoritmoPontesResultado res = alg.getResultado();

        int tamanhoLista = res.getQtdePontes();
        if (g.ehDirigido()) {
            for (int i = 0; i < tamanhoLista; i++) {
                ArestaDirigida a = (ArestaDirigida) res.getPonte(i);

                imprimeln(a.getVi().getDado() + " -> " + a.getVj().getDado());
            }
        } else {
            for (int i = 0; i < tamanhoLista; i++) {
                ArestaNaoDirigida a = (ArestaNaoDirigida) res.getPonte(i);

                imprimeln(a.getVi().getDado() + " <-> " + a.getVj(a.getVi()).getDado());
            }
        }
    }

    public void mostrarGrafico() {
        new TelaDesenho();
    }
}
