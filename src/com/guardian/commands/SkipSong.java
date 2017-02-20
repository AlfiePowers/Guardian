package com.guardian.commands;

import com.guardian.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class SkipSong implements Command {
    private final String HELP = "Usage: !skip";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        /*
        This works. Leave it.
        Don't. Touch. Any. Part. Of. This. Ever. Again.
         */
        Guild guild = event.getGuild();
        String[] command = event.getMessage().getContent().split(" ", 2);
        Member member = guild.getMember(event.getAuthor());
        List roles = member.getRoles();
        List<Role> role = guild.getRolesByName("Bot Commander", true);
        if (roles.contains(role.get(0))) {
            if (guild != null) {
                if ("!skip".equals(command[0])) {
                    try {
                        PlaySong.musicManager.scheduler.nextTrack();
                        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ": Skipped to next track.").queue();
                    }catch (Exception e){
                        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ": Failed to skip the track - Please ensure you have a request in the queue!").queue();
                    }
                }
            }
        } else {
            event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ": Error - Permission denied - Please make sure you are the role [Bot Commander]!").queue();
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
