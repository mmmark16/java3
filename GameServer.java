package com.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private GameConfig config;
    private World serverWorld;
    private int serverTicks;
    private static GameServer instance;
    private File pathConfig;
    private File pathWorld;

    public void updateServer(){
        serverWorld.updateWorld();
        serverTicks++;
        if (serverTicks%(config.getSavePeriod()) == 0 ){
            try {
                FileUtils.saveWorld(pathWorld, serverWorld);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args){
        new GameServer();
    }

    private GameServer() {
        instance = this;

        pathConfig = new File("C:\\Users\\Student\\Downloads\\laba33\\laba33\\src\\com\\company\\config.txt");
        pathWorld = new File("C:\\Users\\Student\\Downloads\\laba33\\laba33\\src\\com\\company\\world.txt");

        config = new GameConfig();
        try {
            FileUtils.saveConfig(pathConfig, config);
            loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CreateWorld();
        try {
            FileUtils.saveWorld(pathWorld, serverWorld);
            loadWorld();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        System.out.println(getInstance());
        while (serverTicks < 100) updateServer();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() throws IOException {
        FileUtils.loadConfig(pathConfig);
        System.out.println(config);
        if (config == null){
            config = new GameConfig();
            FileUtils.saveConfig(pathConfig, config);
        }

    }
    private void loadWorld() throws IOException, ClassNotFoundException {
        try {
            serverWorld = FileUtils.loadWorld(pathWorld);
            System.out.println(serverWorld);
        } catch (IOException | ClassNotFoundException e) {
            throw e;
        }

        if (serverWorld == null){
            CreateWorld();
        }
    }

    public static GameServer getInstance() {
        return instance;
    }

    public static void setInstance(GameServer instance) {
        GameServer.instance = instance;
    }

    public World getServerWorld() {
        return serverWorld;
    }

    public int getServerTicks() {
        return serverTicks;
    }

    public void CreateWorld() {
        serverWorld = new World();
        serverWorld.setId(1);
        serverWorld.setWorldName("ABOBUS");

        Entity Zombie = new Entity("Zombie", 0, 0, true, 140, 140, 10, serverWorld);
        Entity Skeleton = new Entity("Skeleton", 5,5, true, 80, 80, 10, serverWorld);
        Entity Pig = new Entity("Pig", 3, 3, false, 20, 20, 0, serverWorld);
        Entity Human = new EntityPlayer("Human", 1, 1, 100, 100, 20, "mvp44", serverWorld);
        serverWorld.addEntity(Zombie);
        serverWorld.addEntity(Skeleton);
        serverWorld.addEntity(Pig);
        serverWorld.addEntity(Human);
    }

    public GameConfig getConfig() {
        return config;
    }

    public void setConfig(GameConfig config) {
        this.config = config;
    }
}