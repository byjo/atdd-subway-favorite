package nextstep.subway.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FavoriteAcceptanceTest extends AcceptanceTest {
    @Test
    @DisplayName("즐겨찾기를 관리한다.")
    void manageFavorite() {
        // given
        // 지하철 역, 노선, 구간 등록
        // 회원 생성, 로그인

        // when
        // 즐겨찾기를 생성한다.

        // then
        // 즐겨찾기 생성을 확인한다. status code 201

        // when
        // 즐겨찾기 목록을 조회한다.

        // then
        // 생성한 즐겨찾기 정보가 맞는지 확인한다.

        // when
        // 즐겨찾기를 삭제한다.

        // then
        // 즐겨찾기 삭제를 확인한다. status code + 생성한 id 기반 조회
    }
}
