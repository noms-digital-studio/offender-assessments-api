package uk.gov.justice.digital.oasys.jpa.repository;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Optional;

@Repository
public class OasysAuthenticationRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<String> validateCredentials(String username, String password) {
        Session session = em.unwrap(Session.class);

        // The function indirectly updates the database and so must be called within an anonymous block
        // We therefore have to call the function using the raw PL SQL
        String query = "DECLARE " +
                "    LV_RES VARCHAR2(4000); " +
                "BEGIN " +
                "    LV_RES := RESTFUL_API_PKG.USER_LOGIN(P_USER => ?, P_PASSWORD => ?); " +
                "    ? := LV_RES; " +
                "END;";

        String result = session.doReturningWork(
                connection -> {
                    try (CallableStatement function = connection
                            .prepareCall(
                                    query)) {
                        function.setString(1, username);
                        function.setString(2, password);
                        function.registerOutParameter(3, Types.VARCHAR);
                        function.execute();
                        return function.getString(3);
                    }
                });

        return Optional.ofNullable(result);
    }

    public Optional<String> validateUserSentencePlanAccessWithSession(String username, long oasysOffenderId, long sessionId) {
        Session session = em.unwrap(Session.class);

        String query = "DECLARE " +
                "    LV_RES VARCHAR2(4000); " +
                "BEGIN " +
                "    LV_RES := RESTFUL_API_PKG.SENTENCE_PLAN(P_USER => ?, P_OFFENDER_PK => ?, P_SESSION_SNAPSHOT_PK => ?); " +
                "    ? := LV_RES; " +
                "END;";

        String result = session.doReturningWork(
                connection -> {
                    try (CallableStatement function = connection
                            .prepareCall(
                                    query)) {
                        function.setString(1, username);
                        function.setLong(2, oasysOffenderId);
                        function.setLong(3, sessionId);
                        function.registerOutParameter(4, Types.VARCHAR);
                        function.execute();
                        return function.getString(4);
                    }
                });

        return Optional.ofNullable(result);
    }
}
