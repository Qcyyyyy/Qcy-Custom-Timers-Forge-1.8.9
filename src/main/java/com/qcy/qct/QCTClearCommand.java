package com.qcy.qct;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;

public class QCTClearCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "qctclear";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "USAGE: /qctclear";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        qct.config.msgTriggerList = new ArrayList<MessageTrigger>();
        qct.config.timerList = new ArrayList<CustomTimer>();
        qct.config.save();
        sender.addChatMessage(new ChatComponentText("Cleared Timers and Message Triggers"));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
