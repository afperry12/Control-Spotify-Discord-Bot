package Main;
import Callback.Callback;
import Events.*;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import sx.blah.discord.api.internal.json.objects.GameObject;

import javax.security.auth.login.LoginException;

public class Main {
    public static JDA jda;
    public static String prefix = Prefix.prefix;
    public static final String clientId = "Token";
    public static final String clientSecret = "Token";
    public static void main(String[] args) throws LoginException, InterruptedException {
        jda = JDABuilder.createDefault("Token").addEventListeners(new Help(),new Prefix(),new Link(),new ChangeVolume(),new AddSongtoQueue(),new ReactionListener()).build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.playing("Hi"));
//        jda.addEventListener(new Help(), new Prefix(), new Link(),new ChangeVolume());
        Callback.MyCallback(8080);

//        AuthorizationCodeURI.main(args);
//        Callback test=new Callback(8080);
    }


//        String result=test.toString();
//        System.out.println(result);


}



