package evilcraft.modcompat.nei;

import codechicken.nei.recipe.DefaultOverlayHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.inventory.Slot;
import org.cyclops.cyclopscore.inventory.NBTSimpleInventory;

/**
 * An overlay handler for the Exalted Crafter.
 * @author rubensworks
 */
public class ExaltedCrafterOverlayHandler extends DefaultOverlayHandler {

	@Override
	public boolean canMoveFrom(Slot slot, GuiContainer gui) {
        return slot.inventory instanceof InventoryPlayer
        		|| slot.inventory instanceof InventoryEnderChest
                || slot.inventory instanceof NBTSimpleInventory;
    }
	
}
