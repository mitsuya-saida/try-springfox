package com.example.tryspringfox;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MembersResponse {

    private List<Member> members;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Member {
        private Integer memberId;
        private String givenName;
        private LocalDate birthday;
        private OffsetDateTime updatetime;
        private Member.Gender gender;

        public enum Gender {
            MALE, FEMALE, ETC, NONE
        }
    }

}
