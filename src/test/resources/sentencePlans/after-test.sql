DELETE FROM OFFENDER where OFFENDER_PK = 100;

DELETE FROM OASYS_ANSWER where OASYS_QUESTION_PK in (SELECT OASYS_QUESTION_PK from OASYS_QUESTION where OASYS_SECTION_PK in (SELECT OASYS_SECTION_PK from OASYS_SECTION where OASYS_SET_PK = 100) );
DELETE FROM OASYS_QUESTION where OASYS_SECTION_PK in (SELECT OASYS_SECTION_PK from OASYS_SECTION where OASYS_SET_PK = 100);

DELETE FROM BASIC_SENTENCE_PLAN_OBJ where OASYS_SET_PK = 100;
DELETE FROM BASIC_SENTENCE_PLAN_OBJ where OASYS_SET_PK = 200;

DELETE FROM SSP_OBJECTIVES_IN_SET where OASYS_SET_PK = 100;
DELETE FROM SSP_OBJECTIVES_IN_SET where OASYS_SET_PK = 200;

DELETE FROM SSP_OBJECTIVES_IN_SET where OASYS_SET_PK = 100;
DELETE FROM SSP_OBJECTIVES_IN_SET where OASYS_SET_PK = 200;
DELETE FROM SSP_OBJECTIVE where SSP_OBJECTIVES_IN_SET_PK in (317298, 317297, 317296);
DELETE FROM SSP_OBJECTIVE_MEASURE where SSP_OBJECTIVES_IN_SET_PK in (317298, 317297, 317296);
DELETE FROM SSP_OBJ_INTERVENE_PIVOT where SSP_INTERVENTION_IN_SET_PK in (86942060,86941064, 86943056, 86944052, 86945048, 86946044);
DELETE FROM SSP_INTERVENTION_IN_SET where SSP_INTERVENTION_IN_SET_PK in (86942060,86941064, 86943056, 86944052, 86945048, 86946044);
DELETE FROM SSP_INTERVENTION_MEASURE where SSP_INTERVENTION_IN_SET_PK in (86942060,86941064, 86943056, 86944052, 86945048, 86946044);
DELETE FROM  SSP_WHO_DO_WORK_PIVOT where SSP_INTERVENTION_IN_SET_PK in (86942060,86941064, 86943056, 86944052, 86945048, 86946044);
DELETE FROM SSP_CRIM_NEED_OBJ_PIVOT where SSP_OBJECTIVES_IN_SET_PK in (317298, 317297, 317296);

DELETE FROM OASYS_SECTION where OASYS_SET_PK = 100;
DELETE FROM OASYS_SECTION where OASYS_SET_PK = 200;

DELETE FROM OASYS_SET where OASYS_SET_PK = 100;
DELETE FROM OASYS_SET where OASYS_SET_PK = 200;

DELETE FROM OASYS_ASSESSMENT_GROUP where OASYS_ASSESSMENT_GROUP_PK = 100;
