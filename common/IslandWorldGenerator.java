package myMod.common;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class IslandWorldGenerator implements IWorldGenerator
{
	private int HEIGHT = 800;
	private int WIDTH = 600;
	private Perlin q = new Perlin();
	private int[][] hValue = new int[HEIGHT][WIDTH];
	private boolean flagIsland = true;
	private boolean flagGen = false;
	private int spawnHeight = 120;
	private int mini = 255;
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		if (world.provider.isSurfaceWorld())
		{
			if(random.nextInt(256) == 1)
			{
				for (int i = 0; i < 1; i++)
				{
					int x = chunkX*16 + random.nextInt(16);
					int y = random.nextInt(32)+120;
					int z = chunkZ*16 + random.nextInt(16);
			
					if (world.getBlockId(x,y,z) == 0 && !world.getBiomeGenForCoords(x,z).biomeName.equals("Desert") )
					{
						int[][] island = gen(world, x,y,z);
						
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
										world.setBlockWithNotify(x+(I-island.length), (spawnHeight-20)+(island[I][J])/3, z+(J-island[0].length), Block.stone.blockID);
										if(minimum>island[I][J])
											minimum = island[I][J];
										if(maximum<island[I][J])
											maximum = island[I][J];
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
												world.setBlockWithNotify(x+(I-island.length), K, z+(J-island[0].length), Block.grass.blockID);
											else if(random.nextInt(150) == 1)
											{
												world.setBlockWithNotify(x+(I-island.length), K, z+(J-island[0].length), MyMod.testOre.blockID);
											}
											else
												world.setBlockWithNotify(x+(I-island.length), K, z+(J-island[0].length), Block.stone.blockID);
											
										}
									}
								}
							}
						}
					}
				}
			}
			flagIsland = true;
		}
	}
	
	private int[][] gen(World world, int i, int j, int k)
	{

		double[][] p = q.GeneratePerlinNoise(q.GenerateWhiteNoise(HEIGHT,WIDTH), 5, 0.5); 
		double[][] s = q.GeneratePerlinNoise(q.GenerateWhiteNoise(HEIGHT,WIDTH), 5, 0.5);
		double[][] r = q.GeneratePerlinNoise(q.GenerateWhiteNoise(HEIGHT,WIDTH), 5, 0.5);
		
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
		int[][] island = findIsland3(temp, 20);
		return island;
		/*
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
						world.setBlockWithNotify(i+(I-island.length), (spawnHeight-20)+(island[I][J])/3, k+(J-island[0].length), Block.stone.blockID);
						if(minimum>island[I][J])
							minimum = island[I][J];
						if(maximum<island[I][J])
							maximum = island[I][J];
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
								world.setBlockWithNotify(i+(I-island.length), K, k+(J-island[0].length), Block.grass.blockID);
							else
								world.setBlockWithNotify(i+(I-island.length), K, k+(J-island[0].length), Block.stone.blockID);
							
						}
					}
				}
			}
			flagGen = true;
		}
		else
			flagGen = false;
			*/
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
}
