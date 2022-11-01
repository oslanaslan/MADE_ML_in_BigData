# Исполнителя с максимальным числом скробблов

select artist_lastfm from user_aashabokov.artists
where scrobbles_lastfm in (
    select max(scrobbles_lastfm)
    from artists
);

# Самый популярный тэг на ластфм

select counts.tag 
from (
    select tag, count(1) as cnt
    from user_aashabokov.artists
    lateral view explode(split(tags_lastfm, "; ")) tag_table as tag 
    where length(tag) > 0
    group by tag
    order by cnt
    desc limit 1
) as counts;

# Самые популярные исполнители 10 самых популярных тегов ластфм

select res_df.artist_lastfm
from (
    select distinct artist_lastfm, tmp.scrobbles_lastfm
    from (
        select tag, artist_lastfm, scrobbles_lastfm
        from user_aashabokov.artists 
        lateral view explode(split(tags_lastfm, "; ")) tag_table as tag 
        where length(tag) > 0
        order by scrobbles_lastfm desc
    ) as tmp 
    where tmp.tag in (
        select counts.tag
        from (
              select tag,  count(1) as cnt 
              from user_aashabokov.artists 
              lateral view explode(split(tags_lastfm, "; ")) tag_table as tag 
              where length(tag) > 0
              group by tag
              order by cnt desc
              limit 10
        ) as counts
    )
    order by tmp.scrobbles_lastfm desc
    limit 10
) as res_df;

# Топ 10 стран с самым большим количеством анимешников (тэг содержит 'anime')

select country
from (
    select country, sum(listeners_lastfm) as cnt
    from (
        select country_lastfm, country, listeners_lastfm, tags_lastfm
        from user_aashabokov.artists
        lateral view explode(split(country_lastfm, "; ")) country_table as country
        where tags_lastfm like '%anime%'
    ) as anime_table
    group by country
    order by cnt desc
    limit 10
) as res_df;
