package Events;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Songs {
    private String name=null;
    private String uri=null;
    public static String currentmessageSongs;
    public static String URI;
//    public static Object artists=null;

//public class Artists {
//    Gson gson=new Gson();
//    JsonReader jsonReader = new JsonReader(new StringReader((String) artists));
//    Artists newartist = gson.fromJson(jsonReader, Events.Songs.Artists.class);
//
//}
    public void print() throws IOException {
        System.out.println(name);
        System.out.println(uri);
        currentmessageSongs=". " +name;
        URI=uri;
//        System.out.println(artists);


//        Gson gson=new Gson();
//        String artistsinJson = gson.toJson(artists);
//        JsonReader jsonReader = new JsonReader(new StringReader(artistsinJson));
//        Artists[] newArtist = gson.fromJson(jsonReader, Songs.Artists[].class);



    }

    public static class Artists {
        private String name=null;
        private String type=null;
        private String uri=null;
        public static String currentmessageArtists;

        public void print(){
            System.out.println(name);
            System.out.println(type);
            System.out.println(uri);
            currentmessageArtists=" \nBy: "+name+"\n\n";
        }

    }


}
