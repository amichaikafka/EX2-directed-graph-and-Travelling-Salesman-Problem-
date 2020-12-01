package api;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class DWGraph_DS_To_Json implements JsonDeserializer<directed_weighted_graph> {

    @Override
    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObj= jsonElement.getAsJsonObject();
        directed_weighted_graph graph =new DWGraph_DS();
        JsonObject nodesjson =jsonObj.get("nodes").getAsJsonObject();
        JsonObject edgejson = jsonObj.get("Edges").getAsJsonObject();
        for (Entry<String,JsonElement> s: nodesjson.entrySet()) {
            JsonElement val_node=s.getValue();
            int key =val_node.getAsJsonObject().get("Key").getAsInt();
            int tag=val_node.getAsJsonObject().get("Tag").getAsInt();
            String info=val_node.getAsJsonObject().get("Info").getAsString();
            double weight =val_node.getAsJsonObject().get("Weight").getAsDouble();
         /*   JsonObject Geo_Loc=val_node.getAsJsonObject().get("Geo_Loc").getAsJsonObject();
            double x =Geo_Loc.getAsJsonObject().get("X").getAsDouble();
            double y =Geo_Loc.getAsJsonObject().get("Y").getAsDouble();
            double z =Geo_Loc.getAsJsonObject().get("Z").getAsDouble();
            geo_location glocation =new Geo(x,y,z);*/
            geo_location glocation=null;
            node_data n =new NodeData(key,tag,glocation,weight,info);
            graph.addNode(n);

        }
        for (Entry<String,JsonElement> s: edgejson.entrySet()) {
            JsonObject val_edges=s.getValue().getAsJsonObject();
            for (Entry<String,JsonElement> set: val_edges.entrySet()) {
                JsonElement edge_val=set.getValue();
                int src =edge_val.getAsJsonObject().get("Src").getAsInt();
                int dest =edge_val.getAsJsonObject().get("Dest").getAsInt();
                double weight =edge_val.getAsJsonObject().get("Weight").getAsDouble();
                graph.connect(src,dest,weight);
            }

        }
        return graph;

    }
}
