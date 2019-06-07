package uk.gov.justice.digital.oasys.jpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Time;

@Data
@Entity
@Table(name = "OBJECTIVE")
public class Objective {
    @Column(name = "OBJECTIVE_UK")
    private Long objectiveUk;
    @Id
    @Column(name = "OBJECTIVE_CODE")
    private String objectiveCode;
    @Column(name = "OBJECTIVE_DESC")
    private String objectiveDesc;
    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "OBJECTIVE_HEADING_CAT", referencedColumnName = "REF_CATEGORY_CODE"),
            @JoinColumn(name = "OBJECTIVE_HEADING_ELM", referencedColumnName = "REF_ELEMENT_CODE")
    })
    private RefElement objectiveHeading;

    @Column(name = "START_DATE")
    private Time startDate;
    @Column(name = "END_DATE")
    private Time endDate;
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

}
