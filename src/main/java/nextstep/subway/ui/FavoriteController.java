package nextstep.subway.ui;

import nextstep.auth.authorization.AuthenticationPrincipal;
import nextstep.member.domain.LoginMember;
import nextstep.subway.applicaion.FavoriteService;
import nextstep.subway.applicaion.dto.FavoriteRequest;
import nextstep.subway.applicaion.dto.FavoriteResponse;
import nextstep.subway.domain.FavoriteException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<Void> createFavorite(@AuthenticationPrincipal LoginMember loginMember, @RequestBody FavoriteRequest favoriteRequest) {
        if (loginMember == null) {
            throw new FavoriteException.UnauthorizedMember();
        }
        FavoriteResponse favorite = favoriteService.saveFavorite(loginMember.getId(), favoriteRequest);
        return ResponseEntity.created(URI.create("/favorites/" + favorite.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> showFavorites(@AuthenticationPrincipal LoginMember loginMember) {
        if (loginMember == null) {
            throw new FavoriteException.UnauthorizedMember();
        }
        List<FavoriteResponse> favorites = favoriteService.findFavorites(loginMember.getId());
        return ResponseEntity.ok().body(favorites);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@AuthenticationPrincipal LoginMember loginMember, @PathVariable Long id) {
        if (loginMember == null) {
            throw new FavoriteException.UnauthorizedMember();
        }
        favoriteService.deleteFavorite(id, loginMember.getId());
        return ResponseEntity.noContent().build();
    }
}
