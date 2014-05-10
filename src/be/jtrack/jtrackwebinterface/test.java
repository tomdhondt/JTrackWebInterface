package be.jtrack.jtrackwebinterface;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import main.java.info.jtrac.dao.IPersistenceDAOImpl;
import main.java.info.jtrac.dao.NameQueryParam;
import main.java.info.jtrac.domain.User;
import main.java.info.jtrac.exception.data.DataDAOException;
import main.java.info.jtrac.exception.manager.ManagerException;
import main.java.info.jtrac.service.dto.UserDTO;
import main.java.info.jtrac.service.manager.IManager;
import main.java.info.jtrac.util.MappingUtil;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
@SuppressWarnings("unchecked")
public class test {
	/*
	 * Instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IManager<UserDTO> iUserManager = (IManager<UserDTO>) context.getBean("iUserManager");
	IPersistenceDAOImpl<User> userDAOImpl = (IPersistenceDAOImpl<User>) context.getBean("userDAOImpl");
	@Test
	public void testSenario01(){
		User u = new User();
		u.setName("name");
		try {
			userDAOImpl.persist(u);
		} catch (DataDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * Scenario 02 : Persist a UserDTO
	 * 01 - create UserDTO
	 * 02 - persist the UserDTO
	 * 03 - Check object in the database
	 * 04 - check state of the object in the database
	 * 05 - Delete the object in the database 
	 */
//	@Test
	public void testSenario02() {
		UserDTO dto = new UserDTO();
		dto = new UserDTO();
		dto.setEmail("tom.dhondt@hotmail.com");
		dto.setId("");
		dto.setLocked(false);
		dto.setLocale("EN");
		dto.setLoginName("tom.dhondt");
		dto.setName("Tom D'hondt");
		dto.setPassword("1234");
		dto.setType("0");
		try {
			this.iUserManager.persist(dto);
		} catch (ManagerException e) {
			assertNull(e);
		}
		try{
			List<NameQueryParam> list = new ArrayList<NameQueryParam>();
			list.add(new NameQueryParam(1,"loginName","tom.dhondt"));
			List<UserDTO> listDTO =  this.iUserManager.findByCriteria(list, "findByLoginName");
			assertEquals(1, listDTO.size());
			assertEquals("Tom D'hondt",listDTO.get(0).getName());
		}catch (ManagerException e) {
			assertNull(e);
		}
		try {
			List<UserDTO> listDTO = this.iUserManager.findAll();
			assertTrue(0 < listDTO.size());
		} catch (ManagerException e) {
			assertNull(e);
		}
		try {
			List<NameQueryParam> list = new ArrayList<NameQueryParam>();
			list.add(new NameQueryParam(1,"loginName","tom.dhondt"));
			List<UserDTO> listDTO = this.iUserManager.findByCriteria(list, "findByLoginName");
			MappingUtil.mapStringToLong(listDTO.get(0).getId());
			this.userDAOImpl.delete(MappingUtil.mapStringToLong(listDTO.get(0).getId()));
		} catch (ManagerException e) {
			assertNull(e);
		} catch (DataDAOException e) {
			assertNull(e);
		}
	}
}
