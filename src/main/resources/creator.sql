DROP TABLE "Personnel";
DROP TABLE "Used";
DROP TABLE "Patient";
DROP TABLE "Donation";
DROP TABLE "Request";
DROP TABLE "Status";
DROP TABLE "Donator";
DROP TABLE "Thrombocites";
DROP TABLE "RedCells";
DROP TABLE "Plasma";
DROP TABLE "Blood";
DROP TABLE "Location";
DROP TABLE "Doctor";

-- Set the date style to day.month.year
SET DateStyle TO DMY;

-- DROP TABLE public."Location";

CREATE TABLE public."Location"
(
  name       character varying COLLATE pg_catalog."default",
  x          double precision,
  y          double precision,
  idlocation integer primary key
);

INSERT INTO public."Location" (name , x , y , idlocation)
VALUES ('Cluj-Napoca', 46, 23, 1),
       ('Baia Mare', 47, 23, 2),
       ('Bucuresti', 44, 26, 3),
       ('Arad', 46, 21, 4),
       ('Timisoara', 45, 21, 5);

CREATE TABLE public."Blood"
(
  idblood      serial NOT NULL,
  bloodtype    character varying COLLATE pg_catalog."default",
  receiveddate TIMESTAMP DEFAULT now(),
  CONSTRAINT "Blood_pkey" PRIMARY KEY (idblood)
);

CREATE TABLE public."Plasma"
(
  idplasma       serial NOT NULL,
  idblood        integer,
  quantity       REAL,
  expirationdate TIMESTAMP DEFAULT now(),
  CONSTRAINT "Plasma_pkey" PRIMARY KEY (idplasma),
  CONSTRAINT "Plasma_idblood_fkey" FOREIGN KEY (idblood)
  REFERENCES public."Blood" (idblood) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE CASCADE
);


CREATE TABLE public."RedCells"
(
  idredcells     serial NOT NULL,
  idblood        integer,
  quantity       REAL,
  expirationdate TIMESTAMP DEFAULT now(),
  CONSTRAINT "RedCells_pkey" PRIMARY KEY (idredcells),
  CONSTRAINT "RedCells_idblood_fkey" FOREIGN KEY (idblood)
  REFERENCES public."Blood" (idblood) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE CASCADE
);


CREATE TABLE public."Thrombocites"
(
  expirationdate TIMESTAMP DEFAULT now(),
  idthrombocites serial NOT NULL,
  quantity       REAL,
  idblood        integer,
  CONSTRAINT "Thrombocites_pkey" PRIMARY KEY (idthrombocites),
  CONSTRAINT "Thrombocites_idblood_fkey" FOREIGN KEY (idblood)
  REFERENCES public."Blood" (idblood) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE CASCADE
);


CREATE TABLE public."Donator"
(
  iddonator serial      NOT NULL,
  cnp       VARCHAR(13) NOT NULL UNIQUE,
  name      character varying COLLATE pg_catalog."default",
  bloodtype character varying COLLATE pg_catalog."default",
  email     character varying COLLATE pg_catalog."default",
  password  character varying COLLATE pg_catalog."default",
  token     character varying COLLATE pg_catalog."default",
  location  varchar(50),
  CONSTRAINT "Donator_pkey" PRIMARY KEY (iddonator)
);


CREATE TABLE public."Status"
(
  idstatus    serial NOT NULL,
  description character varying COLLATE pg_catalog."default",
  CONSTRAINT "Status_pkey" PRIMARY KEY (idstatus)
);

INSERT INTO "Status" (idstatus , description)
VALUES (0, 'Collected'),
       (1, 'Testing'),
       (2, 'Approved'),
       (3, 'Declined');

CREATE TABLE public."Doctor"
(
  iddoctor SERIAL NOT NULL,
  name     CHARACTER VARYING COLLATE pg_catalog."default",
  email    CHARACTER VARYING COLLATE pg_catalog."default",
  password CHARACTER VARYING COLLATE pg_catalog."default",
  token    CHARACTER VARYING COLLATE pg_catalog."default",
  CONSTRAINT "Doctor._pkey" PRIMARY KEY (iddoctor)
);


CREATE TABLE public."Request"
(
  idrequest     serial primary key,
  quantity      real,
  urgency       integer,
  bloodPartType VARCHAR(50),
  locationId    int,
  bloodType     VARCHAR(50),
  doctorId      int,
  CONSTRAINT "Request_locationId_fkey" FOREIGN KEY (locationId)
  REFERENCES public."Location" (idlocation) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE CASCADE,
  CONSTRAINT "Request_doctorId_fkey" FOREIGN KEY (doctorId)
  REFERENCES public."Doctor" (iddoctor) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE CASCADE
);


CREATE TABLE public."Donation"
(
  iddonation serial NOT NULL,
  cnp        VARCHAR(13),
  quantity   real,
  status     integer,
  idblood    integer,
  idlocation INTEGER,
  CONSTRAINT "Donation_pkey" PRIMARY KEY (iddonation),
  CONSTRAINT "Donation_cnp_fkey" FOREIGN KEY (cnp)
  REFERENCES public."Donator" (cnp) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE CASCADE,
  CONSTRAINT "Donation_idlocation_fkey" FOREIGN KEY (idlocation)
  REFERENCES public."Location" (idlocation) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT "Donation_idblood_fkey" FOREIGN KEY (idblood)
  REFERENCES public."Blood" (idblood) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE CASCADE,
  CONSTRAINT "Donation_status_fkey" FOREIGN KEY (status)
  REFERENCES public."Status" (idstatus) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE CASCADE
);

