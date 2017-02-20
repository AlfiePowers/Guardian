package com.guardian.commands;

import com.guardian.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Help implements Command {
    private final String HELP = "Usage: !help [command]";
    JDA jda;
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String message = event.getMessage().getContent();
        if(message.equals("!help")){
            event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + " - You have not specified the command you want help with! Please type !help [command]").queue();
        }else if(message.contains("play")){
            event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + " \n Info: Play - Plays a given song (Note: Must be connected to a voice channel before requesting the song!)" +
                    "\n Usage: !play [url] " +
                    "\n Note: Taken URLs - YouTube / Soundcloud").queue();
        }else if(message.contains("image")){
            event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + " \n Info: Searches Reddit for a random image and uploads the image to the voice channel" +
                    "\n Usage: !image [url]" +
                    "\n Note: Must be a valid Subreddit for the bot to find an image").queue();
        }
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

}
