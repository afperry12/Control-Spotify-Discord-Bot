package Events;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageSendEvent;

import javax.xml.bind.Marshaller;

import java.security.acl.Owner;
import java.util.Locale;
import java.util.concurrent.CompletionException;

import static net.dv8tion.jda.api.Permission.ADMINISTRATOR;

public class Prefix extends ListenerAdapter {
    public static String prefix = "-";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String messagesent = event.getMessage().getContentRaw();
        Member writer = event.getMember();
        TextChannel channel = event.getChannel();
        String Prefix;
        Prefix = this.prefix;

        if (messagesent.toLowerCase().contains(this.prefix + "prefix ") && writer.hasPermission(ADMINISTRATOR)) {
            String parsedmessage = messagesent.toLowerCase().replace(String.valueOf(Prefix) + "prefix ", "");
            String newprefix = parsedmessage;
            if (newprefix.length() >= 1) {
                System.out.println("New Prefix: " + newprefix);
                this.prefix = newprefix;
                channel.sendMessage("Your new prefix is: " + newprefix).queue();
            }
        }
    }
}

