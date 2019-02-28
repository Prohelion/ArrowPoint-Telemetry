package com.prohelion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "lng_term_trend_data", schema = "public")
public class LongTermTrendData extends AbstractMeasurementData {

    private static final long serialVersionUID = 5804002592776147515L;

    @Id
    @Column(name = "lng_term_trend_data_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    public long getId() {
        return this.id;
    }
}
