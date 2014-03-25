package myMod.common;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class ShellSandWorldGenerator implements IWorldGenerator
{
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		if (world.provider.isSurfaceWorld() && (world.getBiomeGenForCoords(chunkX,chunkZ).biomeName.equals("Beach") || world.getBiomeGenForCoords(chunkX,chunkZ).biomeName.equals("Ocean") || world.getBiomeGenForCoords(chunkX,chunkZ).biomeName.equals("River") || world.getBiomeGenForCoords(chunkX,chunkZ).biomeName.equals("Swampland")))
		{
			for (int i = 0; i < 128; i++)
			{
				int x = chunkX + random.nextInt(16);
				int y = random.nextInt(30)+50;
				int z =chunkZ + random.nextInt(16);
				if (world.getBlockId(x,y,z) == Block.sand.blockID)
				{
					world.setBlock(x, y, z, MyMod.shellSandBlock.blockID);
					System.out.println("Gen");
				}
			}
		}
	}
}
