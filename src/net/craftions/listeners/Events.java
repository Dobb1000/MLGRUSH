package net.craftions.listeners;


import java.util.*;


import net.craftions.config.Config;
import net.craftions.main.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Events implements Listener {
    public String Blue;
    public String Red;

    public List<Block> editable = new ArrayList<Block>();
    public int bluewins;
    public int redwins;



    public int getBluewins() {
        return bluewins;
    }

    public int getRedwins() {
        return redwins;
    }

    public String getBlue() {
        return Blue;
    }

    public String getRed() {
        return Red;
    }

    int playercount = Bukkit.getOnlinePlayers().size();

    Location locationred = (Location) Config.getInstance("mlgconf").get("Redspawn");
    Location locationblue = (Location) Config.getInstance("mlgconf").get("Bluespawn");





    @EventHandler
    public void onQuit(PlayerQuitEvent event) {


        if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            editable.forEach(b -> b.setType(Material.AIR));
            editable.clear();
            bluewins = 0;
            Main.getInstance().mlgBoard.setBluewinscount(0);
            Main.getInstance().mlgBoard.setRedwinscount(0);
            redwins = 0;
        }
        event.getPlayer().setGameMode(GameMode.SURVIVAL);
        event.getPlayer().getInventory().clear();


    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getPlayer().getLocation().getBlock();

        if (event.getAction() == (Action.PHYSICAL) && block.getType() == Material.FARMLAND) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {


        int playercount = Bukkit.getOnlinePlayers().size();
        Player player = event.getPlayer();

        Plugin plugin = Main.getInstance();
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                Main.getInstance().mlgBoard.setScoreboard(player);
                Main.getInstance().mlgBoard.updateScoreboard(player);


                if (!(playercount >= 2)) {
                    String message = "§6§lWarten auf spieler!";
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));

                }
            }
        }, 0L, 20L);

        Config.getInstance("mlgconf").reload(true);
        //String redx = (String) Config.getInstance("mlgconf").get("Redspawn");






        if (playercount >= 3) {
            player.setGameMode(GameMode.SPECTATOR);
        } else if (playercount == 2) {
            this.Blue = player.getName();
            player.teleport(locationblue);
            ItemStack stick = new ItemStack(Material.STICK,1);


            ItemMeta stickmeta = stick.getItemMeta();

            stickmeta.addEnchant(Enchantment.KNOCKBACK, 2, false);
            stick.setItemMeta(stickmeta);
            player.getInventory().addItem(stick);
            ItemStack sandstone = new ItemStack(Material.SANDSTONE,64);
            player.getInventory().addItem(sandstone);

            ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE,1);
            player.getInventory().addItem(pickaxe);

        } else if (playercount == 1) {
            this.Red = player.getName();
            player.teleport(locationred);


            ItemStack stick = new ItemStack(Material.STICK,1);


            ItemMeta stickmeta = stick.getItemMeta();

            stickmeta.addEnchant(Enchantment.KNOCKBACK, 2, false);
            stick.setItemMeta(stickmeta);
            player.getInventory().addItem(stick);
            ItemStack sandstone = new ItemStack(Material.SANDSTONE,64);
            player.getInventory().addItem(sandstone);

            ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE,1);
            player.getInventory().addItem(pickaxe);


        }

    }



    @EventHandler
    public void Fallevent(PlayerMoveEvent event) {

        if (playercount >= 2) { //Todo playercount >= 2



            int midy = (int) Config.getInstance("mlgconf").get("heights.min(reset)");


            if (event.getPlayer().getLocation().getY() <= midy) {
                if (this.Blue == event.getPlayer().getName()) {
                    event.getPlayer().teleport(locationblue);

                    ItemStack stick = new ItemStack(Material.STICK, 1);

                    event.getPlayer().getInventory().clear();
                    ItemMeta stickmeta = stick.getItemMeta();

                    stickmeta.addEnchant(Enchantment.KNOCKBACK, 2, false);
                    stick.setItemMeta(stickmeta);
                    event.getPlayer().getInventory().addItem(stick);
                    ItemStack sandstone = new ItemStack(Material.SANDSTONE, 64);
                    event.getPlayer().getInventory().addItem(sandstone);

                    ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE, 1);
                    event.getPlayer().getInventory().addItem(pickaxe);


                } else if (this.Red == event.getPlayer().getName()) {
                    event.getPlayer().teleport(locationred);

                    ItemStack stick = new ItemStack(Material.STICK, 1);

                    event.getPlayer().getInventory().clear();
                    ItemMeta stickmeta = stick.getItemMeta();

                    stickmeta.addEnchant(Enchantment.KNOCKBACK, 2, false);
                    stick.setItemMeta(stickmeta);
                    event.getPlayer().getInventory().addItem(stick);
                    ItemStack sandstone = new ItemStack(Material.SANDSTONE, 64);
                    event.getPlayer().getInventory().addItem(sandstone);

                    ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE, 1);
                    event.getPlayer().getInventory().addItem(pickaxe);
                }
            }
        }else {

            if (!(event.getPlayer().getGameMode() == GameMode.CREATIVE)) {

            event.setCancelled(true);}
        }
    }


    @EventHandler
    public void onPlace(BlockPlaceEvent event){


        int maxy = (int) Config.getInstance("mlgconf").get("heights.max(construction height)");

        if (playercount >= 2) { //TODO playercount >= 2
            if (!(event.getPlayer().getGameMode() == GameMode.CREATIVE)) {
                if (event.getBlock().getY() <= maxy) {
                    editable.add(event.getBlock());
                } else {

                    event.setCancelled(true);
                }

            }
        } else {

            if (!(event.getPlayer().getGameMode() == GameMode.CREATIVE)) {
                event.setCancelled(true);
            }



        }



    }
    @EventHandler
    public void onrespawn(PlayerRespawnEvent event){

            event.getPlayer().getInventory().clear();

        ItemStack stick = new ItemStack(Material.STICK,1);

        event.getPlayer().getInventory().clear();
        ItemMeta stickmeta = stick.getItemMeta();

        stickmeta.addEnchant(Enchantment.KNOCKBACK, 2, false);
        stick.setItemMeta(stickmeta);
        event.getPlayer().getInventory().addItem(stick);
        ItemStack sandstone = new ItemStack(Material.SANDSTONE,64);
        event.getPlayer().getInventory().addItem(sandstone);

        ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE,1);
        event.getPlayer().getInventory().addItem(pickaxe);





    }
    @EventHandler
    public void entityDamageEvent(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player)event.getEntity();
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {

                event.setCancelled(true);
            }}}


    public void winner() {
        int winsmax = (int) Config.getInstance("mlgconf").get("wins.max");



        if (redwins == winsmax) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");

            for (Player playerwinred : Bukkit.getOnlinePlayers()) {
                if (playerwinred.getName() == Red) {

                    playerwinred.sendTitle(ChatColor.RED + "Du hast Gewonnen", " ", 1, 40, 1);

                } else {
                    playerwinred.sendTitle("Rot hat Gewonnen", " ", 1, 40, 1);

                }}Blue = null;
            Red = null;}
        if (bluewins == winsmax) {

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");

            for (Player playerwinblue : Bukkit.getOnlinePlayers()) {
                if (playerwinblue.getName() == Blue) {
                    playerwinblue.sendTitle(ChatColor.BLUE + "Du hast Gewonnen", " ", 1, 40, 1);
                } else {
                    playerwinblue.sendTitle("Rot hat Gewonnen", " ", 1, 40, 1);

                }
            }

            Blue = null;
            Red = null;
        }
        if (redwins >= winsmax) {

            redwins = winsmax;
        }

        if (bluewins >= winsmax) {

            bluewins = winsmax;
        }


    }
    @EventHandler (priority = EventPriority.HIGH)
    public void onFoodLevelChange (FoodLevelChangeEvent event) {
        if (event.getEntityType () != EntityType.PLAYER) return;
        event.setCancelled (true);
    }


    @EventHandler
    public void onDrop(PlayerDropItemEvent t) {
        if (!(t.getPlayer().getGameMode() == GameMode.CREATIVE)) {

            t.setCancelled(true);
        }

    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();




        if (!(event.getPlayer().getGameMode() == GameMode.CREATIVE)) {
            if (editable.contains(event.getBlock())) {


                editable.remove(event.getBlock());
            } else {

                if (event.getBlock().getType() == Material.BLUE_BED) {
                    if (this.Red == event.getPlayer().getName()) {
                        redwins++;
                        Main.getInstance().mlgBoard.setRedwinscount(redwins);

                        event.getPlayer().teleport(locationred);
                        for (Player playeronline : Bukkit.getOnlinePlayers()) {

                            if (playeronline.getName().equals(this.Blue)) {
                                playeronline.teleport(locationblue);
                            }
                        }


                        editable.forEach(b -> b.setType(Material.AIR));
                        editable.clear();
                        event.setCancelled(true);

                        event.getPlayer().getInventory().clear();

                        ItemStack stick = new ItemStack(Material.STICK,1);

                        event.getPlayer().getInventory().clear();
                        ItemMeta stickmeta = stick.getItemMeta();

                        stickmeta.addEnchant(Enchantment.KNOCKBACK, 2, false);
                        stick.setItemMeta(stickmeta);
                        event.getPlayer().getInventory().addItem(stick);
                        ItemStack sandstone = new ItemStack(Material.SANDSTONE,64);
                        event.getPlayer().getInventory().addItem(sandstone);

                        ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE,1);
                        event.getPlayer().getInventory().addItem(pickaxe);
                        winner();
                    } else if (this.Blue == event.getPlayer().getName()) {

                        event.getPlayer().sendMessage(ChatColor.RED + "Du kannst nicht dein eigenes Bett Abbauen");
                        event.setCancelled(true);
                    }
                }

                if (event.getBlock().getType() == Material.RED_BED) {
                    if (this.Blue == event.getPlayer().getName()) {
                        bluewins++;
                        Main.getInstance().mlgBoard.setRedwinscount(bluewins);
                        event.getPlayer().teleport(locationblue);
                        for (Player playeronline : Bukkit.getOnlinePlayers()) {

                            if (playeronline.getName().equals(this.Red)) {
                                playeronline.teleport(locationred);
                            }
                        }

                        editable.forEach(b -> b.setType(Material.AIR));
                        editable.clear();
                        event.setCancelled(true);

                        event.getPlayer().getInventory().clear();

                        ItemStack stick = new ItemStack(Material.STICK,1);

                        event.getPlayer().getInventory().clear();
                        ItemMeta stickmeta = stick.getItemMeta();

                        stickmeta.addEnchant(Enchantment.KNOCKBACK, 2, false);
                        stick.setItemMeta(stickmeta);
                        event.getPlayer().getInventory().addItem(stick);
                        ItemStack sandstone = new ItemStack(Material.SANDSTONE,64);
                        event.getPlayer().getInventory().addItem(sandstone);

                        ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE,1);
                        event.getPlayer().getInventory().addItem(pickaxe);

                        winner();
                    } else if (this.Red == event.getPlayer().getName()) {

                        event.getPlayer().sendMessage(ChatColor.RED + "Du kannst nicht dein eigenes Bett Abbauen");
                        event.setCancelled(true);
                    }

                }

                event.setCancelled(true);


            }
        }
    }


    }
