package com.qcy.qct;

import com.qcy.qct.GUIFramework.ScrollableList;
import com.qcy.qct.GUIs.QCTAddNewGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;

import java.io.IOException;

public class QCTGui extends GuiScreen {

    public ScrollableList msgTriggerScrollList;
    static QCTConfig config = QCTConfig.load();

    @Override
    public void initGui() {
        config = QCTConfig.load();
        this.buttonList.clear(); // Clear existing buttons to avoid dupes

        int startX = (int)((this.width / 2) - (this.width * 0.25));
        int startY = 20;
        int listWidth = (int)(this.width * 0.5);

        this.buttonList.add(new GuiButton(-1, 5, 5, 20, 20, "+"));

        for (int i = 0; i < config.msgTriggerList.size(); i++) {
            this.buttonList.add(new GuiButton(i, startX + listWidth - 22, (i * 24) + startY + 2, 20, 20, "X"));
        }

        int listHeight = (int)(this.height * 0.7);
        msgTriggerScrollList = new ScrollableList(startX, startY, listWidth, listHeight);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Qcy Custom Timers: Active Message Triggers", this.width / 2, 5, 0x33ccff);

        if (msgTriggerScrollList != null) {
            msgTriggerScrollList.draw(mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int scroll = org.lwjgl.input.Mouse.getDWheel();
        if (scroll != 0 && msgTriggerScrollList != null) {
            msgTriggerScrollList.mouseScrolled(Integer.signum(scroll));
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        config = QCTConfig.load();

        if (button.id == -1) {
            Minecraft.getMinecraft().displayGuiScreen(new QCTAddNewGUI());
        } else if (button.id < config.msgTriggerList.size()) {
            config.msgTriggerList.remove(button.id);
            config.save();

            this.initGui();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}