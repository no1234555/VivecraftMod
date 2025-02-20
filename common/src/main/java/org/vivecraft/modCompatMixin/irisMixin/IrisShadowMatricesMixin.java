package org.vivecraft.modCompatMixin.irisMixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.vivecraft.ClientDataHolder;
import org.vivecraft.render.RenderPass;

@Pseudo
@Mixin(targets = {"net.coderbot.iris.shadow.ShadowMatrices", "net.coderbot.iris.shadows.ShadowMatrices"})
public class IrisShadowMatricesMixin {

    private static float cachedShadowIntervalSize;
    private static Vec3 leftPass;
    private static Vec3 currentPass;


    // iris 1.4.2-
    @Group(name = "shadow interval", min = 1, max = 1)
    @Inject(target = @Desc(value = "snapModelViewToGrid", args = {Matrix4f.class, float.class, double.class, double.class, double.class}), at = @At("HEAD"), remap = false, expect = 0, require = 0)
    private static void cacheInterval(Matrix4f target, float shadowIntervalSize, double cameraX, double cameraY, double cameraZ, CallbackInfo ci){
        cachedShadowIntervalSize = shadowIntervalSize;
    }

    // iris 1.4.3+
    @Group(name = "shadow interval", min = 1, max = 1)
    @Inject(target = @Desc(value = "snapModelViewToGrid", args = {PoseStack.class, float.class, double.class, double.class, double.class}), at = @At("HEAD"), remap = false, expect = 0, require = 0)
    private static void cacheInterval143(PoseStack target, float shadowIntervalSize, double cameraX, double cameraY, double cameraZ, CallbackInfo ci){
        cachedShadowIntervalSize = shadowIntervalSize;
    }

    // offset camera pos, to be in the equal grid as left eye, but with correct offset
    @ModifyVariable( method = "snapModelViewToGrid", at = @At(value = "STORE"), ordinal  = 1, remap = false)
    private static float modifyOffsetX(float original){
        currentPass = ClientDataHolder.getInstance().vrPlayer.getVRDataWorld().getEye(ClientDataHolder.getInstance().currentPass).getPosition();
        if (ClientDataHolder.getInstance().currentPass == RenderPass.LEFT) {
            leftPass = currentPass;
        }
        return (float) (leftPass.x % cachedShadowIntervalSize - (leftPass.x - currentPass.x));
    }
    @ModifyVariable( method = "snapModelViewToGrid", at = @At(value = "STORE"), ordinal  = 2, remap = false)
    private static float modifyOffsetY(float original){
        return (float) (leftPass.y % cachedShadowIntervalSize - (leftPass.y - currentPass.y));
    }
    @ModifyVariable( method = "snapModelViewToGrid", at = @At(value = "STORE"), ordinal  = 3, remap = false)
    private static float modifyOffsetZ(float original){
        return (float) (leftPass.z % cachedShadowIntervalSize - (leftPass.z - currentPass.z));
    }
}