CREATE TABLE public."Patient"
(
  id   serial      NOT NULL,
  cnp  VARCHAR(13) NOT NULL,
  name character varying COLLATE pg_catalog."default",
  CONSTRAINT "Patient_pkey" PRIMARY KEY (id)
);

CREATE TABLE public."Used"
(
  idused serial NOT NULL,
  iddonation integer NOT NULL,
  requestId int,
  patientid INTEGER,
  quantity real,
  bloodPartType VARCHAR(50),
  requestid     INTEGER,
  CONSTRAINT "Used_pkey" PRIMARY KEY (iddonation),
  CONSTRAINT "Used_iddonation_fkey" FOREIGN KEY (iddonation)
  REFERENCES public."Donation" (iddonation) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE CASCADE,
  CONSTRAINT "Used_patientcnp_fkey" FOREIGN KEY (patientid)
  REFERENCES public."Patient" (id) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE CASCADE,
  CONSTRAINT "Used_requestId_fkey" FOREIGN KEY (requestId)
  REFERENCES public."Request" (idrequest) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE CASCADE
);

CREATE TABLE public."Personnel"
(
  idpersonnel SERIAL NOT NULL,
  name        CHARACTER VARYING COLLATE pg_catalog."default",
  email       CHARACTER VARYING COLLATE pg_catalog."default",
  password    CHARACTER VARYING COLLATE pg_catalog."default",
  token       CHARACTER VARYING COLLATE pg_catalog."default",
  CONSTRAINT "Personnel_pkey" PRIMARY KEY (idpersonnel)
);

INSERT INTO public."Patient" (cnp , name)
VALUES ('2970107127098', 'Popa Anastasia'),
       ('1890501126578', 'Abrudan Andrei'),
       ('2991010146789', 'Ile Amalia'),
       ('2901211678123', 'Morar Irina'),
       ('1670126125816', 'Ilisei Victor');


INSERT INTO public."Donator" (cnp , name , bloodtype , email , password , token, location)
VALUES ('2970106123456', 'Prodan Bianca', '0', 'bianca@yahooo.com', 'bp1234', '123abMnOiy', 'Cluj'),
       ('2970421167567', 'Pop Claudia', 'AB', 'claudia@yahooo.com', 'cp1234', 'aaTo945P12', 'Dunarii, Cluj'),
       ('1961028125816', 'Opruta David', 'B', 'david@yahooo.com', 'od1234', 'lmnop874oA', 'Bucharest'),
       ('1661010128765', 'Panaite Dorinel', 'A', 'dorinel@yahooo.com', 'dp1234', '17Bopo0d56', 'Floresti'),
       ('1991211125877', 'Paius Teodor', 'A', 'teodor@yahooo.com', 'tp1234', '9isD57Bls1', 'Piezisa, Cluj'),
       ('1890106125899', 'Podariu Catalin', '0', 'catalin@yahooo.com', 'cp1234', 'bpm67C34nt', 'Dorobantilor, Cluj');


INSERT INTO public."Personnel" (idpersonnel , name , email , password , token)
VALUES (1, 'Pop Bianca', 'popbianca@yahoo.com', 'bp1234', '123abMnOiy'),
       (2, 'Ion Dan', 'ion1974@yahoo.com', 'cp1234', 'aaTo945P12'),
       (3, 'Dragomir Irina', 'dirina@yahoo.com', 'tp1234', '9isD57Bls1');

INSERT INTO public."Doctor" (name , email , password , token)
VALUES ('David', 'da@yahoo.com', 'da', '');

INSERT INTO public."Request" (quantity , urgency , bloodPartType , bloodType , locationId , doctorId)
VALUES (200, 1, 'Plasma', 'A', 1, 1),
       (400, 2, 'Blood', 'A', 1, 1),
       (200, 1, 'Thrombocites', 'A', 1, 1),
       (600, 3, 'Plasma', 'A', 1, 1);


INSERT INTO public."Blood" (bloodtype , receiveddate)
VALUES ('AB', '5.04.2018'),
       ('0', '23.04.2018'),
       ('A', '24.03.2018');


INSERT INTO public."RedCells" (idblood , expirationdate)
VALUES (1, '10.04.2018');


INSERT INTO public."Plasma" (idblood , expirationdate)
VALUES (1, '12.04.2018');


INSERT INTO public."Thrombocites" (expirationdate , idblood)
VALUES ('8.04.2018', 1);


INSERT INTO public."Donation" (cnp , quantity , status , idblood)
VALUES ('2970106123456', 800, 3, 2),
       ('2970421167567', 700, 2, 1),
       ('1991211125877', 750, 1, 3);


INSERT INTO public."Used" (iddonation , patientid , quantity , requestid)
VALUES (1, 1, 200, 1);
