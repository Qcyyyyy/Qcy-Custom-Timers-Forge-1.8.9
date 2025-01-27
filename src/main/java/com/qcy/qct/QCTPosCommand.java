package com.qcy.qct;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;

public class QCTPosCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "qctpos";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "USAGE: /qctpos x y";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        try {
            qct.config.xPos = Integer.parseInt(args[0]);
            qct.config.yPos = Integer.parseInt(args[1]);
            qct.config.save();
        } catch (NumberFormatException e) {
            sender.addChatMessage(new ChatComponentText("Invalid command usage see /qctpos"));
            return;
        }
        sender.addChatMessage(new ChatComponentText("Set new position"));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
