package info.ykqfrost.mahout;

import info.ykqfrost.bean.Movie;
import info.ykqfrost.bean.MovieRating;
import info.ykqfrost.common.MybatisUtils;
import info.ykqfrost.mapper.MovieMapper;
import info.ykqfrost.mapper.MovieRatingMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Yao Keqi
 * @date 2018/5/17
 */
public class MySlopeOneRecommender {
    private String[] types = {"Action", "Adventure", "Animation", "Children's",
            "Comedy", "Crime", "Documentary", "Drama",
            "Fantasy", "Film-Noir", "Horror", "Musical",
            "Mystery", "Romance", "Sci-Fi", "Thriller", "War", "Western"};
    private static final Logger logger = LoggerFactory.getLogger(MySlopeOneRecommender.class);

    public Recommender buildRecommender() {
        try {
            DataModel model = new FileDataModel(new File("src/main/resources/out.txt"));
            return new CachingRecommender(new SlopeOneRecommender(model));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TasteException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getTypeNums(List<Movie> movies) {
        HashMap<String,Integer> map = new HashMap<String, Integer>(types.length);
        for (String s : types) {
            map.put(s,0);
        }

        for (Movie movie : movies) {
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

    private List<Movie> getPreferMovies(int userId) {
        SqlSession session = MybatisUtils.getSession();
        MovieRatingMapper mapper = session.getMapper(MovieRatingMapper.class);
        MovieMapper movieMapper = session.getMapper(MovieMapper.class);
        List<MovieRating> movieRatings = mapper.selectRatingByUserId(userId, 5);
        List<Movie> movies = new ArrayList<Movie>();
        for (MovieRating movieRating : movieRatings) {
            Movie movie = movieMapper.selectMovieByMovieId(movieRating.getMovieId());
            movies.add(movie);
        }
        return movies;
    }

    public List<RecommendedItem> getItems(long userID, int size, Recommender recommender){
        List<RecommendedItem> recommendations = null;
        try {
            recommendations = recommender.recommend(userID, size);
        } catch (TasteException e) {
            e.printStackTrace();
        }
        return recommendations;
    }

    private void getConparation(int userId,Recommender recommender) {
        List<RecommendedItem> recommendedItems = getItems(userId,100,recommender);
        List<Movie> movies = new ArrayList<Movie>(100);
        SqlSession session = MybatisUtils.getSession();
        MovieMapper movieMapper = session.getMapper(MovieMapper.class);
        for (RecommendedItem recommendedItem : recommendedItems) {
            Movie movie = movieMapper.selectMovieByMovieId((int) recommendedItem.getItemID());
            movies.add(movie);
        }
        System.out.println("--------");
        getTypeNums(movies);
//        for (Movie movie : movies) {
//            System.out.println(movie.getMovieId() + "  " + movie.getName()+"  "+movie.getType());
//        }
        System.out.println("============");
        List<Movie> preferMovies = getPreferMovies(userId);
        getTypeNums(preferMovies);
        System.out.println("-----------------");
    }

    public static void main(String[] args) {
        MySlopeOneRecommender mySlopeOneRecommender = new MySlopeOneRecommender();
        Recommender recommender = mySlopeOneRecommender.buildRecommender();
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        while (i<10) {
            int userId = scanner.nextInt();
            i++;
            mySlopeOneRecommender.getConparation(userId,recommender);
        }
    }
}
