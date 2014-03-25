package myMod.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class kabobFood extends ItemFood
{

	public kabobFood(int par1, int par2, float par3, boolean par4) 
	{
		super(par1, par2, par3, par4);
		this.setAlwaysEdible();
	}
	
	@Override
	public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        --par1ItemStack.stackSize;
        par3EntityPlayer.getFoodStats().addStats(this);
        par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
        this.func_77849_c(par1ItemStack, par2World, par3EntityPlayer);
        if (par1ItemStack.stackSize <= 0)
        {
            return new ItemStack(MyMod.skewer);
        }

        if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(MyMod.skewer)))
        {
            par3EntityPlayer.dropPlayerItem(new ItemStack(MyMod.skewer.itemID, 1, 0));
        }
        return par1ItemStack;
    }
}
