# この資料について
* SpringFoxを使ってSwaggerのjsonを吐き出すっていうのを説明する
* 特にSwaggerを意識していないSpringBootのプロジェクトを使って自分が使いそうだと思った部分をまとめた

# SpringFoxについて
* Spring BootでつくられたAPIからSwaggerのjsonをAPIサーバが返却してくれるようになるライブラリ
* springfox-swagger-uiとかを使うとAPIドキュメントもAPIサーバで確認できるようになる
* Apache License 2.0だから安心して使ってOK
* 2018/07/25現在ではOpenApiの2系まで対応してある。8月くらいに3系対応したやつがでるって中の人が言ってた
  * [Add support for Open API 3.0 · Issue #2022 · springfox/springfox · GitHub ](https://github.com/springfox/springfox/issues/2022) 

# とりあえず動かす
とにかく動かしてどんな感じか見てみる

#### APIを適当につくる
今回は下記のようなAPIをお試し用につくった。リポジトリは巻末を参照してくだしい

|パス|メソッド|概要|
|---|---|---|
|/families/list|GET|家族の一覧を返却|
|/families/{familyId}|GET|家族の詳細を返却|
|/families|POST|家族を登録|
|/families/{familyId}/members/list|GET|家族構成員の一覧を返却|



#### build.gradleにSpringFoxの依存を追加
```groovy
dependencies {
	...
	compile('io.springfox:springfox-swagger2:2.9.2') // 追加
	...
}
```

#### 適当なConfigファイルにアノテーションを追加
```java
@Configuration
@EnableSwagger2 // 追加
public class SpringFoxConfig {
}
```

#### アプリケーションを起動してjsonファイルを確認する
* bootRunしてアプリケーションを起動
* http://localhost:8080/v2/api-docs にアクセスしてjsonを確認
  * 自動で用意されるエンドポイントは下記を参照
  * [Springfox Reference Documentation #customizing-the-swagger-endpoints ](https://springfox.github.io/springfox/docs/current/#customizing-the-swagger-endpoints)

吐き出されたjsonは下記の通り
```json
{"swagger":"2.0","info":{"description":"Api Documentation","version":"1.0","title":"Api Documentation","termsOfService":"urn:tos","contact":{},"license":{"name":"Apache 2.0","url":"http://www.apache.org/licenses/LICENSE-2.0"}},"host":"localhost:8080","basePath":"/","tags":[{"name":"basic-error-controller","description":"Basic Error Controller"},{"name":"family-controller","description":"Family Controller"},{"name":"member-controller","description":"Member Controller"}],"paths":{"/error":{"get":{"tags":["basic-error-controller"],"summary":"error","operationId":"errorUsingGET","produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"type":"object","additionalProperties":{"type":"object"}}},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"},"404":{"description":"Not Found"}},"deprecated":false},"head":{"tags":["basic-error-controller"],"summary":"error","operationId":"errorUsingHEAD","consumes":["application/json"],"produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"type":"object","additionalProperties":{"type":"object"}}},"204":{"description":"No Content"},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"}},"deprecated":false},"post":{"tags":["basic-error-controller"],"summary":"error","operationId":"errorUsingPOST","consumes":["application/json"],"produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"type":"object","additionalProperties":{"type":"object"}}},"201":{"description":"Created"},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"},"404":{"description":"Not Found"}},"deprecated":false},"put":{"tags":["basic-error-controller"],"summary":"error","operationId":"errorUsingPUT","consumes":["application/json"],"produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"type":"object","additionalProperties":{"type":"object"}}},"201":{"description":"Created"},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"},"404":{"description":"Not Found"}},"deprecated":false},"delete":{"tags":["basic-error-controller"],"summary":"error","operationId":"errorUsingDELETE","produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"type":"object","additionalProperties":{"type":"object"}}},"204":{"description":"No Content"},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"}},"deprecated":false},"options":{"tags":["basic-error-controller"],"summary":"error","operationId":"errorUsingOPTIONS","consumes":["application/json"],"produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"type":"object","additionalProperties":{"type":"object"}}},"204":{"description":"No Content"},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"}},"deprecated":false},"patch":{"tags":["basic-error-controller"],"summary":"error","operationId":"errorUsingPATCH","consumes":["application/json"],"produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"type":"object","additionalProperties":{"type":"object"}}},"204":{"description":"No Content"},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"}},"deprecated":false}},"/families":{"post":{"tags":["family-controller"],"summary":"create","operationId":"createUsingPOST","consumes":["application/json"],"produces":["*/*"],"parameters":[{"in":"body","name":"familyRequest","description":"familyRequest","required":true,"schema":{"$ref":"#/definitions/FamilyRequest"}}],"responses":{"200":{"description":"OK","schema":{"type":"object"}},"201":{"description":"Created"},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"},"404":{"description":"Not Found"}},"deprecated":false}},"/families/list":{"get":{"tags":["family-controller"],"summary":"list","operationId":"listUsingGET","produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"$ref":"#/definitions/FamiliesListResponse"}},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"},"404":{"description":"Not Found"}},"deprecated":false}},"/families/{familyId}":{"get":{"tags":["family-controller"],"summary":"findFamily","operationId":"findFamilyUsingGET","produces":["*/*"],"parameters":[{"name":"familyId","in":"path","description":"familyId","required":true,"type":"integer","format":"int32"}],"responses":{"200":{"description":"OK","schema":{"$ref":"#/definitions/Family"}},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"},"404":{"description":"Not Found"}},"deprecated":false}},"/families/{familyId}/members/list":{"get":{"tags":["member-controller"],"summary":"findMembersByFamilyId","operationId":"findMembersByFamilyIdUsingGET","produces":["*/*"],"parameters":[{"name":"familyId","in":"path","description":"familyId","required":true,"type":"integer","format":"int32"}],"responses":{"200":{"description":"OK","schema":{"$ref":"#/definitions/MembersResponse"}},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"},"404":{"description":"Not Found"}},"deprecated":false}}},"definitions":{"FamiliesListResponse":{"type":"object","properties":{"families":{"type":"array","items":{"$ref":"#/definitions/Family"}}},"title":"FamiliesListResponse"},"Family":{"type":"object","properties":{"familyId":{"type":"integer","format":"int32"},"familyName":{"type":"string"},"updatetime":{"type":"string","format":"date-time"}},"title":"Family"},"FamilyRequest":{"type":"object","properties":{"familyId":{"type":"integer","format":"int32"},"familyName":{"type":"string"},"members":{"type":"array","items":{"$ref":"#/definitions/Member"}},"updatetime":{"type":"string","format":"date-time"}},"title":"FamilyRequest"},"Member":{"type":"object","properties":{"birthday":{"type":"string","format":"date"},"gender":{"type":"string","enum":["MALE","FEMALE","ETC","NONE"]},"givenName":{"type":"string"},"memberId":{"type":"integer","format":"int32"},"updatetime":{"type":"string","format":"date-time"}},"title":"Member"},"MembersResponse":{"type":"object","properties":{"members":{"type":"array","items":{"$ref":"#/definitions/Member"}}},"title":"MembersResponse"},"ModelAndView":{"type":"object","properties":{"empty":{"type":"boolean"},"model":{"type":"object"},"modelMap":{"type":"object","additionalProperties":{"type":"object"}},"reference":{"type":"boolean"},"status":{"type":"string","enum":["100","101","102","103","200","201","202","203","204","205","206","207","208","226","300","301","302","303","304","305","307","308","400","401","402","403","404","405","406","407","408","409","410","411","412","413","414","415","416","417","418","419","420","421","422","423","424","426","428","429","431","451","500","501","502","503","504","505","506","507","508","509","510","511"]},"view":{"$ref":"#/definitions/View"},"viewName":{"type":"string"}},"title":"ModelAndView"},"View":{"type":"object","properties":{"contentType":{"type":"string"}},"title":"View"}}}
```

#### 吐き出されたjsonをSwagger Editorで見てみる
* jsonみてもちんぷんかんぷんなので [Swagger Editor](https://editor.swagger.io/) にjsonをコピペしてAPIドキュメントを確認する

#### とりあえず眺めてみる
とりあえずSwagger Editorで見たところ、個人的には思ったよりもいい感じに出力されていると感じた。例えば、

* enumとかもちゃんと解釈してる
* 内部クラスがちゃんとひろわれている
* 日付系がちゃんと解釈されている
* APIのリクエストとレスポンスもざっくりは問題なさそう

#### 変なところを確認する
とはいえ変なところもたくさんあったので色々と設定しないとダメそう。個人的に設定しないとと思ったのは以下

* SpringBootがデフォルトで用意しているErrorページがAPIとして存在していたり、モデルにViewがいたりしていてノイズが多い
* LocalDateやOffsetDateTimeなどがメンバ変数全部はきだしておかしな感じなることがある(今回貼ったものは問題ない)
* 想定していないステータスコードが自動で用意されている(201や401など)
* Try it out押すとhttpになってる
* ResponseEntityのメンバ変数がやりかたによっては出力される(今回貼ったものは問題ない)
* APIの名称がコントローラのクラス名になっている
* APIがパスではなくコントローラ単位で別れている
* Modelの名称ちょっと変えたい
* 同じクラス名のModelは共存できない

なので、上記を全部潰してみる。

# ちゃんと設定する
上記で気になったところを一旦全部潰してみた。一旦影響があった部分のコードを貼っていく。

#### 先に結果を見る
直した結果どうなったかは下記のjsonを[Swagger Editor](https://editor.swagger.io/)に貼って確認してください

```json
{"swagger":"2.0","info":{"description":"Api Documentation","version":"1.0","title":"Api Documentation","termsOfService":"urn:tos","contact":{},"license":{"name":"Apache 2.0","url":"http://www.apache.org/licenses/LICENSE-2.0"}},"host":"localhost:8080","basePath":"/","tags":[{"name":"families","description":"families info"}],"schemes":["http","https"],"paths":{"/families":{"post":{"tags":["families"],"summary":"create","operationId":"createUsingPOST","consumes":["application/json"],"produces":["*/*"],"parameters":[{"in":"body","name":"familyRequest","description":"familyRequest","required":true,"schema":{"$ref":"#/definitions/FamilyRequest"}}],"responses":{"200":{"description":"OK","schema":{"type":"object"}},"400":{"description":"Parameter is invalid"},"401":{"description":"認証エラー"},"500":{"description":"Internel server error"}},"deprecated":false}},"/families/list":{"get":{"tags":["families"],"summary":"list","operationId":"listUsingGET","produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"$ref":"#/definitions/FamiliesListResponse"}},"400":{"description":"Parameter is invalid"},"500":{"description":"Internel server error"}},"deprecated":false}},"/families/{familyId}":{"get":{"tags":["families"],"summary":"findFamily","operationId":"findFamilyUsingGET","produces":["*/*"],"parameters":[{"name":"familyId","in":"path","description":"familyId","required":true,"type":"integer","format":"int32"}],"responses":{"200":{"description":"OK","schema":{"$ref":"#/definitions/Family"}},"400":{"description":"Parameter is invalid"},"500":{"description":"Internel server error"}},"deprecated":false}},"/families/{familyId}/members/list":{"get":{"tags":["families"],"summary":"findMembersByFamilyId","operationId":"findMembersByFamilyIdUsingGET","produces":["*/*"],"parameters":[{"name":"familyId","in":"path","description":"familyId","required":true,"type":"integer","format":"int32"}],"responses":{"200":{"description":"OK","schema":{"$ref":"#/definitions/MembersResponse"}},"400":{"description":"Parameter is invalid"},"500":{"description":"Internel server error"}},"deprecated":false}}},"definitions":{"FamiliesListResponse":{"type":"object","properties":{"families":{"type":"array","items":{"$ref":"#/definitions/Family"}}},"title":"FamiliesListResponse"},"Family":{"type":"object","properties":{"familyId":{"type":"integer","format":"int32"},"familyName":{"type":"string"},"updatetime":{"type":"string","format":"date-time"}},"title":"Family"},"FamilyRequest":{"type":"object","properties":{"familyId":{"type":"integer","format":"int32"},"familyName":{"type":"string"},"members":{"type":"array","items":{"$ref":"#/definitions/MemberRequest"}},"updatetime":{"type":"string","format":"date-time"}},"title":"FamilyRequest"},"Member":{"type":"object","properties":{"birthday":{"type":"string","format":"date"},"gender":{"type":"string","enum":["MALE","FEMALE","ETC","NONE"]},"givenName":{"type":"string"},"memberId":{"type":"integer","format":"int32"},"updatetime":{"type":"string","format":"date-time"}},"title":"Member"},"MemberRequest":{"type":"object","properties":{"birthday":{"type":"string","format":"date"},"gender":{"type":"string","enum":["MALE","FEMALE","ETC"]},"givenName":{"type":"string"},"memberId":{"type":"integer","format":"int32"}},"title":"MemberRequest"},"MembersResponse":{"type":"object","properties":{"members":{"type":"array","items":{"$ref":"#/definitions/Member"}}},"title":"MembersResponse"}}}
```

#### Configでなおしたところ

```java
@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket document() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    // ① 指定したパス以下のAPIのみを対象としたい場合記述する。指定しないとSpringのデフォルトのエラーとかまで出力する
                    .paths(PathSelectors.regex("^\\/families.*"))
                    .build()

                // ② directModelSubstituteを指定するとJava8の日付がちゃんと文字列で出る
                // 指定しないと保持するメンバ変数全部返す
                .directModelSubstitute(OffsetDateTime.class, java.util.Date.class)
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)

                // ③ useDefaultResponseMessagesをtrueにすると200,201,400などのデフォルトのステータスコードを各APIのレスポンスに出力する
                .useDefaultResponseMessages(false)

                // ④ 利用可能なprotocolを指定できる
                .protocols(newHashSet("http", "https"))

                // ⑤ ResponseEntity<> で指定している部分がある場合は下記を指定することでResponseEntityのメンバ変数を返却しなくてすむ
                .genericModelSubstitutes(ResponseEntity.class)

                // ⑥ 各リクエストメソッド毎にデフォルトのステータスコードと返却値の型、概要を出力できる
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
```


#### 解説
上記の実装を一つずつ解説する。基本的にコメントに書いてあるけども一応
###### ① .select().path().build()
* .select()がApiSelectorBuilderっていうのを返していて、そこでpathを指定したりして対象とするAPIを絞ったりできる
* build()しないと死ぬ

###### ② directModelSubstitute()
* 特定のクラスを別のクラスに変換して出力することができる
* サンプルではLocalDateをStringにしたりVOをキャストしたりしてた

###### ③ useDefaultResponseMessages()
* デフォルトで用意されているステータスコードとメッセージのセットを返却するかしないかを指定できる
* デフォルトがtrueになっているので全部ちゃんとやってないなら個人的にはfalse推奨

###### ④ protocols()
* 利用可能なプロトコルを指定する
* httpsだけだったら.protocols(newHashSet("https")) みたいな

###### ⑤ genericModelSubstitutes()
* ResponseEntity<>とかOptional<>とかを使っていて最上位がレスポンスの最上位と異なる場合に対象のクラスを引数に食わすと無視してくれる
* 自前でそういうの用意しているときにも使えそう

###### ⑥ globalResponseMessage()
* 各リクエストメソッド毎にデフォルトのステータスコードとメッセージを設定する関数
* 個別でやりたい場合は各APIで設定できる(後述する)

#### Controllerにアノテーションを追加してなおしたところ

```java
// ⑦ APIの概要やtagを変更できる
@Api(tags = "families", description = "families info")
@RestController
public class FamilyController {
...

    // ⑧ 個別でAPIのレスポンスを変更できる
    @ApiResponse(code = 401, message = "これだけ認証があるとか")
    @GetMapping("/families/{familyId}")
    public FamiliesListResponse.Family findFamily(@PathVariable(name = "familyId") Integer familyId) {

...
}

// ⑦ tagを指定することでfamilyタグでまとめることができる。指定しないとMemberとFamilyは別で表現される
@Api(tags = "families")
@RestController
public class MemberController {
...
}
```


#### 解説
###### ⑦ @Api
* 特定のAPIに対する設定ができる
* 名前とか認証方法とかSwaggerの対象から消すとか
* 概要とかがdeprecatedになってるから注意（使ってた）
* 別々のコントローラでtagを同一にするとそのtagでまとめてくれる

###### ⑧ @ApiResponse
* APIのステータスコードに紐づく色々な設定ができる
* メッセージやヘッダとボディの型とかを指定できる

#### Modelでなおしたところ
```java
public class FamilyRequest {
...
    // ⑨ 名前かぶってるからModel名変えたいとかもできる
    @ApiModel(value = "MemberRequest")
    public static class Member {
...
}
```
#### 解説
###### ⑨ @ApiModel
* Modelの名前を変えたいときとかに使える
* 概要とか親とかも変更可能っぽい

# ついでにspringfox-swagger-uiも試す
#### build.gradleに依存を追加
```groovy
dependencies {
...
    compile 'io.springfox:springfox-swagger-ui:2.9.2' // 追加
    compile 'io.springfox:springfox-core:2.9.2' // 追加
...    
}
```

#### 起動して確認
* bootRunする
* http://localhost:8080/swagger-ui.html を確認

#### 解説
* 公式見るとspringfox-swagger-uiの依存だけ出してればいいっぽく見えるけどcoreも追加しないと動かない


# 雑感
* コントローラ等にそこまでアノテーションを書かなくても最低限の設定はできると感じた
* SpringFox用のConfigファイルつくってそこに関連する内容書けばある程度依存を切り出せるので、Configで色々設定できるのは良いと思う
* 細かい設定は実装に書かないといけないが、コメントだと思えばまあいいか？と思った
* SpringFox側にjavadocが少ないから公式ドキュメント読んだり、issueを見ないと辛い印象
* とはいえやりたいことは一通りできると思うので採用はありだと思う。やりたいことがあったら検索すれば大体issueが引っかかる
* Swaggerのymlを書くのに抵抗があるけどフロントとかが使いたがってたり、とはいえcodegenで吐き出されたClientのjar使いたいっていう組織とかは、まずはAPIドキュメントをいきなりSwaggerにするんじゃなくて、SpringFoxでどうにか対処するっていうのはありだと思う


# 参考にしたサイト
* [Springfox Reference Documentation ](https://springfox.github.io/springfox/docs/current/)
* [Spring BootでSpringFox（Swagger）を試す - abcdefg..... ](http://pppurple.hatenablog.com/entry/2016/12/18/195253)
* 後はいろいろなissueなんだけどもうブラウザに残ってないので割愛
