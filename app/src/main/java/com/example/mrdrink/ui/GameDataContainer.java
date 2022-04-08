package com.example.mrdrink.ui;

import java.util.List;

public class GameDataContainer {
    public int ID;
    public String name;
    public String tag1;
    public String tag2;
    public String tag3;

    public List<String> neededMaterials;
    public String explanation;

    // TODO IMAGES

    public GameDataContainer (int newID, String newName, String newTag1, String newTag2, String newTag3)
    {
        this.ID = newID;
        this.name = newName;
        this.tag1 = newTag1;
        this.tag2 = newTag2;
        this.tag3 = newTag3;
    }

    public String getName ()
    {
        return this.name;
    }
}
