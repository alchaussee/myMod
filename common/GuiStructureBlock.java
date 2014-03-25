package myMod.common;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;

public class GuiStructureBlock extends GuiContainer 
{
		TileEntityStructureBlock te;
		EntityPlayer player;
		GuiTextField textField;
		String oldText = "";
		
        public GuiStructureBlock(InventoryPlayer inventoryPlayer, TileEntityStructureBlock tileEntity) 
        {
                super(new InterfaceStructureBlock(inventoryPlayer, tileEntity));
            	te = tileEntity;
            	player = inventoryPlayer.player;
        }

        @Override
        protected void drawGuiContainerForegroundLayer(int param1, int param2)
        {
                //draw text and stuff here
                //the parameters for drawString are: string, x, y, color
                fontRenderer.drawString("Island", this.width/2-181/4, this.height/2-58/3, 4210752);
                this.textField.drawTextBox();
                //draws "Inventory" or your regional equivalent
                //fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) 
        {
                //draw your Gui here, only thing you need to change is the path
                int texture = mc.renderEngine.getTexture("/myMod/common/structureblock.png");
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.mc.renderEngine.bindTexture(texture);
                int x = width / 2-256/2;
                int y = height / 2-256/2;
                this.drawTexturedModalRect(x, y, 0, 0, 256, 256);
        }
        
        @Override
        public void initGui()
        {
        	Keyboard.enableRepeatEvents(false);
        	this.controlList.clear();
        	int posX = this.width / 2-50;
        	int posY = this.height / 2-10;
        	textField = new GuiTextField(fontRenderer, posX, posY+40, 100, 20);
        	if(te.text.contentEquals(""))
        		textField.setText("Height");
        	else
        		textField.setText(te.text);
        	this.textField.setFocused(false);
            this.textField.setMaxStringLength(32);
            this.controlList.add(new GuiButton(0, posX, posY, 100, 20, "Generate Island"));
            if(te.hasSpawned == 0)
            	te.hasSpawned = 1;
        	if(te.hasSpawned == 1)
        		((GuiButton)this.controlList.get(0)).enabled = true;
        	else
        	{
        		((GuiButton)this.controlList.get(0)).enabled = false;
        		this.textField.setVisible(false);
        	}
        }
        
        @Override
        public void actionPerformed(GuiButton button)
        {
        	switch(button.id)
        	{
        		case(0):
        		{
        			if(te.canCreateIsland(te.worldObj, player, te.xCoord, te.yCoord, te.zCoord, textField.getText()))
        			{
        				oldText = textField.getText();
        				te.hasSpawned = 2;
        				this.textField.setVisible(false);
        				((GuiButton)this.controlList.get(0)).enabled = false;
        			}
        			else
        				te.hasSpawned = 1;
        			break;
        		}
        	}
			ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
			DataOutputStream outputStream = new DataOutputStream(bos);
			try
			{
				outputStream.writeInt(te.xCoord);
				outputStream.writeInt(te.yCoord);
				outputStream.writeInt(te.zCoord);
				outputStream.writeInt(te.hasSpawned);
				outputStream.writeUTF(oldText);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Fail");
			}
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "Structure";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			PacketDispatcher.sendPacketToServer(packet);
        }
        
        @Override
        public void updateScreen()
        {
            this.textField.updateCursorCounter();
        }
        
        @Override
        protected void keyTyped(char var1, int var2)
        {
            this.textField.textboxKeyTyped(var1, var2);
            
            if(var1 == 27)
            {
            	this.mc.displayGuiScreen((GuiScreen)null);
            }
            if(var1 == this.mc.gameSettings.keyBindInventory.keyCode)
            {
            	this.mc.displayGuiScreen((GuiScreen)null);
            }
        }
        
        @Override
        protected void mouseClicked(int var1, int var2, int var3)
        {
            super.mouseClicked(var1, var2, var3);
            this.textField.mouseClicked(var1, var2, var3);
            if(textField.getText().contentEquals("Height"))
            	textField.setText("");
        }

}


