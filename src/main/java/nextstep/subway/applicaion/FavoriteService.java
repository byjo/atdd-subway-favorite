package nextstep.subway.applicaion;

import nextstep.subway.applicaion.dto.FavoriteRequest;
import nextstep.subway.applicaion.dto.FavoriteResponse;
import nextstep.subway.domain.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class FavoriteService {
    private final StationRepository stationRepository;
    private final FavoriteRepository favoriteRepository;

    public FavoriteService(StationRepository stationRepository, FavoriteRepository favoriteRepository) {
        this.stationRepository = stationRepository;
        this.favoriteRepository = favoriteRepository;
    }


    public FavoriteResponse saveFavorite(Long memberId, FavoriteRequest favoriteRequest) {
        Station source = stationRepository.findById(favoriteRequest.getSource()).orElseThrow(RuntimeException::new);
        Station target = stationRepository.findById(favoriteRequest.getTarget()).orElseThrow(RuntimeException::new);

        Favorite favorite = new Favorite(memberId, source, target);

        try {
            Favorite updated = favoriteRepository.save(favorite);
            return FavoriteResponse.of(updated);
        } catch (DataIntegrityViolationException e) {
            throw new FavoriteException.Duplicated(favorite);
        }
    }

}
