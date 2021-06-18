    package com.company;

    import javax.imageio.IIOException;
    import java.io.*;

    public class FileUtils {
        public static void saveConfig(File path, GameConfig config) throws IOException {
            try(BufferedWriter bf = new BufferedWriter(new FileWriter(path))) {
                bf.write(config.getIp() + "\n");
                bf.write(config.getPort() + "\n");
                bf.write(config.getDifficulty() + "\n");
                bf.write(config.getUpdatePeriod() + "\n");
                bf.write(config.getSavePeriod() + "\n");
            } catch (IOException e) {
                throw e;
            }
        }
        public static GameConfig loadConfig(File path) throws IOException{
            GameConfig config = new GameConfig();
            try(BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                int i = 0;
                while (i < 5) {
                    line = br.readLine();
                    if (i == 0) config.setIp(line);
                    else if (i == 1) config.setPort(Integer.parseInt(line));
                    else if (i == 2) config.setDifficulty(Integer.parseInt(line));
                    else if (i == 3) config.setUpdatePeriod(Long.parseLong(line));
                    else if (i == 4) config.setSavePeriod(Integer.parseInt(line));
                    i++;
                }
            } catch (IOException e) {
                throw e;
            }
            return config;
        }
        public static void saveWorld(File path, World world) throws IOException{
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
                oos.writeObject(world);
                oos.close();
            } catch (RuntimeException e) {
                throw e;
            }
        }
        public static World loadWorld(File path) throws IOException, ClassNotFoundException {
            World world;
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
                world = (World) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                throw e;
            }
            return world;
        }
    }
