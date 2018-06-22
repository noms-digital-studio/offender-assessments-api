package uk.gov.justice.digital.oasys.api;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Assessment {
    private Long oasysSetId;
    private String assessmentType;
    private AssessmentVersion assessmentVersion;
    private LocalDateTime assessmentDateTime;
    private boolean completed;
    private boolean voided;
    private List<Section> sections;
}
