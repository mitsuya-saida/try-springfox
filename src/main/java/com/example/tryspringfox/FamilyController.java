package com.example.tryspringfox;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// APIの概要やtagを変更できる
@Api(tags = "families", description = "families info")
@RestController
public class FamilyController {

    @GetMapping("/families/list")
    public FamiliesListResponse list() {
        List<FamiliesListResponse.Family> familyList = new ArrayList<>();
        familyList.add(new FamiliesListResponse.Family(1, "tanaka", OffsetDateTime.now()));
        familyList.add(new FamiliesListResponse.Family(2, "suzuki", OffsetDateTime.now()));
        return new FamiliesListResponse(familyList);
    }

    // 個別でAPIのレスポンスを変更できる
    @ApiResponse(code = 401, message = "これだけ認証があるとか")
    @GetMapping("/families/{familyId}")
    public FamiliesListResponse.Family findFamily(@PathVariable(name = "familyId") Integer familyId) {
        switch(familyId) {
            case 1:
                return new FamiliesListResponse.Family(1, "tanaka", OffsetDateTime.now());
            case 2:
                return new FamiliesListResponse.Family(2, "suzuki", OffsetDateTime.now());
            default:
                return null;
        }
    }

    @PostMapping("/families")
    public ResponseEntity<?> create(@RequestBody FamilyRequest familyRequest) {
        if (familyRequest.getFamilyId() == 1 || familyRequest.getFamilyId() == 2) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
