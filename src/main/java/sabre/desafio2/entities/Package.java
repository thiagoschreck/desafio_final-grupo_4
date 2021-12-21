package sabre.desafio2.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Package<T> {
    @Id
    @Column(name = "package_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int packageNumber;

    private String name;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "client_id")
    private int ClientId;

    @ManyToOne
    @JoinColumn(name = "book_res_1")
    private T bookRes1;

    @ManyToOne
    @JoinColumn(name = "book_res_2")
    private T bookRes2;
}
