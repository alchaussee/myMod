package myMod.common;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.TextureFXManager;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.src.ModTextureAnimation;
import net.minecraftforge.client.ForgeHooksClient;

public class AnimBlockFX extends ModTextureAnimation
{
	public AnimBlockFX(String texture, int index) throws IOException
	{
		super(index, 1, texture, TextureFXManager.instance().loadImageFromTexturePack(FMLClientHandler.instance().getClient().renderEngine, texture), 2);
		setup();
	}
	
	@Override
	public void bindImage(RenderEngine re)
	{
		//ForgeHooksClient.bindTexture("/myMod/common/animBlock.png", re.getTexture("/myMod/common/animBlock.png"));
		GL11.glBindTexture(GL11.GL_TEXTURE_2D , re.getTexture("/myMod/common/block.png"));
	}
}
