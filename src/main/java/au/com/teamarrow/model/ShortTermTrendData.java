package au.com.teamarrow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sht_term_trend_data", schema = "public")
public class ShortTermTrendData extends AbstractMeasurementData {

    private static final long serialVersionUID = 5723980283588654376L;

    @Id
    @Column(name = "sht_term_trend_data_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return this.id;
    }
}
