package myMod.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class InterfaceStructureBlock extends Container {

        protected TileEntityStructureBlock tileEntity;

        public InterfaceStructureBlock (InventoryPlayer inventoryPlayer, TileEntityStructureBlock te)
        {
                tileEntity = te;
        }

        @Override
        public boolean canInteractWith(EntityPlayer player) {
                return tileEntity.isUseableByPlayer(player);
        }
}
