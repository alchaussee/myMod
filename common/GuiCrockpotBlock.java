package myMod.common;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiCrockpotBlock extends GuiContainer
{
	private TileEntityCrockpotBlock te;
	private EntityPlayer player;
	
    public GuiCrockpotBlock(InventoryPlayer inventoryPlayer, TileEntityCrockpotBlock tileEntity) 
    {
            super(new InterfaceCrockpotBlock(inventoryPlayer, tileEntity));
        	te = tileEntity;
        	player = inventoryPlayer.player;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2)
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal("Crock Pot"), 60, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2+25, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) 
    {
    	int var4 = this.mc.renderEngine.getTexture("/myMod/common/crockpotblock2.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        int var7;

        if (this.te.isBurning())
        {
            var7 = this.te.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(var5 + 81, var6 + 79 - var7, 176, 12 - var7, 14, var7 + 2);
        }

        var7 = this.te.getCookProgressScaled(24);
        //this.drawTexturedModalRect(var5 + 79, var6 + 34, 176, 14, var7 + 1, 16);
    }

    
}
