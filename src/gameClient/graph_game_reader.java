package gameClient;

import api.*;
import com.google.gson.*;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class graph_game_reader implements JsonDeserializer<directed_weighted_graph> {
    @Override
    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObj= jsonElement.getAsJsonObject();
        directed_weighted_graph graph =new DWGraph_DS();
        JsonArray nodesjson =jsonObj.get("Nodes").getAsJsonArray();
        JsonArray edgejson = jsonObj.get("Edges").getAsJsonArray();
        for (JsonElement nodeElement : nodesjson) {

            int key =nodeElement.getAsJsonObject().get("id").getAsInt();
            String Geo_Loc=nodeElement.getAsJsonObject().get("pos").getAsString();
            String[] a = Geo_Loc.split(",");
            double x = Double.parseDouble(a[0]);
            double y = Double.parseDouble(a[1]);
            double z = Double.parseDouble(a[2]);
            geo_location location = new Geo(x, y, z);
            node_data n =new NodeData(key,location);
            graph.addNode(n);

        }
        for (JsonElement edgeElement : edgejson) {
                int src =edgeElement.getAsJsonObject().get("src").getAsInt();
                int dest =edgeElement.getAsJsonObject().get("dest").getAsInt();
                double weight =edgeElement.getAsJsonObject().get("w").getAsDouble();
                graph.connect(src,dest,weight);
            }
        return graph;
        }

    }

