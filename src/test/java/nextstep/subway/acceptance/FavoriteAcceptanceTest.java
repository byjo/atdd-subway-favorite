package nextstep.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static nextstep.subway.acceptance.MemberSteps.*;

public class FavoriteAcceptanceTest extends AcceptanceTest {
    public static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "password";
    public static final int AGE = 20;

    private Long 강남역;
    private Long 판교역;
    private Long 신분당선;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();

        강남역 = StationSteps.지하철역_생성_요청("강남역").jsonPath().getLong("id");
        판교역 = StationSteps.지하철역_생성_요청("판교역").jsonPath().getLong("id");

        신분당선 = LineSteps.지하철_노선_생성_요청("신분당선", "red").jsonPath().getLong("id");
        LineSteps.지하철_노선에_지하철_구간_생성_요청(신분당선, createSectionCreateParams(강남역, 판교역, 10));

        회원_생성_요청(EMAIL, PASSWORD, AGE);
    }

    @Test
    @DisplayName("즐겨찾기를 관리한다.")
    void manageFavorite() {
        String accessToken = 로그인_되어_있음(EMAIL, PASSWORD);

        ExtractableResponse<Response> 즐겨찾기_생성_응답 = FavoriteSteps.즐겨찾기_생성_요청(강남역, 판교역, accessToken);
        FavoriteSteps.즐겨찾기_생성됨(즐겨찾기_생성_응답);

        ExtractableResponse<Response> 즐겨찾기_조회_응답 = FavoriteSteps.즐겨찾기_조회_요청(accessToken);
        FavoriteSteps.즐겨찾기_목록_조회됨(즐겨찾기_조회_응답, 강남역, 판교역);

        Long 즐겨찾기 = 즐겨찾기_조회_응답.jsonPath().getLong("[0].id");

        ExtractableResponse<Response> 즐겨찾기_삭제_응답 = FavoriteSteps.즐겨찾기_삭제_요청(accessToken, 즐겨찾기);
        FavoriteSteps.즐겨찾기_삭제됨(즐겨찾기_삭제_응답, accessToken);
    }

    @Test
    @DisplayName("권한이 없는 사용자는 즐겨찾기를 관리할 수 없다.")
    void manageFavoriteByUnauthorizedMember() {
        String accessToken = "";

        ExtractableResponse<Response> 즐겨찾기_생성_응답 = FavoriteSteps.즐겨찾기_생성_요청(강남역, 판교역, accessToken);
        FavoriteSteps.권한없음(즐겨찾기_생성_응답);

        ExtractableResponse<Response> 즐겨찾기_조회_응답 = FavoriteSteps.즐겨찾기_조회_요청(accessToken);
        FavoriteSteps.권한없음(즐겨찾기_조회_응답);

        String validAccessToken = 로그인_되어_있음(EMAIL, PASSWORD);
        ExtractableResponse<Response> 유효한_토큰으로_즐겨찾기_생성_응답 = FavoriteSteps.즐겨찾기_생성_요청(강남역, 판교역, validAccessToken);
        FavoriteSteps.즐겨찾기_생성됨(유효한_토큰으로_즐겨찾기_생성_응답);
        ExtractableResponse<Response> 유효한_토큰으로_즐겨찾기_조회_응답 = FavoriteSteps.즐겨찾기_조회_요청(validAccessToken);
        Long 즐겨찾기 = 유효한_토큰으로_즐겨찾기_조회_응답.jsonPath().getLong("[0].id");

        ExtractableResponse<Response> 즐겨찾기_삭제_응답 = FavoriteSteps.즐겨찾기_삭제_요청(accessToken, 즐겨찾기);
        FavoriteSteps.권한없음(즐겨찾기_삭제_응답);
    }

    private Map<String, String> createSectionCreateParams(Long upStationId, Long downStationId, int distance) {
        Map<String, String> params = new HashMap<>();
        params.put("upStationId", upStationId + "");
        params.put("downStationId", downStationId + "");
        params.put("distance", distance + "");
        return params;
    }
}
