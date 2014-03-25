package myMod.common;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LaputaAmuletItem extends Item
{
	private boolean allowFlight = false;
	
	public LaputaAmuletItem(int id) 
	{
		super(id);
		this.setMaxDamage(2000);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer player)
	{
		if(!world.isRemote)
		{
			if(!allowFlight)
			{
				System.out.println("Flight");
				player.capabilities.allowFlying = true;
				allowFlight = true;
			}
			else
			{
				System.out.println("No Flight");
				player.capabilities.allowFlying = false;
				allowFlight = false;
			}
			player.sendPlayerAbilities();
		}
		return par1ItemStack;
    }
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
		if(allowFlight)
		{
			par1ItemStack.damageItem(1, (EntityLiving)par3Entity);
		}
	}
	
	public String getTextureFile()
	{
		return CommonProxy.ITEMS_PNG;
	}
	
}
