-- ArtScribe database schema (SQLite), exported from db/artscribe.db

CREATE TABLE "Bank" (
	"Bank_id"	INTEGER,
	"User_id"	INTEGER,
	"Transaction_date"	TEXT DEFAULT current_timestamp,
	PRIMARY KEY("Bank_id" AUTOINCREMENT),
	FOREIGN KEY("User_id") REFERENCES "User "("User_id")
);

CREATE TABLE "City" (
	"City name"	TEXT NOT NULL,
	"City ID"	INTEGER NOT NULL,
	PRIMARY KEY("City ID")
);

CREATE TABLE "Exhibit" (
	"Exhibit_id"	INTEGER,
	"Description"	TEXT,
	"Museum_id"	INTEGER,
	FOREIGN KEY("Museum_id") REFERENCES "Museum"("Museum_id"),
	PRIMARY KEY("Exhibit_id" AUTOINCREMENT)
);

CREATE TABLE "Museum" (
	"Museum_name"	TEXT NOT NULL,
	"Museum_city"	TEXT NOT NULL,
	"Address"	TEXT,
	"Work_hours"	TEXT,
	"Museum_id"	INTEGER,
	"Category"	TEXT,
	"Ticket_price"	REAL,
	"TrafficInfo"	TEXT,
	PRIMARY KEY("Museum_id" AUTOINCREMENT)
);

CREATE TABLE "Reservation" (
	"Reservation_id"	INTEGER,
	"Date"	TEXT,
	"Time"	TEXT,
	"Ticket_price"	REAL,
	"User_id"	INTEGER,
	"Museum_id"	INTEGER,
	FOREIGN KEY("User_id") REFERENCES "User "("User_id"),
	FOREIGN KEY("Museum_id") REFERENCES "Museum"("Museum_id"),
	PRIMARY KEY("Reservation_id" AUTOINCREMENT)
);

CREATE TABLE "Scan_QR" (
	"Description"	TEXT NOT NULL,
	"Id"	INTEGER NOT NULL,
	PRIMARY KEY("Id" AUTOINCREMENT),
	FOREIGN KEY("Id") REFERENCES "Exhibit"("Exhibit_id")
);

CREATE TABLE "User " (
	"Name"	TEXT NOT NULL,
	"Surname"	TEXT NOT NULL,
	"Email"	TEXT NOT NULL UNIQUE,
	"Phone_number"	TEXT NOT NULL UNIQUE,
	"Password"	TEXT NOT NULL,
	"User_id"	INTEGER,
	PRIMARY KEY("User_id" AUTOINCREMENT)
);

