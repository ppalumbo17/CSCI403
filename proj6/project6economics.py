##############################
#Peter Palumbo
#4-25-16
#CSCI403 Porject 6
##############################

import getpass
import pg8000
import csv

login = input('login: ')
secret = getpass.getpass('password: ')

credentials = {'user' : login,
		'password' : secret,
		'database' : 'csci403',
		'host' : 'flowers.mines.edu'}

try:
	db = pg8000.connect(**credentials)
except pg8000.Error as e:
	print('Database error: ', e.args[2])
	exit()

cursor=db.cursor()


#add economics tables to db

create_query = "CREATE TABLE economics (id serial, PRIMARY KEY(id));"
try:
	cursor.execute(create_query)
	db.commit()
except pg8000.Error as e:
	print("Create Table Error: ", e.args[2])
	db.rollback()


#read csv file
with open('american_community_survey_nbrhd_2006_2010.csv') as csvfile:
	reader = csv.reader(csvfile);
	reader.next();
	for row in reader:
		for (

