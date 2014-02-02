package evilcraft.render.tileentity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import evilcraft.entities.tileentities.EvilCraftBeaconTileEntity;

/**
 * EvilCraft's version of a beacon renderer, this allows us to have custom colors
 * and customize other stuff without being dependend on vanilla code
 * 
 * @author immortaleeb
 *
 */
public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer {
	
	private static final ResourceLocation BEACON_TEXTURE = new ResourceLocation("textures/entity/beacon_beam.png");

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double par2, double par4, double par6, float par8) {
		renderBeacon((EvilCraftBeaconTileEntity)tileentity, par2, par4, par6, par8);
	}
	
	public void renderBeacon(EvilCraftBeaconTileEntity tileentity, double par2, double par4, double par6, float par8) {
		float f1 = tileentity.getBeamRenderVariable();

        if (tileentity.isBeamActive())
        {
        	Vector4f beamInnerColor = tileentity.getBeamInnerColor();
        	Vector4f beamOuterColor = tileentity.getBeamOuterColor();
        	
            Tessellator tessellator = Tessellator.instance;
            this.bindTexture(BEACON_TEXTURE);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            float f2 = (float)tileentity.getWorldObj().getTotalWorldTime() + par8;
            float f3 = -f2 * 0.2F - (float)MathHelper.floor_float(-f2 * 0.1F);
            byte b0 = 1;
            double d3 = (double)f2 * 0.025D * (1.0D - (double)(b0 & 1) * 2.5D);
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(beamInnerColor.x, beamInnerColor.y, beamInnerColor.z, beamInnerColor.w);
            double d4 = (double)b0 * 0.2D;
            double d5 = 0.5D + Math.cos(d3 + 2.356194490192345D) * d4;
            double d6 = 0.5D + Math.sin(d3 + 2.356194490192345D) * d4;
            double d7 = 0.5D + Math.cos(d3 + (Math.PI / 4D)) * d4;
            double d8 = 0.5D + Math.sin(d3 + (Math.PI / 4D)) * d4;
            double d9 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * d4;
            double d10 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * d4;
            double d11 = 0.5D + Math.cos(d3 + 5.497787143782138D) * d4;
            double d12 = 0.5D + Math.sin(d3 + 5.497787143782138D) * d4;
            double d13 = (double)(256.0F * f1);
            double d14 = 0.0D;
            double d15 = 1.0D;
            double d16 = (double)(-1.0F + f3);
            double d17 = (double)(256.0F * f1) * (0.5D / d4) + d16;
            tessellator.addVertexWithUV(par2 + d5, par4 + d13, par6 + d6, d15, d17);
            tessellator.addVertexWithUV(par2 + d5, par4, par6 + d6, d15, d16);
            tessellator.addVertexWithUV(par2 + d7, par4, par6 + d8, d14, d16);
            tessellator.addVertexWithUV(par2 + d7, par4 + d13, par6 + d8, d14, d17);
            tessellator.addVertexWithUV(par2 + d11, par4 + d13, par6 + d12, d15, d17);
            tessellator.addVertexWithUV(par2 + d11, par4, par6 + d12, d15, d16);
            tessellator.addVertexWithUV(par2 + d9, par4, par6 + d10, d14, d16);
            tessellator.addVertexWithUV(par2 + d9, par4 + d13, par6 + d10, d14, d17);
            tessellator.addVertexWithUV(par2 + d7, par4 + d13, par6 + d8, d15, d17);
            tessellator.addVertexWithUV(par2 + d7, par4, par6 + d8, d15, d16);
            tessellator.addVertexWithUV(par2 + d11, par4, par6 + d12, d14, d16);
            tessellator.addVertexWithUV(par2 + d11, par4 + d13, par6 + d12, d14, d17);
            tessellator.addVertexWithUV(par2 + d9, par4 + d13, par6 + d10, d15, d17);
            tessellator.addVertexWithUV(par2 + d9, par4, par6 + d10, d15, d16);
            tessellator.addVertexWithUV(par2 + d5, par4, par6 + d6, d14, d16);
            tessellator.addVertexWithUV(par2 + d5, par4 + d13, par6 + d6, d14, d17);
            tessellator.draw();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDepthMask(false);
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(beamOuterColor.x, beamOuterColor.y, beamOuterColor.z, beamOuterColor.w);
            double d18 = 0.2D;
            double d19 = 0.2D;
            double d20 = 0.8D;
            double d21 = 0.2D;
            double d22 = 0.2D;
            double d23 = 0.8D;
            double d24 = 0.8D;
            double d25 = 0.8D;
            double d26 = (double)(256.0F * f1);
            double d27 = 0.0D;
            double d28 = 1.0D;
            double d29 = (double)(-1.0F + f3);
            double d30 = (double)(256.0F * f1) + d29;
            tessellator.addVertexWithUV(par2 + d18, par4 + d26, par6 + d19, d28, d30);
            tessellator.addVertexWithUV(par2 + d18, par4, par6 + d19, d28, d29);
            tessellator.addVertexWithUV(par2 + d20, par4, par6 + d21, d27, d29);
            tessellator.addVertexWithUV(par2 + d20, par4 + d26, par6 + d21, d27, d30);
            tessellator.addVertexWithUV(par2 + d24, par4 + d26, par6 + d25, d28, d30);
            tessellator.addVertexWithUV(par2 + d24, par4, par6 + d25, d28, d29);
            tessellator.addVertexWithUV(par2 + d22, par4, par6 + d23, d27, d29);
            tessellator.addVertexWithUV(par2 + d22, par4 + d26, par6 + d23, d27, d30);
            tessellator.addVertexWithUV(par2 + d20, par4 + d26, par6 + d21, d28, d30);
            tessellator.addVertexWithUV(par2 + d20, par4, par6 + d21, d28, d29);
            tessellator.addVertexWithUV(par2 + d24, par4, par6 + d25, d27, d29);
            tessellator.addVertexWithUV(par2 + d24, par4 + d26, par6 + d25, d27, d30);
            tessellator.addVertexWithUV(par2 + d22, par4 + d26, par6 + d23, d28, d30);
            tessellator.addVertexWithUV(par2 + d22, par4, par6 + d23, d28, d29);
            tessellator.addVertexWithUV(par2 + d18, par4, par6 + d19, d27, d29);
            tessellator.addVertexWithUV(par2 + d18, par4 + d26, par6 + d19, d27, d30);
            tessellator.draw();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDepthMask(true);
        }
	}

}