BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "korisnik" (
	"id"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ime"	TEXT,
	"prezime"	TEXT,
	"email"	TEXT,
	"username"	TEXT,
	"password"	TEXT,
	"slika" TEXT
);
INSERT INTO korisnik VALUES (null,'Vedran','Ljubović','vljubovic@etf.unsa.ba','vedranlj','test', '');
INSERT INTO korisnik VALUES (null,'Amra','Delić','adelic@etf.unsa.ba','amrad','test', '');
INSERT INTO korisnik VALUES (null,'Tarik','Sijerčić','tsijercic1@etf.unsa.ba','tariks','test', '');
INSERT INTO korisnik VALUES (null,'Rijad','Fejzić','rfejzic1@etf.unsa.ba','rijadf','test', '');
COMMIT;