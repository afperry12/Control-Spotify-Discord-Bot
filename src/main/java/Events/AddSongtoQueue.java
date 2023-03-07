package Events;

import Authorization.AuthorizationCode;
import Authorization.AuthorizationCodeRefresh;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.requests.data.player.AddItemToUsersPlaybackQueueRequest;
import com.wrapper.spotify.requests.data.player.SetVolumeForUsersPlaybackRequest;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.ParseException;
import sx.blah.discord.handle.impl.obj.Channel;

import java.io.IOException;
import java.sql.*;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class AddSongtoQueue extends ListenerAdapter {
    public static String username;
    public static String access;
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        System.out.println(message);
        username=event.getMember().getId();
        TextChannel channel= event.getChannel();
        try {
            if (message.contains(String.valueOf(Prefix.prefix)+"play ")) {
                String parsedmessage = message.toLowerCase().replace(String.valueOf(Prefix.prefix)+"play ", "");
                String song = String.valueOf(parsedmessage);
                System.out.println("Song: "+song);

                    String DiscordID = event.getMember().getId();
//                Connection conn =
//                        DriverManager.getConnection("jdbc:mysql://localhost:3306/test?" +
//                                "user=root&password=&useUnicode=true&characterEncoding=UTF-8&jdbcCompliantTruncation=false");
//                String access;
//                String refresh;
//                String sql = "SELECT * FROM users.users WHERE Username = ?";
//                PreparedStatement st = conn.prepareStatement(sql);
//                st.setString(1, username);
//                ResultSet rs = st.executeQuery();
//                if (rs.next()) {
//                    access = rs.getString(3);
//                    refresh = rs.getString(4);
//                }

                    String accessrefresh = String.valueOf(AuthorizationCode.usersmap.get(DiscordID));
                    access = StringUtils.substringBefore(accessrefresh, "%");
                    String refresh = StringUtils.substringAfter(accessrefresh, "%");
                    System.out.println("ACCESS: " + access + "REFRESH: " + refresh);

                    AuthorizationCodeRefresh.authorizationCodeRefresh_Async(username, access, refresh,channel,"AddSongtoQueue", song, event.getMember());

                    if (AuthorizationCodeRefresh.error==false) {
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(song+" has been added to your queue!").queue();
                        event.getChannel().sendTyping().complete();
                    }



            }
        } catch (NumberFormatException | CompletionException exception) {

            exception.printStackTrace();
            System.out.println("There was an error: "+exception);
            channel.sendMessage("Sorry, but we received the following from that request: "+exception.getMessage()).queue();
            return;
        }
    }



}

