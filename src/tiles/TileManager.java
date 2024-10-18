package tiles;

import graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.Camera;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;

public class TileManager {
    public static TreeMap<Integer, Integer> tileColumns = new TreeMap<>();
    public static int tileWidth;
    public static int tileHeight;
    public static ArrayList<TileMap> tileMaps = new ArrayList<>();
    private final TreeMap<Integer, Sprite> tilesets = new TreeMap<>();

    public TileManager() {
        tileMaps = new ArrayList<TileMap>();
    }

    public TileManager(String path, Camera camera) {
        tileMaps = new ArrayList<TileMap>();
        addTileMap(path, 64, 64, camera);
    }

    public static int getTileWidth() {
        return tileWidth;
    }

    public static void setTileWidth(int tileWidth) {
        TileManager.tileWidth = tileWidth;
    }

    public static int getTileHeight() {
        return tileHeight;
    }

    public static void setTileHeight(int tileHeight) {
        TileManager.tileHeight = tileHeight;
    }

    public static ArrayList<TileMap> getTileMaps() {
        return tileMaps;
    }

    public static void setTileMaps(ArrayList<TileMap> tileMaps) {
        TileManager.tileMaps = tileMaps;
    }

    private void addTileMap(String path, int blockWidth, int blockHeight, Camera camera) {
        int width = 0;
        int height = 0;
        int tileWidth;
        int tileHeight;
        int tileCount;
        int layers = 0;
        Sprite sprite;
        String[] data = new String[10];
        try {
            System.out.println("Loading: " + path + "...");
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            InputStream inputStream=   getClass().getClassLoader().getResourceAsStream(path);
            Document doc = documentBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();
            NodeList mapInfo = doc.getElementsByTagName("map");
            Node mapNode = mapInfo.item(0);
            Element mapElement = (Element) mapNode;
            width = Integer.parseInt(mapElement.getAttribute("width"));
            height = Integer.parseInt(mapElement.getAttribute("height"));
            TileManager.tileWidth = Integer.parseInt(mapElement.getAttribute("tilewidth"));
            TileManager.tileHeight = Integer.parseInt(mapElement.getAttribute("tileheight"));
            // **Tileset Parsing**
            NodeList tilesetList = doc.getElementsByTagName("tileset");
            for (int i = 0; i < tilesetList.getLength(); i++) {
                Node node = tilesetList.item(i);
                Element element = (Element) node;
                int firstGid = Integer.parseInt(element.getAttribute("firstgid"));
                String imagePath = element.getAttribute("name");
                tileWidth = Integer.parseInt(element.getAttribute("tilewidth"));
                tileHeight = Integer.parseInt(element.getAttribute("tileheight"));

                System.out.println("Loading: "+ "tile/" + imagePath + "...");

                Sprite tilesetSprite = new Sprite("tile/" + imagePath, tileWidth, tileHeight);
                tilesets.put(firstGid, tilesetSprite);
                tileColumns.put(firstGid, Integer.parseInt(element.getAttribute("columns")));
            }
            // Layer Parsing
            NodeList layerList = doc.getElementsByTagName("layer");
            for (int i = 0; i < layerList.getLength(); i++) {
                Node node = layerList.item(i);
                Element element = (Element) node;
                data[layers] = element.getElementsByTagName("data").item(0).getTextContent();
                layers++;
                if (element.getAttribute("name").equals("Struct")) {
                    TileMap tileMap = (new TileMapObj(data[layers - 1],
                                                      tilesets,
                                                      width,
                                                      height,
                                                      blockWidth,
                                                      blockHeight,
                                                      tileColumns
                    ));
                    tileMaps.add(tileMap);
                } else {
                    TileMap tileMap = (new TileMapNorm(data[layers - 1],
                                                       tilesets,
                                                       width,
                                                       height,
                                                       blockWidth,
                                                       blockHeight,
                                                       tileColumns
                    ));
                    tileMaps.add(tileMap);
                }
            }
            System.out.println("Start tileMaps");
            for (TileMap tileMap : tileMaps) {
                System.out.println("TileMap: " + tileMap);
            }
            camera.setLimits(width * blockWidth, height * blockHeight);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void render(GraphicsContext gc) {
        for (TileMap tileMap : tileMaps) {
            tileMap.render(gc);
        }
    }

    public void renderGround(GraphicsContext gc) {
        for (int i = 0; i < tileMaps.size()-2; i++) {
                tileMaps.get(i).render(gc);
        }
    }


    public void renderAbove(GraphicsContext gc) {
        tileMaps.get(tileMaps.size()-2).render(gc);
        tileMaps.get(tileMaps.size()-1).render(gc);
    }


}
