package com.project.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-03-13T17:50:43.201+0000")
@StaticMetamodel(BaseData.class)
public class BaseData_ {
	public static volatile SingularAttribute<BaseData, Integer> id;
	public static volatile SingularAttribute<BaseData, Date> date;
	public static volatile SingularAttribute<BaseData, Integer> cellId;
	public static volatile SingularAttribute<BaseData, Integer> duration;
	public static volatile SingularAttribute<BaseData, String> neVersion;
	public static volatile SingularAttribute<BaseData, Long> imsi;
	public static volatile SingularAttribute<BaseData, String> hier3Id;
	public static volatile SingularAttribute<BaseData, String> hier32Id;
	public static volatile SingularAttribute<BaseData, String> hier321Id;
	public static volatile SingularAttribute<BaseData, FailureClass> failureClassFK;
	public static volatile SingularAttribute<BaseData, EventCause> eventCauseFK;
	public static volatile SingularAttribute<BaseData, UE> ueFK;
	public static volatile SingularAttribute<BaseData, MccMnc> mccMncFK;
}
