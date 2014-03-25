package myMod.common;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SkewerItem extends Item
{
	public SkewerItem(int par1)
	{
		super(par1);
	}
	
	public String getTextureFile()
	{
		return CommonProxy.ITEMS_PNG;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		EntitySkewer var8 = new EntitySkewer(par2World, par3EntityPlayer, 2.0F);
		var8.canBePickedUp = 0;
		var8.setDamage(0.5F);
		var8.arrowShake = 0;
		var8.rotationPitch = 0;
		var8.rotationYaw = 0;
		
		boolean var5 = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

        if (var5 || par3EntityPlayer.inventory.hasItem(MyMod.skewer.itemID))
        {
        	if (var5)
            {
                var8.canBePickedUp = 2;
            }
            else
            {
            	par3EntityPlayer.inventory.consumeInventoryItem(MyMod.skewer.itemID);
            }
	
	        if (!par2World.isRemote)
	        {
	            par2World.spawnEntityInWorld(var8);
	        }
        }
        return par1ItemStack;
    }
}
