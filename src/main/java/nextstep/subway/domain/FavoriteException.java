package nextstep.subway.domain;

public class FavoriteException extends RuntimeException {
    public FavoriteException(String message) {
        super(message);
    }

    public static class Duplicated extends FavoriteException {
        public Duplicated(Favorite favorite) {
            super("Duplicated Favorite");
        }
    }

    public static class UnauthorizedMember extends FavoriteException {
        public UnauthorizedMember() {
            super("Unauthorized member request");
        }
    }
}
