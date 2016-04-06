package aplicacao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import algoritmos.AlgoritmoBuscaProfundidade;
import algoritmos.AlgoritmoBuscaProfundidadeResultado;
import algoritmos.AlgoritmoKruskal;
import algoritmos.AlgoritmoKruskalResultado;
import base.ArestaNaoDirigida;
import base.GrafoNaoDirigido;

class PainelDesenho extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.white);
        g.fillRect(0, 0, 600, 600);

        GrafoNaoDirigido grafo = new GrafoNaoDirigido();
        VerticeGeometrico v1 = new VerticeGeometrico(1, 10, 3, 20);
        VerticeGeometrico v2 = new VerticeGeometrico(2, 100, 150, 30);
        VerticeGeometrico v3 = new VerticeGeometrico(3, 58, 300, 50);
        VerticeGeometrico v4 = new VerticeGeometrico(4, 300, 200, 40);
        VerticeGeometrico v5 = new VerticeGeometrico(5, 400, 100, 20);
        VerticeGeometrico v6 = new VerticeGeometrico(6, 450, 200, 30);
        VerticeGeometrico v7 = new VerticeGeometrico(7, 30, 500, 20);
        VerticeGeometrico v8 = new VerticeGeometrico(8, 400, 520, 40);

        v1.setCor(Color.green);
        v2.setCor(Color.yellow);
        v3.setCor(Color.orange);
        v4.setCor(Color.red);
        v5.setCor(Color.black);
        v6.setCor(Color.blue);
        v7.setCor(Color.gray);
        v8.setCor(Color.cyan);

        ArestaNaoDirigida a1 = new ArestaNaoDirigida(1, v1, v2);
        ArestaNaoDirigida a2 = new ArestaNaoDirigida(2, v2, v3);
        ArestaNaoDirigida a3 = new ArestaNaoDirigida(3, v2, v4);
        ArestaNaoDirigida a4 = new ArestaNaoDirigida(4, v4, v1);
        ArestaNaoDirigida a5 = new ArestaNaoDirigida(5, v5, v6);
        ArestaNaoDirigida a6 = new ArestaNaoDirigida(6, v3, v4);
        ArestaNaoDirigida a7 = new ArestaNaoDirigida(7, v3, v7);
        ArestaNaoDirigida a8 = new ArestaNaoDirigida(8, v4, v7);
        ArestaNaoDirigida a9 = new ArestaNaoDirigida(9, v7, v8);
        ArestaNaoDirigida a10 = new ArestaNaoDirigida(10, v8, v4);

        a1.setValor(100);
        a2.setValor(5);
        a3.setValor(40);
        a4.setValor(50);
        a5.setValor(14);
        a6.setValor(3);
        a7.setValor(15);
        a8.setValor(7);
        a9.setValor(2);
        a10.setValor(1);

        try {
            grafo.addVertice(v1);
            grafo.addVertice(v2);
            grafo.addVertice(v3);
            grafo.addVertice(v4);
            grafo.addVertice(v5);
            grafo.addVertice(v6);
            grafo.addVertice(v7);
            grafo.addVertice(v8);

            grafo.addAresta(a1);
            grafo.addAresta(a2);
            grafo.addAresta(a3);
            grafo.addAresta(a4);
            grafo.addAresta(a5);
            grafo.addAresta(a6);
            grafo.addAresta(a7);
            grafo.addAresta(a8);
            grafo.addAresta(a9);
            grafo.addAresta(a10);

            AlgoritmoBuscaProfundidade algDFS = new AlgoritmoBuscaProfundidade();
            algDFS.executar(grafo);
            AlgoritmoBuscaProfundidadeResultado resDFS = algDFS.getResultado();

            AlgoritmoKruskal algKruskal = new AlgoritmoKruskal();
            algKruskal.executar(grafo);
            AlgoritmoKruskalResultado resKruskal = algKruskal.getResultado();

            for (int i = 0; i < grafo.getQtdeArestas(); i++) {
                ArestaNaoDirigida a = grafo.getAresta(i);
                VerticeGeometrico u = (VerticeGeometrico) a.getVi();
                VerticeGeometrico v = (VerticeGeometrico) a.getVj(u);

                g.setColor(Color.black);
                g.drawLine(u.getX() + u.getRaio() / 2, u.getY() + u.getRaio() / 2, v.getX() + v.getRaio() / 2, v.getY() + v.getRaio() / 2);
            }

            for (int i = 0; i < resKruskal.getArestas().size(); i++) {
                ArestaNaoDirigida a = (ArestaNaoDirigida) resKruskal.getArestas().get(i);
                VerticeGeometrico u = (VerticeGeometrico) a.getVi();
                VerticeGeometrico v = (VerticeGeometrico) a.getVj(u);

                g.setColor(Color.green);
                g.drawLine(u.getX() + u.getRaio() / 2, u.getY() + u.getRaio() / 2, v.getX() + v.getRaio() / 2, v.getY() + v.getRaio() / 2);
            }

            for (int i = 0; i < grafo.getQtdeArestas(); i++) {
                ArestaNaoDirigida a = grafo.getAresta(i);
                VerticeGeometrico u = (VerticeGeometrico) a.getVi();
                VerticeGeometrico v = (VerticeGeometrico) a.getVj(u);

                g.setColor(Color.white);
                g.fillRect((u.getX() + v.getX()) / 2, (u.getY() + v.getY()) / 2, String.valueOf(a.getValor()).length() * 7, 10);

                g.setColor(Color.blue);
                g.drawString("" + a.getValor(), (u.getX() + v.getX()) / 2, (u.getY() + v.getY()) / 2 + 10);
            }

            for (int i = 0; i < grafo.getTamanho(); i++) {
                VerticeGeometrico v = (VerticeGeometrico) grafo.getVertice(i);

                g.setColor(v.getCor());
                g.fillOval(v.getX(), v.getY(), v.getRaio(), v.getRaio());

                g.setColor(Color.black);
                g.drawString("{Id = " + v.getId() + "} [" + resDFS.getTempoAbertura(v) + "/" + resDFS.getTempoFechamento(v) + "]", v.getX() + v.getRaio(), v.getY() + v.getRaio() / 2);
            }

            // Propriedades

            g.setColor(Color.black);
            g.drawString("Conexo: " + grafo.ehConexo(), 480, 250);
            g.drawString("Simples: " + grafo.ehSimples(), 480, 270);
            g.drawString("Acíclico: " + grafo.ehAciclico(), 480, 290);
            g.drawString("Denso: " + grafo.ehDenso(), 480, 310);
            g.drawString("Bipartido: " + grafo.ehBipartido(), 480, 330);
            g.drawString("Completo: " + grafo.ehCompleto(), 480, 350);
            g.drawString("Regular: " + grafo.ehRegular(), 480, 370);
            g.drawString("Ciclo: " + grafo.ehCiclo(), 480, 390);
            g.drawString("Nulo: " + grafo.ehNulo(), 480, 410);
            g.drawString("Trivial: " + grafo.ehTrivial(), 480, 430);
            g.drawString("Árvore: " + grafo.ehArvore(), 480, 450);
            g.drawString("Floresta: " + grafo.ehFloresta(), 480, 470);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

public class TelaDesenho extends JFrame {

    public static void main(String[] args) {
        new TelaDesenho();
    }

    public TelaDesenho() {
        super("Grafo");

        PainelDesenho painel = new PainelDesenho();
        getContentPane().add(painel, BorderLayout.CENTER);

        setSize(600, 600);
        setVisible(true);
    }

}
