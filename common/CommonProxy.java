package myMod.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;


public class CommonProxy
{
	public static String ITEMS_PNG = "/myMod/common/items.png";
	public static String BLOCK_PNG = "/myMod/common/block.png";
	
	public void registerRenderers() {}
	
	public void registerRenderInformation() {}
	
	public void registerFX() {}
	
	protected static EntityPlayer modPlayer;
	
	public String playerName()
	{
		return "";
	}
	
	private EntityPlayer createNewPlayer(World world)
	{
		EntityPlayer player = new EntityPlayer(world)
		{
			@Override
			public void sendChatToPlayer(String var1)
			{
				
			}
			
			@Override
			public boolean canCommandSenderUseCommand(int var1, String var2)
			{
				return false;
			}
			
			@Override
			public ChunkCoordinates getPlayerCoordinates()
			{
				return null;
			}
		};
		player.username = "[MyMod]";
		return player;
	}
	
	private EntityPlayer createNewPlayer(World world, int x, int y, int z)
	{
		EntityPlayer player = new EntityPlayer(world)
		{
			@Override
			public void sendChatToPlayer(String var1)
			{
				
			}
			
			@Override
			public boolean canCommandSenderUseCommand(int var1, String var2)
			{
				return false;
			}
			
			@Override
			public ChunkCoordinates getPlayerCoordinates()
			{
				return null;
			}
		};
		player.username = "[MyMod]";
		player.posX = x;
		player.posY = y;
		player.posZ = z;
		return player;
	}
	
	public EntityPlayer getModPlayer(World world)
	{
		if(CommonProxy.modPlayer == null)
			CommonProxy.modPlayer = createNewPlayer(world);
		else
			CommonProxy.modPlayer.worldObj = world;
		return CommonProxy.modPlayer;
	}
	
	public EntityPlayer getModPlayer(World world, int x, int y, int z)
	{
		if(CommonProxy.modPlayer == null)
			CommonProxy.modPlayer = createNewPlayer(world, x, y, z);
		else
		{
			CommonProxy.modPlayer.worldObj = world;
			CommonProxy.modPlayer.posX = x;
			CommonProxy.modPlayer.posY = y;
			CommonProxy.modPlayer.posZ = z;
		}
		return CommonProxy.modPlayer;
	}
}
