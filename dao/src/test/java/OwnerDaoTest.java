import dao.OwnerDao;
import enums.CatColor;
import enums.CatType;
import entity.Owners;
import entity.Owners;
import entity.Owners;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OwnerDaoTest {

    private static Owners mafioznik;
    private static Owners limarchik;
    private static OwnerDao ownerDao;

    @BeforeEach
    void setUp() {
        mafioznik = Owners.CreateOwnerBuilder()
                .withName("Mihail Petrovich")
                .withBirthday(new Date(122, 5, 20))
                .build();

        limarchik = Owners.CreateOwnerBuilder()
                .withName("Sraniy Matstat")
                .withBirthday(new Date(122, 5, 20))
                .build();

        ownerDao = new OwnerDao();
    }


    @Test
    void persist() {
        ownerDao.deleteAll();

        ownerDao.persist(mafioznik);
        List<Owners> currentOwners = ownerDao.findAll();

        Assertions.assertEquals(1, currentOwners.size());
    }

    @Test
    void update() {
        ownerDao.deleteAll();

        Owners mafioznikBefore = ownerDao.persist(mafioznik);
        mafioznik = mafioznikBefore.toBuilder().withName("Fredi Shoevich").build();
        Owners mafioznikAfter = ownerDao.update(mafioznik);

        Assertions.assertEquals("Fredi Shoevich", mafioznikAfter.getName());

        Assertions.assertNotEquals(mafioznikBefore.getName(), mafioznikAfter.getName());
    }

    @Test
    void findById() {
        ownerDao.deleteAll();

        Owners persistedMafioznik = ownerDao.persist(mafioznik);
        Owners foundedMafioznik = ownerDao.findById(persistedMafioznik.getId());

        Assertions.assertNotNull(foundedMafioznik);
        Assertions.assertEquals(persistedMafioznik.getId(), foundedMafioznik.getId());
        Assertions.assertEquals(persistedMafioznik, foundedMafioznik);
    }

    @Test
    void delete() {
        ownerDao.deleteAll();

        Owners persistedMafioznik = ownerDao.persist(mafioznik);
        Owners persistedLimar = ownerDao.persist(limarchik);
        ownerDao.delete(persistedMafioznik);

        Owners foundedMafioznik = ownerDao.findById(persistedMafioznik.getId());

        Assertions.assertNull(foundedMafioznik);
        List<Owners> currentOwners = ownerDao.findAll();
        Assertions.assertEquals(1, currentOwners.size());
        Assertions.assertEquals(currentOwners.get(0).getId(), persistedLimar.getId());
    }
}