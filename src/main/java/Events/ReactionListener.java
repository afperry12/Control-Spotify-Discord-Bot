package Events;

import Authorization.AuthorizationCodeRefresh;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static Events.Link.channel;

public class ReactionListener extends ListenerAdapter {

    public static Long messageofsongtoPlay;
    public static String songURI;

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        System.out.println(event.getMember().getId());
        System.out.println(AddSongtoQueue.username);

        System.out.println(event.getChannel());
        System.out.println(channel);
//        if (AuthorizationCodeRefresh.messageids.contains(event.getMessageId())) {
//            if (event.getMember().getId() == AddSongtoQueue.username && event.getChannel() == channel) {
            messageofsongtoPlay= Long.valueOf(event.getMessageId());
            for (int l=0;l<AuthorizationCodeRefresh.messageids.size();l++) {
//                if (AuthorizationCodeRefresh.messageids.get(l)== ReactionListener.messageofsongtoPlay) {
                    System.out.println(messageofsongtoPlay+"\n"+AuthorizationCodeRefresh.messageids.get(l)+"\n"+AuthorizationCodeRefresh.URIArrayList.get(l));
                    songURI = AuthorizationCodeRefresh.URIArrayList.get(l);
                System.out.println(songURI);
                AuthorizationCodeRefresh.AddItemToUsersPlaybackQueue();
//                    AddSongtoQueue.addsongtoPlayback(songURI, AddSongtoQueue.access);

//                }
            }


//            }
//        }
    }
}