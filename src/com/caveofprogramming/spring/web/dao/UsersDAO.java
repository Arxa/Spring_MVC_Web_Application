package com.caveofprogramming.spring.web.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository // Classes with this annotation will have exception translation from Hibernate -> Spring. And we need that cause our ErrorHandler catches DataAccess exceptions which they belong to Spring not Hibernate.
@Transactional // Required in order to use Hibernate
@Component("usersDao")
public class UsersDAO 
{
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SessionFactory  sessionFactory;
	
	public Session session()
	{
		return sessionFactory.getCurrentSession();
	}
	
	@Transactional
	public void create(User user)
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		session().save(user);
	}

	public boolean exists(String username) 
	{
		User user = getUser(username);
		return user != null;
	}
	
	@SuppressWarnings("unchecked") // Disable warnings
	public List<User> getAllUsers() 
	{
		return session().createQuery("from User").list();
	}

	public User getUser(String username) 
	{
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.idEq(username));
		return (User)crit.uniqueResult();
	}
}
