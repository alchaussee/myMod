package myMod.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class StructureBlock extends BlockContainer
{
    private int spawnHeight = 100;
    public boolean hasSpawned;
    private List<Integer> blockCoordArray = new ArrayList<Integer>();
    private boolean cond = true;
	private Perlin q = new Perlin();
	private int HEIGHT = 800;
	private int WIDTH = 600;
	private int[][] hValue = new int[HEIGHT][WIDTH];
	private int mini = 255;
	private boolean flagIsland = true;
	public boolean flagGen = false;
	private int X;
	private int Y;
	
	public StructureBlock (int id, int texture, boolean par3)
	{
		super(id, texture, Material.rock);
        this.hasSpawned = par3;
        flagGen = false;
    } 
	
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
		flagGen = false;
        return par9;
    }
	    
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if(this.blockID == 503)
		{
	        TileEntity tileEntity = world.getBlockTileEntity(i, j, k);

	        if (tileEntity == null || entityplayer.isSneaking())
	            return false;
	        else if(((TileEntityStructureBlock)tileEntity).hasSpawned == 2)
	        	createIsland(world, entityplayer, i, j, k, ((TileEntityStructureBlock)tileEntity).text);
	        else
	        	entityplayer.openGui(MyMod.instance, 1, world, i, j, k);
	        return true;
		}
		if(this.blockID == 504)
		{
			teleport(world, i, j, k, entityplayer);
		}
		return false;
	}

	public boolean createIsland(World world, EntityPlayer entityplayer, int i, int j, int k, String spawnHeightS)
	{        
		int Grass = Block.grass.blockID;
		int Stone = Block.stone.blockID;
		try
		{
			if(spawnHeightS.contentEquals(""))
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
			return false;
		}
		else if(spawnHeight>250)
		{
			return false;
		}
		else if(j>90)
		{
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
						if(world.getBlockId(s, r, t) != 0)
						{
							return false;
						}
					}
				}
			}
			entityplayer.sendChatToPlayer("Generating Island at "+spawnHeight);
			spawnHeight-=19;
		}

		if (((TileEntityStructureBlock)world.getBlockTileEntity(i, j, k)).hasSpawned == 2)
		{
			while(!flagGen)
			{
				this.gen(world, i, j, k);
			}
			if(flagGen)
			{
				for(int w=0;w<50;w++)
				{
					if (world.isAirBlock(i, spawnHeight+w, k))
					{
						world.setBlock(i,spawnHeight+w-1,k, MyMod.structureBlockBase.blockID);
						world.setBlock(i, j, k, MyMod.structureBlockBase.blockID);
						break;
					}
				}
			}
			entityplayer.sendChatToPlayer("Island Manifested!");
			spawnHeight+=19;
			return true;
		}
		return false;
	}
