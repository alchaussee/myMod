package myMod.common;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import myMod.common.EntityTestOre;

public class TestOre extends Block
{

    public static boolean riseInstantly = false;
	
	public TestOre(int id, int texture) {
		super(id, texture, Material.rock);
	}
	
    public TestOre(int par1, int par2, Material par3Material)
    {
        super(par1, par2, par3Material);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate());
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            this.tryToRise(par1World, par2, par3, par4);
        }
    }

    /**
     * If there is space to fall below will start this block falling
     */

    private void tryToRise(World par1World, int par2, int par3, int par4)
    {
        if (canRiseAbove(par1World, par2, par3 + 1, par4) && par3 >= 0)
        {
            byte var8 = 32;

            if (!riseInstantly && par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
            {
                    EntityTestOre var9 = new EntityTestOre(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), this.blockID, par1World.getBlockMetadata(par2, par3, par4));
                    this.onStartRising(var9);
                    par1World.spawnEntityInWorld(var9);
                
            }
            else
            {
                par1World.setBlockWithNotify(par2, par3, par4, 0);

                while (canRiseAbove(par1World, par2, par3 + 1, par4) && par3 > 0)
                {
                    ++par3;
                }

                if (par3 > 0)
                {
                    par1World.setBlockWithNotify(par2, par3, par4, MyMod.testOre.blockID);
                }
            }
        }
    }

    /**
     * Called when the falling block entity for this block is created
     */

    protected void onStartRising(EntityTestOre par1EntityTestOre) {}

    /**
     * How many world ticks before ticking
     */
    @Override
    public int tickRate()
    {
        return 5;
    }

    /**
     * Checks to see if the sand can fall into the block below it
     */

    public static boolean canRiseAbove(World par0World, int par1, int par2, int par3)
    {
        int var4 = par0World.getBlockId(par1, par2, par3);

        if (var4 == 0)
        {
            return true;
        }
        else if (var4 == Block.fire.blockID)
        {
            return true;
        }
        else
        {
            Material var5 = Block.blocksList[var4].blockMaterial;
            return var5 == Material.water ? true : var5 == Material.lava;
        }
    }

    /**
     * Called when the falling block entity for this block hits the ground and turns back into a block
     */
 
    public void onFinishRising(World par1World, int par2, int par3, int par4, int par5) {}
	
	
	@Override
	public String getTextureFile()
	{
		return CommonProxy.BLOCK_PNG;
	}
	
	@Override
	public int idDropped(int par1, Random random, int zero)
	{
		return MyMod.testIngot.itemID;
	}
	
}