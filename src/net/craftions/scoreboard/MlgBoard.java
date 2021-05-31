package net.craftions.scoreboard;


import net.craftions.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class MlgBoard {

    private Main plugin;


    public int Redwinscount;
    public int Bluewinscount;



    public int getRedwinscount() {
        return Redwinscount;
    }

    public void setRedwinscount(int redwinscount) {
        this.Redwinscount = redwinscount;
    }

    public int getBluewinscount() {
        return Bluewinscount;
    }

    public void setBluewinscount(int bluewinscount) {
        this.Bluewinscount = bluewinscount;
    }

    public void setScoreboard(Player p ) {
        String displayName = p.getDisplayName();
        String none = " ";
        String none2 = " ";
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("Stats", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§lMLG RUSH§7");

        Team name =  board.registerNewTeam("name");
        Team friends =  board.registerNewTeam("friends");

        obj.getScore("§e").setScore(3);
        obj.getScore("§d").setScore(2);





        name.addEntry("§d");
        name.setPrefix("§e" + Redwinscount);


        friends.addEntry("§e");
        friends.setPrefix("§a" + Bluewinscount);


        p.setScoreboard(board);

    }

    public void updateScoreboard(Player p) {
        String displayName = p.getDisplayName();
        Scoreboard board = p.getScoreboard();
        Team name = board.getTeam("name");
        Team friends = board.getTeam("friends");
        name.setPrefix(ChatColor.RED +"ROT: " + ChatColor.WHITE+  Redwinscount);
        friends.setPrefix(ChatColor.BLUE +"BLAU: " +  ChatColor.WHITE+ Bluewinscount);




}}




