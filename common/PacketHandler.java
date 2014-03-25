package myMod.common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) 
	{
		if(packet.channel.equals("Structure"))
			handlePacket(packet, player);
		
	}
	
	private void handlePacket(Packet250CustomPayload packet, Player player)
	{
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		int x;
		int y;
		int z;
		int spawn;
		String text;
		
		try
		{
			x = inputStream.readInt();
			y = inputStream.readInt();
			z = inputStream.readInt();
			spawn = inputStream.readInt();
			text = inputStream.readUTF();
			
			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z) ;
			if(te instanceof TileEntityStructureBlock)
			{
				((TileEntityStructureBlock)te).hasSpawned = spawn;
				((TileEntityStructureBlock)te).text = text;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return;
		}
		
	}
}
