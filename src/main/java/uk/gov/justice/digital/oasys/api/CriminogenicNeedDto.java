package uk.gov.justice.digital.oasys.api;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import uk.gov.justice.digital.oasys.jpa.entity.SspCrimNeedObjPivot;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Builder(access = AccessLevel.PRIVATE)
@Value
public class CriminogenicNeedDto {
    String code;
    String description;
    Integer priority;

    public static Set<CriminogenicNeedDto> from(Set<SspCrimNeedObjPivot> sspCrimNeedObjPivots) {
        return Optional.ofNullable(sspCrimNeedObjPivots)
                .map(needs -> needs
                        .stream()
                        .map(need -> CriminogenicNeedDto
                                .builder()
                                .code(DtoUtils.refElementCode(need.getCriminogenicNeed()))
                                .description(DtoUtils.refElementDesc(need.getCriminogenicNeed()))
                                .priority(need.getDisplaySort() != null ? need.getDisplaySort().intValue() : null)
                                .build())
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }
}
