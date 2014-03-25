package myMod.common;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraftforge.common.ForgeDirection;

public class EnderiteOre extends Block
{
	private boolean cond2 = false;
	
	public EnderiteOre (int id, int texture)
	{
		super(id, texture, Material.rock);
    } 
	
	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer par5EntityPlayer)
	{
		this.effects(10, world, i,j,k);
		if(cond2)
		{
			Random random = new Random();
			int chance = random.nextInt(3);
			if(chance != 1)
			{
				int randX = random.nextInt(5)-2;
				int randY = random.nextInt(5)-2;
				int randZ = random.nextInt(5)-2;
		
				if(world.getBlockId(i+randX, j+randY, k+randZ) == Block.stone.blockID)
				{
					this.effects(20, world, i,j,k);
					
					world.setBlockWithNotify(i,j,k,Block.stone.blockID);
					world.setBlockWithNotify(i+randX, j+randY, k+randZ, MyMod.enderiteOre.blockID);
				}
				
			}
			cond2 = false;
		}
		else
			cond2 = true;
	}
	
	private void effects(int num, World world, int x, int y, int z)
	{
		for(int i=0;i<num;i++)
		{
			sparkle(world, x,y,z);
		}
	}
	
    private void sparkle(World par1World, int par2, int par3, int par4)
    {
        Random var5 = par1World.rand;
        double var6 = 0.0625D;

        for (int var8 = 0; var8 < 6; ++var8)
        {
            double var9 = (double)((float)par2 + var5.nextFloat());
            double var11 = (double)((float)par3 + var5.nextFloat());
            double var13 = (double)((float)par4 + var5.nextFloat());

            if (var8 == 0 && !par1World.isBlockOpaqueCube(par2, par3 + 1, par4))
            {
                var11 = (double)(par3 + 1) + var6;
            }

            if (var8 == 1 && !par1World.isBlockOpaqueCube(par2, par3 - 1, par4))
            {
                var11 = (double)(par3 + 0) - var6;
            }

            if (var8 == 2 && !par1World.isBlockOpaqueCube(par2, par3, par4 + 1))
            {
                var13 = (double)(par4 + 1) + var6;
            }

            if (var8 == 3 && !par1World.isBlockOpaqueCube(par2, par3, par4 - 1))
            {
                var13 = (double)(par4 + 0) - var6;
            }

            if (var8 == 4 && !par1World.isBlockOpaqueCube(par2 + 1, par3, par4))
            {
                var9 = (double)(par2 + 1) + var6;
            }

            if (var8 == 5 && !par1World.isBlockOpaqueCube(par2 - 1, par3, par4))
            {
                var9 = (double)(par2 + 0) - var6;
            }

            if (var9 < (double)par2 || var9 > (double)(par2 + 1) || var11 < 0.0D || var11 > (double)(par3 + 1) || var13 < (double)par4 || var13 > (double)(par4 + 1))
            {
            	Random random = new Random();
            	double VelX = random.nextDouble();
            	double VelY = random.nextDouble();
            	double VelZ = random.nextDouble();
            	int signX = random.nextInt(2);
        		int signY = random.nextInt(2);
        		int signZ = random.nextInt(2);
        		if (signX == 1)
        		{
        			VelX = VelX*(-1);
        		}
        		if (signY == 1)
        		{
        			VelY = VelY*(-1);
        		}
        		if (signZ == 1)
        		{
        			VelZ = VelZ*(-1);
        		}
                par1World.spawnParticle("portal", var9, var11, var13, VelX, VelY, VelZ);
            }
        }
    }
	@Override
	public String getTextureFile ()
	{
		return CommonProxy.BLOCK_PNG;
	}
	
	public int idDropped(int par1, Random random, int zero)
	{
		return MyMod.enderiteChunk.itemID;
	}
	
	public int quantityDropped(Random par1Random)
    {
        return par1Random.nextInt(2)+1;
    }
	
	
}
