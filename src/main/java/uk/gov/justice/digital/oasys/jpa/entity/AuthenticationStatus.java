package uk.gov.justice.digital.oasys.jpa.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationStatus {

    @JsonProperty("STATE")
    private String state;

    public boolean isAuthenticated() {
        return state.equals("SUCCESS");
    }

}
