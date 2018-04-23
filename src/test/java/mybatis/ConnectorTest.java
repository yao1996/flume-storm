package mybatis;

import info.ykqfrost.bean.ErrorLog;
import info.ykqfrost.mapper.ErrorLogMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author Yao Keqi
 * @date 2018/4/23
 */
public class ConnectorTest {

    @Test
    public void testConnector() throws IOException {
        String res = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(res);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ErrorLogMapper errorLogMapper = session.getMapper(ErrorLogMapper.class);
            ErrorLog errorLog = new ErrorLog();
            errorLog.setDate(new Date());
            errorLog.setMsg("aa");
            errorLog.setLocation("bb");
            errorLogMapper.insertError(errorLog);
            session.commit();
        } finally {
            session.close();
        }
    }
}
