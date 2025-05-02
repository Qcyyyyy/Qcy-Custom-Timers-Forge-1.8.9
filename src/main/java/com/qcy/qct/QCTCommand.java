package com.qcy.qct;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QCTCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "qct"; // The name of your command
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Open Main GUI: /qct ";
    }

    @Override
    public void processCommand(final ICommandSender sender, String[] args) {
        qct.shouldOpenGui = true;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
