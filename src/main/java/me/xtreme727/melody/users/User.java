package me.xtreme727.melody.users;

import me.xtreme727.melody.soundtools.SoundBit;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class User {

    private Player player;
    public boolean commandToolsWarning;

    public User(Player player) {
        this.player = player;
        commandToolsWarning = false;
    }

    public void sendMessage(Component component) {
        player.sendMessage(component);
    }

    public void playSoundBit(SoundBit bit) {
        player.playSound(player, bit.getInstrument().getBukkitInstrument().getSound(), bit.getDynamics().getNumericVolume(), bit.getNote().getPitch());
    }

    public PlayerInventory getInventory () {
        return player.getInventory();
    }

    public Block getTargetBlock() {
        return player.getTargetBlockExact(4);
    }

}
