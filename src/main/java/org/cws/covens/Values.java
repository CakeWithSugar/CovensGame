package org.cws.covens;

import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.cws.MainGeneral.main;

public class Values {
    private boolean debug = true;
    private final CovensMain instance = CovensMain.getCovens();

    public Map<Player,String> coven = new HashMap<>();
    public Map<Player,Integer> ability1Level = new HashMap<>();
    public Map<Player,Integer> ability2Level = new HashMap<>();
    public Map<Player,Integer> ability3Level = new HashMap<>();

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
            int l1 = Integer.parseInt(readFile(player, 1));
            int l2 = Integer.parseInt(readFile(player, 2));
            int l3 = Integer.parseInt(readFile(player, 3));

            ability1Level.put(player, l1);
            ability2Level.put(player, l2);
            ability3Level.put(player, l3);
            if (debug) {
                player.sendMessage("§aCoven: " + coven.get(player));
                player.sendMessage("§aAbilities: " + ability1Level.get(player) + ", " + ability2Level.get(player) + ", " + ability3Level.get(player));
            }
        } else {
            editFile(player);
            coven.remove(player);
            ability1Level.remove(player);
            ability2Level.remove(player);
            ability3Level.remove(player);
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
            writer.write(String.format(Locale.US, "%d", -1));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", -1));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", -1));
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
            writer.write(String.format(Locale.US, "%d", ability1Level.get(player)));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", ability2Level.get(player)));
            writer.newLine();
            writer.write(String.format(Locale.US, "%d", ability3Level.get(player)));
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
}
