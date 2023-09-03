package org.vivecraft.client.extensions;

import net.minecraft.client.renderer.ShaderInstance;
import org.lwjgl.opengl.GL30;

public interface RenderTargetExtension {

	String getName();

	void clearWithColor(float r, float g, float b, float a, boolean isMac);

	default void blitToScreen(int i, int viewWidth, int viewHeight, int j, boolean b, float f, float g, boolean c) {
		blitToScreen(null, i, viewWidth, viewHeight, j, b, f, g, c);
	}


	void blitToScreen(ShaderInstance instance, int i, int viewWidth, int viewHeight, int j, boolean b, float f, float g, boolean c);

	default void genMipMaps() {
		GL30.glGenerateMipmap(3553);
	}

	void setTextid(int texid);

	void setUseStencil(boolean useStencil);

	boolean getUseStencil();

	void isLinearFilter(boolean linearFilter);

	void blitFovReduction(ShaderInstance instance, int width, int height);
}
