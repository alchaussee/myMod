package myMod.common;

import net.minecraft.block.Block;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityTestOre extends Entity
{
    public int blockID = MyMod.testOreID;
    public int metadata;

    /** How long the block has been falling for. */
    public int fallTime;
    public boolean shouldDropItem;
    private boolean isBreakingAnvil;
    private boolean isAnvil;
    private int field_82156_g;
    private float field_82158_h;
    private boolean first;
	
	public EntityTestOre(World world)
    {
        super(world);
        this.fallTime = 0;
        this.shouldDropItem = true;
        this.isBreakingAnvil = false;
        this.isAnvil = false;
        this.field_82156_g = 40;
        this.first = false;
        this.field_82158_h = 2.0F;
    }
	
	public EntityTestOre(World par1World, double par2, double par4, double par6, int par8)
	{
		this(par1World, par2, par4, par6, par8, 0);
	}

	public EntityTestOre(World par1World, double par2, double par4, double par6, int par8, int par9)
	{
	    super(par1World);
	    this.fallTime = 0;
	    this.shouldDropItem = true;
	    this.isBreakingAnvil = false;
	    this.isAnvil = false;
        this.field_82156_g = 40;
        this.field_82158_h = 2.0F;
        this.metadata = par9;
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        this.yOffset = this.height / 2.0F;
        this.setPosition(par2, par4, par6);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
        this.first = false;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    @Override
    protected void entityInit() {}

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    @Override
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

	 @Override
	public void onUpdate()
    {
        if (this.blockID == 0)
        {
            this.setDead();
        }
        else
        {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            ++this.fallTime;
            this.motionY += 0.03999999910593033D;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.9800000190734863D;
            this.motionY *= 0.9800000190734863D;
            this.motionZ *= 0.9800000190734863D;

            if (!this.worldObj.isRemote)
            {
                int var1 = MathHelper.floor_double(this.posX);
                int var2 = MathHelper.floor_double(this.posY);
                int var3 = MathHelper.floor_double(this.posZ);

                if (this.fallTime == 1)
                {
                    if (this.fallTime != 1 || this.worldObj.getBlockId(var1, var2, var3) != this.blockID)
                    {
                        this.setDead();
                        return;
                    }

                    this.worldObj.setBlockWithNotify(var1, var2, var3, 0);
                }

                if (this.isCollidedVertically)
                {
                    this.motionX *= 0.699999988079071D;
                    this.motionZ *= 0.699999988079071D;
                    this.motionY *= +0.5D;
                    if (this.worldObj.getBlockId(var1, var2, var3) != Block.pistonMoving.blockID)
                    {
                        this.setDead();

                        if (!this.isBreakingAnvil && this.worldObj.canPlaceEntityOnSide(this.blockID, var1, var2, var3, true, 1, (Entity)null) && !TestOre.canRiseAbove(this.worldObj, var1, var2 +1, var3) && this.worldObj.setBlockAndMetadataWithNotify(var1, var2, var3, this.blockID, this.metadata))
                        {
                            if (Block.blocksList[this.blockID] instanceof TestOre)
                            {
                                ((TestOre)Block.blocksList[this.blockID]).onFinishRising(this.worldObj, var1, var2, var3, this.metadata);
                            }
                        }
                        else if (this.shouldDropItem && !this.isBreakingAnvil)
                        {
                            this.entityDropItem(new ItemStack(this.blockID, 1, Block.blocksList[this.blockID].damageDropped(this.metadata)), 0.0F);
                        }
                    }
                }
                else if (this.fallTime > 100 && !this.worldObj.isRemote && (var2 < 1 || var2 > 256) || this.fallTime > 600)
                {
                    if (this.shouldDropItem)
                    {
                        this.entityDropItem(new ItemStack(MyMod.testOreID, 1, Block.blocksList[MyMod.testOreID].damageDropped(this.metadata)), 0.0F);
                    }

                    this.setDead();
                }
            }
        }
    }
	@Override
	 protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	    {
	        par1NBTTagCompound.setByte("Tile", (byte)this.blockID);
	        par1NBTTagCompound.setByte("Data", (byte)this.metadata);
	        par1NBTTagCompound.setByte("Time", (byte)this.fallTime);
	        par1NBTTagCompound.setBoolean("DropItem", this.shouldDropItem);
	        par1NBTTagCompound.setBoolean("HurtEntities", this.isAnvil);
	        par1NBTTagCompound.setFloat("FallHurtAmount", this.field_82158_h);
	        par1NBTTagCompound.setInteger("FallHurtMax", this.field_82156_g);
	    }

	    /**
	     * (abstract) Protected helper method to read subclass entity data from NBT.
	     */
	@Override
	    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	    {
	        this.blockID = par1NBTTagCompound.getByte("Tile") & 255;
	        this.metadata = par1NBTTagCompound.getByte("Data") & 255;
	        this.fallTime = par1NBTTagCompound.getByte("Time") & 255;
	        
	        if (par1NBTTagCompound.hasKey("HurtEntities"))
	        {
	            this.isAnvil = par1NBTTagCompound.getBoolean("HurtEntities");
	            this.field_82158_h = par1NBTTagCompound.getFloat("FallHurtAmount");
	            this.field_82156_g = par1NBTTagCompound.getInteger("FallHurtMax");
	        }
	        else if (this.blockID == Block.anvil.blockID)
	        {
	            this.isAnvil = true;
	        }

	        if (par1NBTTagCompound.hasKey("DropItem"))
	        {
	            this.shouldDropItem = par1NBTTagCompound.getBoolean("DropItem");
	        }

	        if (this.blockID == 0)
	        {
	            this.blockID = MyMod.testOre.blockID;
	        }
	    }
		
		@Override
	    @SideOnly(Side.CLIENT)
	    public float getShadowSize()
	    {
	        return 0.0F;
	    }
		
		
	    @SideOnly(Side.CLIENT)
	    public World getWorld()
	    {
	        return this.worldObj;
	    }

	    public void setIsAnvil(boolean par1)
	    {
	        this.isAnvil = par1;
	    }
	    
	    @Override
	    @SideOnly(Side.CLIENT)

	    /**
	     * Return whether this entity should be rendered as on fire.
	     */

	    public boolean canRenderOnFire()
	    {
	        return false;
	    }

	    @Override
	    public void func_85029_a(CrashReportCategory par1CrashReportCategory)
	    {
	        super.func_85029_a(par1CrashReportCategory);
	        par1CrashReportCategory.addCrashSection("Immitating block ID", Integer.valueOf(this.blockID));
	        par1CrashReportCategory.addCrashSection("Immitating block data", Integer.valueOf(this.metadata));
	    }
	
}
