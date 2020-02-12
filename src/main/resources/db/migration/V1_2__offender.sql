create table CMS_STUB_OFFENDER
(
	CMS_STUB_OFFENDER_PK NUMBER not null
		constraint CMS_STUB_OFFENDER_PK
			primary key,
	CMS_PROB_NUMBER VARCHAR2(20),
	PRISON_NUMBER VARCHAR2(6),
	CRAMS_REF VARCHAR2(20),
	CMS_PRIS_NUMBER VARCHAR2(20),
	PNC VARCHAR2(20),
	CRO_NUMBER VARCHAR2(11),
	FAMILY_NAME VARCHAR2(100),
	FORENAME_1 VARCHAR2(100),
	FORENAME_2 VARCHAR2(100),
	FORENAME_3 VARCHAR2(100),
	GENDER_ELM VARCHAR2(50),
	GENDER_CAT VARCHAR2(50),
	ETHNIC_CATEGORY_ELM VARCHAR2(50),
	ETHNIC_CATEGORY_CAT VARCHAR2(50),
	DATE_OF_BIRTH DATE,
	LAO_INDICATOR VARCHAR2(1) default 'N'
		check ( lao_indicator IN ('N' , 'Y' )),
	ADDRESS_LINE_1 VARCHAR2(256),
	ADDRESS_LINE_2 VARCHAR2(256),
	ADDRESS_LINE_3 VARCHAR2(256),
	ADDRESS_LINE_4 VARCHAR2(256),
	ADDRESS_LINE_5 VARCHAR2(256),
	ADDRESS_LINE_6 VARCHAR2(256),
	ADDRESS_POSTCODE VARCHAR2(32),
	TELEPHONE_NUMBER VARCHAR2(32),
	NOTES_ID VARCHAR2(100),
	LANGUAGE_ELM VARCHAR2(50),
	LANGUAGE_CAT VARCHAR2(50),
	RELIGION_ELM VARCHAR2(50),
	RELIGION_CAT VARCHAR2(50),
	CELL_LOCATION VARCHAR2(100),
	SECURITY_CATEGORY VARCHAR2(100),
	DISCHARGE_ADDRESS_LINE_1 VARCHAR2(256),
	DISCHARGE_ADDRESS_LINE_2 VARCHAR2(256),
	DISCHARGE_ADDRESS_LINE_3 VARCHAR2(256),
	DISCHARGE_ADDRESS_LINE_4 VARCHAR2(256),
	DISCHARGE_ADDRESS_LINE_5 VARCHAR2(256),
	DISCHARGE_ADDRESS_LINE_6 VARCHAR2(256),
	DISCHARGE_POSTCODE VARCHAR2(32),
	DISCHARGE_TELEPHONE_NUMBER VARCHAR2(32),
	APPEALANT_IND VARCHAR2(1)
		check ( appealant_ind IN ('N' , 'Y' )),
	CURFEW_DATE DATE,
	PAROLE_ELIGIBILITY_DATE DATE,
	LICENCE_EXPIRY_DATE DATE,
	NON_PAROLE_DATE DATE,
	AUTOMATIC_RELEASE_DATE DATE,
	CONDITIONAL_RELEASE_DATE DATE,
	SENTENCE_EXPIRY_DATE DATE,
	RISK_OF_SELF_HARM VARCHAR2(100),
	FIRST_CONVICTION_AGE VARCHAR2(100),
	CT_AREA_EST_CODE VARCHAR2(100) not null,
	RECEPTION_CODE_ELM VARCHAR2(50),
	RECEPTION_CODE_CAT VARCHAR2(50),
	DISCHARGE_CODE_ELM VARCHAR2(50),
	DISCHARGE_CODE_CAT VARCHAR2(50),
	CHECKSUM VARCHAR2(4000),
	CREATE_DATE DATE,
	CREATE_USER VARCHAR2(100) not null,
	LASTUPD_DATE DATE,
	LASTUPD_USER VARCHAR2(100) not null
);



