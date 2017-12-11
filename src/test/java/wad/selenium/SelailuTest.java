package wad.selenium;

import org.fluentlenium.adapter.junit.FluentTest;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SelailuTest extends FluentTest {

    @LocalServerPort
    private Integer port;

    @Test
    public void voiSelataSivustoa() {
        goTo("http://localhost:" + port);

        find(By.linkText("UUSIMMAT")).click();

        assertTrue(pageSource().contains("Uusimmat uutiset"));

        find("a").first().click();

        assertFalse(pageSource().contains("Uusimmat uutiset"));

        find(By.linkText("LUETUIMMAT")).click();

        assertTrue(pageSource().contains("Luetuimmat uutiset"));

        find(By.linkText("KIRJAUDU")).click();

        assertFalse(pageSource().contains("Luetuimmat uutiset"));
        assertTrue(pageSource().contains("Kirjaudu uutisten hallintaan"));

        // paluu etusivulle
        find("a").first().click();

        assertFalse(pageSource().contains("Kirjaudu uutisten hallintaan"));
    }

    @Test
    public void voiSiirtyaHallintapaneeliinOikeillaTunnuksilla() {
        goTo("http://localhost:" + port);

        find(By.linkText("KIRJAUDU")).click();

        assertTrue(pageSource().contains("Kirjaudu uutisten hallintaan"));

        find("#kayttajatunnus").fill().with("hakkeri");
        find("#salasana").fill().with("arvaus");
        find("form").first().submit();

        assertTrue(pageSource().contains("Kirjaudu uutisten hallintaan"));

        find("#kayttajatunnus").fill().with("admin");
        find("#salasana").fill().with("password");
        find("form").first().submit();

        assertTrue(pageSource().contains("Luo uusi uutinen"));

        // paluu etusivulle
        find("a").first().click();

        assertFalse(pageSource().contains("Luo uusi uutinen"));

    }
}
