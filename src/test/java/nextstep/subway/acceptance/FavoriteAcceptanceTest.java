package nextstep.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static nextstep.subway.acceptance.MemberSteps.*;

public class FavoriteAcceptanceTest extends AcceptanceTest {
    public static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "password";
    public static final int AGE = 20;

    @Test
    @DisplayName("즐겨찾기를 관리한다.")
    void manageFavorite() {
        Long 강남역 = StationSteps.지하철역_생성_요청("강남역").jsonPath().getLong("id");
        Long 판교역 = StationSteps.지하철역_생성_요청("판교역").jsonPath().getLong("id");

        Long 신분당선 = LineSteps.지하철_노선_생성_요청("신분당선", "red").jsonPath().getLong("id");
        LineSteps.지하철_노선에_지하철_구간_생성_요청(신분당선, createSectionCreateParams(강남역, 판교역, 10));

        회원_생성_요청(EMAIL, PASSWORD, AGE);
        String accessToken = 로그인_되어_있음(EMAIL, PASSWORD);

        ExtractableResponse<Response> 즐겨찾기_생성_응답 = FavoriteSteps.즐겨찾기_생성_요청(강남역, 판교역, accessToken);
        FavoriteSteps.즐겨찾기_생성됨(즐겨찾기_생성_응답);

        ExtractableResponse<Response> 즐겨찾기_조회_응답 = FavoriteSteps.즐겨찾기_조회_요청(accessToken);
        FavoriteSteps.즐겨찾기_목록_조회됨(즐겨찾기_조회_응답, 강남역, 판교역);

        // when
        // 즐겨찾기를 삭제한다.

        // then
        // 즐겨찾기 삭제를 확인한다. status code + 생성한 id 기반 조회
    }

    private Map<String, String> createSectionCreateParams(Long upStationId, Long downStationId, int distance) {
        Map<String, String> params = new HashMap<>();
        params.put("upStationId", upStationId + "");
        params.put("downStationId", downStationId + "");
        params.put("distance", distance + "");
        return params;
    }
}
