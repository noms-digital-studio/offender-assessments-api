package uk.gov.justice.digital.oasys.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.justice.digital.oasys.api.Ogp;
import uk.gov.justice.digital.oasys.api.Ogrs;
import uk.gov.justice.digital.oasys.jpa.entity.OasysAssessmentGroup;
import uk.gov.justice.digital.oasys.jpa.entity.OasysSet;
import uk.gov.justice.digital.oasys.jpa.entity.Offender;
import uk.gov.justice.digital.oasys.jpa.repository.OffenderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class AssessmentsControllerTest {

    @LocalServerPort
    int port;

    @MockBean
    private OffenderRepository offenderRepository;

    @Autowired
    @Qualifier("globalObjectMapper")
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        RestAssured.port = port;
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
                (aClass, s) -> objectMapper
        ));

        Mockito.when(offenderRepository.findByCmsProbNumber(eq("crn1"))).thenReturn(anOffender());
        Mockito.when(offenderRepository.findByCmsProbNumber(eq("crn2"))).thenReturn(Optional.empty());
    }

    private Optional<Offender> anOffender() {
        return Optional.ofNullable(Offender.builder()
                .oasysAssessmentGroups(anAssessmentGroup())
                .build());
    }

    private List<OasysAssessmentGroup> anAssessmentGroup() {
        return ImmutableList.of(OasysAssessmentGroup.builder()
                .oasysAssessmentGroupPk(1L)
                .oasysSets(someOasysSets())
                .build());
    }

    private List<OasysSet> someOasysSets() {
        return ImmutableList.of(OasysSet.builder()
                        .oasysSetPk(1L)
                        .ogrs31Year(BigDecimal.ONE)
                        .ogrs32Year(BigDecimal.TEN)
                        .ogp1Year(BigDecimal.ONE)
                        .ogp2Year(BigDecimal.TEN)
                        .ogpDyWesc(BigDecimal.ONE)
                        .ogpStWesc(BigDecimal.TEN)
                        .ogpTotWesc(BigDecimal.ONE)
                        .ovpStWesc(BigDecimal.TEN)
                        .ovpDyWesc(BigDecimal.ONE)
                        .ovpTotWesc(BigDecimal.TEN)
                        .ovp1Year(BigDecimal.ONE)
                        .ovp2Year(BigDecimal.TEN)
                        .ovpPrevWesc(BigDecimal.ONE)
                        .ovpVioWesc(BigDecimal.TEN)
                        .ovpNonVioWesc(BigDecimal.ONE)
                        .ovpAgeWesc(BigDecimal.TEN)
                        .ovpSexWesc(BigDecimal.ONE)
                        .build(),
                OasysSet.builder()
                        .oasysSetPk(2L)
                        .ogrs31Year(BigDecimal.TEN)
                        .ogrs32Year(BigDecimal.ONE)
                        .ogp1Year(BigDecimal.TEN)
                        .ogp2Year(BigDecimal.ONE)
                        .ogpDyWesc(BigDecimal.TEN)
                        .ogpStWesc(BigDecimal.ONE)
                        .ogpTotWesc(BigDecimal.TEN)
                        .ovpStWesc(BigDecimal.ONE)
                        .ovpDyWesc(BigDecimal.TEN)
                        .ovpTotWesc(BigDecimal.ONE)
                        .ovp1Year(BigDecimal.TEN)
                        .ovp2Year(BigDecimal.ONE)
                        .ovpPrevWesc(BigDecimal.TEN)
                        .ovpVioWesc(BigDecimal.ONE)
                        .ovpNonVioWesc(BigDecimal.TEN)
                        .ovpAgeWesc(BigDecimal.ONE)
                        .ovpSexWesc(BigDecimal.TEN)
                        .build());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void canGetOgrsForOffenderCRNs() {
        Ogrs[] ogrss = given()
                .when()
                .get("/offenders/crn/{0}/ogrs3", "crn1")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Ogrs[].class);

        assertThat(ogrss).extracting("oasysSetId").containsExactly(1L, 2L);
    }

    @Test
    public void getOgrsForUnknownOffenderGivesNotFound() {
        given()
                .when()
                .get("/offenders/crn/{0}/ogrs3", "crn2")
                .then()
                .statusCode(404);
    }

    @Test
    public void canGetOgpForOffenderCRNs() {
        Ogp[] ogps = given()
                .when()
                .get("/offenders/crn/{0}/ogp", "crn1")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Ogp[].class);

        assertThat(ogps).extracting("oasysSetId").containsExactly(1L, 2L);
    }

    @Test
    public void getOgpForUnknownOffenderGivesNotFound() {
        given()
                .when()
                .get("/offenders/crn/{0}/ogp", "crn2")
                .then()
                .statusCode(404);
    }

    @Test
    public void canGetOvpForOffenderCRNs() {
        Ogp[] ogps = given()
                .when()
                .get("/offenders/crn/{0}/ovp", "crn1")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Ogp[].class);

        assertThat(ogps).extracting("oasysSetId").containsExactly(1L, 2L);
    }

    @Test
    public void getOvpForUnknownOffenderGivesNotFound() {
        given()
                .when()
                .get("/offenders/crn/{0}/ovp", "crn2")
                .then()
                .statusCode(404);
    }

}