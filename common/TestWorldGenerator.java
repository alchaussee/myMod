package myMod.common;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class TestWorldGenerator implements IWorldGenerator
{
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		if (world.provider.isSurfaceWorld())
		{
			for (int i = 0; i < 16; i++)
			{
				int x = chunkX + random.nextInt(16);
				int y = random.nextInt(32);
				int z =chunkZ + random.nextInt(16);
				if (world.getBlockId(x,y,z) == 1)
				{
					world.setBlock(x, y, z, MyMod.testOre.blockID);
				}
			}
		}
	}
}

