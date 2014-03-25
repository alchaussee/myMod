package myMod.common;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderFallingSand;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTestOre extends RenderFallingSand
{
    private RenderBlocks testOreRenderBlocks = new RenderBlocks();
    private int blockID;
    
    public RenderTestOre()
    {
        this.shadowSize = 0.5F;
     //   this.renderBlocks.setOverrideBlockTexture(1);
    }

    /**
     * The actual render method that is used in doRender
     */
    public void doRenderTestOre(EntityTestOre par1EntityTestOre, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        
        this.loadTexture(CommonProxy.BLOCK_PNG);
        Block var10 = MyMod.testOre;
        
        World var11 = par1EntityTestOre.getWorld();
        GL11.glDisable(GL11.GL_LIGHTING);
        Tessellator var12;
        
	    this.testOreRenderBlocks.setRenderBoundsFromBlock(var10);
	    this.renderTestOre(var10, var11, MathHelper.floor_double(par1EntityTestOre.posX), MathHelper.floor_double(par1EntityTestOre.posY), MathHelper.floor_double(par1EntityTestOre.posZ), par1EntityTestOre.metadata);
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
    
    public void renderTestOre(Block par1Block, World par2World, int par3, int par4, int par5, int par6)
    {
        float var7 = 1.0F;
        float var8 = 0.5F;
        float var9 = 0.8F;
        float var10 = 0.6F;
        Tessellator var11 = Tessellator.instance;
        var11.startDrawingQuads();
        var11.setBrightness(par1Block.getMixedBrightnessForBlock(par2World, par3, par4, par5));
        float var12 = 1.0F;
        float var13 = 1.0F;

        if (var13 < var12)
        {
            var13 = var12;
        }

        var11.setColorOpaque_F(var7 * var13, var7 * var13, var7 * var13);
        this.testOreRenderBlocks.renderBottomFace(par1Block, -0.5D, -0.5D, -0.5D, par1Block.getBlockTextureFromSide(0));
        var13 = 1.0F;

        if (var13 < var12)
        {
            var13 = var12;
        }

        var11.setColorOpaque_F(var8 * var13, var8 * var13, var8 * var13);
        this.testOreRenderBlocks.renderTopFace(par1Block, -0.5D, -0.5D, -0.5D, par1Block.getBlockTextureFromSide(1));
        var13 = 1.0F;

        if (var13 < var12)
        {
            var13 = var12;
        }

        var11.setColorOpaque_F(var9 * var13, var9 * var13, var9 * var13);
        this.testOreRenderBlocks.renderEastFace(par1Block, -0.5D, -0.5D, -0.5D, par1Block.getBlockTextureFromSide(2));
        var13 = 1.0F;

        if (var13 < var12)
        {
            var13 = var12;
        }

        var11.setColorOpaque_F(var9 * var13, var9 * var13, var9 * var13);
        this.testOreRenderBlocks.renderWestFace(par1Block, -0.5D, -0.5D, -0.5D, par1Block.getBlockTextureFromSide(3));
        var13 = 1.0F;

        if (var13 < var12)
        {
            var13 = var12;
        }

        var11.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
        this.testOreRenderBlocks.renderNorthFace(par1Block, -0.5D, -0.5D, -0.5D, par1Block.getBlockTextureFromSide(4));
        var13 = 1.0F;

        if (var13 < var12)
        {
            var13 = var12;
        }

        var11.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
        this.testOreRenderBlocks.renderSouthFace(par1Block, -0.5D, -0.5D, -0.5D, par1Block.getBlockTextureFromSide(5));
        var11.draw();
    }

    
    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRenderTestOre((EntityTestOre)par1Entity, par2, par4, par6, par8, par9);
    }
    
}
