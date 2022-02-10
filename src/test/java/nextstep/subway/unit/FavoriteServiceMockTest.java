package nextstep.subway.unit;

import nextstep.subway.applicaion.FavoriteService;
import nextstep.subway.applicaion.dto.FavoriteRequest;
import nextstep.subway.applicaion.dto.FavoriteResponse;
import nextstep.subway.domain.Favorite;
import nextstep.subway.domain.FavoriteRepository;
import nextstep.subway.domain.Station;
import nextstep.subway.domain.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceMockTest {
    @Mock
    private StationRepository stationRepository;
    @Mock
    private FavoriteRepository favoriteRepository;
    private FavoriteService favoriteService;
    private Station 강남역;
    private Station 판교역;
    private Favorite 즐겨찾기;

    @BeforeEach
    void setUp() {
        favoriteService = new FavoriteService(stationRepository, favoriteRepository);

        강남역 = new Station("강남역");
        ReflectionTestUtils.setField(강남역, "id", 1L);
        판교역 = new Station("판교역");
        ReflectionTestUtils.setField(판교역, "id", 2L);

        Mockito.when(stationRepository.findById(강남역.getId())).thenReturn(Optional.of(강남역));
        Mockito.when(stationRepository.findById(판교역.getId())).thenReturn(Optional.of(판교역));

        즐겨찾기 = new Favorite(1L, 강남역, 판교역);
        Mockito.when(favoriteRepository.save(any())).thenReturn(즐겨찾기);
        ReflectionTestUtils.setField(즐겨찾기, "id", 1L);
    }

    @Test
    void saveFavorite() {
        FavoriteRequest favoriteRequest = new FavoriteRequest(강남역.getId(), 판교역.getId());
        FavoriteResponse favorite = favoriteService.saveFavorite(1L, favoriteRequest);

        assertThat(favorite.getSource().getId()).isEqualTo(강남역.getId());
        assertThat(favorite.getTarget().getId()).isEqualTo(판교역.getId());
    }
}