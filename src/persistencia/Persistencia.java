package persistencia;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import auxiliar.Comandos;
import base.Aresta;
import base.ArestaDirigida;
import base.ArestaNaoDirigida;
import base.Grafo;
import base.GrafoDirigido;
import base.GrafoNaoDirigido;
import base.Vertice;

/**
 * Classe Persistencia<br>
 * Contém métodos para carregar e persistir grafos em arquivo.
 * 
 * @author Maicon Rafael Zatelli
 * 
 */
public class Persistencia {

    /**
     * Persiste uma grafo em arquivo
     * 
     * @param g Grafo
     * @param arquivo Arquivo de destino
     * @throws IOException
     */
    public static void salvarGrafo(Grafo g, String arquivo) throws IOException {
        String texto = getXMLGrafo(g);

        Comandos.salvar(arquivo, texto, false);
    }

    private static GrafoNaoDirigido carregaGrafoNaoDirigido(Document documento) {
        GrafoNaoDirigido g = new GrafoNaoDirigido();

        Element elementoGrafo = (Element) documento.getElementsByTagName("Grafo").item(0);

        //Vertices
        Element elementoVertices = (Element) elementoGrafo.getElementsByTagName("Vertices").item(0);
        NodeList nodelistVertices = elementoVertices.getElementsByTagName("Vertice");
        for (int i = 0; i < nodelistVertices.getLength(); i++) {
            Element nodeVertice = (Element) nodelistVertices.item(i);

            Node nodeId = nodeVertice.getElementsByTagName("Id").item(0).getFirstChild();
            Element elementoCor = (Element) nodeVertice.getElementsByTagName("Cor").item(0);
            Node nodeR = elementoCor.getElementsByTagName("R").item(0).getFirstChild();
            Node nodeG = elementoCor.getElementsByTagName("G").item(0).getFirstChild();
            Node nodeB = elementoCor.getElementsByTagName("B").item(0).getFirstChild();
            Node nodeDado = nodeVertice.getElementsByTagName("Dado").item(0).getFirstChild();

            Vertice v = new Vertice(Integer.parseInt(nodeId.getNodeValue()));
            v.setCor(new Color(Integer.valueOf(nodeR.getNodeValue()), Integer.valueOf(nodeG.getNodeValue()), Integer.valueOf(nodeB.getNodeValue())));
            v.setDado(nodeDado.getNodeValue());
            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        //Arestas
        Element elementoArestas = (Element) elementoGrafo.getElementsByTagName("Arestas").item(0);
        NodeList nodelistArestas = elementoArestas.getElementsByTagName("Aresta");
        for (int i = 0; i < nodelistArestas.getLength(); i++) {
            Element nodeAresta = (Element) nodelistArestas.item(i);

            Node nodeId = nodeAresta.getElementsByTagName("Id").item(0).getFirstChild();
            Node nodeVi = nodeAresta.getElementsByTagName("Vi").item(0).getFirstChild();
            Node nodeVj = nodeAresta.getElementsByTagName("Vj").item(0).getFirstChild();
            Node nodeValor = nodeAresta.getElementsByTagName("Valor").item(0).getFirstChild();
            Node nodeCapacidade = nodeAresta.getElementsByTagName("Capacidade").item(0).getFirstChild();
            Node nodeDado = nodeAresta.getElementsByTagName("Dado").item(0).getFirstChild();

            Vertice u = g.getVerticeById(Integer.valueOf(nodeVi.getNodeValue()));
            Vertice v = g.getVerticeById(Integer.valueOf(nodeVj.getNodeValue()));

            ArestaNaoDirigida a = new ArestaNaoDirigida(Integer.valueOf(nodeId.getNodeValue()), u, v);
            a.setValor(Double.valueOf(nodeValor.getNodeValue()));
            a.setCapacidade(Double.valueOf(nodeCapacidade.getNodeValue()));
            a.setDado(nodeDado.getNodeValue());

            try {
                g.addAresta(a);
            } catch (Exception e) {}
        }

        return g;
    }

    private static GrafoDirigido carregaGrafoDirigido(Document documento) {
        GrafoDirigido g = new GrafoDirigido();

        Element elementoGrafo = (Element) documento.getElementsByTagName("Grafo").item(0);

        //Vertices
        Element elementoVertices = (Element) elementoGrafo.getElementsByTagName("Vertices").item(0);
        NodeList nodelistVertices = elementoVertices.getElementsByTagName("Vertice");
        for (int i = 0; i < nodelistVertices.getLength(); i++) {
            Element nodeVertice = (Element) nodelistVertices.item(i);

            Node nodeId = nodeVertice.getElementsByTagName("Id").item(0).getFirstChild();
            Element elementoCor = (Element) nodeVertice.getElementsByTagName("Cor").item(0);
            Node nodeR = elementoCor.getElementsByTagName("R").item(0).getFirstChild();
            Node nodeG = elementoCor.getElementsByTagName("G").item(0).getFirstChild();
            Node nodeB = elementoCor.getElementsByTagName("B").item(0).getFirstChild();
            Node nodeDado = nodeVertice.getElementsByTagName("Dado").item(0).getFirstChild();

            Vertice v = new Vertice(Integer.parseInt(nodeId.getNodeValue()));
            v.setCor(new Color(Integer.valueOf(nodeR.getNodeValue()), Integer.valueOf(nodeG.getNodeValue()), Integer.valueOf(nodeB.getNodeValue())));
            v.setDado(nodeDado.getNodeValue());
            try {
                g.addVertice(v);
            } catch (Exception e) {}
        }

        //Arestas
        Element elementoArestas = (Element) elementoGrafo.getElementsByTagName("Arestas").item(0);
        NodeList nodelistArestas = elementoArestas.getElementsByTagName("Aresta");
        for (int i = 0; i < nodelistArestas.getLength(); i++) {
            Element nodeAresta = (Element) nodelistArestas.item(i);

            Node nodeId = nodeAresta.getElementsByTagName("Id").item(0).getFirstChild();
            Node nodeVi = nodeAresta.getElementsByTagName("Vi").item(0).getFirstChild();
            Node nodeVj = nodeAresta.getElementsByTagName("Vj").item(0).getFirstChild();
            Node nodeValor = nodeAresta.getElementsByTagName("Valor").item(0).getFirstChild();
            Node nodeCapacidade = nodeAresta.getElementsByTagName("Capacidade").item(0).getFirstChild();
            Node nodeDado = nodeAresta.getElementsByTagName("Dado").item(0).getFirstChild();

            Vertice u = g.getVerticeById(Integer.valueOf(nodeVi.getNodeValue()));
            Vertice v = g.getVerticeById(Integer.valueOf(nodeVj.getNodeValue()));

            ArestaDirigida a = new ArestaDirigida(Integer.valueOf(nodeId.getNodeValue()), u, v);
            a.setValor(Double.valueOf(nodeValor.getNodeValue()));
            a.setCapacidade(Double.valueOf(nodeCapacidade.getNodeValue()));
            a.setDado(nodeDado.getNodeValue());

            try {
                g.addAresta(a);
            } catch (Exception e) {}
        }

        return g;
    }

    /**
     * Carrega um grafo a partir de um arquivo
     * 
     * @param arquivo Arquivo onde estÃ¡ o grafo
     * @return Grafo
     * @throws ParserConfigurationException
     * @throws FileNotFoundException
     * @throws SAXException
     * @throws IOException
     */
    public static Grafo carregarGrafo(String arquivo) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder construtor = fabrica.newDocumentBuilder();
        Document documento = construtor.parse(retornaDadosXML(arquivo));

        Element elementoGrafo = (Element) documento.getElementsByTagName("Grafo").item(0);
        Node nodeTipo = elementoGrafo.getElementsByTagName("Tipo").item(0).getFirstChild();

        if (nodeTipo.getNodeValue().equals("1")) {
            return carregaGrafoDirigido(documento);
        } else {
            return carregaGrafoNaoDirigido(documento);
        }
    }

