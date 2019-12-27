package org.vizadev.ddcapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Organization.
 */
@Entity
@Table(name = "organization")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "org_name", nullable = false)
    private String orgName;

    @NotNull
    @Column(name = "org_reg_num", nullable = false)
    private String orgRegNum;

    @OneToMany(mappedBy = "organization")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Location> locations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public Organization orgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgRegNum() {
        return orgRegNum;
    }

    public Organization orgRegNum(String orgRegNum) {
        this.orgRegNum = orgRegNum;
        return this;
    }

    public void setOrgRegNum(String orgRegNum) {
        this.orgRegNum = orgRegNum;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Organization locations(Set<Location> locations) {
        this.locations = locations;
        return this;
    }

    public Organization addLocation(Location location) {
        this.locations.add(location);
        location.setOrganization(this);
        return this;
    }

    public Organization removeLocation(Location location) {
        this.locations.remove(location);
        location.setOrganization(null);
        return this;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return id != null && id.equals(((Organization) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", orgName='" + getOrgName() + "'" +
            ", orgRegNum='" + getOrgRegNum() + "'" +
            "}";
    }
}
