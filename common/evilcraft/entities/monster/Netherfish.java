package evilcraft.entities.monster;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.item.Item;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import evilcraft.api.config.ElementType;
import evilcraft.api.config.ExtendedConfig;
import evilcraft.api.config.configurable.Configurable;
import evilcraft.blocks.NetherfishSpawn;
import evilcraft.blocks.NetherfishSpawnConfig;

public class Netherfish extends EntitySilverfish implements Configurable{
    
    protected ExtendedConfig eConfig = null;
    
    public static ElementType TYPE = ElementType.MOB;
    
    private static final int MAX_FIRE_DURATION = 3;
    private static final double FIRE_CHANCE = 0.5;

    // Set a configuration for this entity
    public void setConfig(ExtendedConfig eConfig) {
        this.eConfig = eConfig;
    }

    public Netherfish(World par1World) {
        super(par1World);
        this.isImmuneToFire = true;
        this.experienceValue = 10;
    }
    
    /**
     * Returns the block ID for the item the mob drops on death.
     */
    @Override
    protected int getDropItemId() {
        return Item.gunpowder.itemID;
    }
    
    /**
     * The amount of drops (1 or 2)
     * @param par1Random 
     * @return 
     */
    public int quantityDropped(Random par1Random) {
        return par1Random.nextInt(1) + 1;
    }

    @Override
    public String getUniqueName() {
        return "entity."+eConfig.NAMEDID;
    }

    @Override
    public boolean isEntity() {
        return true;
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        // A bit stronger than those normal silverfish...
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.8D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(2.0D);
    }
    
    @Override
    public boolean attackEntityAsMob(Entity entity) {
        // Ignite the attacked entity for a certain duration with a certain chance.
        if(this.rand.nextFloat() < FIRE_CHANCE)
            entity.setFire(this.rand.nextInt(MAX_FIRE_DURATION));
        return super.attackEntityAsMob(entity);
    }
    
    @Override
    public void onLivingUpdate() {
        if(!this.worldObj.isRemote) {
            for (int i = 0; i < 2; ++i) {
                this.worldObj.spawnParticle("fire", this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
            }
        }
        super.onLivingUpdate();
    }
    
    @Override
    public boolean isBurning() {
        // A line copied from EntityBlaze
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }
    
    @Override
    protected void updateEntityActionState() {
        // This fish does NOT grief, maybe this could be implemented, maybe not, nobody likes griefing mobs, but it is quite evil though...
        if (!this.worldObj.isRemote) {
            if (this.entityToAttack == null && !this.hasPath())
            {
                int i, j, k, l;
                i = MathHelper.floor_double(this.posX);
                j = MathHelper.floor_double(this.posY + 0.5D);
                k = MathHelper.floor_double(this.posZ);
                int i2 = this.rand.nextInt(6);
                l = this.worldObj.getBlockId(i + Facing.offsetsXForSide[i2], j + Facing.offsetsYForSide[i2], k + Facing.offsetsZForSide[i2]);

                if (NetherfishSpawn.getInstance().getPosingIdByMetadata(l))
                {
                    this.worldObj.setBlock(i + Facing.offsetsXForSide[i2], j + Facing.offsetsYForSide[i2], k + Facing.offsetsZForSide[i2], NetherfishSpawnConfig._instance.ID, NetherfishSpawn.getInstance().getMetadataFromBlockID(l), 3);
                    this.spawnExplosionParticle();
                    this.setDead();
                }
                else
                {
                    this.updateWanderPath();
                }
            }
        }
        super.updateEntityActionState();
    }
    
}
