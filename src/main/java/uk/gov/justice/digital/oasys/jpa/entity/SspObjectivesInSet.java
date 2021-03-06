package uk.gov.justice.digital.oasys.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "SSP_OBJECTIVES_IN_SET")
@Builder
public class SspObjectivesInSet {
    @Id
    @Column(name = "SSP_OBJECTIVES_IN_SET_PK")
    private Long sspObjectivesInSetPk;
    @Column(name = "COPIED_FORWARD_INDICATOR")
    private String copiedForwardIndicator;
    @Column(name = "HOW_PROGRESS_MEASURED")
    private String howProgressMeasured;
    @Column(name = "DISPLAY_SORT")
    private Long displaySort;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "OBJECTIVE_TYPE_CAT", referencedColumnName = "REF_CATEGORY_CODE"),
            @JoinColumn(name = "OBJECTIVE_TYPE_ELM", referencedColumnName = "REF_ELEMENT_CODE")
    })
    private RefElement objectiveType;

    @Column(name = "LAST_UPDATE_DATE")
    private LocalDateTime lastUpdateDate;
    @Column(name = "OASYS_SET_PK")
    private Long oasysSetPk;
    @Column(name = "MIG_GUID")
    private String migGuid;
    @Column(name = "MIG_ID")
    private String migId;
    @Column(name = "CF_LAST_ASSMT_OBJ")
    private Long cfLastAssmtObj;
    @Column(name = "CF_ORIG_ASSMT_OBJ")
    private Long cfOrigAssmtObj;
    @Column(name = "CHECKSUM")
    private String checksum;
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
    @Column(name = "CREATE_USER")
    private String createUser;
    @Column(name = "LASTUPD_DATE")
    private LocalDateTime lastupdDate;
    @Column(name = "LASTUPD_USER")
    private String lastupdUser;

    @OneToMany
    @JoinColumn(name = "SSP_OBJECTIVES_IN_SET_PK", referencedColumnName = "SSP_OBJECTIVES_IN_SET_PK")
    private Set<SspObjIntervenePivot> sspObjIntervenePivots;

    @ManyToOne
    @JoinColumn(name = "SSP_OBJECTIVES_IN_SET_PK", referencedColumnName = "SSP_OBJECTIVES_IN_SET_PK",insertable=false, updatable=false)
    private SspObjective sspObjective;

    @ManyToOne
    @JoinColumn(name = "SSP_OBJECTIVES_IN_SET_PK", referencedColumnName = "SSP_OBJECTIVES_IN_SET_PK",insertable=false, updatable=false)
    private SspObjectiveMeasure sspObjectiveMeasure;

    @OneToMany
    @JoinColumn(name = "SSP_OBJECTIVES_IN_SET_PK", referencedColumnName = "SSP_OBJECTIVES_IN_SET_PK")
    private Set<SspCrimNeedObjPivot> sspCrimNeedObjPivots;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SspObjectivesInSet))
            return false;

        SspObjectivesInSet other = (SspObjectivesInSet) o;
        return getSspObjectivesInSetPk() != null &&
                getSspObjectivesInSetPk().equals(other.getSspObjectivesInSetPk());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
