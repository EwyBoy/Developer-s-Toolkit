package com.ewyboy.devkit.client;

import com.ewyboy.devkit.util.TestWorld;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MainMenuEvent {

    private Button buttonCreate;
    private Button buttonDelete;
    private Button buttonLoad;

    @SubscribeEvent
    public void onScreenDraw(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (event.getGui() instanceof MainMenuScreen) {
            int textY = event.getGui().height / 4 + 38;
            int textX = event.getGui().width / 2 + 146;
            event.getGui().drawCenteredString(event.getMatrixStack(), Minecraft.getInstance().font, "Test World", textX, textY, 0xFFFFFF);

            if (buttonDelete.isHovered() && !buttonDelete.active) {
                event.getGui().renderTooltip(event.getMatrixStack(), new StringTextComponent("Press [Left Shift] 2 times to enable delete"), event.getMouseX(), event.getMouseY());
            }

            buttonCreate.visible = true;
            buttonLoad.visible = true;
            buttonDelete.visible = true;

            /*if (!DevWorldUtils.saveExist()) {
                buttonCreate.visible = true;
                buttonLoad.visible = false;
                buttonDelete.visible = false;
            } else {
                buttonCreate.visible = false;
                buttonLoad.visible = true;
                buttonDelete.visible = true;
            }*/
        }
    }

    @SubscribeEvent
    public void onScreenInitPost(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof MainMenuScreen) {
            int buttonY = event.getGui().height / 4 + 48;
            int buttonX = event.getGui().width / 2 + 104;

            buttonCreate = new Button(buttonX, buttonY, 82, 20, new StringTextComponent("New"), (button) -> {
                TestWorld.createTestWorld();
            });

            buttonY += 24;

            buttonLoad = new Button(buttonX, buttonY, 82, 20, new StringTextComponent("Load"), (button) -> {
                TestWorld.loadDevWorld();
            });

            buttonY += 24;

            buttonDelete = new Button(buttonX, buttonY, 82, 20, new StringTextComponent("Delete"), (button) -> {
                TestWorld.deleteDevWorld();
            });

            buttonCreate.visible = false;
            buttonLoad.visible = false;
            buttonDelete.visible = false;
            buttonDelete.active = false;

            //keyShiftCount = 0;

            event.addWidget(buttonCreate);
            event.addWidget(buttonLoad);
            event.addWidget(buttonDelete);
        }
    }


}
