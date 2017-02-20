package com.guardian.util;


import com.guardian.Main;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.managers.AccountManager;

import java.util.Random;

public class SchedulerService {
    public SchedulerService() {
        changeGame();
    }


    public void changeGame() {
        Thread changeGame = new Thread();
        changeGame.start();

        while (true) {
            try {
                Thread.sleep(1000 * (5 * 60));
                Random random = new Random();
                Main.jda.getPresence().setGame(Game.of(SetBotGame.list.get(random.nextInt(SetBotGame.list.size()))));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
