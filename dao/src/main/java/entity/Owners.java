package entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "owners", schema = "public", catalog = "postgres")
@Value
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Builder(builderClassName = "OwnerBuilder",
        builderMethodName = "CreateOwnerBuilder",
        toBuilder = true,
        access = AccessLevel.PUBLIC,
        setterPrefix = "with")
public class Owners {
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    @Column(name = "id", nullable = false)
    int id;

    @Basic
    @Column(name = "name", nullable = true, length = -1)
    String name;

    @Basic
    @Column(name = "birthday", nullable = true)
    Date birthday;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Singular List<Cats> cats;
}
