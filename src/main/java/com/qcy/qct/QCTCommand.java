package com.qcy.qct;

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
        return "USAGE (USE THE BRACKETS): /qct [Timer Name] [Duration in seconds] [String that triggers the timer]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.addChatMessage(new ChatComponentText("USAGE (USE THE BRACKETS): /qct [Timer Name] [Duration in seconds] [String that triggers the timer]"));
            return;
        }

        int duration = -1;

        String commandArgs;
        commandArgs = String.join(" ", args);

        Pattern pattern = Pattern.compile("\\[([^\\]]+)\\]");
        Matcher matcher = pattern.matcher(commandArgs);

        String arg1 = "", arg2 = "", arg3 = "";
        int index = 0;

        while (matcher.find()) {
            if (index == 0) {
                arg1 = matcher.group(1);
            } else if (index == 1) {
                arg2 = matcher.group(1);
            } else if (index == 2) {
                arg3 = matcher.group(1);
            }
            index++;
        }

        try {
            duration = Integer.parseInt(arg2);
        } catch (NumberFormatException e) {
            sender.addChatMessage(new ChatComponentText("Invalid command usage see /qct"));
            return;
        }
        if (duration == -1) {
            sender.addChatMessage(new ChatComponentText("Invalid command usage see /qct"));
            return;
        }

        qct.config.msgTriggerList.add(new MessageTrigger(arg3, arg1, duration));
        qct.config.save();

        sender.addChatMessage(new ChatComponentText("Added new message trigger..."));
        sender.addChatMessage(new ChatComponentText("Name: " + arg1));
        sender.addChatMessage(new ChatComponentText("Duration: " + duration));
        sender.addChatMessage(new ChatComponentText("Message Trigger: " + arg3));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