    private static InputStream retornaDadosXML(String arquivo) throws FileNotFoundException {
        return new FileInputStream(new File(arquivo));
    }

    /**
     * Retorna uma String com o grafo convertido em XML
     * 
     * @param g Grafo
     * @return String XML que representa o grafo
     */
    public static String getXMLGrafo(Grafo g) {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder construtor = null;
        try {
            construtor = fabrica.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document documento = construtor.newDocument();

        Element elementoGrafo = documento.createElement("Grafo");
        documento.appendChild(elementoGrafo);

        Element elementoTipo = documento.createElement("Tipo");
        Text nodeTipo = documento.createTextNode(String.valueOf(g.getTipo()));
        elementoTipo.appendChild(nodeTipo);
        elementoGrafo.appendChild(elementoTipo);

        //Vertices
        Element elementoVertices = documento.createElement("Vertices");
        elementoGrafo.appendChild(elementoVertices);

        int tamanhoGrafo = g.getTamanho();
        for (int i = 0; i < tamanhoGrafo; i++) {
            Vertice v = g.getVertice(i);

            Element elementoVertice = documento.createElement("Vertice");

            Element elementoId = documento.createElement("Id");
            Text nodeId = documento.createTextNode(String.valueOf(v.getId()));
            elementoId.appendChild(nodeId);
            elementoVertice.appendChild(elementoId);

            Element elementoCor = documento.createElement("Cor");
            Element elementoR = documento.createElement("R");
            Text nodeR = documento.createTextNode(String.valueOf(v.getCor().getRed()));
            elementoR.appendChild(nodeR);
            elementoCor.appendChild(elementoR);

            Element elementoG = documento.createElement("G");
            Text nodeG = documento.createTextNode(String.valueOf(v.getCor().getGreen()));
            elementoG.appendChild(nodeG);
            elementoCor.appendChild(elementoG);

            Element elementoB = documento.createElement("B");
            Text nodeB = documento.createTextNode(String.valueOf(v.getCor().getBlue()));
            elementoB.appendChild(nodeB);
            elementoCor.appendChild(elementoB);
            elementoVertice.appendChild(elementoCor);

            Element elementoDado = documento.createElement("Dado");
            Text nodeDado = documento.createTextNode(String.valueOf(v.getDado()));
            elementoDado.appendChild(nodeDado);
            elementoVertice.appendChild(elementoDado);

            elementoVertices.appendChild(elementoVertice);
        }

        //Arestas
        Element elementoArestas = documento.createElement("Arestas");
        elementoGrafo.appendChild(elementoArestas);

        HashMap<Integer, Boolean> arestasForam = new HashMap<Integer, Boolean>();
        int qtdeArestas = g.getQtdeArestas();
        for (int i = 0; i < qtdeArestas; i++) {
            Aresta a = g.getAresta(i);

            if (arestasForam.get(a.getId()) == null) {
                arestasForam.put(a.getId(), true);

                Element elementoAresta = documento.createElement("Aresta");

                Element elementoId = documento.createElement("Id");
                Text nodeId = documento.createTextNode(String.valueOf(a.getId()));
                elementoId.appendChild(nodeId);
                elementoAresta.appendChild(elementoId);

                Element elementoVi = documento.createElement("Vi");
                Text nodeVi = null;
                if (g.ehDirigido()) {
                    nodeVi = documento.createTextNode(String.valueOf(((ArestaDirigida) a).getVi().getId()));
                } else {
                    nodeVi = documento.createTextNode(String.valueOf(((ArestaNaoDirigida) a).getVi().getId()));
                }
                elementoVi.appendChild(nodeVi);
                elementoAresta.appendChild(elementoVi);

                Element elementoVj = documento.createElement("Vj");
                Text nodeVj = null;
                if (g.ehDirigido()) {
                    nodeVj = documento.createTextNode(String.valueOf(((ArestaDirigida) a).getVj().getId()));
                } else {
                    nodeVj = documento.createTextNode(String.valueOf(((ArestaNaoDirigida) a).getVj(((ArestaNaoDirigida) a).getVi()).getId()));
                }
                elementoVj.appendChild(nodeVj);
                elementoAresta.appendChild(elementoVj);

                Element elementoValor = documento.createElement("Valor");
                Text nodeValor = documento.createTextNode(String.valueOf(a.getValor()));
                elementoValor.appendChild(nodeValor);
                elementoAresta.appendChild(elementoValor);

                Element elementoCapacidade = documento.createElement("Capacidade");
                Text nodeCapacidade = documento.createTextNode(String.valueOf(a.getCapacidade()));
                elementoCapacidade.appendChild(nodeCapacidade);
                elementoAresta.appendChild(elementoCapacidade);

                Element elementoDado = documento.createElement("Dado");
                Text nodeDado = documento.createTextNode(String.valueOf(a.getDado()));
                elementoDado.appendChild(nodeDado);
                elementoAresta.appendChild(elementoDado);

                elementoArestas.appendChild(elementoAresta);
            }
        }

        //Grava xml
        //set up a transformer
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = null;
        try {
            trans = transfac.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");

        //create string from xml tree
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(documento);
        try {
            trans.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        String xmlString = sw.toString();

        return xmlString;
    }
}
