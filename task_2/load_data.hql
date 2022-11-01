# Create table in hive and load data from CSV in HDFS

CREATE TABLE IF NOT EXISTS user_aashabokov.artists(
    mbid STRING,
    artist_mb STRING,
    artist_lastfm STRING,
    country_mb STRING,
    country_lastfm STRING,
    tags_mb STRING,
    tags_lastfm STRING,
    listeners_lastfm FLOAT,
    scrobbles_lastfm FLOAT,
    ambiguous_artist BOOLEAN
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ',';

LOAD DATA INPATH '/artists.csv' OVERWRITE INTO TABLE user_aashabokov.artists;
