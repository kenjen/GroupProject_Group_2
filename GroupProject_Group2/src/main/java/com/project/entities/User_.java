package com.project.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-04-13T20:06:23.363+0100")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Integer> userType;
}
