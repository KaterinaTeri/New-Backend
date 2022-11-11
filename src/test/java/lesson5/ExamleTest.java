package lesson5;

import lesson6.db.dao.ProductsMapper;
import lesson6.db.model.Products;
import lesson6.db.model.ProductsExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ExamleTest {

    public static void main(String[] args) throws IOException {
        SqlSession session = null;

        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new
                    SqlSessionFactoryBuilder().build(inputStream);
//        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
//                .build(Resources.getResourceAsStream("mybatis-config.xml"));
            session = sqlSessionFactory.openSession();

            ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
            Products product = productsMapper.selectByPrimaryKey(444L);
            System.out.println(product);

            ProductsExample example = new ProductsExample();
            example.createCriteria()
                    .andIdEqualTo(10L)
                    .andTitleLike("apple")
                    .andPriceGreaterThan(10);

            List<Products> products = productsMapper.selectByExample(example);
            System.out.println(products);

//            example.clear();
//            example.createCriteria()
//                    .andCategory_idEqualTo(2L);

            products = productsMapper.selectByExample(example);
            System.out.println(products);

//            productsMapper.deleteByPrimaryKey(444L);
//            example.clear();

            example.createCriteria()
                    .andTitleLike("apple")
                    .andPriceGreaterThan(10);

            products = productsMapper.selectByExample(example);
            System.out.println(products);
            System.out.println("Hi");
        } finally {
            session.close();
        }
    }

}
