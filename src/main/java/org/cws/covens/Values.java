package org.cws.covens;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static org.cws.MainGeneral.main;

public class Values {
    private boolean debug = true;
    private final CovensMain instance = CovensMain.getCovens();

    public Map<Player,String> coven = new HashMap<>();
    public Map<Player,Integer> passiveLevel = new HashMap<>();
    public Map<Player,Integer> ability1Level = new HashMap<>();
    public Map<Player,Integer> ability2Level = new HashMap<>();
    public Map<Player,Integer> ability3Level = new HashMap<>();
    public Map<Player,Integer> currentLevel = new HashMap<>();
    public Map<Player,Integer> experience = new HashMap<>();

    public void setup(Player player, boolean leaving) {
        if (!hasFile(player)) {
            createEmptyFile(player);
        }
        if (!leaving) {
            coven.putIfAbsent(player, "None");
            String cov = readFile(player, 0);
            if (cov.equals("None") && coven.get(player).equals("None")) {
                instance.covenChoseing.openMenu(player);
            } else if (!coven.get(player).equals("None") && cov.equals("None")) {
                cov = coven.get(player);
            }
            coven.put(player, cov);
            String line1 = readFile(player, 1);
            String line2 = readFile(player, 2);
            String line3 = readFile(player, 3);
            String line4 = readFile(player, 4);

            String line5 = readFile(player, 5);
            String line6 = readFile(player, 6);


            int l1 = Integer.parseInt(line1 != null ? line1 : "0");
            int l2 = Integer.parseInt(line2 != null ? line2 : "-1");
            int l3 = Integer.parseInt(line3 != null ? line3 : "-1");
            int l4 = Integer.parseInt(line4 != null ? line4 : "-1");
            int l5 = Integer.parseInt(line5 != null ? line5 : "1");
            int l6 = Integer.parseInt(line6 != null ? line6 : "0");


            passiveLevel.put(player, l1);
            ability1Level.put(player, l2);
            ability2Level.put(player, l3);
            ability3Level.put(player, l4);
            currentLevel.put(player,l5);
            experience.put(player,l6);
            if (debug) {
                player.sendMessage("§aCoven: " + coven.get(player));
                player.sendMessage("§aAbilities: " + passiveLevel.get(player) + ", " + ability1Level.get(player) + ", " + ability2Level.get(player) + ", " + ability3Level.get(player));
                player.sendMessage("§aCurrent Level: " + currentLevel.get(player) + ", Exp: " + experience.get(player));
            }
            instance.covenAbilityOrganizer.getPassiv(player);
        } else {
            editFile(player);
            coven.remove(player);
            passiveLevel.remove(player);
            ability1Level.remove(player);
            ability2Level.remove(player);
            ability3Level.remove(player);
            currentLevel.remove(player);
            experience.remove(player);
        }
    }

    public void createEmptyFile(Player player) {
        File parent = new File("plugins/Covens");
        if (!parent.exists()) {
            if (!parent.mkdirs()) {
                main.getLogger().severe("File does not exist: plugins/Covens");
                return;
            }
        }
        File file = new File(parent, player.getName()+".dat");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(String.format(Locale.US, "%s", "None"));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", 0));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", -1));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", -1));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", -1));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", 1));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", 0));
            writer.newLine();
        } catch (IOException e) {
            main.getLogger().severe("Error while creating the Player-File!");
        }
    }

    public void editFile(Player player) {
        File parent = new File("plugins/Covens");
        if (!parent.exists()) {
            if (!parent.mkdirs()) {
                main.getLogger().severe("File does not exist: plugins/Covens");
                return;
            }
        }
        File file = new File(parent, player.getName()+".dat");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(String.format(Locale.US, "%s", coven.get(player)));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", passiveLevel.get(player)));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", ability1Level.get(player)));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", ability2Level.get(player)));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", ability3Level.get(player)));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", currentLevel.get(player)));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", experience.get(player)));
            writer.newLine();
        } catch (IOException e) {
            main.getLogger().severe("Error while creating the Player-File!");
        }
    }

    public boolean hasFile(Player player) {
        File file = new File("plugins/Covens", player.getName()+".dat");
        return file.exists();
    }

    public String readFile(Player player, int position) {
        File parent = new File("plugins/Covens");
        if (!parent.exists()) {
            if (!parent.mkdirs()) {
                main.getLogger().severe("File does not exist: plugins/Covens");
                return null;
            }
        }
        File file = new File(parent, player.getName()+".dat");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int currentLine = 0;

            while ((line = reader.readLine()) != null) {
                if (currentLine == position) {
                    return line;
                }
                currentLine++;
            }
        } catch (IOException e) {
            main.getLogger().severe("Error while reading the Player-File!");
        }
        return null;
    }

    public void setCooldown(Player player, int zeit) {
        final int[] taskId = new int[1];
        final int[] n = {zeit};
        taskId[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            n[0]--;
            if (n[0] != 0) {
                player.sendActionBar(Component.text("§7Cooldown: §c" + n[0]));
            } else {
                Bukkit.getScheduler().cancelTask(taskId[0]);
            }
        },0,20);
    }

    public void playColorParticle(String color, Location loc, double width, int count, double hight, float size) {
        Color particleColor = switch (color) {
            case "RED" -> Color.RED;
            case "ORANGE" -> Color.ORANGE;
            case "YELLOW" -> Color.YELLOW;
            case "LIME" -> Color.LIME;
            case "GREEN" -> Color.GREEN;
            case "LIGHT_BLUE" -> Color.AQUA;
            case "BLUE" -> Color.BLUE;
            case "PURPLE" -> Color.PURPLE;
            case "CYAN" -> Color.TEAL;
            case "BLACK" -> Color.BLACK;
            case "GRAY" -> Color.GRAY;
            case "MAGENTA" -> Color.FUCHSIA;
            case "WHITE" -> Color.WHITE;
            default -> Color.SILVER;
        };

        Particle.DustOptions dustOptions = new Particle.DustOptions(particleColor, size);

        for (int i = 0; i < count; i++) {
            Objects.requireNonNull(loc.getWorld()).spawnParticle(
                    Particle.DUST,
                    loc.getX() + (Math.random() * width * 2 - width),
                    loc.getY() + (Math.random() * hight * 2 - hight),
                    loc.getZ() + (Math.random() * width * 2 - width),
                    1, 0, 0, 0, 0, dustOptions
            );
        }
    }
}
