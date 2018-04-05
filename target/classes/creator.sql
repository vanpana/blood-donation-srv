--DROP TABLE public."Used";
--DROP TABLE public."Patient";
--DROP TABLE public."Donation";
--DROP TABLE public."Request";
--DROP TABLE public."Status";
--DROP TABLE public."Donator";
--DROP TABLE public."Plasma";
--DROP TABLE public."RedCells";
--DROP TABLE public."Thrombocites";
--DROP TABLE public."Blood";


CREATE TABLE public."Blood"
(
  idblood serial NOT NULL,
  bloodtype character varying COLLATE pg_catalog."default",
  receivedate date,
  CONSTRAINT "Blood_pkey" PRIMARY KEY (idblood)
);


CREATE TABLE public."Plasma"
(
  idplasma serial NOT NULL,
  idblood integer,
  expirationdate date,
  CONSTRAINT "Plasma_pkey" PRIMARY KEY (idplasma),
  CONSTRAINT "Plasma_idblood_fkey" FOREIGN KEY (idblood)
  REFERENCES public."Blood" (idblood) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
);


CREATE TABLE public."RedCells"
(
  idredcells serial NOT NULL,
  idblood integer,
  expirationdate date,
  CONSTRAINT "RedCells_pkey" PRIMARY KEY (idredcells),
  CONSTRAINT "RedCells_idblood_fkey" FOREIGN KEY (idblood)
  REFERENCES public."Blood" (idblood) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
);


CREATE TABLE public."Thrombocites"
(
  expirationdate date,
  idthrombocite serial NOT NULL,
  idblood integer,
  CONSTRAINT "Thrombocites_pkey" PRIMARY KEY (idthrombocite),
  CONSTRAINT "Thrombocites_idblood_fkey" FOREIGN KEY (idblood)
  REFERENCES public."Blood" (idblood) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
);


CREATE TABLE public."Donator"
(
  cnp bigint NOT NULL,
  name character varying COLLATE pg_catalog."default",
  bloodtype character varying COLLATE pg_catalog."default",
  CONSTRAINT "Donator_pkey" PRIMARY KEY (cnp)
);


CREATE TABLE public."Status"
(
  idstatus serial NOT NULL,
  description character varying COLLATE pg_catalog."default",
  CONSTRAINT "Status_pkey" PRIMARY KEY (idstatus)
);


CREATE TABLE public."Request"
(
  idrequest serial primary key ,
  quantity real,
  urgency integer
);


CREATE TABLE public."Donation"
(
  iddonation serial NOT NULL,
  cnp bigint,
  quantity real,
  status integer,
  idblood integer,
  CONSTRAINT "Donation_pkey" PRIMARY KEY (iddonation),
  CONSTRAINT "Donation_cnp_fkey" FOREIGN KEY (cnp)
  REFERENCES public."Donator" (cnp) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT "Donation_idblood_fkey" FOREIGN KEY (idblood)
  REFERENCES public."Blood" (idblood) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT "Donation_status_fkey" FOREIGN KEY (status)
  REFERENCES public."Status" (idstatus) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
);

CREATE TABLE public."Patient"
(
  cnp bigint NOT NULL,
  name character varying COLLATE pg_catalog."default",
  CONSTRAINT "Patient_pkey" PRIMARY KEY (cnp)
);

CREATE TABLE public."Used"
(
  iddonation integer NOT NULL,
  patientcnp bigint,
  quantity real,
  CONSTRAINT "Used_pkey" PRIMARY KEY (iddonation),
  CONSTRAINT "Used_iddonation_fkey" FOREIGN KEY (iddonation)
  REFERENCES public."Donation" (iddonation) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION,
  CONSTRAINT "Used_patientcnp_fkey" FOREIGN KEY (patientcnp)
  REFERENCES public."Patient" (cnp) MATCH SIMPLE
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
)