create table OFFENDER
(
	OFFENDER_PK NUMBER not null
		constraint OFFENDER_PK
			primary key,
	ADDRESS_LAST_UPDATE_DATE DATE,
	ADDRESS_LINE_1 VARCHAR2(256),
	ADDRESS_LINE_2 VARCHAR2(256),
	ADDRESS_LINE_3 VARCHAR2(256),
	ADDRESS_LINE_4 VARCHAR2(256),
	ADDRESS_LINE_5 VARCHAR2(256),
	ADDRESS_LINE_6 VARCHAR2(256),
	ADDRESS_POSTCODE VARCHAR2(32),
	TELEPHONE_NUMBER VARCHAR2(32),
	DATE_OF_BIRTH DATE,
	TITLE_ELM VARCHAR2(50),
	TITLE_CAT VARCHAR2(50) default 'TITLE',
	FAMILY_NAME VARCHAR2(100),
	NAME_SOUNDSLIKE VARCHAR2(30),
	FORENAME_1 VARCHAR2(100),
	FORENAME_2 VARCHAR2(100),
	FORENAME_3 VARCHAR2(100),
	LIMITED_ACCESS_OFFENDER VARCHAR2(1) default 'N' not null,
	PNC VARCHAR2(20),
	CMS_PROB_NUMBER VARCHAR2(20),
	CMS_PRIS_NUMBER VARCHAR2(20),
	LEGACY_CMS_PROB_NUMBER VARCHAR2(20),
	CRO_NUMBER VARCHAR2(12),
	PRISON_NUMBER VARCHAR2(6),
	MERGE_PNC_NUMBER VARCHAR2(20),
	DATE_OF_DEATH DATE,
	DATE_OF_DEPORTATION DATE,
	OFFENDER_MANAGED_IND VARCHAR2(1) default 'N' not null,
	PPO_IND VARCHAR2(1)
		check ( ppo_ind IN ('N' , 'Y' )),
	REMAND_IND VARCHAR2(1) default 'N' not null,
	REVIEW_DATE DATE,
	REVIEW_REMINDER_DATE DATE,
	TERM_REMINDER_DATE DATE,
	ETHNIC_CATEGORY_ELM VARCHAR2(50),
	ETHNIC_CATEGORY_CAT VARCHAR2(50),
	GENDER_ELM VARCHAR2(50),
	GENDER_CAT VARCHAR2(50),
	RISK_TO_OTHERS_ELM VARCHAR2(50),
	RISK_TO_OTHERS_CAT VARCHAR2(50),
	RISK_TO_SELF_ELM VARCHAR2(50),
	RISK_TO_SELF_CAT VARCHAR2(50),
	OFFENDER_HISTORIC_ELM VARCHAR2(50) default 'PENDING' not null,
	OFFENDER_HISTORIC_CAT VARCHAR2(50) default 'HISTORIC_STATUS' not null,
	LOCAL_AUTHORITY_ELM VARCHAR2(50),
	LOCAL_AUTHORITY_CAT VARCHAR2(50),
	ORIGINATING_CMS_ELM VARCHAR2(50),
	ORIGINATING_CMS_CAT VARCHAR2(50),
	DECEASED_IND VARCHAR2(1) default 'N' not null,
	NFA_IND VARCHAR2(1) default 'N' not null,
	CUSTODY_IND VARCHAR2(1) default 'N' not null,
	PSR_AREA VARCHAR2(100)
		constraint PSR_AREA
			references CT_AREA_EST,
	OWNING_PRISON_AREA VARCHAR2(100)
		constraint OWNING_PRISON_AREA
			references CT_AREA_EST,
	OWNING_PROBATION_AREA VARCHAR2(100)
		constraint OWNING_PROBATION_AREA
			references CT_AREA_EST,
	PRIMARY_LOCATION_AREA VARCHAR2(100) not null
		constraint CONTROLLING_OWNER
			references CT_AREA_EST,
	AWAITING_PROB_AREA VARCHAR2(100)
		constraint AWAITING_PROB_AREA
			references CT_AREA_EST,
	AWAITING_PRIS_AREA VARCHAR2(100)
		constraint AWAITING_PRIS_AREA
			references CT_AREA_EST,
	PHYSICAL_LOCATION_OTHER VARCHAR2(100),
	DELETED_DATE DATE,
	MERGED_IND VARCHAR2(1) default 'N' not null,
	CMS_MESSAGE CLOB,
	ICMSCLIREF VARCHAR2(60),
	ICMSSUPREF VARCHAR2(60),
	ICMSLINKREF VARCHAR2(60),
	CMS_EVENT_NUMBER NUMBER,
	POTENTIAL_DELETION_DATE DATE,
	RETAINED_IND VARCHAR2(1) default 'N' not null,
	RETAINED_REASON VARCHAR2(1024),
	LIFE_IND VARCHAR2(1) default 'N' not null,
	RELEASE_POT_DELETION_DATE DATE,
	RESTORE_PNC_IND VARCHAR2(1) default 'N' not null,
	PHYSICAL_LOCATION VARCHAR2(100)
		constraint PHYSICAL_LOCATION_OFF
			references CT_AREA_EST,
	MIG_GUID VARCHAR2(256),
	MIG_ID VARCHAR2(256),
	CHECKSUM VARCHAR2(4000),
	CREATE_DATE DATE,
	CREATE_USER VARCHAR2(100) not null,
	LASTUPD_DATE DATE,
	LASTUPD_USER VARCHAR2(100) not null,
	DISCHARGE_CODE_CAT VARCHAR2(50),
	DISCHARGE_CODE_ELM VARCHAR2(50),
	HOST_CPA VARCHAR2(100)
		constraint OFF_HOST_CPA
			references CT_AREA_EST,
	constraint ELM_FK100
		foreign key (TITLE_CAT, TITLE_ELM) references REF_ELEMENT,
	constraint ELM_FK101
		foreign key (ETHNIC_CATEGORY_CAT, ETHNIC_CATEGORY_ELM) references REF_ELEMENT,
	constraint ELM_FK102
		foreign key (GENDER_CAT, GENDER_ELM) references REF_ELEMENT,
	constraint ELM_FK103
		foreign key (RISK_TO_OTHERS_CAT, RISK_TO_OTHERS_ELM) references REF_ELEMENT,
	constraint ELM_FK104
		foreign key (RISK_TO_SELF_CAT, RISK_TO_SELF_ELM) references REF_ELEMENT,
	constraint ELM_FK105
		foreign key (OFFENDER_HISTORIC_CAT, OFFENDER_HISTORIC_ELM) references REF_ELEMENT,
	constraint ELM_FK106
		foreign key (LOCAL_AUTHORITY_CAT, LOCAL_AUTHORITY_ELM) references REF_ELEMENT,
	constraint ELM_FK99
		foreign key (ORIGINATING_CMS_CAT, ORIGINATING_CMS_ELM) references REF_ELEMENT,
	constraint OFF_ELM_FK
		foreign key (DISCHARGE_CODE_CAT, DISCHARGE_CODE_ELM) references REF_ELEMENT
);


