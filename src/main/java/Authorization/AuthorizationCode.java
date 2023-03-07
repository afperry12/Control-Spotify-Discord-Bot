package Authorization;
import Callback.Handlers;
import Events.Link;
import Users.Users;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import org.apache.commons.collections4.KeyValue;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.hc.core5.http.ParseException;
import org.apache.logging.log4j.core.util.KeyValuePair;

import javax.ws.rs.core.MultivaluedHashMap;
import java.io.IOException;
import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class AuthorizationCode {
//    private static final String clientId = "bdbca3bac28c4dfda38356e725d8bb40";
//    private static final String clientSecret = "cbba1208ace94e5a8215378d8e590827";
//    private static final URI redirectUri = SpotifyHttpManager.makeUri("https://google.com");

    private static final String clientId = "504e8256e00a4f4892bc16af9b6d5478";
    private static final String clientSecret = "d697a7cbb59f456290d46af7a81577f5";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://73.219.248.42:8080/callback");
    //    private static final String code = "AQDwsb-M9IikqCxvqyJQdsCOyR7R83y_gXYiAuqmKwwFvHBPgNciJPlfrWJMk6fQdR7QZvLEv8CdcuDx2CMn78U3N556eq915ybKQeNhihsuxhii-pmRhJto1MdOBEFjG3qUT4iYCEYxP4ni_ErrXMFao23Jj14nfiy5LJJ3bbPoyfwntHQZ0xABbi_eoNLrJ4a0EGuWLMQJZFKXuCcxrl6MuvfbgtiOhwUoMuS0x-Pq02RLjOMTVHIOFxxrksTpmNoWr4skgTrUruP99saDmo4Uun3BhAEu5uwN_ke_ENsDNbXq8lLqM5OmarnW7vmsMpVx-2bd_MSPHcLn3gReEf6LhtEqaHwdExxEH3hicPUhs2ge7cEzfOOVtxUddrPwp-_kYjq1QIjwJlEMv2uK5rrTIrxhcoDj_Fh0PG1eXOfxGlyRHfMWsdG7T7Yfs_p3RenqirK3h92rjd51u4tMTuvuiUbc-YoeJLT36vFfutCqdPWlfHthGZSUZuk4PEJTmpmKL6XF_U7JgReGhbW_JpKjOQJ_Z8Y0t0JLZpnbE8etcNzOiHkCHnp0ctsD0sXGiUhPWmcOZdn2rel1jKSESBXHr4gpQXBidmbA9MYPOxoJcoQDLStgIJcvbuCMPGM4kAjiqIpaO_TO1XpFR2ECXe-LylPSEcHJ6VuaBh5Nun4dpED_7M6AyXHzZqbWd1yDBBJ_bSSRkfs";
//    private static final String code = ;
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();


    public static void main(String[] args) {
//        authorizationCode_Sync();

//        authorizationCode_Async();
    }

//    public static void authorizationCode_Sync() {
//        try {
//            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
//
//            // Set access and refresh token for further "spotifyApi" object usage
//            String test= authorizationCodeCredentials.getAccessToken();
//            String test2 = authorizationCodeCredentials.getRefreshToken();
//            System.out.println(test);
//            System.out.println(test2);
//            spotifyApi.setAccessToken(test);
//            spotifyApi.setRefreshToken(test2);
//
//            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
//        } catch (IOException | SpotifyWebApiException | ParseException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }
       public static MultivaluedHashMap<String, String> usersmap = new MultivaluedHashMap<String, String>();
    public static void authorizationCode_Async(String username, String code, TextChannel channel) {
        try {
            String accessToken;
            String refreshToken;
            System.out.println("USERNAME! "+username);
            System.out.println("CODE! "+code);
            final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeRequest = spotifyApi.authorizationCode(code)
                    .build()
                    .executeAsync();
            final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodeRequest;

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();

            // Set access and refresh token for further "spotifyApi" object usage
//            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
//            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            accessToken= authorizationCodeCredentials.getAccessToken();
            refreshToken = authorizationCodeCredentials.getRefreshToken();
            System.out.println(accessToken);
            System.out.println(refreshToken);
            spotifyApi.setAccessToken(accessToken);
            spotifyApi.setRefreshToken(refreshToken);
            Connection conn = null;
            try {
                conn =
                        DriverManager.getConnection("jdbc:mysql://localhost:3306/test?" +
                                "user=root&password=&useUnicode=true&characterEncoding=UTF-8&jdbcCompliantTruncation=false");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


//            String dbName="Users";

            try
            {
            PreparedStatement updateAccessRefresh = conn.prepareStatement("update users.users set access = ?, refresh = ? where username= ?");
            updateAccessRefresh.setString(1,accessToken);
            updateAccessRefresh.setString(2,refreshToken);
            updateAccessRefresh.setString(3,username);
            updateAccessRefresh.executeUpdate();
            updateAccessRefresh.close();
            }
            catch (SQLException se)
            {
                // log the exception
                throw se;
            }
//            PreparedStatement updateRefresh = conn.prepareStatement("update users.users set refresh = "+refreshToken+" where username= ? ");

//            updateAccess.setString(1, username);
//            updateRefresh.setString(1, username);

//            int affectedrows = updateAccess.executeUpdate()+updateRefresh.executeUpdate();





            usersmap.put(username, Collections.singletonList((accessToken +"%"+ refreshToken)));

            System.out.println("Usermap of usernames and accessrefresh pairs: "+usersmap.get(username));

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
            AuthorizationCodeRefresh.usernameexpiry.put(username,authorizationCodeCredentials.getExpiresIn());
        } catch (CompletionException error) {
            System.out.println("Error: " + error.getCause().getMessage());
            channel.sendMessage(error.getMessage());
        } catch (CancellationException e) {
            System.out.println("Error: " + e.getCause().getMessage());
            channel.sendMessage(e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
