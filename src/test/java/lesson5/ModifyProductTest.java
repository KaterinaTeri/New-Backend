package lesson5;

import api.ProductService;
import com.github.javafaker.Faker;
import dto.Product;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import utils.RetrofitUtils;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

public class ModifyProductTest {

    static ProductService productService;
    Product product = new Product();
//    Faker faker = new Faker();
    int id = 3;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withId(3)
                .withTitle("Chicken")
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
    }

    @Test
    void modifyProductTest() throws IOException {
        Response<Product> response = productService.modifyProduct(product)
                .execute();
        id = response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }




}
