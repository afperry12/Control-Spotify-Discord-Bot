package Events;
import Authorization.AuthorizationCodeURI;
import Callback.Callback;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import javafx.util.Pair;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorHandler;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.xml.bind.Marshaller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static net.dv8tion.jda.api.Permission.ADMINISTRATOR;
import static net.dv8tion.jda.api.requests.ErrorResponse.CANNOT_SEND_TO_USER;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import net.dv8tion.jda.api.requests.ErrorResponse;
import net.jodah.expiringmap.ExpiringMap;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;

public class Link extends ListenerAdapter {

    public static Map<String, String> usernamestate = ExpiringMap.builder().expiration(120, TimeUnit.SECONDS).build();
    public static Map<String, String> stateusername = ExpiringMap.builder().expiration(120, TimeUnit.SECONDS).build();
    public static String DiscordID;
    public static TextChannel channel;
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    channel=event.getChannel();
        String messageSent = event.getMessage().getContentRaw();
        DiscordID = event.getMember().getId();
//        System.out.println("Discord ID: "+DiscordID);
        TextChannel channel = event.getChannel();

        if (messageSent.equalsIgnoreCase(Prefix.prefix + "Link")) {
//            if (stateusername.containsValue(writer)) {
//                String error = "A link has already been generated for your discord account within the last two minutes... Please use that link or wait until you can request another link";
//                event.getAuthor().openPrivateChannel().complete()
//                        .sendMessage(error.substring(0, Math.min(error.length(), 1999))).queue();
//            } else {
                AuthorizationCodeURI.authorizationCodeUri_Async();
                String content = AuthorizationCodeURI.URI;
                try {
                    List<NameValuePair> params = new URIBuilder(AuthorizationCodeURI.URI).getQueryParams();
                    System.out.println("URI Params:" + params);
                    String state = params.get(3).getValue();
                    System.out.println("State from link:" + state);
                    usernamestate.put(DiscordID, state);
                    stateusername.put(state, DiscordID);
                    System.out.println("Map of usernamestate" + usernamestate);
                    System.out.println("Map of stateusername" + stateusername);

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                try {
                    event.getAuthor().openPrivateChannel().complete()
                            .sendMessage(content.substring(0, Math.min(content.length(), 1999))).queue();

                } catch (ErrorResponseException e) {
                    channel.sendMessage((Message) e).queue();
                }

            }
//        }
    }










}