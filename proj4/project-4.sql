/* Peter Palumbo*/
/* Project 4*/

/*PART 1*/
/* Create the artist database*/
DROP TABLE IF EXISTS ppalumbo.artist CASCADE;
CREATE TABLE ppalumbo.artist(
	artist_type text,
	artist_name text,
	artist_id serial PRIMARY KEY);

/*Create the Album Table*/

DROP TABLE IF EXISTS ppalumbo.album CASCADE;
CREATE TABLE ppalumbo.album(
	album_id serial PRIMARY KEY,
	album_title text,
	album_year numeric(4,0),
	album_genre text);

/*Create the Label Table*/

DROP TABLE IF EXISTS ppalumbo.label CASCADE;
CREATE TABLE ppalumbo.label(
	label_id serial,
	label_name text,
	label_location text);

/*Create the Song Table*/

DROP TABLE IF EXISTS ppalumbo.songs CASCADE;
CREATE TABLE ppalumbo.songs(
	song_name text,
	sone_number integer);

/*Create the Band Table*/

DROP TABLE IF EXISTS ppalumbo.band_member CASCADE;
CREATE TABLE ppalumbo.band_member(
	band_member_id integer REFERENCES ppalumbo.artist(artist_id),
	band_group_id integer REFERENCES ppalumbo.artist(artist_id),
	band_start numeric(4,0),
	band_end numeric(4,0),
	PRIMARY KEY(band_member_id, band_group_id));

/*Define Columns*/
COMMENT ON COLUMN ppalumbo.music.artist_type IS 'Type of Artist';
COMMENT ON COLUMN ppalumbo.music.artist_name IS 'Name of Artist';

/*Create Keys
ALTER TABLE ppalumbo.artist ADD PRIMARY KEY (artist_id);

ALTER TABLE ppalumbo.album ADD PRIMARY KEY (album_id);
*/

/*PART 2*/
INSERT INTO ppalumbo.artist(artist_type, artist_name)(
	(SELECT DISTINCT 'Group', artist_name FROM project4 WHERE artist_type LIKE 'Group' UNION SELECT DISTINCT 'Person', member_name FROM project4)
	UNION
	(SELECT DISTINCT 'Person', artist_name FROM project4 WHERE artist_type LIKE 'Person'));

/*Populate bands*/
INSERT INTO ppalumbo.band_member(band_group_id, band_member_id, band_start, band_end)
	SELECT DISTINCT ON (groupid, memberid) bands.id AS groupid, ppalumbo.artist.artist_id AS memberid, member_begin_year, member_end_year FROM
	(SELECT artist_id,
		member_name,
		artist_name,
		band_start,
		band_end FROM ppalumbo.artist INNER JOIN
	(SELECT member_name,
		artist_name,
		member_begin_year,
		member_end_year from project4)
	tmp ON tmp.artist_name = ppalumbo.artist.artist_name)
	bands INNER JOIN ppalumbo.artist ON ppalumbo.artist.name = bands.member_name;

/*Populate Labels Table*/
INSERT INTO ppalumbo.label(label_name, label_location) SELECT DISTINCT label, headquarters FROM project4;

/*populate album table and album names*/
INSERT INTO ppalumbo.album
