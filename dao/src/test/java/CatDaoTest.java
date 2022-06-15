import dao.CatDao;
import enums.CatColor;
import enums.CatType;
import entity.Cats;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

class CatDaoTest {

    private static Cats biba;
    private static Cats boba;

    private static CatDao catDao;

    @BeforeEach
    void setUp() {
        biba = Cats.CreateCatBuilder()
                .withName("Bibonskiy")
                .withBirthday(new Date(122, 5, 20))
                .withColor(CatColor.BLACK)
                .withBreed(CatType.RUS)
                .build();

        boba = Cats.CreateCatBuilder()
                .withName("Bolkonskiy")
                .withBirthday(new Date(122, 4, 5))
                .withColor(CatColor.WHITE)
                .withBreed(CatType.SYS)
                .build();

        catDao = new CatDao();
    }


    @Test
    void persist1Cat_DBShouldContain1Cat() {
        catDao.deleteAll();
        //biba was created in setUp()

        catDao.persist(biba);

        List<Cats> currentCats = catDao.findAll();
        Assertions.assertEquals(1, currentCats.size());
    }

    @Test
    void updateCat_CatDataChanged() {
        catDao.deleteAll();

        Cats clone = biba.toBuilder().build();
        catDao.update(clone);

        Cats bibaBefore = catDao.persist(biba);
        biba = bibaBefore.toBuilder().withName("Ben, ohoho, no").withBreed(CatType.BEN).withColor(CatColor.EBONY).build();
        Cats bibaAfter = catDao.update(biba);

        Assertions.assertEquals("Ben, ohoho, no", bibaAfter.getName());
        Assertions.assertEquals(CatType.BEN.toString(), bibaAfter.getBreed().toString());
        Assertions.assertEquals(CatColor.EBONY.toString(), bibaAfter.getColor().toString());

        Assertions.assertNotEquals(bibaBefore.getName(), bibaAfter.getName());
        Assertions.assertNotEquals(bibaBefore.getBreed().toString(), bibaAfter.getBreed().toString());
        Assertions.assertNotEquals(bibaBefore.getColor().toString(), bibaAfter.getColor().toString());

    }

    @Test
    void findById() {
        catDao.deleteAll();

        Cats persistedBiba = catDao.persist(biba);
        Cats foundedBiba = catDao.findById(persistedBiba.getId());

        Assertions.assertNotNull(foundedBiba);
        Assertions.assertEquals(persistedBiba.getId(), foundedBiba.getId());
        Assertions.assertEquals(persistedBiba, foundedBiba);
    }

    @Test
    void delete() {
        catDao.deleteAll();

        Cats persistedBiba = catDao.persist(biba);
        Cats persistedBoba = catDao.persist(boba);
        catDao.delete(biba);

        Cats foundedBiba = catDao.findById(persistedBiba.getId());

        Assertions.assertNull(foundedBiba);
        List<Cats> currentCats = catDao.findAll();
        Assertions.assertEquals(1, currentCats.size());
        Assertions.assertEquals(currentCats.get(0), persistedBoba);
    }
}