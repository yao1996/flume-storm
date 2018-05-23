package info.ykqfrost.bean;

/**
 * @author Yao Keqi
 * @date 2018/5/18
 * 用户对某一电影的评分
 */
public class MovieRating {
    private int movieId;
    private int userId;
    private int score;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
