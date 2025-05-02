package com.qcy.qct.GUIs;

import com.qcy.qct.GUIFramework.ScrollableList;
import com.qcy.qct.MessageTrigger;
import com.qcy.qct.QCTConfig;
import com.qcy.qct.QCTGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class QCTAddNewGUI extends GuiScreen {

    private GuiTextField textFieldName;
    private GuiTextField textFieldDuration;
    private GuiTextField textFieldTrigger;
    static QCTConfig config;

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);

        this.textFieldName = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, this.height / 2 - 30, 200, 20);
        this.textFieldName.setMaxStringLength(50);

        this.textFieldDuration = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, this.height / 2 + 10, 200, 20);
        this.textFieldDuration.setMaxStringLength(9);

        this.textFieldTrigger = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, this.height / 2 + 50, 200, 20);
        this.textFieldTrigger.setMaxStringLength(50);

        this.textFieldName.setFocused(true);
        this.textFieldDuration.setFocused(false);
        this.textFieldTrigger.setFocused(false);

        this.buttonList.add(new GuiButton(1, this.width / 2 - 50, this.height / 2 + 80, 100, 20, "Add New"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 1) {
            config = QCTConfig.load();
            String inputName = textFieldName.getText();
            int inputDuration;
            try {
                inputDuration = Integer.parseInt(textFieldDuration.getText());
            } catch (NumberFormatException e) {
                return;
            }
            String inputTrigger = textFieldTrigger.getText();
            config.msgTriggerList.add(new MessageTrigger(inputTrigger, inputName, inputDuration));
            this.initGui();
            config.save();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (this.textFieldName.textboxKeyTyped(typedChar, keyCode)) return;
        if (this.textFieldDuration.textboxKeyTyped(typedChar, keyCode)) return;
        if (this.textFieldTrigger.textboxKeyTyped(typedChar, keyCode)) return;

        try {
            super.keyTyped(typedChar, keyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        textFieldName.mouseClicked(mouseX, mouseY, mouseButton);
        try {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // For updating focus on one of the three text boxes
        this.textFieldName.mouseClicked(mouseX, mouseY, mouseButton);
        this.textFieldDuration.mouseClicked(mouseX, mouseY, mouseButton);
        this.textFieldTrigger.mouseClicked(mouseX, mouseY, mouseButton);

        this.textFieldName.setFocused(isInside(this.textFieldName, mouseX, mouseY));
        this.textFieldDuration.setFocused(isInside(this.textFieldDuration, mouseX, mouseY));
        this.textFieldTrigger.setFocused(isInside(this.textFieldTrigger, mouseX, mouseY));

    }

    @Override
    public void updateScreen() {
        this.textFieldName.updateCursorCounter();
        this.textFieldDuration.updateCursorCounter();
        this.textFieldTrigger.updateCursorCounter();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Name", this.width / 2, this.height / 2 - 40, 0x33ccff);
        this.drawCenteredString(this.fontRendererObj, "Duration (s)", this.width / 2, this.height / 2, 0x33ccff);
        this.drawCenteredString(this.fontRendererObj, "Trigger", this.width / 2, this.height / 2 + 40, 0x33ccff);
        this.textFieldName.drawTextBox();
        this.textFieldDuration.drawTextBox();
        this.textFieldTrigger.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    // Checks if mouse is hovered over specific text box
    private boolean isInside(GuiTextField field, int mouseX, int mouseY) {
        return mouseX >= field.xPosition && mouseX < field.xPosition + field.width &&
                mouseY >= field.yPosition && mouseY < field.yPosition + field.height;
    }
}
