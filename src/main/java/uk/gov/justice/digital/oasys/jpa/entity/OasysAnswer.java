package uk.gov.justice.digital.oasys.jpa.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OASYS_ANSWER")
public class OasysAnswer {
    @Id
    @Column(name = "OASYS_ANSWER_PK")
    private Long oasysAnswerPk;
    @Column(name = "MIG_GUID")
    private String migGuid;
    @Column(name = "MIG_ID")
    private String migId;
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

    @OneToOne
    @JoinColumn(name = "OASYS_QUESTION_PK")
    private OasysQuestion oasysQuestion;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "REF_ASS_VERSION_CODE", referencedColumnName = "REF_ASS_VERSION_CODE"),
            @JoinColumn(name = "VERSION_NUMBER", referencedColumnName = "VERSION_NUMBER"),
            @JoinColumn(name = "REF_SECTION_CODE", referencedColumnName = "REF_SECTION_CODE"),
            @JoinColumn(name = "REF_QUESTION_CODE", referencedColumnName = "REF_QUESTION_CODE"),
            @JoinColumn(name = "REF_ANSWER_CODE", referencedColumnName = "REF_ANSWER_CODE")
    })
    private RefAnswer refAnswer;

    public boolean hasRefAnswer(){
        return refAnswer != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof OasysAnswer))
            return false;

        OasysAnswer other = (OasysAnswer) o;
        return getOasysAnswerPk() != null &&
                getOasysAnswerPk().equals(other.getOasysAnswerPk());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
