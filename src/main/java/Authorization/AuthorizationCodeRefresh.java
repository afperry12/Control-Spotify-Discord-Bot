package Authorization;
import Events.AddSongtoQueue;
import Events.ReactionListener;
import Events.Songs;
import Main.Main;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PagingCursorbased;
import com.wrapper.spotify.model_objects.specification.PlayHistory;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.player.AddItemToUsersPlaybackQueueRequest;
import com.wrapper.spotify.requests.data.player.GetCurrentUsersRecentlyPlayedTracksRequest;
import com.wrapper.spotify.requests.data.player.SetVolumeForUsersPlaybackRequest;
import com.wrapper.spotify.requests.data.player.SkipUsersPlaybackToNextTrackRequest;
import com.wrapper.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import com.wrapper.spotify.requests.data.search.SearchItemRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.ParseException;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static Events.Link.DiscordID;
import static Events.ReactionListener.songURI;
import static Events.Songs.Artists.currentmessageArtists;
import static Events.Songs.URI;
import static Events.Songs.currentmessageSongs;

public class AuthorizationCodeRefresh extends ListenerAdapter {
    private static final String clientId = "504e8256e00a4f4892bc16af9b6d5478";
    private static final String clientSecret = "d697a7cbb59f456290d46af7a81577f5";
    private static final String refreshToken = "AQDecYg1I9xCRohkTVw4nIKc08ttVxe4ct8A7Ivkx4Lqm1zLeMfilTNlREkqWTya1nMWM7-JFQCYepRpfRXyabLmQZI7el4bI4-L2kkuW359K03da9fBiLR2HouAY4szOd4";
    private static String test= null;


//    public static void authorizationCodeRefresh_Sync() {
//        try {
//            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();
//
//            // Set access and refresh token for further "spotifyApi" object usage
//            test = authorizationCodeCredentials.getAccessToken();
//            spotifyApi.setAccessToken(test);
//            System.out.println(test);
//
//            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
//        } catch (IOException | SpotifyWebApiException | ParseException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }
    public static void AddItemstoPlaylist() {
        final String playlistId = "2KH5hUmoSbl5xPdJmi5PCP";
        String[] uris = new String[]{"spotify:track:01iyCAUm8EvOFqVWYJ3dVX"};
        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(test)
                .build();
        final AddItemsToPlaylistRequest addItemsToPlaylistRequest = spotifyApi
                .addItemsToPlaylist(playlistId, uris)
                .position(0)
                .build();
        try {
            final CompletableFuture<SnapshotResult> snapshotResultFuture = addItemsToPlaylistRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final SnapshotResult snapshotResult = snapshotResultFuture.join();

            System.out.println("Snapshot ID: " + snapshotResult.getSnapshotId());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }
    public static void SkipUsersPlaybackToNextTrackExample () {
        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(test)
                .build();
        final SkipUsersPlaybackToNextTrackRequest skipUsersPlaybackToNextTrackRequest = spotifyApi
                .skipUsersPlaybackToNextTrack()
                .build();
        try {
            final String string = skipUsersPlaybackToNextTrackRequest.execute();

            System.out.println("Null: " + string);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void SearchItem() {
        final String query= JOptionPane.showInputDialog("Enter the name of a song or artist to search for!");
        final String type= ModelObjectType.ARTIST.getType();
//        Gson gson = new Gson();
//        gson.toJson(objectj, new FileWriter("C:\\fileName.json"));

        // 2. Java object to JSON string
//        String json = gson.toJson(obj);

        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(test)
                .build();
        System.out.println(test);
        System.out.println(query);
        String test= new String();
        final SearchItemRequest searchItemRequest = spotifyApi.searchItem(query,type)
//                .market(CountryCode.SE)
//                .limit(10)
//                .offset(0)
//                .includeExternal("audio")
                .build();
        try {
            final SearchResult searchResult = searchItemRequest.execute();
            test=searchResult.toString();
            System.out.println("Total tracks: " + searchResult.toString());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

//        Artist artist = gson.fromJson(jsonReader,Artist.class)
//        Matcher m = Pattern.compile("uri=.+?(?= )").matcher(test);

//        System.out.println(m.group(0));
//            System.out.println(m.group(1));


    }
    public static void SetVolumeForUsersPlaybackExample () {
        final int volumePercent = 100;

        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(test)
                .build();
        final SetVolumeForUsersPlaybackRequest setVolumeForUsersPlaybackRequest = spotifyApi
                .setVolumeForUsersPlayback(volumePercent)
//          .device_id("5fbb3ba6aa454b5534c4ba43a8c7e8e45a63ad0e")
                .build();
        try {
            final String string = setVolumeForUsersPlaybackRequest.execute();

            System.out.println("Null: " + string);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void GetCurrentUsersRecentlyPlayedTracksExample () {
        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(test)
                .build();
        final GetCurrentUsersRecentlyPlayedTracksRequest getCurrentUsersRecentlyPlayedTracksRequest =
                spotifyApi.getCurrentUsersRecentlyPlayedTracks()
//                  .after(new Date(1517087230000L))
//                  .before(new Date(1453932420000L))
//                  .limit(10)
                        .build();
        try {
            final PagingCursorbased<PlayHistory> playHistoryPagingCursorbased = getCurrentUsersRecentlyPlayedTracksRequest.execute();

            System.out.println("Total: " + playHistoryPagingCursorbased.getTotal());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        try {
            final CompletableFuture<PagingCursorbased<PlayHistory>> pagingCursorbasedFuture = getCurrentUsersRecentlyPlayedTracksRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final PagingCursorbased<PlayHistory> playHistoryPagingCursorbased = pagingCursorbasedFuture.join();

            System.out.println("Total: " + playHistoryPagingCursorbased.toString());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }





    public static boolean usernameexpired=false;
    public static HashMap<String, Integer> usernameexpiry=new HashMap<>();
    public static boolean error=false;
    public static ArrayList<Long> messageids=new ArrayList<Long>();
    public static ArrayList<String> URIArrayList=new ArrayList<>();
    public static String accesstoken;
    public static void authorizationCodeRefresh_Async(String username, String access, String refresh, TextChannel channel, String ThingToDo, String variable1,Member member) {
        try {
            System.out.println("CHANNEL: "+channel);
            System.out.println("USERNAME: "+username);
            System.out.println("OLD ACCESS: "+access.replaceAll("\\[","").replaceAll("\\]",""));
            System.out.println("OLD REFRESH: "+refresh.replaceAll("\\[","").replaceAll("\\]",""));
            final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                    .setClientId(Main.clientId)
                    .setClientSecret(Main.clientSecret)
                    .setRefreshToken(refresh.replaceAll("\\[","").replaceAll("\\]",""))
                    .setAccessToken(access.replaceAll("\\[","").replaceAll("\\]",""))
                    .build();
            final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
                    .build()
                    .executeAsync();
                System.out.println("Expiry: "+usernameexpiry);
//                try {
//                    if (usernameexpiry.get(username) <= 0) {
//                        usernameexpired = true;
//                        // Thread free to do other tasks...
//                        usernameexpiry.remove(username);
//                        // Example Only. Never block in production code.
//                        final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.join();
//
//                        // Set access token for further "spotifyApi" object usage
//                        accesstoken = authorizationCodeCredentials.getAccessToken();
//                        String refreshtoken = authorizationCodeCredentials.getRefreshToken();
//                        List<String> oldValues = Collections.singletonList((access + "%" + refresh));
//                        List<String> newValues = Collections.singletonList(accesstoken + "%" + refreshtoken);
//                        System.out.println("OLDVALUES: " + oldValues);
//                        System.out.println("NEWVALUES: " + newValues);
//                        AuthorizationCode.usersmap.replace(username, oldValues, newValues);
//                        System.out.println("VALUES IN USERMAP: " + AuthorizationCode.usersmap.get(username));
//                        System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
//                        usernameexpiry.put(username, authorizationCodeCredentials.getExpiresIn());
//                    }
//                } catch (NullPointerException n) {
//                    channel.sendMessage("Sorry, but we received the following from that request: "+n.getMessage()).queue();
//                    error=true;
//                    return;
//                }
            if (ThingToDo.equalsIgnoreCase("ChangeVolume")) {
                final int volumePercent = Math.toIntExact(Long.parseLong(variable1));

                final SetVolumeForUsersPlaybackRequest setVolumeForUsersPlaybackRequest = spotifyApi
                        .setVolumeForUsersPlayback(volumePercent)
//          .device_id("5fbb3ba6aa454b5534c4ba43a8c7e8e45a63ad0e")
                        .build();
                try {
                    final String string = setVolumeForUsersPlaybackRequest.execute();

                    System.out.println("Null: " + string);
                } catch (IOException | SpotifyWebApiException | ParseException e) {
                    System.out.println("Error: " + e.getMessage());
                    channel.sendMessage("Sorry, but we received the following from that request: "+e.getMessage()).queue();
                    error=true;
                    return;
                }
            }
            if (ThingToDo.equalsIgnoreCase("AddSongtoQueue")) {
                final SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(variable1)
//          .market(CountryCode.SE)
//          .limit(10)
//          .offset(0)
//          .includeExternal("audio")
                        .build();
                try {
                    final CompletableFuture<Paging<Track>> pagingFuture = searchTracksRequest.executeAsync();

                    String tracktojson=searchTracksRequest.getJson().trim();
//                    System.out.println(tracktojson);
                    JSONObject jObject = new JSONObject(tracktojson);
                    JSONObject jObect2 = jObject.getJSONObject("tracks");
                    JSONArray jsonArray = jObect2.getJSONArray("items");
                    System.out.println(jsonArray);

                    String songResults=null;

                    for (int x=0;x<jsonArray.length();x++) {
                        String songs= String.valueOf(jsonArray.get(x));
                        System.out.println(songs);
                        JSONObject jsonObj = new JSONObject(songs.toString());
                        JSONArray artistsArray = (JSONArray) jsonObj.getJSONArray("artists");

                        Gson gson=new Gson();
                        JsonReader jsonReader = new JsonReader(new StringReader(songs));
                        Songs newSong = gson.fromJson(jsonReader, Events.Songs.class);
                        newSong.print();
                        URIArrayList.add(URI);
                        String multipleArtists = null;
                        for (int y=0;y<artistsArray.length();y++) {
                            String artists = String.valueOf(artistsArray.get(y));
                            Gson gsontest=new Gson();
//                            System.out.println("Songs as string: "+songs.toString());
//                            System.out.println("Artists as string: "+artists.toString());
                            JsonReader jsonReadertest = new JsonReader(new StringReader(artists));
                            Songs.Artists newArtist = gsontest.fromJson(jsonReadertest, Events.Songs.Artists.class);
                            newArtist.print();
                            System.out.println(currentmessageArtists);
                            multipleArtists=currentmessageArtists;







                        }
                        int xvalue=x;
                        channel.sendMessage(x+currentmessageSongs+multipleArtists).queue(message -> {


//                            message.addReaction().queue();
//                            message.addReaction("negative_squared_cross_mark").queue();


                            long messageId = message.getIdLong();

                                messageids.add(xvalue, messageId);




                        });

                        multipleArtists=null;
                    }








//                    System.out.println(tracktojson);

//                    Gson gson=new GsonBuilder().create();
//                    Songs[] data = gson.fromJson(String.valueOf(jsonArray), Songs[].class);
//                    System.out.println(data);

                    // Thread free to do other tasks...

                    // Example Only. Never block in production code.


//                    final Paging<Track> trackPaging = pagingFuture.join();
//                    System.out.println(trackPaging);


//                    String parser =trackPaging.toString();
//                    Pattern pattern = Pattern.compile("([^,]+=[^=]+)(,|$)");
//                    Matcher matcher = pattern.matcher(parser);
//                    while (matcher.find()) {
//                        System.out.println(matcher.group(1));
//                    }

//                    String parsed=parser.replace(":", ": ").replace("=", ":").replace("[","{").replace("]","}");



//                    System.out.println("Total: " + trackPaging.getTotal());

                } catch (CompletionException e) {
                    System.out.println("Error: " + e.getCause().getMessage());
                } catch (CancellationException e) {
                    System.out.println("Async operation cancelled.");
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (SpotifyWebApiException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if (ThingToDo.equalsIgnoreCase("Playback")) {
                final GetCurrentUsersRecentlyPlayedTracksRequest getCurrentUsersRecentlyPlayedTracksRequest =
                        spotifyApi.getCurrentUsersRecentlyPlayedTracks()
//                  .after(new Date(1517087230000L))
//                  .before(new Date(1453932420000L))
//                  .limit(10)
                                .build();
                try {
                    final PagingCursorbased<PlayHistory> playHistoryPagingCursorbased = getCurrentUsersRecentlyPlayedTracksRequest.execute();

                    System.out.println("Total: " + playHistoryPagingCursorbased.getTotal());
                } catch (IOException | SpotifyWebApiException | ParseException e) {
                    System.out.println("Error: " + e.getMessage());
                    channel.sendMessage("Sorry, but we received the following from that request: "+e.getMessage()).queue();
                    error=true;
                    return;
                }
                try {
                    final CompletableFuture<PagingCursorbased<PlayHistory>> pagingCursorbasedFuture = getCurrentUsersRecentlyPlayedTracksRequest.executeAsync();

                    // Thread free to do other tasks...

                    // Example Only. Never block in production code.
                    final PagingCursorbased<PlayHistory> playHistoryPagingCursorbased = pagingCursorbasedFuture.join();

                    System.out.println("Total: " + playHistoryPagingCursorbased.toString());
                } catch (CompletionException e) {
                    System.out.println("Error: " + e.getCause().getMessage());
                    channel.sendMessage("Sorry, but we received the following from that request: "+e.getMessage()).queue();
                    error=true;
                    return;
                } catch (CancellationException e) {
                    System.out.println("Async operation cancelled.");
                    error=true;
                    return;
                }
            }

        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
            channel.sendMessage("Sorry, but we received the following from that request: "+e.getMessage()).queue();
            error=true;
            return;
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
            error=true;
            return;

        }
    }
    public static String accessrefresh = String.valueOf(AuthorizationCode.usersmap.get(DiscordID));
    public static String acces = StringUtils.substringBefore(accessrefresh, "%");
    public static void AddItemToUsersPlaybackQueue() {
        System.out.println(accesstoken);
        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(acces)
                .build();
        final AddItemToUsersPlaybackQueueRequest addItemToUsersPlaybackQueueRequest = spotifyApi
                .addItemToUsersPlaybackQueue(songURI)
//    .device_id("5fbb3ba6aa454b5534c4ba43a8c7e8e45a63ad0e")
                .build();
        System.out.println(songURI);

        try {
            final CompletableFuture<String> stringFuture = addItemToUsersPlaybackQueueRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final String string = stringFuture.join();

            System.out.println("Null: " + string);
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }

    }
    public static void main(String[] args) {
//        authorizationCodeRefresh_Sync();
//        AddItemstoPlaylist();
//        SearchItem();
//        SkipUsersPlaybackToNextTrackExample();
        GetCurrentUsersRecentlyPlayedTracksExample();
//        SetVolumeForUsersPlaybackExample();
    }
}