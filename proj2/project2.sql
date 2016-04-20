/*Create hogwarts_dada table*/
CREATE TABLE ppalumbo.hogwarts_dada AS SELECT * FROM public.hogwarts3;

/*Description for hogwarts_dada*/
COMMENT ON COLUMN ppalumbo.hogwarts_dada.last IS 'Teacher Last Name';
COMMENT ON COLUMN ppalumbo.hogwarts_dada.first IS 'Teacher First Name';
COMMENT ON COLUMN ppalumbo.hogwarts_dada.start IS 'Teacher Start Year';
COMMENT ON COLUMN ppalumbo.hogwarts_dada.finish IS 'Teacher Finish Year';

/*Create hogwarts_houses table*/
CREATE TABLE ppalumbo.hogwarts_houses AS SELECT * FROM public.hogwarts2;
ALTER TABLE hogwarts_houses ADD PRIMARY KEY (house);

/*Description for hogwarts_dada*/
COMMENT ON COLUMN ppalumbo.hogwarts_houses.house IS 'House Name';
COMMENT ON COLUMN ppalumbo.hogwarts_houses.founder IS 'House Founder Full Name';
COMMENT ON COLUMN ppalumbo.hogwarts_houses.animal IS 'Animal on house crest'; 
COMMENT ON COLUMN ppalumbo.hogwarts_houses.relic IS 'Founders special relic';
COMMENT ON COLUMN ppalumbo.hogwarts_houses.colors IS 'House colors';


/*Create hogwarts_students table*/
CREATE TABLE ppalumbo.hogwarts_students AS SELECT * FROM public.hogwarts1;
UPDATE hogwarts_students SET start = NULL WHERE start = '?' ;
ALTER TABLE hogwarts_students ALTER COLUMN start TYPE numeric(4,0) USING start::numeric;
UPDATE hogwarts_students SET finish = NULL WHERE finish = '?' ;
ALTER TABLE hogwarts_students ALTER COLUMN finish TYPE numeric(4,0) USING finish::numeric;
ALTER TABLE hogwarts_students ADD PRIMARY KEY (last,first);
UPDATE hogwarts_students SET house = NULL WHERE house = '?' ;
UPDATE hogwarts_students SET house = 'Gryffindor' WHERE house = 'Griffindor' ;
ALTER TABLE hogwarts_students ADD FOREIGN KEY (house) REFERENCES hogwarts_houses (house);

/*Description for hogwarts_dada*/
COMMENT ON COLUMN ppalumbo.hogwarts_students.last IS 'Students Last Name';
COMMENT ON COLUMN ppalumbo.hogwarts_students.first IS 'Students First Name';
COMMENT ON COLUMN ppalumbo.hogwarts_students.start IS 'Students Start Year';
COMMENT ON COLUMN ppalumbo.hogwarts_students.finish IS 'Students Finish Year';
COMMENT ON COLUMN ppalumbo.hogwarts_students.house IS 'Students house';

/*PART II*/
/*Question 1*/
SELECT last, first FROM hogwarts_students WHERE start = 1991 AND house = 'Gryffindor';
/*Question 2*/
SELECT COUNT(*) FROM hogwarts_students WHERE house = 'Slytherin';
/*Question 3*/
SELECT MIN(start) FROM hogwarts_students; 
/*Question 4 */
SELECT COUNT(*) FROM hogwarts_students WHERE last IS NULL OR first IS NULL OR house IS NULL OR start IS NULL OR finish IS NULL;
/*Question 5*/
SELECT COUNT(*) FROM hogwarts_students WHERE last IS NOT NULL AND first IS NOT NULL AND house IS NOT NULL AND start IS NOT NULL AND finish IS NOT NULL;
/*Question 6*/
SELECT founder FROM hogwarts_houses WHERE house IN (SELECT house FROM hogwarts_students WHERE last = 'McDougal' AND first = 'Morag');
/*Question 7*/
SELECT last, first FROM hogwarts_students WHERE house IN (SELECT house FROM hogwarts_houses WHERE animal = 'Badger');
/*Question 8 */
SELECT house, COUNT(house) FROM hogwarts_students GROUP by house;
/*Question 9*/
SELECT first, last FROM hogwarts_students WHERE start IN (SELECT MIN(start) FROM hogwarts_students); 
/*Question 10*/
SELECT house, COUNT(*) FROM hogwarts_students WHERE start IN (SELECT start FROM hogwarts_dada WHERE last = 'Moody' AND first = 'Alastor') GROUP BY house;
/*Question 11 TODO*/
SELECT * FROM hogwarts_students WHERE start < (SELECT start FROM hogwarts_dada WHERE last = 'Lockhart' AND first = 'Gilderoy') AND finish > (SELECT start FROM hogwarts_dada WHERE last = 'Lockhart' AND first = 'Gilderoy');
/*Question 12*/
 SELECT first, last FROM hogwarts_dada WHERE last IN (SELECT last FROM hogwarts_students);
/*Question 13*/
SELECT COUNT(last), SUBSTRING(last from 1 for 1) FROM hogwarts_students WHERE last IS NULL OR first IS NULL OR house IS NULL OR start IS NULL OR finish IS NULL GROUP BY substring(last from 1 for 1);

