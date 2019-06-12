package uk.gov.justice.digital.oasys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.justice.digital.oasys.api.Assessment;
import uk.gov.justice.digital.oasys.api.AssessmentNeed;
import uk.gov.justice.digital.oasys.api.AssessmentResource;
import uk.gov.justice.digital.oasys.api.BasicSentencePlan;
import uk.gov.justice.digital.oasys.api.Question;
import uk.gov.justice.digital.oasys.api.Section;
import uk.gov.justice.digital.oasys.jpa.entity.OasysSet;
import uk.gov.justice.digital.oasys.jpa.entity.Offender;
import uk.gov.justice.digital.oasys.jpa.repository.AssessmentRepository;
import uk.gov.justice.digital.oasys.jpa.repository.OffenderRepository;
import uk.gov.justice.digital.oasys.service.filters.AssessmentFilters;
import uk.gov.justice.digital.oasys.transformer.AssessmentsTransformer;
import uk.gov.justice.digital.oasys.transformer.SentencePlanTransformer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static uk.gov.justice.digital.oasys.service.filters.AssessmentFilters.curry;

@Service
@Transactional(readOnly = true)
public class AssessmentsService {

    private final OffenderRepository offenderRepository;
    private final AssessmentRepository assessmentRepository;
    private final AssessmentsTransformer assessmentsTransformer;
    private final SentencePlanTransformer sentencePlanTransformer;

    private final String LAYER_3_TYPE = "LAYER_3";

    @Autowired
    public AssessmentsService(OffenderRepository offenderRepository, AssessmentRepository assessmentRepository, AssessmentsTransformer assessmentsTransformer, SentencePlanTransformer sentencePlanTransformer) {
        this.offenderRepository = offenderRepository;
        this.assessmentRepository = assessmentRepository;
        this.assessmentsTransformer = assessmentsTransformer;
        this.sentencePlanTransformer = sentencePlanTransformer;
    }

    public Optional<List<AssessmentResource>> getAssessmentsForOffenderPK(Long oasysOffenderPk, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.findById(oasysOffenderPk);

        return assessmentResourceOf(assessmentsFilter, maybeOffender);
    }

    public Optional<List<AssessmentResource>> getAssessmentsForOffenderCRN(String crn, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByCmsProbNumber(crn);

        return assessmentResourceOf(assessmentsFilter, maybeOffender);
    }

    public Optional<List<AssessmentResource>> getAssessmentsForOffenderPnc(String pnc, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByPnc(pnc);

        return assessmentResourceOf(assessmentsFilter, maybeOffender);
    }

    public Optional<List<AssessmentResource>> getAssessmentsForOffenderNomisId(String nomisId, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByCmsPrisNumber(nomisId);

        return assessmentResourceOf(assessmentsFilter, maybeOffender);
    }

    public Optional<List<AssessmentResource>> getAssessmentsForOffenderBookingId(String bookingId, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByPrisonNumber(bookingId);

        return assessmentResourceOf(assessmentsFilter, maybeOffender);
    }

    public Optional<Assessment> getLatestAssessmentForOffenderPk(Long oasysOffenderPk, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.findById(oasysOffenderPk);

        return latestAssessmentOf(assessmentsFilter, maybeOffender);
    }

    public Optional<Assessment> getLatestAssessmentForOffenderCRN(String crn, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByCmsProbNumber(crn);

        return latestAssessmentOf(assessmentsFilter, maybeOffender);
    }

    public Optional<Assessment> getLatestAssessmentForOffenderPnc(String pnc, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByPnc(pnc);

        return latestAssessmentOf(assessmentsFilter, maybeOffender);
    }

    public Optional<Assessment> getLatestAssessmentForOffenderNomisId(String nomisId, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByCmsPrisNumber(nomisId);

        return latestAssessmentOf(assessmentsFilter, maybeOffender);
    }

    public Optional<Assessment> getLatestAssessmentForOffenderBookingId(String bookingId, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByPrisonNumber(bookingId);

        return latestAssessmentOf(assessmentsFilter, maybeOffender);
    }

    private Optional<List<AssessmentResource>> assessmentResourceOf(Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter, Optional<Offender> maybeOffender) {
        return maybeOffender.map(offender -> offender.getOasysAssessmentGroups()
                .stream()
                .flatMap(
                        oasysAssessmentGroup -> assessmentsFilter.apply(oasysAssessmentGroup.getOasysSets().stream())
                                .sorted(Comparator.comparing(OasysSet::getCreateDate).reversed())
                                .map(assessmentsTransformer::assessmentResourceOf))
                .collect(Collectors.toList()));
    }

    public List<AssessmentNeed> getLatestAsessementNeedsForOffenderPk(Long oasysOffenderPk) {
        Optional<Offender> maybeOffender = offenderRepository.findById(oasysOffenderPk);
        Optional<Assessment> maybeAssessment = latestAssessmentOf(assessmentsFilterOf(Optional.empty(),Optional.of(LAYER_3_TYPE), Optional.empty(), Optional.empty()), maybeOffender);

        if(maybeAssessment.isPresent()) {
            return maybeAssessment.get().getLayer3SentencePlanNeeds();
        }
        return Collections.emptyList();
    }

