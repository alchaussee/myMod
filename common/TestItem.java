package myMod.common;

import net.minecraft.item.Item;

public class TestItem extends Item {

	public TestItem(int id) 
	{
		super(id);
	}

	public String getTextureFile()
	{
		return CommonProxy.ITEMS_PNG;
	}
	
}
