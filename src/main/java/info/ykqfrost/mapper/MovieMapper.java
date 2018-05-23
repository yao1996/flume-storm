package info.ykqfrost.mapper;

import info.ykqfrost.bean.Movie;

/**
 * @author Yao Keqi
 * @date 2018/5/18
 */
public interface MovieMapper {
    /**
     * 插入新的电影信息
     *
     * @param movie 电影
     */
    void insertMovie(Movie movie);

    Movie selectMovieByMovieId(int movieId);
}
