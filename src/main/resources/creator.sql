CREATE TABLE public."Donation"
(
  iddonation integer NOT NULL,
  cnp bigint,
  quantity real,
  status integer,
  idblood integer,
  CONSTRAINT "Donation_pkey" PRIMARY KEY (iddonation)
)