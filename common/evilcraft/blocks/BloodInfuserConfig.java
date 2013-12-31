package evilcraft.blocks;

import net.minecraft.tileentity.TileEntity;
import evilcraft.Reference;
import evilcraft.api.config.ExtendedConfig;
import evilcraft.entities.tileentities.TileBloodInfuser;

public class BloodInfuserConfig extends ExtendedConfig {
    
    public static BloodInfuserConfig _instance;

    public BloodInfuserConfig() {
        super(
            Reference.BLOCK_BLOODINFUSER,
            "Blood Infuser",
            "bloodInfuser",
            null,
            BloodInfuser.class
        );
    }
    
}
