package org.vivecraft.client.gui.framework;

import javax.annotation.Nullable;

import org.vivecraft.client_vr.ClientDataHolderVR;
import org.vivecraft.client_vr.settings.VRSettings;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TranslatableComponent;

public class GuiVROptionButton extends Button implements GuiVROption
{
    @Nullable
    private final VRSettings.VrOptions enumOptions;
    private int id = -1;

    public GuiVROptionButton(int id, int x, int y, String text, OnPress action)
    {
        this(id, x, y, (VRSettings.VrOptions)null, text, action);
    }

    public GuiVROptionButton(int id, int x, int y, @Nullable VRSettings.VrOptions option, String text, OnPress action)
    {
        this(id, x, y, 150, 20, option, text, action);
    }

    public GuiVROptionButton(int id, int x, int y, int width, int height, @Nullable VRSettings.VrOptions option, String text, OnPress action)
    {
        super(x, y, width, height, new TranslatableComponent(text), action);
        this.id = id;
        this.enumOptions = option;
        ClientDataHolderVR dataholder = ClientDataHolderVR.getInstance();

        if (option != null && dataholder.vrSettings.overrides.hasSetting(option) && dataholder.vrSettings.overrides.getSetting(option).isValueOverridden())
        {
            this.active = false;
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    @Nullable
    public VRSettings.VrOptions getOption()
    {
        return this.enumOptions;
    }
}
