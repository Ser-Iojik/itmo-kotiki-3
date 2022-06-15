package entity;

import enums.CatColor;
import enums.CatType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "cats", schema = "public", catalog = "postgres")
@Value
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Builder(builderClassName = "CatBuilder",
builderMethodName = "CreateCatBuilder",
toBuilder = true,
access = AccessLevel.PUBLIC,
setterPrefix = "with")
public class Cats {
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    @Column(name = "id", nullable = false)
    int id;

    @Basic
    @Column(name = "name", nullable = true, length = 64)
    String name;

    @Basic
    @Column(name = "birthday", nullable = true)
    Date birthday;

    @Enumerated(EnumType.STRING)
    @Basic
    @Column(name = "breed", nullable = true, length = 32)
    CatType breed; // enum

    @Enumerated(EnumType.STRING)
    @Basic
    @Column(name = "color", nullable = true, length = 32)
    CatColor color;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    Owners owner;
}
