package uk.gov.justice.digital.oasys.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Answer {
    private Long refAnswerId;
    private String refAnswerCode;
    private Long oasysAnswerId;
    private String staticText;
    private String freeformText;
    private Integer ogpScore;
    private Integer ovpScore;
    private Integer qaRawScore;
}

