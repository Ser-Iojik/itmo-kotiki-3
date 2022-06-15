import dao.CatDao;
import dao.FriendsDao;
import entity.Friends;
import lombok.AllArgsConstructor;
import entity.Cats;
import entity.Owners;

import java.util.*;

@AllArgsConstructor
public class CatService {

    private final CatDao catDao;
    private final FriendsDao friendsDao;

    public Cats createCat(Cats cat) throws Exception {
        if (cat == null)
            throw new Exception("Invalid cat.");

        return catDao.persist(cat);
    }

    public Cats findCatById(int id) {
        return catDao.findById(id);
    }

    public List<Cats> findAllCats() {
        return catDao.findAll();
    }

    public void deleteById(int id) {
        Cats cat = catDao.findById(id);
        if (cat == null)
            return;

        catDao.delete(cat);
    }

    public void deleteAll() {
        catDao.deleteAll();
    }



    public Cats changeOwnerOfCat(int idOfCat, Owners newOwner) throws Exception {
        Cats cat = findCatById(idOfCat);
        if (cat == null)
            throw new Exception("Can't find cat");

        cat = cat.toBuilder().withOwner(newOwner).build();
        return catDao.update(cat);
    }

    public Cats changeNameOfCat(int idOfCat, String name) throws Exception {
        Cats cat = findCatById(idOfCat);
        if (cat == null)
            throw new Exception("Can't find cat");

        cat = cat.toBuilder().withName(name).build();
        return catDao.update(cat);
    }

    public void friendCats(int idOfFirstCat, int idOfSecondCat) throws Exception {
        if (idOfFirstCat == idOfSecondCat)
            return;

        Friends friends = new Friends();
        friends.setFirstCatId(idOfFirstCat);
        friends.setSecondCatId(idOfSecondCat);
        friendsDao.persist(friends);
    }

    public void unfriendCats(int idOfFirstCat, int idOfSecondCat) throws Exception {
        if (idOfFirstCat == idOfSecondCat)
            return;

        Friends friendshipOfFirstCat = friendsDao.findAll().stream()
                .filter(friendship -> friendship.getFirstCatId() == idOfFirstCat
                        && friendship.getSecondCatId() == idOfSecondCat).findFirst()
                .orElse(null);

        Friends friendshipOfSecondCat = friendsDao.findAll().stream()
                .filter(friendship -> friendship.getFirstCatId() == idOfSecondCat
                        && friendship.getSecondCatId() == idOfFirstCat).findFirst()
                .orElse(null);

        if (friendshipOfFirstCat != null) {
            friendsDao.delete(friendshipOfFirstCat);
        } else if (friendshipOfSecondCat != null) {
            friendsDao.delete(friendshipOfSecondCat);
        }
    }

    public List<Cats> getFriendsOfCat(int idOfCat) throws Exception {
        Cats cat = findCatById(idOfCat);
        if (cat == null)
            throw new Exception("Invalid cat");

        List<Friends> friendsOfCat = friendsDao.findAll().stream()
                .filter(friendship -> friendship.getFirstCatId() == idOfCat
                        || friendship.getSecondCatId() == idOfCat).toList();

        List<Cats> cats = new ArrayList<>();

        for (Friends friendship:friendsOfCat) {
            if (friendship.getFirstCatId() == idOfCat) {
                cats.add(catDao.findById(friendship.getSecondCatId()));
                continue;
            }
            cats.add(catDao.findById(friendship.getFirstCatId()));
        }

        return cats;
    }
}




















