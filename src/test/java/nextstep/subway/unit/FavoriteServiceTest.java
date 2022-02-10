package nextstep.subway.unit;

import nextstep.subway.applicaion.FavoriteService;
import nextstep.subway.applicaion.dto.FavoriteRequest;
import nextstep.subway.applicaion.dto.FavoriteResponse;
import nextstep.subway.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
class FavoriteServiceTest {
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private FavoriteService favoriteService;

    private static final Long 회원 = 1L;
    private Station 강남역;
    private Station 판교역;

    @BeforeEach
    void setUp() {
        강남역 = stationRepository.save(new Station("강남역"));
        판교역 = stationRepository.save(new Station("판교역"));
    }

    @Test
    void saveFavorite() {
        FavoriteRequest favoriteRequest = new FavoriteRequest(강남역.getId(), 판교역.getId());
        FavoriteResponse favorite = favoriteService.saveFavorite(회원, favoriteRequest);

        assertThat(favorite.getSource().getId()).isEqualTo(강남역.getId());
        assertThat(favorite.getTarget().getId()).isEqualTo(판교역.getId());
    }

    @Test
    void saveDuplicatedFavorite() {
        FavoriteRequest favoriteRequest = new FavoriteRequest(강남역.getId(), 판교역.getId());
        FavoriteResponse favorite = favoriteService.saveFavorite(회원, favoriteRequest);

        assertThat(favorite.getSource().getId()).isEqualTo(강남역.getId());
        assertThat(favorite.getTarget().getId()).isEqualTo(판교역.getId());

        assertThatThrownBy(() -> {
            favoriteService.saveFavorite(회원, favoriteRequest);
        }).isInstanceOf(FavoriteException.Duplicated.class);
    }
}