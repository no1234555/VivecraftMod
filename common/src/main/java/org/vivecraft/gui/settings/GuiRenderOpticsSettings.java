package org.vivecraft.gui.settings;

import org.vivecraft.ClientDataHolder;
import org.vivecraft.IrisHelper;
import org.vivecraft.Xplat;
import org.vivecraft.gui.framework.GuiVROptionButton;
import org.vivecraft.gui.framework.GuiVROptionsBase;
import org.vivecraft.settings.VRHotkeys;
import org.vivecraft.settings.VRSettings;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;

public class GuiRenderOpticsSettings extends GuiVROptionsBase
{
    static VRSettings.VrOptions[] monoDisplayOptions = new VRSettings.VrOptions[] {
            VRSettings.VrOptions.MONO_FOV,
            VRSettings.VrOptions.DUMMY,
            VRSettings.VrOptions.FSAA
    };
    static VRSettings.VrOptions[] openVRDisplayOptions = new VRSettings.VrOptions[] {
            VRSettings.VrOptions.RENDER_SCALEFACTOR,
            VRSettings.VrOptions.MIRROR_DISPLAY,
            VRSettings.VrOptions.FSAA,
            VRSettings.VrOptions.STENCIL_ON,
            VRSettings.VrOptions.HANDHELD_CAMERA_RENDER_SCALE,
            VRSettings.VrOptions.HANDHELD_CAMERA_FOV,
            VRSettings.VrOptions.RELOAD_EXTERNAL_CAMERA,
            VRSettings.VrOptions.MIRROR_EYE
    };
    static VRSettings.VrOptions[] MROptions = new VRSettings.VrOptions[] {
            VRSettings.VrOptions.MIXED_REALITY_UNITY_LIKE,
            VRSettings.VrOptions.MIXED_REALITY_RENDER_HANDS,
            VRSettings.VrOptions.MIXED_REALITY_KEY_COLOR,
            VRSettings.VrOptions.MIXED_REALITY_FOV,
            VRSettings.VrOptions.MIXED_REALITY_UNDISTORTED,
            VRSettings.VrOptions.MONO_FOV,
            VRSettings.VrOptions.MIXED_REALITY_ALPHA_MASK,
            VRSettings.VrOptions.MIXED_REALITY_RENDER_CAMERA_MODEL
    };
    static VRSettings.VrOptions[] UDOptions = new VRSettings.VrOptions[] {
            VRSettings.VrOptions.MONO_FOV
    };
    static VRSettings.VrOptions[] TUDOptions = new VRSettings.VrOptions[] {
            VRSettings.VrOptions.MIXED_REALITY_FOV,
            VRSettings.VrOptions.MIXED_REALITY_RENDER_CAMERA_MODEL
    };
    private float prevRenderScaleFactor = this.settings.renderScaleFactor;
    private float prevHandCameraResScale = this.settings.handCameraResScale;

    public GuiRenderOpticsSettings(Screen par1Screen)
    {
        super(par1Screen);
    }

