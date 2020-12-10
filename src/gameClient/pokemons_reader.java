package gameClient;

import api.directed_weighted_graph;
import com.google.gson.*;
import gameClient.util.Point3D;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class pokemons_reader implements JsonDeserializer<List<CL_Pokemon>> {
    @Override
    public List<CL_Pokemon> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        List<CL_Pokemon> C=new ArrayList<CL_Pokemon>();
        JsonObject jsonObj= jsonElement.getAsJsonObject();
        JsonArray pokemons =jsonObj.get("Pokemons").getAsJsonArray();
        for (JsonElement pokelement : pokemons) {
            JsonObject poke =pokelement.getAsJsonObject().get("Pokemon").getAsJsonObject();
           double v=poke.get("value").getAsDouble();
            int t=poke.get("type").getAsInt();
            String l=poke.get("pos").getAsString();
            Point3D loc=new Point3D(l);
            CL_Pokemon pokemon=new CL_Pokemon(v,t,loc);
           C.add(pokemon);

        }
        return C;
    }
}
