package com.example.tryspringfox;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyRequest {
    private Integer familyId;
    private String familyName;
    private OffsetDateTime updatetime;
    private List<Member> members;

    // 内部クラスもModelとしてちゃんと出力される
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    // 名前かぶってるからModel名変えたいとかもできる
    @ApiModel(value = "MemberRequest")
    public static class Member {
        private Integer memberId;
        private String givenName;
        private LocalDate birthday;
        private Gender gender;

        public enum Gender {
            MALE, FEMALE, ETC
        }
    }
}