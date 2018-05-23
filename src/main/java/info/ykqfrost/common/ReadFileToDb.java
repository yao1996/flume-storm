package info.ykqfrost.common;

import info.ykqfrost.bean.Movie;
import info.ykqfrost.bean.MovieRating;
import info.ykqfrost.mapper.MovieMapper;
import info.ykqfrost.mapper.MovieRatingMapper;
import org.apache.ibatis.session.SqlSession;

import java.io.*;

/**
 * @author Yao Keqi
 * @date 2018/5/18
 */
public class ReadFileToDb {
    private SqlSession session = MybatisUtils.getSession();

    private void readMovie2Db() throws IOException {
        MovieMapper movieMapper = session.getMapper(MovieMapper.class);
        File file = new File(Constants.MOVIE_FILE_PATH);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        while ((s = br.readLine()) != null && !"".equals(s)) {
            String[] paras = s.split("::");
            Movie movie = new Movie();
            movie.setMovieId(Integer.parseInt(paras[0]));
            movie.setName(paras[1]);
            movie.setType(paras[2]);
            movieMapper.insertMovie(movie);
            session.commit();
            System.out.println(movie.getMovieId());
        }
        br.close();
    }

    private void readRating2Db() throws IOException {
        MovieRatingMapper movieRatingMapper = session.getMapper(MovieRatingMapper.class);
        File file = new File(Constants.RATING_FILE_PATH);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        while ((s = br.readLine()) != null && !"".equals(s)) {
            String[] paras = s.split("::");
            MovieRating movieRating = new MovieRating();
            movieRating.setUserId(Integer.parseInt(paras[0]));
            movieRating.setMovieId(Integer.parseInt(paras[1]));
            movieRating.setScore(Integer.parseInt(paras[2]));
            movieRatingMapper.insertMovieRating(movieRating);
            session.commit();
        }
        br.close();
    }

    public static void main(String[] args) {
        ReadFileToDb readFileToDb = new ReadFileToDb();
        try {
            readFileToDb.readMovie2Db();
            readFileToDb.readRating2Db();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