create table OFFENDER_ALIAS
(
	OFFENDER_ALIAS_PK NUMBER not null
		constraint OFFENDER_ALIAS_PK
			primary key,
	DISPLAY_SORT NUMBER,
	ALIAS_DATE_OF_BIRTH DATE,
	ALIAS_FAMILY_NAME VARCHAR2(100),
	NAME_SOUNDSLIKE VARCHAR2(30),
	ALIAS_FORENAME_1 VARCHAR2(100),
	ALIAS_FORENAME_2 VARCHAR2(100),
	OFFENDER_PK NUMBER not null
		constraint OFF_OAL
			references OFFENDER
				on delete cascade,
	MIG_GUID VARCHAR2(256),
	MIG_ID VARCHAR2(256),
	CHECKSUM VARCHAR2(4000),
	CREATE_DATE DATE,
	CREATE_USER VARCHAR2(100) not null,
	LASTUPD_DATE DATE,
	LASTUPD_USER VARCHAR2(100) not null
);


create table OFFENDER_TEAM
(
	OFFENDER_TEAM_PK NUMBER not null
		constraint OFFENDER_TEAM_PK
			primary key,
	OFFENDER_PK NUMBER not null
		constraint OFF_OTE
			references OFFENDER
				on delete cascade,
	CT_AREA_EST_CODE VARCHAR2(100) not null,
	DIVISION_CODE VARCHAR2(100) not null,
	TEAM_CODE VARCHAR2(100) not null,
	TEAM_ACCESS_TYPE_ELM VARCHAR2(50) not null,
	TEAM_ACCESS_TYPE_CAT VARCHAR2(50) not null,
	START_DATE DATE not null,
	END_DATE DATE,
	MIG_GUID VARCHAR2(256),
	MIG_ID VARCHAR2(256),
	CHECKSUM VARCHAR2(4000),
	CREATE_DATE DATE,
	CREATE_USER VARCHAR2(100) not null,
	LASTUPD_DATE DATE,
	LASTUPD_USER VARCHAR2(100) not null,
	constraint ELM_FK109
		foreign key (TEAM_ACCESS_TYPE_CAT, TEAM_ACCESS_TYPE_ELM) references REF_ELEMENT,
	constraint OTE_TEA
		foreign key (DIVISION_CODE, TEAM_CODE, CT_AREA_EST_CODE) references TEAM
);

create table OFFENDER_USER
(
	OFFENDER_USER_PK NUMBER not null
		constraint OFFENDER_USER_PK
			primary key,
	OFFENDER_PK NUMBER not null
		constraint OFF_OFU
			references OFFENDER
				on delete cascade,
	OASYS_USER_CODE VARCHAR2(100) not null
		constraint OUS_OLU
			references OASYS_USER,
	USER_ACCESS_TYPE_ELM VARCHAR2(50) not null,
	USER_ACCESS_TYPE_CAT VARCHAR2(50) not null,
	START_DATE DATE not null,
	END_DATE DATE,
	TASK_PK NUMBER,
	MIG_GUID VARCHAR2(256),
	MIG_ID VARCHAR2(256),
	CHECKSUM VARCHAR2(4000),
	CREATE_DATE DATE,
	CREATE_USER VARCHAR2(100) not null,
	LASTUPD_DATE DATE,
	LASTUPD_USER VARCHAR2(100) not null,
	constraint ELM_FK110
		foreign key (USER_ACCESS_TYPE_CAT, USER_ACCESS_TYPE_ELM) references REF_ELEMENT
);






