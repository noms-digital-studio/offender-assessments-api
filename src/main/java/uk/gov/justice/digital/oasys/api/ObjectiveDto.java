package uk.gov.justice.digital.oasys.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import uk.gov.justice.digital.oasys.jpa.entity.SspObjective;
import uk.gov.justice.digital.oasys.jpa.entity.SspObjectivesInSet;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class ObjectiveDto {
    Set<CriminogenicNeedDto> criminogenicNeeds;
    Set<InterventionDto> interventions;
    ObjectiveMeasureDto objectiveMeasure;
    RefElementDto objectiveType;
    String objectiveCode;
    String objectiveDescription;
    String objectiveHeading;
    String objectiveComment;
    String howMeasured;
    LocalDateTime createdDate;

    public static Set<ObjectiveDto> from(Set<SspObjectivesInSet> sspObjectivesInSets) {

        if (sspObjectivesInSets.isEmpty()) {
            return Collections.emptySet();
        }

        return sspObjectivesInSets.stream()
                .map(ObjectiveDto::from)
                .collect(Collectors.toSet());
    }

    public static ObjectiveDto from(SspObjectivesInSet sspo) {
        if (Objects.isNull(sspo)) {
            return null;
        }
        return ObjectiveDto
                .builder()
                .criminogenicNeeds(CriminogenicNeedDto.from(sspo.getSspCrimNeedObjPivots()))
                .howMeasured(sspo.getHowProgressMeasured())
                .interventions(InterventionDto.from(sspo.getSspObjIntervenePivots()))
                .objectiveCode(objectiveCodeOf(sspo.getSspObjective()))
                .objectiveDescription(objectiveDescriptionOf(sspo.getSspObjective()))
                .objectiveComment(objectiveCommentOf(sspo.getSspObjective()))
                .objectiveHeading(objectiveHeadingOf(sspo.getSspObjective()))
                .objectiveMeasure(ObjectiveMeasureDto.from(sspo.getSspObjectiveMeasure()))
                .objectiveType(RefElementDto.from(sspo.getObjectiveType()))
                .createdDate(sspo.getCreateDate())
                .build();
    }

    private static String objectiveDescriptionOf(SspObjective sspObjective) {
        if (Objects.isNull(sspObjective) || Objects.isNull(sspObjective.getObjective())) {
            return null;
        }
        return sspObjective.getObjective().getObjectiveDesc();
    }

    private static String objectiveHeadingOf(SspObjective sspObjective) {
        if (Objects.isNull(sspObjective) || Objects.isNull(sspObjective.getObjective())) {
            return null;
        }
        return sspObjective.getObjective().getObjectiveHeading().getRefElementDesc();
    }

    private static String objectiveCommentOf(SspObjective sspObjective) {
        if (Objects.isNull(sspObjective)) {
            return null;
        }
        return sspObjective.getObjectiveDesc();
    }

    private static String objectiveCodeOf(SspObjective sspObjective) {
        if (Objects.isNull(sspObjective) || Objects.isNull(sspObjective.getObjective())) {
            return null;
        }
        return sspObjective.getObjective().getObjectiveCode();
    }
}
