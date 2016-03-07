/* Peter Palumbo*/
/* Project 4*/

/* Create the music database*/
CREATE TABLE ppalumbo.music(
	artist_type text,
	artist_name text,
	artist_id serial,
	album_title text,
	album_year numeric(4,0),
	album_id serial,
	album_genre text,
	label_name text,
	label_location text,
	label_id serial,
	song_name text,
	song_number integer);

/*Define Columns*/
COMMENT ON COLUMN ppalumbo.music.artist_type IS 'Type of Artist';
COMMENT ON COLUMN ppalumbo.music.artist_name IS 'Name of Artist';