    private Optional<Assessment> latestAssessmentOf(Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter, Optional<Offender> maybeOffender) {
        return maybeOffender.map(offender -> getOasysSetStream(assessmentsFilter, offender)
                .max(Comparator.comparing(OasysSet::getCreateDate))
                .flatMap(a -> Optional.ofNullable(assessmentsTransformer.assessmentOf(a)))
                .orElse(null));
    }


    public Optional<Assessment> getAssessment(Long oasysSetId) {
        return assessmentRepository.findById(oasysSetId).map(assessmentsTransformer::assessmentOf);
    }

    public Optional<Question> getLatestQAndAforOffenderCRN(String crn, String assessmentType, String section, String question) {

        final Optional<Assessment> maybeAssessment = latestAssessmentOf(AssessmentFilters.curry(AssessmentFilters.byAssessmentType, assessmentType), offenderRepository.getByCmsProbNumber(crn));

         return maybeAssessment.map(Assessment::getSections)
                 .flatMap(stringSectionMap -> Optional.ofNullable(stringSectionMap.get(section)))
                 .map(Section::getQuestions)
                 .flatMap(stringQuestionMap -> Optional.ofNullable(stringQuestionMap.get(question)));
    }

    private Optional<BasicSentencePlan> latestBasicSentencePlanOf(Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter, Optional<Offender> maybeOffender) {
        return maybeOffender.map(offender -> getOasysSetStream(assessmentsFilter, offender)
                .max(Comparator.comparing(OasysSet::getCreateDate))
                .flatMap(sentencePlanTransformer::basicSentencePlanOf)
                .orElse(null));
    }

    private Stream<OasysSet> getOasysSetStream(Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter, Offender offender) {
        return offender.getOasysAssessmentGroups()
                .stream()
                .flatMap(oasysAssessmentGroup -> assessmentsFilter.apply(oasysAssessmentGroup.getOasysSets().stream()));
    }

    public Optional<BasicSentencePlan> getLatestSentencePlanForOffenderPk(Long oasysOffenderId, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.findById(oasysOffenderId);

        return latestBasicSentencePlanOf(assessmentsFilter, maybeOffender);
    }

    public Optional<BasicSentencePlan> getLatestSentencePlanForOffenderCrn(String crn, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByCmsProbNumber(crn);

        return latestBasicSentencePlanOf(assessmentsFilter, maybeOffender);

    }

    public Optional<BasicSentencePlan> getLatestSentencePlanForOffenderPnc(String pnc, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByPnc(pnc);

        return latestBasicSentencePlanOf(assessmentsFilter, maybeOffender);
    }

    public Optional<BasicSentencePlan> getLatestSentencePlanForOffenderNomsId(String nomisId, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByCmsPrisNumber(nomisId);

        return latestBasicSentencePlanOf(assessmentsFilter, maybeOffender);

    }

    public Optional<BasicSentencePlan> getLatestSentencePlanForOffenderBookingId(String bookingId, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByPrisonNumber(bookingId);

        return latestBasicSentencePlanOf(assessmentsFilter, maybeOffender);
    }

    public Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilterOf(Optional<String> filterAssessmentStatus, Optional<String> filterAssessmentType, Optional<String> filterGroupStatus, Optional<Boolean> filterVoided) {

        return filterAssessmentStatus.map(
                assessmentStatus -> curry(AssessmentFilters.byAssessmentStatus, assessmentStatus))
                .orElse(AssessmentFilters.identity)
                .andThen(
                        filterAssessmentType.map(
                                assessmentType -> curry(AssessmentFilters.byAssessmentType, assessmentType))
                                .orElse(AssessmentFilters.identity))
                .andThen(
                        filterGroupStatus.map(
                                groupStatus -> curry(AssessmentFilters.byGroupStatus, groupStatus))
                                .orElse(AssessmentFilters.identity))
                .andThen(
                        filterVoided.map(
                                voided -> curry(AssessmentFilters.byVoided, voided))
                                .orElse(AssessmentFilters.identity));
    }

    public Optional<List<BasicSentencePlan>> getSentencePlansForOffenderPk(Long oasysOffenderId, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.findById(oasysOffenderId);

        return basicSentencePlansOf(assessmentsFilter, maybeOffender);
    }

    private Optional<List<BasicSentencePlan>> basicSentencePlansOf(Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter, Optional<Offender> maybeOffender) {
        return maybeOffender.map(o -> getOasysSetStream(assessmentsFilter, o)
                .map(sentencePlanTransformer::basicSentencePlanOf)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList()));
    }

    public Optional<List<BasicSentencePlan>> getSentencePlansForOffenderCrn(String crn, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByCmsProbNumber(crn);

        return basicSentencePlansOf(assessmentsFilter, maybeOffender);

    }

    public Optional<List<BasicSentencePlan>> getSentencePlansForOffenderPnc(String pnc, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByPnc(pnc);

        return basicSentencePlansOf(assessmentsFilter, maybeOffender);
    }

    public Optional<List<BasicSentencePlan>> getSentencePlansForOffenderNomsId(String nomisId, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByCmsPrisNumber(nomisId);

        return basicSentencePlansOf(assessmentsFilter, maybeOffender);

    }

    public Optional<List<BasicSentencePlan>> getSentencePlansForOffenderBookingId(String bookingId, Function<Stream<OasysSet>, Stream<OasysSet>> assessmentsFilter) {
        Optional<Offender> maybeOffender = offenderRepository.getByPrisonNumber(bookingId);

        return basicSentencePlansOf(assessmentsFilter, maybeOffender);
    }
}

