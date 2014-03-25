package myMod.common;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CoreItem extends Item
{
		public CoreItem(int id) 
		{
			super(id);
		}

		public String getTextureFile()
		{
			return CommonProxy.ITEMS_PNG;
		}
		
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
		{
		par3List.add("It definately does something...");
		par3List.add("what that something is,");
		par3List.add("you aren't quite sure");
		}
		
	    @SideOnly(Side.CLIENT)
	    public boolean hasEffect(ItemStack par1ItemStack)
	    {
	        return true;
	    }

	
}
