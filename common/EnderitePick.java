package myMod.common;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class EnderitePick extends ItemTool
{
    /** an array of the blocks this pickaxe is effective against */
    public static final Block[] blocksEffectiveAgainst = new Block[] {Block.cobblestone, Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockSteel, Block.oreCoal, Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstone, Block.oreRedstoneGlowing, Block.rail, Block.railDetector, Block.railPowered};

    public EnderitePick(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1, 2, par2EnumToolMaterial, blocksEffectiveAgainst);
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canHarvestBlock(Block par1Block)
    {
        return par1Block == Block.obsidian ? this.toolMaterial.getHarvestLevel() == 3 : (par1Block != Block.blockDiamond && par1Block != Block.oreDiamond ? (par1Block != Block.oreEmerald && par1Block != Block.blockEmerald ? (par1Block != Block.blockGold && par1Block != Block.oreGold ? (par1Block != Block.blockSteel && par1Block != Block.oreIron ? (par1Block != Block.blockLapis && par1Block != Block.oreLapis ? (par1Block != Block.oreRedstone && par1Block != Block.oreRedstoneGlowing ? (par1Block.blockMaterial == Material.rock ? true : (par1Block.blockMaterial == Material.iron ? true : par1Block.blockMaterial == Material.anvil)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2);
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return par2Block != null && (par2Block.blockMaterial == Material.iron || par2Block.blockMaterial == Material.anvil || par2Block.blockMaterial == Material.rock) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(par1ItemStack, par2Block);
    }
    
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        int var11 = world.getBlockId(par4, par5, par6);

        if (var11 != Block.stone.blockID)
        {
            return false;
        }
        else
        {
        	for (int a = par4-4; a < par4+4; a++)
        	{
        		for (int b = par5-4; b < par5+4; b++)
        		{
        			for (int c = par6-4; c < par6+4; c++)
        			{
        				if (world.getBlockId(a, b, c)==Block.oreCoal.blockID || world.getBlockId(a, b, c)==Block.oreDiamond.blockID ||
        						world.getBlockId(a, b, c)==Block.oreIron.blockID || world.getBlockId(a, b, c)==Block.oreRedstone.blockID || world.getBlockId(a, b, c)==Block.oreEmerald.blockID
        						|| world.getBlockId(a, b, c)==Block.oreLapis.blockID || world.getBlockId(a, b, c)==MyMod.testOre.blockID || world.getBlockId(a, b, c)==MyMod.enderiteOre.blockID)
        				{
        					int var13 = world.getBlockId(a, b, c);
        					world.setBlockWithNotify(a, b, c, Block.stone.blockID);
        					world.setBlockWithNotify(par4, par5, par6, var13);
        	                par1ItemStack.damageItem(20, par2EntityPlayer);
        	        		for (int r=0;r<15;r++)
        	        		{
        	        			this.sparkle(world, par4, par5, par6);
        	        		}
        					break;
        				}
        			}
        		}

        	}
        	return true;
        }
    }
    
    private void sparkle(World par1World, int par2, int par3, int par4)
    {
	    Random var5 = par1World.rand;
	    double var6 = 0.0625D;
	
	    for (int var8 = 0; var8 < 50; ++var8)
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

	public String getTextureFile()
	{
		return CommonProxy.ITEMS_PNG;
	}
}


