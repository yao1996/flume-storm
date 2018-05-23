package info.ykqfrost.mapper;

import info.ykqfrost.bean.MovieRating;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Yao Keqi
 * @date 2018/5/18
 */
public interface MovieRatingMapper {
    /**
     * 插入新的电影评分
     *
     * @param movieRating 电影评分
     */
    void insertMovieRating(MovieRating movieRating);

    List<MovieRating> selectRatingByUserId(@Param("userId") int userId,@Param("score") int score);
}
