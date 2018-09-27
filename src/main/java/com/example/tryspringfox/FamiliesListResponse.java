package com.example.tryspringfox;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FamiliesListResponse {

    private List<Family> families;

    @AllArgsConstructor
    @Getter
    public static class Family {

        private Integer familyId;

        private String familyName;

        private OffsetDateTime updatetime;

    }
}
