package me.xtreme727.beatblocks.soundtools;

import me.xtreme727.beatblocks.Message;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Note {

    C(0, Material.RED_STAINED_GLASS, "C", 0.707107, 1.414214),
    C_SHARP(-1, null, null, 0.749154, 1.498307),
    D(1, Material.ORANGE_STAINED_GLASS, "D", 0.793701, 1.587401),
    D_SHARP(-1, null, null, 0.840896, 1.681793),
    E(2, Material.YELLOW_STAINED_GLASS, "E", 0.890899, 1.781797),
    F(3, Material.LIME_STAINED_GLASS, "F", 0.943874, 1.887749),
    F_SHARP(-1, null, null, 0.5, 1, 2),
    G(4, Material.GREEN_STAINED_GLASS, "G", 0.529732, 1.059463),
    G_SHARP(-1, null, null, 0.561231, 1.122462),
    A(5, Material.LIGHT_BLUE_STAINED_GLASS, "A", 0.594604, 1.189207),
    A_SHARP(-1, null, null, 0.629961, 1.259921),
    B(6, Material.PURPLE_STAINED_GLASS, "B", 0.667420, 1.334840);

    private Material dMaterial;
    private String dName;
    private double[] pitches;
    private ItemStack dItem;
    private int octave;
    private int iSlot;

    private Note(int iSlot, Material dMaterial, String dName, double... pitches) {
        this.dMaterial = dMaterial;
        this.dName = dName;
        this.pitches = pitches;
        this.iSlot = iSlot;

        if (dMaterial != null) {
            dItem = new ItemStack(dMaterial, 1);
            ItemMeta dItemMeta = dItem.getItemMeta();
            dItemMeta.displayName(Message.formatItemMeta("&f&l" + dName).decoration(TextDecoration.ITALIC, false));
            dItem.setItemMeta(dItemMeta);
        }
    }

    public ItemStack getDisplayItem() {
        return dItem;
    }

    public Material getDisplayMaterial() {
        return dMaterial;
    }

    public float getPitch() {
        if (octave > 0 && pitches.length < (octave - 1)) return (float) pitches[pitches.length - 1];
        return (float) pitches[octave - 1];
    }

    public void setOctave(int octave) {
        this.octave = octave;
    }

    public int getOctave() {
        return octave;
    }

    public int getItemSlot() {
        return iSlot;
    }

    private static Note getSharp(Note n) {
        if (n == B) return C;
        return Note.values()[n.ordinal() + 1];
    }

    private static Note getFlat(Note n) {
        if (n == C) {
            Note newNote = B;
            newNote.setOctave(n.getOctave() - 1);
            return newNote;
        }
        return Note.values()[n.ordinal()-1];
    }

    public static Note fromBlock(Block instrumentBlock) {
        for (int i = 0; i <= 5; i++) {
            if (instrumentBlock.getRelative(0, i, 0).getType() == Material.AIR) {
                for (Note n : Note.values()) {

                    Instrument ins = Instrument.fromBlock(instrumentBlock);
                    if ((ins == Instrument.BASS_DRUM || ins == Instrument.STICKS || ins == Instrument.SNARE_DRUM) && i == 1) {
                        Note returnNote = F_SHARP;
                        returnNote.setOctave(2);
                        return returnNote;
                    }

                    if (instrumentBlock.getRelative(0, i-1, 0).getType() == Material.POLISHED_DIORITE_SLAB) {
                        if (instrumentBlock.getRelative(0, i-2, 0).getType() == n.getDisplayMaterial()) {
                            n = getSharp(n);
                            n.setOctave(n == F_SHARP ? i-1 : i-2);
                            return n;
                        }
                    }

                    if (instrumentBlock.getRelative(0, i-1, 0).getType() == Material.IRON_TRAPDOOR) {
                        if (instrumentBlock.getRelative(0, i-2, 0).getType() == n.getDisplayMaterial()) {
                            n = getFlat(n);
                            n.setOctave(i-2);
                            return n;
                        }
                    }

                    if (instrumentBlock.getRelative(0, i-1, 0).getType() == n.getDisplayMaterial()) {
                        n.setOctave(i-1);
                        return n;
                    }
                }
            }
        }
        return null;
    }

    public static ItemStack getSharpItemStack()  {
        ItemStack sharp = new ItemStack(Material.POLISHED_DIORITE_SLAB, 1);
        ItemMeta iMeta = sharp.getItemMeta();
        iMeta.displayName(Message.formatItemMeta("&f&lSharp"));
        sharp.setItemMeta(iMeta);
        return sharp;
    }

    public static ItemStack getFlatItemStack()  {
        ItemStack flat = new ItemStack(Material.IRON_TRAPDOOR, 1);
        ItemMeta iMeta = flat.getItemMeta();
        iMeta.displayName(Message.formatItemMeta("&f&lFlat"));
        flat.setItemMeta(iMeta);
        return flat;
    }

}
