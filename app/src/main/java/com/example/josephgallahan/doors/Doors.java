package com.example.josephgallahan.doors;

import java.util.UUID;

/**
 * Created by joseph.gallahan on 9/30/2016.
 */

public class Doors
{
    private UUID mId;
    private String mTitle;

    public Doors() {
            mId = UUID.randomUUID();
        }

    public UUID getId() {
            return mId;
        }

    public String getTitle() {
            return mTitle;
        }

    public void setTitle(String title) { mTitle = title; }
}
