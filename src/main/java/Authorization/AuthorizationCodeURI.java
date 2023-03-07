package Authorization;
import Events.Link;
import Main.Main;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;
import java.util.Random;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class AuthorizationCodeURI {

    public static String URI;
    public static final String clientId = Main.clientId;
    public static final String clientSecret = Main.clientSecret;
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://73.219.248.42:8080/callback");
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    public static void main(String[] args) {
//        authorizationCodeUri_Sync();

        authorizationCodeUri_Async();


    }

//    private static AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
//
////            .state("x4xkmn9pu3j6ukrs8n")
//            .state(randomStr)
//            .scope("user-modify-playback-state,user-read-currently-playing,user-read-playback-state,ugc-image-upload,user-read-recently-played,user-top-read,user-read-playback-position,app-remote-control,streaming,playlist-modify-public,playlist-modify-private,playlist-read-private,playlist-read-collaborative,user-follow-modify,user-follow-read,user-library-modify,user-library-read,user-read-email,user-read-private")
//            .show_dialog(true)
//            .build();
        private static AuthorizationCodeUriRequest.Builder authorizationCodeUriRequest = spotifyApi.authorizationCodeUri();


    public static void authorizationCodeUri_Sync() {
//        URI uri = authorizationCodeUriRequest.execute();
//        URI=uri.toString();

//        System.out.println("URI: " + uri.toString());
    }

    public static void authorizationCodeUri_Async() {
        try {
            int length=10;
            String aToZ="ABCDEFGHIJCLMNOPQRSTUVWXYZ123456789";
            Random rand=new Random();
            StringBuilder res=new StringBuilder();
            for (int i = 0; i < length; i++) {
                int randIndex=rand.nextInt(aToZ.length());
                res.append(aToZ.charAt(randIndex));
            }
            String randomString=res.toString();
            CompletableFuture<URI> uriFuture = authorizationCodeUriRequest
                    .state(randomString)
                    .scope("user-modify-playback-state,user-read-currently-playing,user-read-playback-state,ugc-image-upload,user-read-recently-played,user-top-read,user-read-playback-position,app-remote-control,streaming,playlist-modify-public,playlist-modify-private,playlist-read-private,playlist-read-collaborative,user-follow-modify,user-follow-read,user-library-modify,user-library-read,user-read-email,user-read-private")
                    .show_dialog(true)
                    .build()
                    .executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            URI uri = uriFuture.join();
            System.out.println("URI: " + uri.toString());
            URI=uri.toString();
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

}