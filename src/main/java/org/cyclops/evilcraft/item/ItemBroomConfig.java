package org.cyclops.evilcraft.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.cyclops.cyclopscore.config.ConfigurableProperty;
import org.cyclops.cyclopscore.config.extendedconfig.ItemConfig;
import org.cyclops.cyclopscore.helper.LootHelpers;
import org.cyclops.evilcraft.EvilCraft;
import org.cyclops.evilcraft.Reference;

/**
 * Config for the {@link ItemBroom}.
 * @author rubensworks
 *
 */
public class ItemBroomConfig extends ItemConfig {

    @ConfigurableProperty(category = "item", comment = "If the broom should spawn in loot chests.")
    public static boolean lootChests = true;

    @ConfigurableProperty(category = "item", comment = "The position to render the broom gui overlay at. (0=NE, 1=SE, 2=SW,3=NW)", isCommandable = true)
    public static int guiOverlayPosition = 1;

    @ConfigurableProperty(category = "item", comment = "The X offset for the broom gui overlay.", isCommandable = true)
    public static int guiOverlayPositionOffsetX = -15;

    @ConfigurableProperty(category = "item", comment = "The Y offset for the broom gui overlay.", isCommandable = true)
    public static int guiOverlayPositionOffsetY = -10;

    @ConfigurableProperty(category = "item", comment = "The blood usage in mB per tick.")
    public static int bloodUsage = 1;

    @ConfigurableProperty(category = "item", comment = "The blood usage in mB per block break.")
    public static int bloodUsageBlockBreak = 1;

    @ConfigurableProperty(category = "item", comment = "Show broom part tooltips on source items.")
    public static boolean broomPartTooltips = true;
    @ConfigurableProperty(category = "item", comment = "Show broom modifier tooltips on source items.")
    public static boolean broomModifierTooltips = false;

    public ItemBroomConfig() {
        super(
                EvilCraft._instance,
            "broom",
                eConfig -> new ItemBroom(new Item.Properties()
                        .stacksTo(1)
                        .tab(EvilCraft._instance.getDefaultItemGroup()))
        );
    }

    @Override
    public void onForgeRegistered() {
        super.onForgeRegistered();
        if (lootChests) {
            LootHelpers.injectLootTable(new ResourceLocation(Reference.MOD_ID, "inject/chests/broom"),
                    BuiltInLootTables.SPAWN_BONUS_CHEST,
                    BuiltInLootTables.VILLAGE_TOOLSMITH,
                    BuiltInLootTables.VILLAGE_WEAPONSMITH,
                    BuiltInLootTables.VILLAGE_SHEPHERD,
                    BuiltInLootTables.SIMPLE_DUNGEON,
                    BuiltInLootTables.ABANDONED_MINESHAFT,
                    BuiltInLootTables.JUNGLE_TEMPLE,
                    BuiltInLootTables.IGLOO_CHEST);
        }
    }

}
