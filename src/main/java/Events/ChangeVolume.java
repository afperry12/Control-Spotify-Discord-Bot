package Events;

import Authorization.AuthorizationCode;
import Authorization.AuthorizationCodeRefresh;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.requests.data.player.SetVolumeForUsersPlaybackRequest;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.ParseException;
import sx.blah.discord.handle.impl.obj.Channel;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.CompletionException;

public class ChangeVolume extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        System.out.println(message);
        String username=event.getMember().getId();
        TextChannel channel= event.getChannel();
        try {
            if (message.contains(String.valueOf(Prefix.prefix)+"volume")) {
                String parsedmessage = message.toLowerCase().replace(String.valueOf(Prefix.prefix)+"volume", "");
                Long volumelevel = Long.parseLong(parsedmessage.trim());
                System.out.println(volumelevel);
                if (volumelevel <= 100 && volumelevel >= 0) {
                    String DiscordID = event.getMember().getId();
                    String accessrefresh = String.valueOf(AuthorizationCode.usersmap.get(DiscordID));
                    String access = StringUtils.substringBefore(accessrefresh, "%");
                    String refresh = StringUtils.substringAfter(accessrefresh, "%");
                    System.out.println("ACCESS: " + access + "REFRESH: " + refresh);

                    AuthorizationCodeRefresh.authorizationCodeRefresh_Async(username, access, refresh,channel,"ChangeVolume", String.valueOf(volumelevel),event.getMember());

                    if (AuthorizationCodeRefresh.error==false) {
                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage("You have set your spotify volume to " + volumelevel).queue();
                        event.getChannel().sendTyping().complete();
                    }

                }
                else if (volumelevel > 100&&AuthorizationCodeRefresh.error==false) {
                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage("You have set your spotify volume to 100%").queue();
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

