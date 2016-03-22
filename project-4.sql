/* Peter Palumbo*/
/* Project 4*/

/* Create the artist database*/
CREATE TABLE ppalumbo.artist(
	artist_type text,
	artist_name text,
	artist_id serial);

/*Create the Album Table*/
CREATE TABLE ppalumbo.album(
	album_id serial,
	album_title text,
	album_year numeric(4,0),
	album_genre text);

/*Create the Label Table*/
CREATE TABLE ppalumbo.label(
	label_id serial,
	label_name text,
	label_location text);

/*Create the Song Table*/
CREATE TABLE ppalumbo.songs(
	song_name text,
	sone_number integer);

/*Create the Band Table*/
CREATE TABLE ppalumbo.band(
	band_start numeric(4,0),
	band_end numeric(4,0));

/*Define Columns*/
COMMENT ON COLUMN ppalumbo.music.artist_type IS 'Type of Artist';
COMMENT ON COLUMN ppalumbo.music.artist_name IS 'Name of Artist';

/*Create Keys*/
ALTER TABLE ppalumbo.artist ADD PRIMARY KEY (artist_id);

ALTER TABLE ppalumbo.album ADD PRIMARY KEY (album_id);


