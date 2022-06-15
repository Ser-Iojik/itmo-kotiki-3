import dao.CatDao;
import dao.OwnerDao;
import lombok.AllArgsConstructor;
import entity.Cats;
import entity.Owners;

import java.util.List;

@AllArgsConstructor
public class OwnerService {

    private final OwnerDao ownerDao;

    public Owners createOwner(Owners entity) throws Exception {
        if (entity == null)
            throw new Exception("Entity was null");
        return ownerDao.persist(entity);
    }

    public Owners findOwnerById(int id) {
        return ownerDao.findById(id);
    }

    public List<Owners> findAllOwners() {
        return ownerDao.findAll();
    }

    public void deleteById(int id) {
        Owners owner = ownerDao.findById(id);
        if (owner == null)
            return;
        ownerDao.delete(owner);
    }

    public void deleteAll() {
        ownerDao.deleteAll();
    }

    public Owners addCat(Owners owner, Cats cat) throws Exception {
        if (cat == null)
            throw new Exception("Invalid cat");
        if(owner == null)
            throw new Exception("Invalid owner");

        owner = owner.toBuilder().withCat(cat).build();
        Owners updatedOwner = ownerDao.update(owner);
        return updatedOwner;
    }

    public void deleteCat(int idOfOwner, Cats cat) throws Exception {
        if(cat == null)
            return;

        Owners owner = ownerDao.findById(idOfOwner);
        if (owner == null)
            throw new Exception("Can't find owner");

        List<Cats> cats = findCatsOfOwner(idOfOwner);
        Cats foundedCat = cats.stream().filter(someCat -> someCat.getId() == cat.getId())
                .findFirst().orElse(null);
        if (foundedCat == null)
            throw new Exception("Can't find owner's cat");
        cats.remove(foundedCat);

        owner = owner.toBuilder().clearCats().withCats(cats).build();
        ownerDao.update(owner);
    }

    public List<Cats> findCatsOfOwner(int idOfOwner) throws Exception {
        Owners owner = findOwnerById(idOfOwner);
        if (owner == null)
            throw new Exception("Can't find owner");

        return owner.getCats();
    }
}