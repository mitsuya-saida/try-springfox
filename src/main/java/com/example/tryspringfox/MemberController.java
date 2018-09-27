package com.example.tryspringfox;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// tagを指定することでfamilyタグでまとめることができる。指定しないとMemberとFamilyは別で表現される
@Api(tags = "families")
@RestController
public class MemberController {

    @GetMapping("/families/{familyId}/members/list")
    public ResponseEntity<MembersResponse> findMembersByFamilyId(@PathVariable(name = "familyId") Integer familyId) {
        return new ResponseEntity<MembersResponse>(new MembersResponse(Lists.newArrayList(
                new MembersResponse.Member(1, "taro", LocalDate.now(), OffsetDateTime.now(), MembersResponse.Member.Gender.MALE),
                new MembersResponse.Member(2, "hanako", LocalDate.now(), OffsetDateTime.now(), MembersResponse.Member.Gender.FEMALE)
        )), HttpStatus.OK);
    }

}
