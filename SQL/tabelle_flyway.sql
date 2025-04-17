create database flyway;
use flyway;

create table aeroporti(
id_aeroporto int auto_increment,
citta varchar(30),
nazione varchar(30),
num_piste int,
constraint id_aeroporto_pk primary key (id_aeroporto)
);

create table voli(
id_volo int auto_increment,
giorno varchar(50),
citta_partenza varchar(50),
ora_partenza timestamp,
citta_arrivo varchar(50),
ora_arrivo timestamp,
tipo_aereo char(5),
passeggeri int,
merci int,
constraint id_volo_pk primary key (id_volo),
constraint tipo_aereo_fk foreign key(tipo_aereo) references aereo(tipo_aereo)
);

create table aereo(
tipo_aereo char(5),
num_pass int,
qta_merci int,
constraint tipo_areo_pk primary key(tipo_aereo)

);

CREATE TABLE utenti (
    id_utente INT AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    PRIMARY KEY (id_utente)
);





