package info.ykqfrost.bean;

/**
 * @author Yao Keqi
 * @date 2018/5/18
 * 电影信息
 */
public class Movie {
    private int movieId;
    private String name;
    private String type;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
