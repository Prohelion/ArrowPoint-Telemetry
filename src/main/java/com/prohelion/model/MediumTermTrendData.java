package com.prohelion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "med_term_trend_data", schema = "public")
public class MediumTermTrendData extends AbstractMeasurementData {

    private static final long serialVersionUID = -1540004028164463235L;

    @Id
    @Column(name = "med_term_trend_data_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return this.id;
    }
}
