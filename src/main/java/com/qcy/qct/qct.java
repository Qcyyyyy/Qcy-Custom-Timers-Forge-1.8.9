package com.qcy.qct;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Date;

@Mod(modid = qct.MODID, version = qct.VERSION)
public class qct
{
    public static final String MODID = "qct";
    public static final String VERSION = "1.1";
    public static QCTConfig config = QCTConfig.load();
    public static boolean shouldOpenGui = false;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new QCTCommand());
        ClientCommandHandler.instance.registerCommand(new QCTClearCommand());
        ClientCommandHandler.instance.registerCommand(new QCTPosCommand());
    }

    @SubscribeEvent // Weird but this is the only simple way I could figure out how to delay opening the gui so it works with a command (Got cut off while still in chat gui)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (shouldOpenGui) {
                Minecraft.getMinecraft().displayGuiScreen(new QCTGui());
                shouldOpenGui = false;
            }
        }
    }

    // Renders timer GUI
    @SubscribeEvent
    public void onRenderHUD(RenderGameOverlayEvent.Post event) {
        if (config.timerList.size() > 0) {
            if (event.type != RenderGameOverlayEvent.ElementType.ALL) {
                return;
            }

            Minecraft mc = Minecraft.getMinecraft();

            int x = 10 + config.xPos;
            int y = 10 + config.yPos;

            ArrayList<String> renderStringList = new ArrayList<String>();
            ArrayList<Integer> renderColorList = new ArrayList<Integer>();
            int maxWidth = 0;

            for (int i = 0; i < config.timerList.size(); i++) {
                CustomTimer timer = config.timerList.get(i);
                Date currentDate = new Date();
                long secondsElapsed = (currentDate.getTime() - timer.getStartTime().getTime())/1000;
                long secondsLeft = timer.getDuration() - secondsElapsed;
                String timerString = "";
                int timerColor;
                if (secondsLeft >= 0) { // Normal timer display
                    timerString = timer.getTimerName() + ": " + timeFormatter(secondsLeft);
                    timerColor = 0x33ccff;
                    renderStringList.add(timerString);
                    renderColorList.add(timerColor);
                    if (maxWidth < mc.fontRendererObj.getStringWidth(timerString)) {
                        maxWidth = mc.fontRendererObj.getStringWidth(timerString);
                    }
                }

                else if (secondsLeft < 0 && secondsLeft > -2) { // Timer reaches 0 plays sound and is displayed in red
                    ResourceLocation soundLocation = new ResourceLocation("minecraft", "random.orb");
                    timerString = timer.getTimerName() + ": 0";
                    timerColor = 0xFF6666;
                    renderStringList.add(timerString);
                    renderColorList.add(timerColor);
                    if (maxWidth < mc.fontRendererObj.getStringWidth(timerString)) {
                        maxWidth = mc.fontRendererObj.getStringWidth(timerString);
                    }
                    mc.getSoundHandler().playSound(PositionedSoundRecord.create(soundLocation, 1.0F));
                }

                else if (secondsLeft <= -2 && secondsLeft > -4) { // Just red display after sound + red
                    timerString = timer.getTimerName() + ": 0";
                    timerColor = 0xFF6666;
                    renderStringList.add(timerString);
                    renderColorList.add(timerColor);
                    if (maxWidth < mc.fontRendererObj.getStringWidth(timerString)) {
                        maxWidth = mc.fontRendererObj.getStringWidth(timerString);
                    }
                }

                else { // Remove timer
                    config.timerList.remove(i);
                    config.save();
                }
            }

            Gui.drawRect(x-5, y-5, x + maxWidth + 5, y + renderStringList.size()*9 + 5, 0x80000000);

            for (int i = 0; i < renderStringList.size(); i++) {
                mc.fontRendererObj.drawString(renderStringList.get(i), x, y+(i*9), renderColorList.get(i));
            }
        }
    }


    // Formats time strings into a nice to look at format
    private String timeFormatter(long secondsLeft) {
        long days = secondsLeft / 86400;
        secondsLeft %= 86400;
        long hours = secondsLeft / 3600;
        secondsLeft %= 3600;
        long minutes = secondsLeft / 60;
        secondsLeft %= 60;

        StringBuilder sb = new StringBuilder();

        if (days > 0) {
            sb.append(days).append(":");
            sb.append(String.format("%02d:%02d:%02d", hours, minutes, secondsLeft));
        }

        else if (hours > 0) {
            sb.append(hours).append(":");
            sb.append(String.format("%02d:%02d", minutes, secondsLeft));
        }

        else if (minutes > 0) {
            sb.append(minutes).append(":");
            sb.append(String.format("%02d", secondsLeft));
        }

        else {
            sb.append(secondsLeft);
        }

        return sb.toString();
    }

    // Check chat messages for all message triggers
    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();

        config = QCTConfig.load();

        for (int i = 0; i < config.msgTriggerList.size(); i++) {
            if (message.contains(config.msgTriggerList.get(i).getStringToTrigger())) {
                MessageTrigger msgTrigger = config.msgTriggerList.get(i);
                config.timerList.add(new CustomTimer(msgTrigger.getTimerName(), new Date(), msgTrigger.getDuration()));
                config.save();
            }
        }
    }
}
