package com.project.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-04-13T10:11:02.957+0100")
@StaticMetamodel(ErrorBaseData.class)
public class ErrorBaseData_ {
	public static volatile SingularAttribute<ErrorBaseData, Integer> id;
	public static volatile SingularAttribute<ErrorBaseData, Date> date;
	public static volatile SingularAttribute<ErrorBaseData, Integer> eventId;
	public static volatile SingularAttribute<ErrorBaseData, Integer> failureClass;
	public static volatile SingularAttribute<ErrorBaseData, Integer> tac;
	public static volatile SingularAttribute<ErrorBaseData, Integer> mnc;
	public static volatile SingularAttribute<ErrorBaseData, Integer> mcc;
	public static volatile SingularAttribute<ErrorBaseData, Integer> cellId;
	public static volatile SingularAttribute<ErrorBaseData, Integer> duration;
	public static volatile SingularAttribute<ErrorBaseData, Integer> causeCode;
	public static volatile SingularAttribute<ErrorBaseData, String> neVersion;
	public static volatile SingularAttribute<ErrorBaseData, Long> imsi;
	public static volatile SingularAttribute<ErrorBaseData, String> hier3Id;
	public static volatile SingularAttribute<ErrorBaseData, String> hier32Id;
	public static volatile SingularAttribute<ErrorBaseData, String> hier321Id;
}
