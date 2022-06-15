import enums.CatColor;
import enums.CatType;
import lombok.AllArgsConstructor;
import entity.Cats;
import entity.Owners;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MainService {

    private OwnerService ownerService;
    private CatService catService;

    public Cats registerCat(
            String name,
            Date birthday,
            CatType breed,
            CatColor color,
            int idOfOwner
            ) throws Exception {

        Owners owner = ownerService.findOwnerById(idOfOwner);
        if (owner == null)
            throw new Exception("Can't register cat because his owner doesn't registered.");

        Cats cat = Cats.CreateCatBuilder()
                .withName(name)
                .withBirthday(birthday)
                .withBreed(breed)
                .withColor(color)
                .withOwner(owner)
                .build();

        Owners updatedOwner = ownerService.addCat(owner, cat);

        return catService.createCat(cat);
    }

    public Owners registerOwner(
            String name,
            String surname,
            String mail,
            Date birthday,
            List<Cats> cats) throws Exception {
        if (cats == null)
            cats = new ArrayList<>();

        Owners owner = Owners.CreateOwnerBuilder()
                .withName(name)
                .withBirthday(birthday)
                .withCats(cats)
                .build();

        return ownerService.createOwner(owner);
    }

    public List<Cats> getCatsOfOwner(int idOfOwner) throws Exception {
        return ownerService.findCatsOfOwner(idOfOwner);
    }

    public List<Cats> getFriendsOfCat(int idOfCat) throws Exception {
        return catService.getFriendsOfCat(idOfCat);
    }

    public void friend2Cats(int idOfFirstCat, int idOfSecondCat) throws Exception {
        catService.friendCats(idOfFirstCat, idOfSecondCat);
    }

    public void unfriend2Cats(int idOfFirstCat, int idOfSecondCat) throws Exception {
        catService.unfriendCats(idOfFirstCat, idOfSecondCat);
    }

    public void friendCats(List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 1)
            return;

        for (int leftId : ids) {
            for (int rightId : ids) {
                catService.friendCats(leftId, rightId);
            }
        }
    }

    public void unfriendCats(List<Integer> ids) throws Exception {
        if (ids == null || ids.size() == 1)
            return;

        for (int leftId : ids) {
            for (int rightId : ids) {
                catService.unfriendCats(leftId, rightId);
            }
        }
    }

    public Cats changeNameOfCat(int idOfCat, String name) throws Exception {
        return catService.changeNameOfCat(idOfCat, name);
    }

    public Cats changeOwnerOfCat(int idOfCat, int idOfNewOwner) throws Exception {
        Owners newOwner = ownerService.findOwnerById(idOfNewOwner);
        Cats cat = catService.findCatById(idOfCat);

        ownerService.deleteCat(cat.getOwner().getId(), cat);
        return catService.changeOwnerOfCat(idOfCat, newOwner);
    }



    public Cats findCatById(int id) {
        return catService.findCatById(id);
    }

    public List<Cats> findCatsById(List<Integer> ids) {
        List<Cats> res = new ArrayList<>();
        for(int id : ids) {
            res.add(findCatById(id));
        }

        return res;
    }

    public Owners findOwnerById(int id) {
        return ownerService.findOwnerById(id);
    }

    public List<Owners> findOwnersById(List<Integer> ids) {
        List<Owners> res = new ArrayList<>();
        for(int id : ids) {
            res.add(findOwnerById(id));
        }

        return res;
    }
    public List<Cats> getAllCats() {
        return catService.findAllCats();
    }

    public List<Owners> getAllOwners() {
        return ownerService.findAllOwners();
    }

    public void deleteCatById(int id) {
        catService.deleteById(id);
    }

    public void deleteOwnerById(int id) {
        ownerService.deleteById(id);
    }

    public void deleteCatsById(List<Integer> ids) {
        for(int id : ids) {
            catService.deleteById(id);
        }
    }

    public void deleteOwnersById(List<Integer> ids) {
        for(int id : ids) {
            ownerService.deleteById(id);
        }
    }

    public void deleteAllOwners() {
        ownerService.deleteAll();
    }

    public void deleteAllCats() {
        catService.deleteAll();
    }


}
