package test;

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
public class UserManagerTest {
	/*
	 * Instance members
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContent.xml"});	
	IManager<UserDTO> iUserManager = (IManager<UserDTO>) context.getBean("iUserManager");
	IPersistenceDAOImpl<User> userDAOImpl = (IPersistenceDAOImpl<User>) context.getBean("userDAOImpl");
	/*
	 * Scenario 01 : Persist null object
	 * 01 - create UserDTO
	 * 02 - persist to the database
	 * 03 - catch error
	 */
//	@Test
	public void testSenario01(){
		UserDTO dto = null;
		try {
			this.iUserManager.persist(dto);
		} catch (ManagerException e) {
			assertEquals("Object.isNull", e.getCaption());
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
	@Test
	public void testSenario02() {
		UserDTO dto = new UserDTO();
		dto = new UserDTO();
		dto.setEmail("dqskhdfkh@etst.be");
		dto.setId("");
//		dto.setLocked(false);
//		dto.setLocale("EN");
		dto.setLoginName("fxgg");
		dto.setName("zret");
		dto.setPassword("pass");
//		dto.setType("0");
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
