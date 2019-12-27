package org.vizadev.ddcapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Task entity.\n@author The JHipster team.
 */
@ApiModel(description = "Task entity.\n@author The JHipster team.")
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "max_scoring")
    private Long maxScoring;

    @Column(name = "h_5_p_link")
    private String h5pLink;

    @Column(name = "link")
    private String link;

    @ManyToMany(mappedBy = "tasks")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Briefing> briefings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Task title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMaxScoring() {
        return maxScoring;
    }

    public Task maxScoring(Long maxScoring) {
        this.maxScoring = maxScoring;
        return this;
    }

    public void setMaxScoring(Long maxScoring) {
        this.maxScoring = maxScoring;
    }

    public String geth5pLink() {
        return h5pLink;
    }

    public Task h5pLink(String h5pLink) {
        this.h5pLink = h5pLink;
        return this;
    }

    public void seth5pLink(String h5pLink) {
        this.h5pLink = h5pLink;
    }

    public String getLink() {
        return link;
    }

    public Task link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Set<Briefing> getBriefings() {
        return briefings;
    }

    public Task briefings(Set<Briefing> briefings) {
        this.briefings = briefings;
        return this;
    }

    public Task addBriefing(Briefing briefing) {
        this.briefings.add(briefing);
        briefing.getTasks().add(this);
        return this;
    }

    public Task removeBriefing(Briefing briefing) {
        this.briefings.remove(briefing);
        briefing.getTasks().remove(this);
        return this;
    }

    public void setBriefings(Set<Briefing> briefings) {
        this.briefings = briefings;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", maxScoring=" + getMaxScoring() +
            ", h5pLink='" + geth5pLink() + "'" +
            ", link='" + getLink() + "'" +
            "}";
    }
}
