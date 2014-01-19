package evilcraft.api.entities.tileentitites;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import evilcraft.api.Helpers;
import evilcraft.api.inventory.SimpleInventory;

/**
 * A TileEntity with an internal inventory.
 * @author rubensworks
 *
 */
public abstract class InventoryTileEntity extends EvilCraftTileEntity implements IInventory, ISidedInventory{
    
    protected SimpleInventory inventory;
    protected Map<ForgeDirection, int[]> slotSides;
    protected Map<ForgeDirection, Integer> slotSidesSize;
    
    /**
     * Make new tile with an inventory.
     * @param inventorySize Amount of slots in the inventory.
     * @param inventoryName Internal name of the inventory.
     * @param stackSize The maximum stacksize each slot can have
     */
    public InventoryTileEntity(int inventorySize, String inventoryName, int stackSize) {
        inventory = new SimpleInventory(inventorySize , inventoryName, stackSize);
        slotSides = new HashMap<ForgeDirection, int[]>();
        slotSidesSize = new HashMap<ForgeDirection, Integer>();
        for(ForgeDirection side : Helpers.DIRECTIONS) {
            // Init each side to it can theoretically hold all possible slots,
            // Integer lists are not option because Java allows to autoboxing
            // and that would be required in the getter methods below.
            slotSides.put(side, new int[inventorySize]); 
            slotSidesSize.put(side, 0); 
        }
    }
    
    /**
     * Make new tile with an inventory.
     * @param inventorySize Amount of slots in the inventory.
     * @param inventoryName Internal name of the inventory.
     */
    public InventoryTileEntity(int inventorySize, String inventoryName) {
        this(inventorySize , inventoryName, 64);
    }
    
    /**
     * Add mappings to slots to a certain (normalized) side of this TileEntity.
     * @param side The side to map this slots to.
     * @param slots The numerical representations of the slots to map.
     */
    protected void addSlotsToSide(ForgeDirection side, Collection<Integer> slots) {
        int[] currentSlots = slotSides.get(side);
        int offset = slotSidesSize.get(side);
        int i = 0;
        for(int slot : slots) {
            currentSlots[offset + i] = slot;
            i++;
        }
        slotSidesSize.put(side, offset + i);
    }
    
    /**
     * Get the internal inventory.
     * @return The SimpleInventory.
     */
    public SimpleInventory getInventory() {
        return inventory;
    }
    
    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }
    
    @Override
    public ItemStack getStackInSlot(int slotId) {
        if(slotId >= getSizeInventory() || slotId < 0)
            return null;
        return inventory.getStackInSlot(slotId);
    }

    @Override
    public ItemStack decrStackSize(int slotId, int count) {
        return inventory.decrStackSize(slotId, count);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slotId) {
        return inventory.getStackInSlotOnClosing(slotId);
    }

    @Override
    public void setInventorySlotContents(int slotId, ItemStack itemstack) {
        inventory.setInventorySlotContents(slotId, itemstack);
    }

    @Override
    public String getInvName() {
        return inventory.getInvName();
    }

    @Override
    public boolean isInvNameLocalized() {
        return inventory.isInvNameLocalized();
    }

    @Override
    public int getInventoryStackLimit() {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && entityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
    }
    
    @Override
    public void openChest() {
        
    }

    @Override
    public void closeChest() {
        
    }
    
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        inventory.readFromNBT(data);
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        inventory.writeToNBT(data);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return slotSides.get(ForgeDirection.getOrientation(side));
    }
    
    private boolean canAccess(int slot, int side) {
        boolean canAccess = false;
        for(int slotAccess : getAccessibleSlotsFromSide(side)) {
            if(slotAccess == slot)
                canAccess = true;
        }
        return canAccess;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
        System.out.println("Slot:"+slot+"side:"+side);
        System.out.println("Can?"+canAccess(slot, side) + " Valid?"+this.isItemValidForSlot(slot, itemStack));
        return canAccess(slot, side) && this.isItemValidForSlot(slot, itemStack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
        return canAccess(slot, side);
    }
    
}