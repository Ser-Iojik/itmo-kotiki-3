package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "friends", schema = "public", catalog = "postgres")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Friends {
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "first_cat_id", nullable = false)
    private int firstCatId;

    @Basic
    @Column(name = "second_cat_id", nullable = false)
    private int secondCatId;
}
