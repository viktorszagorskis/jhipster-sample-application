package org.vizadev.ddcapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Briefing.
 */
@Entity
@Table(name = "briefing")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Briefing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "brief_title")
    private String briefTitle;

    @Column(name = "scoring")
    private Long scoring;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "briefing_task",
               joinColumns = @JoinColumn(name = "briefing_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
    private Set<Task> tasks = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("briefings")
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBriefTitle() {
        return briefTitle;
    }

    public Briefing briefTitle(String briefTitle) {
        this.briefTitle = briefTitle;
        return this;
    }

    public void setBriefTitle(String briefTitle) {
        this.briefTitle = briefTitle;
    }

    public Long getScoring() {
        return scoring;
    }

    public Briefing scoring(Long scoring) {
        this.scoring = scoring;
        return this;
    }

    public void setScoring(Long scoring) {
        this.scoring = scoring;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Briefing tasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Briefing addTask(Task task) {
        this.tasks.add(task);
        task.getBriefings().add(this);
        return this;
    }

    public Briefing removeTask(Task task) {
        this.tasks.remove(task);
        task.getBriefings().remove(this);
        return this;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Briefing employee(Employee employee) {
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
        if (!(o instanceof Briefing)) {
            return false;
        }
        return id != null && id.equals(((Briefing) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Briefing{" +
            "id=" + getId() +
            ", briefTitle='" + getBriefTitle() + "'" +
            ", scoring=" + getScoring() +
            "}";
    }
}
