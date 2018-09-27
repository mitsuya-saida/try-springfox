package com.example.tryspringfox;


import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Sets.newHashSet;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket document() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    // 指定したパス以下のAPIのみを対象としたい場合記述する。指定しないとSpringのデフォルトのエラーとかまで出力する
                    .paths(PathSelectors.regex("^\\/families.*"))
                    .build()

                // directModelSubstituteを指定するとJava8の日付がちゃんと文字列で出る
                // 指定しないと保持するメンバ変数全部返す
                .directModelSubstitute(OffsetDateTime.class, java.util.Date.class)
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)

                // useDefaultResponseMessagesをtrueにすると200,201,400などのデフォルトのステータスコードを各APIのレスポンスに出力する
                .useDefaultResponseMessages(false)

                // 利用可能なprotocolを指定できる
                .protocols(newHashSet("http", "https"))

                // ResponseEntity<> で指定している部分がある場合は下記を指定することでResponseEntityのメンバ変数を返却しなくてすむ
                .genericModelSubstitutes(ResponseEntity.class)

                // 各リクエストメソッド毎にデフォルトのステータスコードと返却値の型、概要を出力できる
                .globalResponseMessage(RequestMethod.POST,
                        Lists.newArrayList(
                                new ResponseMessageBuilder().code(200).message("OK").build(),
                                new ResponseMessageBuilder().code(400).message("Parameter is invalid").build(),
                                new ResponseMessageBuilder().code(401).message("認証エラー").build(),
                                new ResponseMessageBuilder().code(500).message("Internel server error").build()
                        ))
                .globalResponseMessage(RequestMethod.GET,
                        Lists.newArrayList(
                                new ResponseMessageBuilder().code(200).message("OK").build(),
                                new ResponseMessageBuilder().code(400).message("Parameter is invalid").build(),
                                new ResponseMessageBuilder().code(500).message("Internel server error").build()
                        ));
    }
}
