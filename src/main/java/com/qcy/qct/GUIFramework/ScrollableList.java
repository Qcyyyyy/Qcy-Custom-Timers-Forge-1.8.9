package com.qcy.qct.GUIFramework;

import com.qcy.qct.QCTConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.List;

public class ScrollableList {
    private int x, y, width, height;
    private static int scrollOffset = 0;
    private static QCTConfig config = QCTConfig.load();

    public ScrollableList(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(int mouseX, int mouseY) {
        config = QCTConfig.load();
        int entryHeight = 24;
        int start = scrollOffset / entryHeight;
        int end = Math.min(config.msgTriggerList.size(), start + height / entryHeight + 1);

        for (int i = start; i < end; i++) {
            int drawY = y + (i - start) * entryHeight;
            Gui.drawRect(x, drawY, x + width, drawY + entryHeight, 0x80343434);
            int nameWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth("Name: " + config.msgTriggerList.get(i).getTimerName());
            Minecraft.getMinecraft().fontRendererObj.drawString("Name: " + config.msgTriggerList.get(i).getTimerName(), x + 2, drawY + 2, 0x33ccff);
            Minecraft.getMinecraft().fontRendererObj.drawString("Duration (s): " + config.msgTriggerList.get(i).getDuration(), x + 6 + nameWidth, drawY + 2, 0x66ffff);
            Minecraft.getMinecraft().fontRendererObj.drawString("Trigger: " + config.msgTriggerList.get(i).getStringToTrigger(), x + 4, drawY + 14, 0xFFFFFF);
        }
    }

    public void mouseScrolled(int scrollDelta) {
        scrollOffset -= scrollDelta * 10; // scroll speed
        scrollOffset = Math.max(0, Math.min(scrollOffset, Math.max(0, config.msgTriggerList.size() * 24 - height)));
    }
}