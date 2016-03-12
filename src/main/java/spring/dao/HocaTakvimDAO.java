package spring.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.model.HocaTakvim;
import spring.model.Odeme;
import spring.model.OdemeBilgisi;

/**
 * Created by dozac on 15/04/2015.
 */

@Repository
@Transactional
public class HocaTakvimDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    
    public int addHocaTakvim(HocaTakvim hocaTakvim) {
    	Session session = getSessionFactory().getCurrentSession();
		try {
			Serializable ddd =  session.save(hocaTakvim);
			session.flush();
			System.out.println("ffffff : " + hocaTakvim.getId());
			System.out.println("ffffff : " + ddd);
			
			return hocaTakvim.getId();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
    }

    public int deleteHocaTakvim(HocaTakvim hocaTakvim) {
    	try {
    		getSessionFactory().getCurrentSession().delete(hocaTakvim);
    		Query query = getSessionFactory().getCurrentSession().createQuery("delete from HocaTakvim where id = :id");
    		query.setLong("id", hocaTakvim.getId());
    		return query.executeUpdate();			
		} catch (Exception e) {
			return -1;
		}
    }

    public void updateHocaTakvim(HocaTakvim hocaTakvim) {
        getSessionFactory().getCurrentSession().update(hocaTakvim);
    }
    
	public void updateOdeme(OdemeBilgisi odemeBilgisi) {
		System.out.println("odeme guncellendi");
		String sql = "Select id, kredi from batibaha_canlii.odeme o where user = " + Integer.parseInt( odemeBilgisi.getUser()) + " and kredi > 0";
		List list = getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		
		if(list.size()> 0){
			int id = 0 ;
			int kredi = 0;
			for (Object obje : list) {
				Map row = (Map) obje;
				id = (Integer) row.get("id");
				kredi = (Integer) row.get("kredi");
			}
			Odeme odeme = new Odeme();
			odeme.setId(id);
			odeme.setUser(Integer.parseInt(odemeBilgisi.getUser()));
			odeme.setKredi(kredi + Integer.parseInt(odemeBilgisi.getLectures()));
			getSessionFactory().getCurrentSession().update(odeme);
			
//			getSessionFactory().getCurrentSession().createQuery("UPDATE batibaha_canlii.odeme SET kredi="+ 
//			(kredi + Integer.parseInt(odemeBilgisi.getLectures()) ) +" WHERE id=" + id ).executeUpdate();
		}else{
			Odeme odeme = new Odeme();
			odeme.setUser(Integer.parseInt(odemeBilgisi.getUser()));
			odeme.setKredi(Integer.parseInt(odemeBilgisi.getLectures()));
			getSessionFactory().getCurrentSession().save(odeme);
//			getSessionFactory().getCurrentSession().createQuery("INSERT INTO batibaha_canlii.odeme ( user, kredi) VALUES ("+ 
//					Integer.parseInt(odemeBilgisi.getUser())+", " + Integer.parseInt(odemeBilgisi.getLectures()) + ")").executeUpdate();
		}
		String updateSql = "UPDATE batibaha_canlii.wp_woocommerce_order_items SET durum="+ 
		Integer.parseInt(odemeBilgisi.getUser()) +" WHERE order_id=" + odemeBilgisi.getOrderid();
		getSessionFactory().getCurrentSession().createQuery( updateSql).executeUpdate();
		
	}

    public HocaTakvim getHocaTakvimById(int id) {
        List list = getSessionFactory().getCurrentSession().createQuery("from HocaTakvim where id=?").setParameter(0, id).list();
        return (HocaTakvim)list.get(0);
    }
    
	public List<OdemeBilgisi> getOdemes() {

		List<OdemeBilgisi> list = null ;
		List<OdemeBilgisi> listDonen = new ArrayList<OdemeBilgisi>() ;
		OdemeBilgisi odeme ;
		
		String sql = "SELECT oi.order_id as orderid, oim.meta_value as metavalue, "
				+ " (SELECT a. meta_value FROM batibaha_canlii.wp_postmeta a WHERE a.post_id = oi.order_id AND a.meta_key =  '_customer_user') as user , "
				+ " (SELECT a. meta_value FROM batibaha_canlii.wp_postmeta a WHERE a.post_id = oi.order_id AND a.meta_key =  '_billing_first_name') as name , "
				+ " (SELECT a. meta_value FROM batibaha_canlii.wp_postmeta a WHERE a.post_id = oi.order_id AND a.meta_key =  '_billing_last_name') as surname , "
				+ " (SELECT a. meta_value FROM batibaha_canlii.wp_postmeta a WHERE a.post_id = oi.order_id AND a.meta_key =  '_billing_email') as email , "
				+ " (SELECT a. meta_value FROM batibaha_canlii.wp_postmeta a WHERE a.post_id = oi.order_id AND a.meta_key =  '_billing_phone') as phone , "
				+ " (SELECT a. meta_value FROM batibaha_canlii.wp_postmeta a WHERE a.post_id = oi.order_id AND a.meta_key =  '_payment_method_title') as method , "
				+ " (SELECT a. meta_value FROM batibaha_canlii.wp_postmeta a WHERE a.post_id = oi.order_id AND a.meta_key =  '_order_total') as total , "
				+ " (SELECT a. meta_value FROM batibaha_canlii.wp_postmeta a WHERE a.post_id =oim.meta_value AND a.meta_key =  '_price') as price , "
				+ " (SELECT a. meta_value FROM batibaha_canlii.wp_postmeta a WHERE a.post_id =oim.meta_value AND a.meta_key =  'lectures') as lectures "
				+ " FROM  batibaha_canlii.wp_woocommerce_order_items oi,"
				+ " batibaha_canlii.wp_woocommerce_order_itemmeta oim"
				+ " where oi.order_item_id = oim.order_item_id "
				+ " and oim.meta_key = '_product_id'" + " and oi.durum = 0";
		
		try {
			list = getSessionFactory().getCurrentSession().createSQLQuery(sql).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			for (Object obje : list) {
				
				Map row = (Map)obje;
				odeme = new OdemeBilgisi();
				odeme.setOrderid((BigInteger) row.get("orderid"));
				odeme.setMetavalue((String) row.get("metavalue"));
				odeme.setUser((String) row.get("user"));
				odeme.setName((String) row.get("name"));
				odeme.setSurname((String) row.get("surname"));
				odeme.setEmail((String) row.get("email"));
				odeme.setPhone((String) row.get("phone"));
				odeme.setMethod((String) row.get("method"));
				odeme.setTotal((String) row.get("total"));
				odeme.setPrice((String) row.get("price"));
				odeme.setLectures((String) row.get("lectures"));
				listDonen.add(odeme);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listDonen;
	}

    public List<HocaTakvim> getHocaTakvims() {
        List list = getSessionFactory().getCurrentSession().createQuery("from HocaTakvim where startdate > ?").setParameter(0, new Date()).list();
        return list;
    }



}
