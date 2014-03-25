package myMod.common.client;

import java.io.IOException;

import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.client.registry.RenderingRegistry;
import myMod.common.AnimBlockFX;
import myMod.common.CommonProxy;
import myMod.common.EntitySkewer;
import myMod.common.EntityTestOre;
import myMod.common.GuiStructureBlock;
import myMod.common.RenderSkewer;
import myMod.common.RenderTestOre;
import myMod.common.TileEntityStructureBlock;
import net.minecraft.client.renderer.entity.RenderFallingSand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
	public static int RENDER_ID;
	
//	@Override
	public void registerRenderers()
	{
		MinecraftForgeClient.preloadTexture(ITEMS_PNG);
		MinecraftForgeClient.preloadTexture(BLOCK_PNG);
		RenderingRegistry.registerEntityRenderingHandler(EntityTestOre.class, new RenderTestOre());
		RenderingRegistry.registerEntityRenderingHandler(EntitySkewer.class, new RenderSkewer());
	}
	
	@Override
	public void registerFX()
	{
		RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		try {
			TextureFXManager.instance().addAnimation(new AnimBlockFX("/myMod/common/animBlock.png", 7));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
