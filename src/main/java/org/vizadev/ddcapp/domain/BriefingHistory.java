package org.vizadev.ddcapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import org.vizadev.ddcapp.domain.enumeration.TypeOfBriefing;

/**
 * A BriefingHistory.
 */
@Entity
@Table(name = "briefing_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BriefingHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_briefing")
    private TypeOfBriefing typeOfBriefing;

    @OneToOne
    @JoinColumn(unique = true)
    private Briefing job;

    @OneToOne
    @JoinColumn(unique = true)
    private Department department;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public BriefingHistory startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public BriefingHistory endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public TypeOfBriefing getTypeOfBriefing() {
        return typeOfBriefing;
    }

    public BriefingHistory typeOfBriefing(TypeOfBriefing typeOfBriefing) {
        this.typeOfBriefing = typeOfBriefing;
        return this;
    }

    public void setTypeOfBriefing(TypeOfBriefing typeOfBriefing) {
        this.typeOfBriefing = typeOfBriefing;
    }

    public Briefing getJob() {
        return job;
    }

    public BriefingHistory job(Briefing briefing) {
        this.job = briefing;
        return this;
    }

    public void setJob(Briefing briefing) {
        this.job = briefing;
    }

    public Department getDepartment() {
        return department;
    }

    public BriefingHistory department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getEmployee() {
        return employee;
    }

    public BriefingHistory employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BriefingHistory)) {
            return false;
        }
        return id != null && id.equals(((BriefingHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BriefingHistory{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", typeOfBriefing='" + getTypeOfBriefing() + "'" +
            "}";
    }
}
