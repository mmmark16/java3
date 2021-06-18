package com.company;
import java.io.Serializable;
import java.util. *;

public class World implements Serializable {
    private int id;
    private String worldName;
    private List<Entity> entities = new ArrayList<>();

    public void updateWorld() {
        //System.out.println(entities);
        entities.removeIf(i -> i.isDead());
        //System.out.println(entities);
        for (Entity e : entities) e.update();
    }


    public List<Entity> getEntitiesInRegion(double x, double z, double range) {
        List<Entity> sortList = new ArrayList<Entity>();
        double distanceMin = Math.pow(range, 2);

        for (Entity e : entities) {
            if ((Math.pow((e.getPosX() - x), 2) + Math.pow((e.getPosX() - z), 2)) <= distanceMin) {
                sortList.add(e);
            }
        }
        List<SearchEntity> sortEntities = new ArrayList<SearchEntity>();
        for (Entity e : sortList) {
            sortEntities.add(new SearchEntity(e, Math.pow((e.getPosX() - x), 2) + Math.pow((e.getPosX() - z), 2)));
        }

        Collections.sort(sortEntities, new Comparator<SearchEntity>() {
            @Override
            public int compare(SearchEntity o1, SearchEntity o2) {
                return Double.compare(o1.getDistance(), o2.getDistance());
            }
        });
        sortList.clear();

        for (SearchEntity e : sortEntities) {
            sortList.add(e.getEntity());
        }

        return sortList;
    }

    public List<Entity> getEntitiesNearEntity(Entity entity, double range) {
        return getEntitiesInRegion(entity.getPosX(), entity.getPosZ(), range);
    }

    public World() {
    }
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public World(int id, String worldName, List<Entity> entities) {
        this.id = id;
        this.worldName = worldName;
        this.entities = entities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    @Override
    public String toString() {
        return "World{" +
                "id=" + id + "\n" +
                ", worldName='" + worldName + '\'' + "\n" +
                ", entities=" + entities +
                '}';
    }
}