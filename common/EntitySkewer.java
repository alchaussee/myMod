package myMod.common;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntitySkewer extends EntityArrow
{
	public EntitySkewer(World par1World) 
	{
		super(par1World);
	}
	
	public EntitySkewer(World par1World, double par2, double par4, double par6)
    {
		super(par1World, par2, par4, par6);
    }

	public EntitySkewer(World par1World, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving, float par4, float par5)
    {
		super(par1World, par2EntityLiving, par3EntityLiving, par4, par5);
    }
	
	public EntitySkewer(World par1World, EntityLiving par2EntityLiving, float par3)
    {
		super(par1World, par2EntityLiving, par3);
    }
	
	@Override
	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
    {
        if (!this.worldObj.isRemote && this.onGround && this.arrowShake <= 0)
        {
            boolean var2 = this.canBePickedUp == 1 || this.canBePickedUp == 2 && par1EntityPlayer.capabilities.isCreativeMode;

            if (this.canBePickedUp == 1 && !par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(MyMod.skewer, 1)))
            {
                var2 = false;
            }

            if (var2)
            {
                this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                par1EntityPlayer.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }
	
}
