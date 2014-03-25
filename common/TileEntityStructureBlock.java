package myMod.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.server.FMLServerHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraftforge.common.ForgeChunkManager;

public class TileEntityStructureBlock extends TileEntity 
{
	StructureBlock block;
	protected int hasSpawned;
	protected String text = "";
	private int spawnHeight = 100;
	private Perlin q = new Perlin();
	private int HEIGHT = 800;
	private int WIDTH = 600;
	private int[][] hValue = new int[HEIGHT][WIDTH];
	private int mini = 255;
	private boolean flagIsland = true;
	public boolean flagGen = false;
	private int X;
	private int Y;
	
    public TileEntityStructureBlock(StructureBlock block)
    {
    	this.block = block;
    }
    
    public TileEntityStructureBlock()
    {
    }

    public boolean isUseableByPlayer(EntityPlayer player) 
    {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tagCompound) 
    {
        super.readFromNBT(tagCompound);
        hasSpawned = tagCompound.getInteger("hasSpawned");
        text = tagCompound.getString("text");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tagCompound) 
    {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("hasSpawned", hasSpawned);
        tagCompound.setString("text", text);
    }

    @Override
    public Packet getDescriptionPacket()
    {
    	NBTTagCompound tileTag = new NBTTagCompound();
    	this.writeToNBT(tileTag);
    	return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, tileTag);
    }
    
    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
    {
    	this.readFromNBT(pkt.customParam1);
    }
    
    public String getInvName() 
    {
    	return "myMod.tileEntityStructureBlock";
    }

    public boolean canCreateIsland(World world, EntityPlayer entityplayer, int i, int j, int k, String spawnHeightS)
	{        
		int Grass = Block.grass.blockID;
		int Stone = Block.stone.blockID;

		try
		{
			if(spawnHeightS.contentEquals("") || spawnHeightS.contentEquals("Height"))
				this.spawnHeight = 100;
			else
				this.spawnHeight = Integer.parseInt(spawnHeightS);
		}
		catch(Exception e)
		{
			return false;
		}
		if(spawnHeight<100)
		{
			entityplayer.sendChatToPlayer("Height too low using 100");
			return false;
		}
		else if(spawnHeight>250)
		{
			entityplayer.sendChatToPlayer("Height too high using 100");
			return false;
		}
		else if(j>90)
		{
			entityplayer.sendChatToPlayer("Island cannot be created here");
			return false;
		}
		else
		{
			for(int r = spawnHeight-30;r<spawnHeight;r = r+3)
			{
				for(int s=i-15; s<i+16; s = s+3)
				{
					for(int t=k-15; t<k+16; t = t+3)
					{
						if(worldObj.getBlockId(s, r, t) != 0)
						{
							entityplayer.sendChatToPlayer("Island cannot be created here");
							return false;
						}
					}
				}
			}
		}
		return true;
	}
}
