package au.com.teamarrow.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import au.com.teamarrow.dao.CustomLongTermTrendDataRepository;
import au.com.teamarrow.model.LongTermTrendData;

@Repository
public class LongTermTrendDataRepositoryImpl implements CustomLongTermTrendDataRepository {
    
    @PersistenceContext
    private EntityManager em;

    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.showcase.snippets.AccountRepositoryCustom#removedExpiredAccounts(org.joda.time.LocalDate)
     */
    /*@Override
    public void removedExpiredAccounts(LocalDate reference) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> account = query.from(Account.class);

        query.where(cb.lessThan(account.get("expiryDate").as(Date.class), reference.toDateMidnight().toDate()));

        for (Account each : em.createQuery(query).getResultList()) {
            em.remove(each);
        }
    }*/
    
    @Override
    public List<LongTermTrendData> getTrendDataForDay(Integer deviceId, DateTime day) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LongTermTrendData> query = cb.createQuery(LongTermTrendData.class);
        Root<LongTermTrendData> data = query.from(LongTermTrendData.class);
        
        query.where(
            cb.and(
                cb.lessThan(data.get("timestamp").as(DateTime.class), day.plusDays(1).toDateTime()),
                cb.greaterThan(data.get("timestamp").as(DateTime.class), day.toDateTime())));
        
        return em.createQuery(query).getResultList();
    }
}
