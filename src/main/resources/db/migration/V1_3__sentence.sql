create table SENTENCE
(
	SENTENCE_UK NUMBER not null
		constraint SENTENCE_UN
			unique,
	SENTENCE_CTID VARCHAR2(50),
	SENTENCE_CODE VARCHAR2(100) not null
		constraint SENTENCE_PK
			primary key,
	SENTENCE_DESC VARCHAR2(4000),
	DISPLAY_SORT NUMBER,
	CUSTODIAL_IND VARCHAR2(1) default 'N' not null,
	CJA_IND VARCHAR2(1) default 'N' not null,
	START_DATE DATE not null,
	END_DATE DATE,
	ORDER_TYPE_ELM VARCHAR2(50) not null,
	ORDER_TYPE_CAT VARCHAR2(50) not null,
	CHECKSUM VARCHAR2(4000),
	CREATE_DATE DATE,
	CREATE_USER VARCHAR2(100) not null,
	LASTUPD_DATE DATE,
	LASTUPD_USER VARCHAR2(100) not null,
	constraint ELM_FK15
		foreign key (ORDER_TYPE_CAT, ORDER_TYPE_ELM) references REF_ELEMENT
);





