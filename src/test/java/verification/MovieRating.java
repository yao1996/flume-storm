package verification;

import info.ykqfrost.bean.Movie;
import info.ykqfrost.common.MybatisUtils;
import info.ykqfrost.mapper.MovieMapper;
import info.ykqfrost.mapper.MovieRatingMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yao Keqi
 * @date 2018/5/19
 */
public class MovieRating {
    String[] types = {"Action", "Adventure", "Animation", "Children's",
            "Comedy", "Crime", "Documentary", "Drama",
            "Fantasy", "Film-Noir", "Horror", "Musical",
            "Mystery", "Romance", "Sci-Fi", "Thriller", "War", "Western"};

    @Test
    public void test() {
        SqlSession session = MybatisUtils.getSession();
        MovieRatingMapper mapper = session.getMapper(MovieRatingMapper.class);
        MovieMapper movieMapper = session.getMapper(MovieMapper.class);
        List<info.ykqfrost.bean.MovieRating> movieRatings = mapper.selectRatingByUserId(10, 4);
        List<Movie> movies = new ArrayList<Movie>();
        for (info.ykqfrost.bean.MovieRating movieRating : movieRatings) {
            Movie movie = movieMapper.selectMovieByMovieId(movieRating.getMovieId());
            movies.add(movie);
        }
        getTypeNums(movies);
    }

    public void getTypeNums(List<Movie> movies) {
        HashMap<String,Integer> map = new HashMap<String, Integer>(types.length);
        for (String s : types) {
            map.put(s,0);
        }

        for (Movie movie : movies) {
            System.out.println(movie.getMovieId() + "  " + movie.getName()+"  "+movie.getType());
            String[] types = movie.getType().split("\\|");
            for (String s : types) {
                int i = map.get(s);
                map.put(s,i+1);
            }
        }
        for (Map.Entry<String,Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "  " + entry.getValue());
        }
    }
}
