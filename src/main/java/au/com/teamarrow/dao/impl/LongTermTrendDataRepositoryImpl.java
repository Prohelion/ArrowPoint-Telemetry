package au.com.teamarrow.dao.impl;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import au.com.teamarrow.dao.CustomLongTermTrendDataRepository;
import au.com.teamarrow.model.LongTermTrendData;

@Repository
public class LongTermTrendDataRepositoryImpl implements CustomLongTermTrendDataRepository {
    
    @PersistenceContext
    private EntityManager em;  
    
    @Override
    public List<LongTermTrendData> getTrendDataForDay(Integer deviceId, OffsetDateTime day) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LongTermTrendData> query = cb.createQuery(LongTermTrendData.class);
        Root<LongTermTrendData> data = query.from(LongTermTrendData.class);
        
        query.where(
            cb.and(
                cb.lessThan(data.get("timestamp").as(OffsetDateTime.class), day.plusDays(1)),
                cb.greaterThan(data.get("timestamp").as(OffsetDateTime.class), day)));
        
        return em.createQuery(query).getResultList();
    }
}
