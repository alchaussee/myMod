package myMod.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class TestBlock extends Block
{
	public TestBlock (int id, int texture, Material material)
	{
		super(id, texture, material);
	}
	
	@Override
	public String getTextureFile ()
	{
		return CommonProxy.BLOCK_PNG;
	}

}
