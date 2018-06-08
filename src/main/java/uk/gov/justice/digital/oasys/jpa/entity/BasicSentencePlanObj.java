package uk.gov.justice.digital.oasys.jpa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;
import java.util.Objects;

@Data
@Entity
@Table(name = "BASIC_SENTENCE_PLAN_OBJ")
public class BasicSentencePlanObj {
    @Id
    @Column(name = "BASIC_SENT_PLAN_OBJ_PK")
    private Long basicSentPlanObjPk;
    @Column(name = "DISPLAY_SORT")
    private Long displaySort;
    @Column(name = "INCLUDE_IN_PLAN_IND")
    private String includeInPlanInd;
    @Column(name = "OFFENCE_BEHAV_LINK_ELM")
    private String offenceBehavLinkElm;
    @Column(name = "OFFENCE_BEHAV_LINK_CAT")
    private String offenceBehavLinkCat;
    @Column(name = "OBJECTIVE_TEXT")
    private String objectiveText;
    @Column(name = "MEASURE_TEXT")
    private String measureText;
    @Column(name = "WHAT_WORK_TEXT")
    private String whatWorkText;
    @Column(name = "WHO_WILL_DO_WORK_TEXT")
    private String whoWillDoWorkText;
    @Column(name = "TIMESCALES_TEXT")
    private String timescalesText;
    @Column(name = "OASYS_SET_PK")
    private Long oasysSetPk;
    @Column(name = "DATE_OPENED")
    private Time dateOpened;
    @Column(name = "DATE_COMPLETED")
    private Time dateCompleted;
    @Column(name = "PROBLEM_AREA_COMP_IND")
    private String problemAreaCompInd;
    @Column(name = "MIG_GUID")
    private String migGuid;
    @Column(name = "MIG_ID")
    private String migId;
    @Column(name = "CHECKSUM")
    private String checksum;
    @Column(name = "CREATE_DATE")
    private Time createDate;
    @Column(name = "CREATE_USER")
    private String createUser;
    @Column(name = "LASTUPD_DATE")
    private Time lastupdDate;
    @Column(name = "LASTUPD_USER")
    private String lastupdUser;
    @Column(name = "CF_LAST_BCS_INT")
    private Long cfLastBcsInt;
    @Column(name = "CF_ORIG_BCS_INT")
    private Long cfOrigBcsInt;

}
