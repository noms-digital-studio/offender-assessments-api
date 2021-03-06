package uk.gov.justice.digital.oasys.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import uk.gov.justice.digital.oasys.jpa.entity.RefAnswer;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class RefAnswerDto {
    Long refAnswerId;
    String refAnswerCode;
    Long refDisplaySort;

    public static List<RefAnswerDto> from(List<RefAnswer> refAnswers) {
        return Optional.ofNullable(refAnswers)
                .map(ra -> ra
                        .stream()
                        .map(RefAnswerDto::from)
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    private static RefAnswerDto from(RefAnswer refAnswer) {
        return Optional.ofNullable(refAnswer)
                .map(refAnswer1 -> RefAnswerDto.builder()
                        .refAnswerCode(refAnswer.getRefAnswerCode())
                        .refAnswerId(refAnswer.getRefAnswerUk())
                        .refDisplaySort(refAnswer.getDisplaySort())
                        .build())
                .orElse(null);

    }
}
