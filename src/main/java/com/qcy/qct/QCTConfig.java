package com.qcy.qct;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class QCTConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/qctconfig.json");

    public ArrayList<MessageTrigger> msgTriggerList = new ArrayList<MessageTrigger>();
    public ArrayList<CustomTimer> timerList = new ArrayList<CustomTimer>();
    public int xPos = 0;
    public int yPos = 0;

    // Load the config from the file, or create a new one if it doesn't exist
    public static QCTConfig load() {
        FileReader reader = null;
        try {
            if (CONFIG_FILE.exists()) {
                reader = new FileReader(CONFIG_FILE);
                return GSON.fromJson(reader, QCTConfig.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new QCTConfig(); // Return a default config if loading fails
    }

    // Save the config to the file
    public void save() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(CONFIG_FILE);
            GSON.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}