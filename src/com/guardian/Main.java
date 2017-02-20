package com.guardian;

import com.google.code.chatterbotapi.*;
import com.guardian.commands.*;
import com.guardian.util.SchedulerService;
import com.guardian.util.SetBotGame;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AccountManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Main {
    String key;
    public static JDA jda;
    public static final CommandParser parser = new CommandParser();
    public static AccountManager manager;

    public static HashMap<String, Command> commands = new HashMap<String, Command>();

    public Main(){
        File file = new File("key.txt");
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            key = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader !=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try{
            jda = new JDABuilder(AccountType.BOT).setToken(key).buildBlocking();
            jda.addEventListener(new BotListener());
            jda.setAutoReconnect(true);
            new Thread(() -> new SchedulerService()).start();
        }catch(Exception e){
            e.printStackTrace();
        }
        commands.put("ping", new Ping());
        commands.put("queue", new QueueInfo());
        commands.put("status", new Status());
        commands.put("play", (Command) new PlaySong());
        commands.put("reddit", new SubredditImage());
        commands.put("skip", new SkipSong());
        commands.put("help", new Help());
        commands.put("commands", new CommandList());
        commands.put("banned", new BannedCommand());
        commands.put("profile", new ProfileInfo());
        //manager = jda.getSelfUser().getManager();
        //Start the Set-up
        new Thread(() -> {
            try {
                new SetBotGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public static void handleCommand(CommandParser.CommandContainer cmd){

        if(commands.containsKey(cmd.invoke)){
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);
            if(safe){
                commands.get(cmd.invoke).action(cmd.args, cmd.event);
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }else{
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }
        }
    }
    public static void handleConversation(MessageReceivedEvent event){
        ChatterBotFactory factory = new ChatterBotFactory();
        try {
            ChatterBot bot = factory.create(ChatterBotType.CLEVERBOT);
            ChatterBotSession botsession = bot.createSession();
            String s = BotListener.init_text1;
            s = botsession.think(s);
            event.getTextChannel().sendMessage(event.getMessage().getAuthor().getAsMention().toString() + " " + s).queue();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
