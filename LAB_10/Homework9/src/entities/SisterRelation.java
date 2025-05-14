package entities;

import javax.persistence.*;

@Entity
@Table(name = "sisters")
public class SisterRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private City city1;

    @ManyToOne
    private City city2;

    public SisterRelation() {}

    public SisterRelation(City city1, City city2) {
        this.city1 = city1;
        this.city2 = city2;
    }

    // Getters and Setters
}
