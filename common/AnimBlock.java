package myMod.common;

import myMod.common.client.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AnimBlock extends Block
{
	public AnimBlock(int par1, int par2, Material par3Material) 
	{
		super(par1, par2, par3Material);
	}

	public String getTextureFile()
	{
		return "/myMod/common/block.png";
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
}
