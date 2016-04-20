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
COMMENT ON COLUMN ppalumbo.music.album_title IS 'Name of Album';
COMMENT ON COLUMN ppalumbo.music.album_year IS 'Year Album Was Produced';
COMMENT ON COLUMN ppalumbo.music.album_genre IS 'The Genre of The Album';
COMMENT ON COLUMN ppalumbo.music.label_name IS 'Production Company of Album';
COMMENT ON COLUMN ppalumbo.music.label_location IS 'Production Company Location';
COMMENT ON COLUMN ppalumbo.music.song_name IS 'Name of the Song';
COMMENT ON COLUMN ppalumbo.music.song_number IS 'Track Number of Song on Album';

/*Create Keys*/

