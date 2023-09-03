package org.vivecraft.client_vr.extensions;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.vivecraft.client_vr.render.RenderPass;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.phys.Vec3;

public interface GameRendererExtension {

	boolean wasInWater();
	
	void setWasInWater(boolean b);

	void cacheRVEPos(LivingEntity e);

	boolean isInWater();

	boolean isInMenuRoom();

	boolean willBeInMenuRoom(Screen newScreen);
	
	boolean isInPortal();

	Vec3 getControllerRenderPos(int i);

	Vec3 getCrossVec();

	void setupClipPlanes();

	float getMinClipDistance();

	float getClipDistance();

	void applyVRModelView(RenderPass currentPass, PoseStack poseStack);

	void renderDebugAxes(int i, int j, int k, float f);

	void drawScreen(float f, Screen screen, PoseStack poseStack);

	Matrix4f getThirdPassProjectionMatrix();

	void drawEyeStencil(boolean flag1);

	float inBlock();

	double getRveY();

	void renderVrFast(float partialTicks, boolean secondpass, boolean menuhandright, boolean menuHandleft,
					  PoseStack poseStack);

	void renderVRFabulous(float f, LevelRenderer levelRenderer, boolean menuhandright, boolean menuHandleft, PoseStack poseStack);

	void restoreRVEPos(LivingEntity e);

	void setupRVE();

    void DrawScopeFB(PoseStack matrixStackIn, int i);

	void setShouldDrawScreen(boolean shouldDrawScreen);
	void setShouldDrawGui(boolean shouldDrawGui);
}