/*
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		if(entityplayer.isSneaking())
			return;
		if(this.blockID == 503)
		{
			for(int w=0;w<100;w++)
			{
				if (world.getBlockId(i, spawnHeight+w, k) == 503)
				{
					entityplayer.setPositionAndUpdate((i+0.5), spawnHeight+w, (k+0.5));
					entityplayer.sendChatToPlayer("Going Up!");
					break;
				}
			}
		}
		else
		{
			for(int w=200;w>0;w--)
			{
				if (world.getBlockId(i, j-w, k) == 503)
				{
					entityplayer.setPositionAndUpdate((i+1.5), j-w+1, (k+0.5));
					entityplayer.sendChatToPlayer("Going Down!");
					break;
				}
			}
		}
	}
*/
	public void teleport(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		if(entityplayer.isSneaking())
			return;

		for(int w=1;w<200;w++)
		{
			if (world.getBlockId(i, j+w, k) == 504)
			{
				entityplayer.setPositionAndUpdate((i+0.5), j+w+1, (k+0.5));
				entityplayer.sendChatToPlayer("Going Up!");
				break;
			}
		}
		for(int w=200;w>1;w--)
		{
			if (world.getBlockId(i, j-w, k) == 504)
			{
				entityplayer.setPositionAndUpdate((i+1.5), j-w, (k+0.5));
				entityplayer.sendChatToPlayer("Going Down!");
				break;
			}
		}
	}
	
	private void gen(World world, int i, int j, int k)
	{
		double[][] p = q.GeneratePerlinNoise(q.GenerateWhiteNoise(HEIGHT,WIDTH), 7, 0.5); 
		double[][] s = q.GeneratePerlinNoise(q.GenerateWhiteNoise(HEIGHT,WIDTH), 7, 0.5);
		double[][] r = q.GeneratePerlinNoise(q.GenerateWhiteNoise(HEIGHT,WIDTH), 7, 0.5);
		
		for(int x=0;x<HEIGHT;x++)
		{
			for(int y=0;y<WIDTH;y++)
			{
				int h =0;
				int h1=0;
				int h2=0;
				int h3=0;
				int h4=0;
				try{
					h = (int)Math.round(p[x][y]*255);
					h1 = (int)Math.round(r[x+1][y]*255);
					h2 = (int)Math.round(s[x-1][y]*255);
					h3 = (int)Math.round(p[x][y+1]*255);
					h4 = (int)Math.round(s[x][y-1]*255);
				}
				catch(Exception e) {}

				if(h<=200)
					h=255;
				if(h1>150&&h2>150&&h3>150&&h4>150)
					h=(h1+h2+h3+h4)/5;
				if(h>=200)
					h=255;
				h=255-h;
				if(h==0)
					h=255;
				hValue[x][y] = h;
			}
		}

		int[][] temp = hValue;
		int[][] island = findIsland3(temp, 40);
		
		if(flagIsland)
		{
			int minimum = 255;
			int maximum = 0;
			for(int I=0;I<island.length;I++)
			{
				for(int J=0;J<island[0].length;J++)
				{
					if(island[I][J]!=255)
					{
						world.setBlock(i+(I-island.length/2), (spawnHeight-20)+(island[I][J])/3, k+(J-island[0].length/2), Block.stone.blockID);
						
						if(minimum>island[I][J])
						{
							minimum = island[I][J];
							X = i+(I-island.length/2);
							Y = k+(J-island[0].length/2);
						}
						if(maximum<island[I][J])
						{
							maximum = island[I][J];
						}
					}
				}
			}
			for(int I=0;I<island.length;I++)
			{
				for(int J=0;J<island[0].length;J++)
				{
					if(island[I][J]!=255)
					{
						for(int K=(spawnHeight-20)+(island[I][J])/3;K<(spawnHeight-20)+(maximum)/3-4;K++)
						{
							if(K==(spawnHeight-20)+(maximum)/3-5)
								world.setBlock(i+(I-island.length/2), K, k+(J-island[0].length/2), Block.grass.blockID);
							else
								world.setBlock(i+(I-island.length/2), K, k+(J-island[0].length/2), Block.stone.blockID);
						}
					}
				}
			}
			flagGen = true;
		}
		else
			flagGen = false;
	}
	
	private int[][] findIsland3(int[][] arr, int size)
	{
		int count = 0;
		int count2 = 0;
		int[][] arr2 = new int[size*2][size*2];
		int min = 255;
		int h=0;
		for(int i =size;i<HEIGHT-size;i++)
		{
			for(int j=size;j<WIDTH-size;j++)
			{
				h = arr[i][j];
				if(h!=255 && min>h)
				{
					min = h;
				}
			}
		}
		mini = min;
		for(int i =0;i<HEIGHT;i++)
		{
			for(int j=0;j<WIDTH;j++)
			{
				count = 0;
				count2 = 0;
				if(arr[i][j]!=255 && arr[i][j]<=min+6)
				{
					for(int x=i-size;x<i+size+1;x++)
					{
						for(int y=j-size;y<j+size+1;y++)
						{
							if(x==i-size || x==i+size)
							{
								if(x>=arr.length || x<0 || y>=arr[0].length || y<0){}
								else if(arr[x][y] == 255)
								{
									count++;
								}
							}
							if(y==j-size || y==j+size)
							{
								if(y>=arr[0].length || y<0 || x>=arr.length || x<0){}
								else if(arr[x][y] == 255)
								{
									count2++;
								}
							}
						}
					}
					int k = 0;
					int g = 0;
					if(count >= size*4+2 && count2 >= size*4+2)
					{
						for(int x=i-size;x<i+size;x++)
						{
							for(int y=j-size;y<j+size;y++)
							{
								if(g<arr2.length && k<arr2[0].length && x<arr.length && y<arr[0].length && x>0 && y>0)
								{
									arr2[g][k] = arr[x][y];
								}
								k++;
							}
							g++;
							k=0;	
						}
						return arr2;
					}
				}
			}
		}
		flagIsland = false;
		return arr2;
	}

    @Override
    public TileEntity createNewTileEntity(World world) 
    {
    	return new TileEntityStructureBlock(this);
    }

    public void breakBlock(World world, int x, int y, int z, int i, int j)
    {
    	world.removeBlockTileEntity(x, y, z);
    }
	
    
    @SideOnly(Side.CLIENT)

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par5 == 0)
        {
            return this.blockIndexInTexture + 1;
        }
        else if (par5 == 1)
        {
            return this.blockIndexInTexture + 1;
        }
        return 3;
    }
    
    public int getBlockTextureFromSide(int par1)
    {
        return par1 == 1 ? this.blockIndexInTexture+1 : (par1 == 3 ? this.blockIndexInTexture  : (par1 == 4 ? this.blockIndexInTexture : this.blockIndexInTexture+1));
    }
	
	@Override
	public String getTextureFile ()
	{
		return CommonProxy.BLOCK_PNG;
	}

}