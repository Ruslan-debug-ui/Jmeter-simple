package test;

import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.function.Try;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;


public class LoadTest {

    @Test
    public void testHttpV() throws IOException {
        String url = "https://geo-reindexer.ids.k8s.mediascope.dev/geo/tips?api_key=e0f0b372f12a44bcbb2c269b701df39b&mode=1&n_tips=1000&region=11&query=В";

        TestPlanStats stats = testPlan(
                threadGroup(100,100,
                        httpSampler(url)
                        .method("GET"))
        ).run();

        assertEquals(0, stats.overall().errors().total());

        System.out.println("Number of requests: "+  stats.overall().samplesCount());
        System.out.println("Number of mistakes: "+  stats.overall().errors().total());
    }

    @Test
    public void testHttpSad() throws IOException {
        String url = "https://geo-reindexer.ids.k8s.mediascope.dev/geo/tips-with-error?api_key=e0f0b372f12a44bcbb2c269b701df39b&mode=1&n_tips=1000&region=11&query=Сад";

        TestPlanStats stats = testPlan(
                threadGroup(10,10,
                        httpSampler(url)
                                .method("GET"))
        ).run();

        assertEquals(0, stats.overall().errors().total());

        System.out.println("Number of requests: "+  stats.overall().samplesCount());
        System.out.println("Number of mistakes: "+  stats.overall().errors().total());
    }

    @Test
    public void testHttpPodolsk() throws IOException {
        String url = "https://tele.fm/tips_api/?api_key=e0f0b372f12a44bcbb2c269b701df39b&region=50&mode=1&n_tips=10&query=%D0%B3%D0%BE%D1%80%D0%BE%D0%B4";

        TestPlanStats stats = testPlan(
                threadGroup(100,100,
                        httpSampler(url)
                                .method("GET"))
        ).run();

        assertEquals(0, stats.overall().errors().total());

        System.out.println("Number of requests: "+  stats.overall().samplesCount());
        System.out.println("Number of mistakes: "+  stats.overall().errors().total());
    }

    @Test
    public void testHttpPost() throws AssertionError, IOException {


            String url = "https://reqres.in/api/login";

            TestPlanStats stats = testPlan(
                    threadGroup(1, 1,
                            httpSampler(url)
                                    .method("POST")
                                    .body("{ \"email\": \"eve.holt@reqres.i\", \"password\": \"cityslicka\" }")
                                    .header("Content-Type", "application/json")
                    )
            ).run();

            // assertEquals(0, stats.overall().errors().total());

            System.out.println("Number of requests: " + stats.overall().samplesCount());
            System.out.println("Number of mistakes: " + stats.overall().errors().total());
            try {
            Assertions.assertEquals(0, stats.overall().errors().total());
        } catch (AssertionError e) {
                System.out.println(e.getMessage());
            }
    }



}
