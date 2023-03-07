package Events;

import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
//        String[] args = event.getMessage().getContentRaw().split(" ");
//        if (args[0].equalsIgnoreCase(Prefix.prefix+"help")) {
//            event.getChannel().sendTyping().queue();
//            event.getChannel().sendMessage("help me make a help list").queue();
//        }
        String messageSent = event.getMessage().getContentRaw();
        if (messageSent.equalsIgnoreCase(Prefix.prefix+"help")){
        event.getChannel().sendMessage("help me make a help list").queue();
        }
        if (messageSent.equalsIgnoreCase("help")){
            event.getChannel().sendMessage("help me make a help list").queue();
        }
    }
}
