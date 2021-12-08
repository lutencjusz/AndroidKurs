package com.example.kursapplication.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "podcast")
public class PodcastInDB {

    public class Columns{
        public static final String PODCAST_ID = "podcast_id";
        public static final String USER_ID = "user_id";
        public static final String NO_OF_FIELDS = "no_of_fields";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String URL = "url";

    }

    @DatabaseField( columnName = Columns.USER_ID)
    public long userId;
    @DatabaseField(columnName = Columns.PODCAST_ID)
    public long podcastId;
    @DatabaseField(columnName = Columns.NO_OF_FIELDS)
    public int numberOfEpisodes;
    @DatabaseField(columnName =Columns.TITLE)
    public String title;
    @DatabaseField(columnName = Columns.DESCRIPTION)
    public String description;
    @DatabaseField(columnName = Columns.URL)
    public String url;
}
