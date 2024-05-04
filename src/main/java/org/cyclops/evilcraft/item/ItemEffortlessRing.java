package org.cyclops.evilcraft.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import org.cyclops.cyclopscore.helper.ItemStackHelpers;
import org.cyclops.evilcraft.Reference;

/**
 * A ring that allows the player to walk faster with a double step height.
 * @author rubensworks
 *
 */
public class ItemEffortlessRing extends Item {

    private static final int TICK_MODULUS = 1;

    private static final float SPEED_BONUS = 0.05F;
    private static final AttributeModifier STEP_SIZE_MODIFIER = new AttributeModifier(Reference.MOD_ID + ":stepHeightModifier", 1, AttributeModifier.Operation.ADDITION);
    private static final float JUMP_DISTANCE_FACTOR = 0.05F;
    private static final float JUMP_HEIGHT_FACTOR = 0.3F;
    private static final float FALLDISTANCE_REDUCTION = 2F;

    public ItemEffortlessRing(Properties properties) {
        super(properties);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerFall);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerJump);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerUpdate);
    }

    /**
     * Re-apply the ring effects.
     * @param itemStack The item.
     * @param player The player.
     */
    public void adjustParameters(ItemStack itemStack, Player player) {
        // Speed
        if(player.zza > 0 && player.isOnGround()) {
            player.moveRelative(player.isInWater() ? SPEED_BONUS / 3 : SPEED_BONUS, new Vec3(0, 0, 1));
        }

        // Step height
        AttributeInstance attribute = player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
        if (attribute != null && !attribute.hasModifier(STEP_SIZE_MODIFIER)) {
            attribute.addTransientModifier(STEP_SIZE_MODIFIER);
        }

        // Jump distance
        if(!player.isOnGround()) {
            player.flyingSpeed = JUMP_DISTANCE_FACTOR;
        }
    }

    public void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(ItemStackHelpers.hasPlayerItem(player, this)) {
                player.setDeltaMovement(player.getDeltaMovement().add(0, JUMP_HEIGHT_FACTOR, 0));;
            }
        }
    }

    public void onPlayerUpdate(LivingEvent.LivingTickEvent event) {
        // Reset the step height.
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            AttributeInstance attribute = player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
            if (attribute != null && attribute.hasModifier(STEP_SIZE_MODIFIER)) {
                if (!ItemStackHelpers.hasPlayerItem(player, this)) {
                    attribute.removeModifier(STEP_SIZE_MODIFIER);
                }
            }
        }
    }

    public void onPlayerFall(LivingFallEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(ItemStackHelpers.hasPlayerItem(player, this)) {
                event.setDistance(event.getDistance() - FALLDISTANCE_REDUCTION);
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(entityIn instanceof Player) {
            adjustParameters(stack, (Player) entityIn);
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

}
