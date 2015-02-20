package com.project.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.project.entities.BaseData;
import com.project.entities.FailureClass;
import com.project.entities.UE;

@Stateless
@Local
public class JPABaseDataDAO implements BaseDataDAO {
	
	@EJB
	private UserEquipmentDAO ueDAO;
	@EJB
	private EventCauseDAO eventCauseDAO;
	@EJB
	FailureClassDAOLocal failurClassDAO;


	@PersistenceContext(unitName="GroupProject_Group2") EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public Collection<BaseData> getAllBaseData() {
		Query query = entityManager.createNamedQuery("BaseData.getAllBaseData");
		List<BaseData> data = query.getResultList();
		return data;
	}

	@SuppressWarnings("rawtypes")
	public void addAllBaseData(Collection baseDataList) {
		entityManager.createNativeQuery("truncate table base_data").executeUpdate();
		entityManager.createNativeQuery("alter table base_data AUTO_INCREMENT = 1").executeUpdate();
		for (Object o : baseDataList) {
			entityManager.persist(o);
		}
		//addUEForeignKey();
		//addFailureClassForeignKey();
	}
	
	public Collection addUEForeignKey(){
		
		Collection<BaseData> allBaseData = this.getAllBaseData();
		Long l = System.nanoTime();
		Date date = new Date();
		BaseData sampleBD = new BaseData(date, 4, 4, 4, 4, 4, 4, 4, 4, "TEST", l, "TEST", "TEST", "TEST");
		allBaseData.add(sampleBD);
		List<UE> allUE = (List<UE>) ueDAO.getAllUEs();
		UE sampleUE = new UE(4, "test", "test", "test");
		allUE.add(sampleUE);
		for (BaseData o : allBaseData) {
			for(UE ue : allUE){
				if(o.getTac().intValue()==ue.getTac()){
					o.setUeFK(ue);
				}
				else{
					//NB - FOR TEST ONLY
					o.setUeFK(allUE.get(allUE.size()-1));
				}
			}
		}
		Collection test = null;
		test.add(allUE);
		test.add(allBaseData);
		return test;
	}
	
	public Collection addFailureClassForeignKey(){
		Collection<BaseData> allBaseData = this.getAllBaseData();
		 Long l = System.nanoTime();
		 Date date = new Date();
		BaseData bd = new BaseData(date, 4, 4, 4, 4, 4, 4, 4, 4, "TEST", l, "TEST", "TEST", "TEST");
		allBaseData.add(bd);
		List<FailureClass> failureClasses = (List<FailureClass>) failurClassDAO.getAllFailureClasses();
		FailureClass sampleFC = new FailureClass(4, "test");
		failureClasses.add(sampleFC);
		for (BaseData o : allBaseData) {
			for(FailureClass fc : failureClasses){
				if(o.getFailureClass().intValue()==fc.getFailureClass()){
					o.setFaliureClassFK(fc);
				}
				else{
					//NB - FOR TEST ONLY
					o.setFaliureClassFK(failureClasses.get(failureClasses.size()-1));
				}
			}
		}
		Collection test = null;
		test.add(failureClasses);
		test.add(allBaseData);
		return test;
	}

}
