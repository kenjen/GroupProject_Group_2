package com.project.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-04-13T20:06:23.363+0100")
@StaticMetamodel(MccMnc.class)
public class MccMnc_ {
	public static volatile SingularAttribute<MccMnc, Integer> id;
	public static volatile SingularAttribute<MccMnc, Integer> mcc;
	public static volatile SingularAttribute<MccMnc, Integer> mnc;
	public static volatile SingularAttribute<MccMnc, String> country;
	public static volatile SingularAttribute<MccMnc, String> operator;
}
