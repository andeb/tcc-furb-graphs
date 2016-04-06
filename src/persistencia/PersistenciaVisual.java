/*
 * Created on 11/11/2014.
 *
 * Copyright 2014 Senior Ltda. All rights reserved.
 */
package persistencia;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import aplicacao.GraphViewer;
import aplicacao.GraphViewer.ArestaVisual;
import aplicacao.GraphViewer.VerticeVisual;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * @author ande
 */
public class PersistenciaVisual {

    private static String NULL_SIGNAL = "--null--";

    private static class GsonContext {

        static Map<Integer, VerticeVisual> map;

        static int last_id;

        public static void reset() {
            map = new HashMap<Integer, GraphViewer.VerticeVisual>();
            last_id = -1;
        }

        public static void put(GraphViewer.VerticeVisual node) {
            Integer id = node.id;

            map.put(id, node);
            if (id > last_id) {
                last_id = id;
            }
        }

        /**
         * @param nextInt
         * @return
         */
        public static VerticeVisual get(int nextInt) {
            VerticeVisual nodeHelper = map.get(nextInt);
            if (nodeHelper == null) {
                throw new RuntimeException("Not found " + nextInt);
            }
            return nodeHelper;
        }

    }

    public static void salvarGrafo(DefinicaoGrafoVisual definicaoGrafoVisual, File file) {
        GsonContext.reset();

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("vertex", definicaoGrafoVisual.vertices);
        map.put("edge", definicaoGrafoVisual.arestas);

        GsonBuilder builder = createGsonBuilder();

        //        new GraphAdapterBuilder()
        //        /**/.addType(Edge.class)
        //        /**/.addType(NodeHelper.class)
        //        /**/.registerOn(builder);

        String json = builder.create().toJson(map);
        System.out.println(json);

        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(json);
            writer.flush();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e1);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                throw new RuntimeException(e1);
            }
        }
    }

    public static DefinicaoGrafoVisual carregarGrafo(File file) {
        GsonContext.reset();

        Gson gson = createGsonBuilder().create();

        Map<String, Object> map;

        FileReader json = null;
        try {
            json = new FileReader(file);
            map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } finally {
            try {
                if (json != null) {
                    json.close();
                }
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                throw new RuntimeException(e1);
            }
        }

        List<VerticeVisual> nodes = (List<VerticeVisual>) map.get("vertex");
        List<ArestaVisual> edges = (List<ArestaVisual>) map.get("edge");
        int id = GsonContext.last_id;

        return new DefinicaoGrafoVisual(nodes, edges, id);
    }

    /**
     * @return
     */
    private static GsonBuilder createGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(new TypeToken<Map<String, Object>>() {}.getType(), new MyTypeModelDeserializer());

        builder.registerTypeHierarchyAdapter(Color.class, new TypeAdapter<Color>() {

            @Override
            public void write(JsonWriter out, Color value) throws IOException {
                out.beginObject();
                out.name("rgb").value(value.getRGB());
                out.endObject();
            }

            @Override
            public Color read(JsonReader in) throws IOException {
                in.beginObject();

                in.nextName(); // rgb
                int rgb = in.nextInt();
                in.endObject();

                return new Color(rgb);
            }

        });
        builder.registerTypeHierarchyAdapter(Rectangle.class, new TypeAdapter<Rectangle>() {

            @Override
            public void write(JsonWriter out, Rectangle value) throws IOException {
                out.beginObject();

                // x, y, width, height
                out.name("x").value(value.getX());
                out.name("y").value(value.getY());
                out.name("width").value(value.getWidth());
                out.name("height").value(value.getHeight());

                out.endObject();
            }

            @Override
            public Rectangle read(JsonReader in) throws IOException {
                in.beginObject();

                in.nextName(); // x
                int x = in.nextInt();
                in.nextName(); // y
                int y = in.nextInt();
                in.nextName(); // width
                int width = in.nextInt();
                in.nextName(); // height
                int height = in.nextInt();

                in.endObject();

                return new Rectangle(x, y, width, height);
            }

        });
        builder.registerTypeHierarchyAdapter(Point.class, new TypeAdapter<Point>() {

            @Override
            public void write(JsonWriter out, Point value) throws IOException {
                // x, y
                out.beginObject();
                out.name("x").value(value.getX());
                out.name("y").value(value.getY());
                out.endObject();
            }

            @Override
            public Point read(JsonReader in) throws IOException {
                in.beginObject();
                in.nextName(); // x
                int x = in.nextInt();
                in.nextName(); // y
                int y = in.nextInt();

                in.endObject();

                return new Point(x, y);
            }

        });
        builder.registerTypeHierarchyAdapter(ArestaVisual.class, new TypeAdapter<ArestaVisual>() {

            @Override
            public void write(JsonWriter out, ArestaVisual value) throws IOException {
                // TODO Auto-generated method stub
                out.beginObject();
                out.name("direction").value(value.direction.name());
                out.name("n1").value(value.n1.id);
                out.name("n2").value(value.n2.id);
                out.name("value").value(value.value == null ? NULL_SIGNAL : value.value);
                out.name("name").value(value.name == null ? NULL_SIGNAL : value.name);
                out.endObject();
            }

            @Override
            public ArestaVisual read(JsonReader in) throws IOException {
                in.beginObject();

                in.nextName(); // direction
                ArestaVisual.Direction direction = ArestaVisual.Direction.valueOf(in.nextString());
                in.nextName(); // n1
                VerticeVisual n1 = GsonContext.get(in.nextInt());
                in.nextName(); // n2
                VerticeVisual n2 = GsonContext.get(in.nextInt());

                in.nextName(); // value
                String value = in.nextString();
                if (isNull(value)) {
                    value = null;
                }

                in.nextName(); // name
                String name = in.nextString();
                if (isNull(name)) {
                    name = null;
                }

                in.endObject();

                ArestaVisual edge = new ArestaVisual(n1, n2);
                edge.direction = direction;
                edge.value = value;
                edge.name = name;
                return edge;
            }

        });
        builder.setPrettyPrinting();
        return builder;
    }

    private static class MyTypeModelDeserializer implements JsonDeserializer<Map<String, Object>> {

        @Override
        public Map<String, Object> deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            Map<String, Object> typeModel = new HashMap<String, Object>();

            //    JsonElement jsonType = jsonObject.get("edge");
            //    String type = jsonType.getAsString();

            //    map.put("vertex", nodes);
            //    map.put("edge", edges);

            List<VerticeVisual> vertex = context.deserialize(jsonObject.get("vertex"), new TypeToken<List<VerticeVisual>>() {}.getType());
            for (VerticeVisual nodeHelper : vertex) {
                GsonContext.put(nodeHelper);
            }

            Object edge__ = context.deserialize(jsonObject.get("edge"), new TypeToken<List<ArestaVisual>>() {}.getType());

            typeModel.put("vertex", vertex);
            typeModel.put("edge", edge__);

            return typeModel;
        }
    }

    private static boolean isNull(String v) {
        return NULL_SIGNAL.equals(v);
    }

}