    public void init()
    {
        this.vrTitle = "vivecraft.options.screen.stereorendering";
        VRSettings.VrOptions[] avrsettings$vroptions = new VRSettings.VrOptions[openVRDisplayOptions.length];
        System.arraycopy(openVRDisplayOptions, 0, avrsettings$vroptions, 0, openVRDisplayOptions.length);

        for (int i = 0; i < avrsettings$vroptions.length; ++i)
        {
            VRSettings.VrOptions vrsettings$vroptions = avrsettings$vroptions[i];

            if (vrsettings$vroptions == VRSettings.VrOptions.RELOAD_EXTERNAL_CAMERA && (!VRHotkeys.hasExternalCameraConfig() || this.dataholder.vrSettings.displayMirrorMode != VRSettings.MirrorMode.MIXED_REALITY && this.dataholder.vrSettings.displayMirrorMode != VRSettings.MirrorMode.THIRD_PERSON))
            {
                avrsettings$vroptions[i] = VRSettings.VrOptions.DUMMY;
            }

            if (vrsettings$vroptions == VRSettings.VrOptions.MIRROR_EYE && this.dataholder.vrSettings.displayMirrorMode != VRSettings.MirrorMode.CROPPED && this.dataholder.vrSettings.displayMirrorMode != VRSettings.MirrorMode.SINGLE)
            {
                avrsettings$vroptions[i] = VRSettings.VrOptions.DUMMY;
            }
        }

        super.init(avrsettings$vroptions, true);

        if (this.dataholder.vrSettings.displayMirrorMode == VRSettings.MirrorMode.MIXED_REALITY)
        {
            avrsettings$vroptions = new VRSettings.VrOptions[MROptions.length];
            System.arraycopy(MROptions, 0, avrsettings$vroptions, 0, MROptions.length);

            for (int j = 0; j < avrsettings$vroptions.length; ++j)
            {
                VRSettings.VrOptions vrsettings$vroptions1 = avrsettings$vroptions[j];

                if (vrsettings$vroptions1 == VRSettings.VrOptions.MONO_FOV && (!this.dataholder.vrSettings.mixedRealityUndistorted || !this.dataholder.vrSettings.mixedRealityUnityLike))
                {
                    avrsettings$vroptions[j] = VRSettings.VrOptions.DUMMY;
                }

                if (vrsettings$vroptions1 == VRSettings.VrOptions.MIXED_REALITY_ALPHA_MASK && !this.dataholder.vrSettings.mixedRealityUnityLike)
                {
                    avrsettings$vroptions[j] = VRSettings.VrOptions.DUMMY;
                }

                if (vrsettings$vroptions1 == VRSettings.VrOptions.MIXED_REALITY_UNDISTORTED && !this.dataholder.vrSettings.mixedRealityUnityLike)
                {
                    avrsettings$vroptions[j] = VRSettings.VrOptions.DUMMY;
                }

                if (vrsettings$vroptions1 == VRSettings.VrOptions.MIXED_REALITY_KEY_COLOR && this.dataholder.vrSettings.mixedRealityAlphaMask && this.dataholder.vrSettings.mixedRealityUnityLike)
                {
                    avrsettings$vroptions[j] = VRSettings.VrOptions.DUMMY;
                }
            }

            super.init(avrsettings$vroptions, false);
        }
        else if (this.dataholder.vrSettings.displayMirrorMode == VRSettings.MirrorMode.FIRST_PERSON)
        {
            super.init(UDOptions, false);
        }
        else if (this.dataholder.vrSettings.displayMirrorMode == VRSettings.MirrorMode.THIRD_PERSON)
        {
            super.init(TUDOptions, false);
        }

        super.addDefaultButtons();
        this.renderables.stream().filter((w) ->
        {
            return w instanceof GuiVROptionButton;
        }).forEach((w) ->
        {
            GuiVROptionButton guivroptionbutton = (GuiVROptionButton)w;

//            if (guivroptionbutton.getOption() == VRSettings.VrOptions.HANDHELD_CAMERA_RENDER_SCALE && Config.isShaders())  //Optifine
//            {
//                guivroptionbutton.active = false;
//            }
        });
    }

    public void render(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks)
    {
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
    }

    protected void loadDefaults()
    {
        super.loadDefaults();
        this.minecraft.options.fov = 70.0D;
        this.dataholder.vrRenderer.reinitFrameBuffers("Defaults Loaded");
    }

    protected void actionPerformed(AbstractWidget widget)
    {
        if (widget instanceof GuiVROptionButton)
        {
            GuiVROptionButton guivroptionbutton = (GuiVROptionButton)widget;

            if (guivroptionbutton.id == VRSettings.VrOptions.MIRROR_DISPLAY.ordinal() || guivroptionbutton.id == VRSettings.VrOptions.FSAA.ordinal() || guivroptionbutton.id == VRSettings.VrOptions.STENCIL_ON.ordinal())
            {
                if (guivroptionbutton.id != VRSettings.VrOptions.MIRROR_DISPLAY.ordinal() || !((Xplat.isModLoaded("iris") || Xplat.isModLoaded("oculus")) && IrisHelper.isShaderActive())) {
                    this.dataholder.vrRenderer.reinitFrameBuffers("Render Setting Changed");
                }
            }
            if (guivroptionbutton.id == VRSettings.VrOptions.RELOAD_EXTERNAL_CAMERA.ordinal())
            {
                VRHotkeys.loadExternalCameraConfig();
            }
        }
    }

    public boolean mouseReleased(double pMouseX, double p_94754_, int pMouseY)
    {
        if (this.settings.renderScaleFactor != this.prevRenderScaleFactor || this.settings.handCameraResScale != this.prevHandCameraResScale)
        {
            this.prevRenderScaleFactor = this.settings.renderScaleFactor;
            this.prevHandCameraResScale = this.settings.handCameraResScale;
            this.dataholder.vrRenderer.reinitFrameBuffers("Render Setting Changed");
        }

        return super.mouseReleased(pMouseX, p_94754_, pMouseY);
    }
}
