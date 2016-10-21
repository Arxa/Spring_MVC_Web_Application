package com.caveofprogramming.spring.web.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Component("messagesDao")
public class MessagesDAO 
{
	@Autowired
	private SessionFactory  sessionFactory;
	
	public Session session()
	{
		return sessionFactory.getCurrentSession();
	}

	public void saveOrUpdate(Message message) 
	{
		System.out.println(message);
		session().saveOrUpdate(message);	
	}

	public boolean delete(int id) 
	{
		Query query = session().createQuery("delete from Message where id=:id"); // This is not SQL, it's HQL
		query.setLong("id", id);
		return query.executeUpdate() == 1; 
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> getMessages() 
	{
		Criteria crit = session().createCriteria(Message.class);
		return crit.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> getMessages(String username) 
	{
		Criteria crit = session().createCriteria(Message.class);
		crit.add(Restrictions.eq("username", username));
		return crit.list();
	}
	
	public Message getMessage(int id) 
	{
		Criteria crit = session().createCriteria(Message.class);
		crit.add(Restrictions.idEq(id));
		return (Message)crit.uniqueResult();
	}
}
