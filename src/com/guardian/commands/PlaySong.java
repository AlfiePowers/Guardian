/*
This section of code is modified from the LavaPlayer example.
I didn't write most of this code, but I modified it for my handler.
So there, I did something. It's spaghetti code but it works.
 */

package com.guardian.commands;

import com.guardian.Command;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.GuildVoiceState;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AccountManager;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.HashMap;
import java.util.Map;


public class PlaySong extends ListenerAdapter implements Command {
    private final String HELP = "Usage: !ping";
    public static GuildMusicManager musicManager;
    static TextChannel channel;
    MessageReceivedEvent location;

    public AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    public Map<Long, GuildMusicManager> musicManagers = new HashMap<>();

    public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
        String[] command = event.getMessage().getContent().split(" ", 2);
        Guild guild = event.getGuild();
        channel = event.getTextChannel();
        if (guild != null) {
            loadAndPlay(event.getTextChannel(), command[1]);
        }

        super.onMessageReceived(event);
        return true;
    }

    private void loadAndPlay(final TextChannel channel, final String trackUrl) {
        final GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage("Added " + track.getInfo().title + " to the queue").queue();
                play(channel.getGuild(), musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }
                channel.sendMessage("Added " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

                play(channel.getGuild(), musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
        try {
            connectToFirstVoiceChannel(guild, guild.getAudioManager());
            musicManager.scheduler.queue(track);
        }catch (Exception e){
            location.getTextChannel().sendMessage("Failed to Play " + track.getInfo() + ": Song URL Invalid").queue();
        }

    }

    private static void connectToFirstVoiceChannel(Guild guild, AudioManager audioManager) {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                audioManager.openAudioConnection(voiceChannel);
                break;
            }
        }
    }

    public static void disconnectFromVoiceChannel(AudioManager audioManager, MessageReceivedEvent event) {
        if(audioManager.isConnected()){
            System.out.println("Disconnecting from " + event.getGuild().getName());
            audioManager.closeAudioConnection();
        }
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

    }

    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }


}
