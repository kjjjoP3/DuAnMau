/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.edusys.DAO;

import com.edusys.entity.ChuyenDe;
import com.edusys.helper.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author thanh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JdbcHelper.class,ChuyenDeDAO.class})
public class ChuyenDeDAOTest {
    ChuyenDeDAO chuyenDeDAO;
    ChuyenDeDAO chuyenDeDaoSpy;
    public ChuyenDeDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        chuyenDeDAO =new ChuyenDeDAO();
        PowerMockito.mockStatic(JdbcHelper.class);
        chuyenDeDaoSpy =PowerMockito.spy(new ChuyenDeDAO());
    }
    
    @After
    public void tearDown() {
    }

    @Test(expected=Exception.class)
    public void testInsertWithNullModel() {
        ChuyenDe e = null;
       
        chuyenDeDAO.insert(e);
    }
    @Test(expected=Exception.class)
    public void testInsertWithEmptyModel() {
        ChuyenDe e = new ChuyenDe();    
        chuyenDeDAO.insert(e);
    }
     @Test()
    public void testInsertWithValidModel() {
        ChuyenDe e = new ChuyenDe(); 
        e.setHinh("test.jpg");
        e.setHocPhi(100.0);
        e.setMaCD("CD01");
        e.setMoTa("Mo Ta");
        e.setTenCD("Chuyen de");
        e.setThoiLuong(20);
        chuyenDeDAO.insert(e);
    }

    @Test(expected = Exception.class)
    public void testUpdateWithNullModel() {
       
        ChuyenDe e = null;
        chuyenDeDAO.update(e);
    }
    @Test(expected = Exception.class)
    public void testUpdateWithEmptyModel() {
       
        ChuyenDe e = new ChuyenDe();
        chuyenDeDAO.update(e);
    }
    @Test()
    public void testUpdateWithValidModel() {
        ChuyenDe e = new ChuyenDe(); 
        e.setHinh("test.jpg");
        e.setHocPhi(100.0);
        e.setMaCD("CD01");
        e.setMoTa("Mo Ta");
        e.setTenCD("Chuyen de");
        e.setThoiLuong(20);
        chuyenDeDAO.update(e);
    }

    @Test(expected = Exception.class)
    public void testDeleteWithEmptyId() {
        String key = "";
        chuyenDeDAO.delete(key);
    }
    @Test(expected = Exception.class)
    public void testDeleteWithNullId() {
        String key = null;
        chuyenDeDAO.delete(key);
    }
    @Test()
    public void testDeleteWithValidId() {
        String key = "12";
        chuyenDeDAO.delete(key);
    }

    @Test
    public void testSelectAll() throws Exception {
       
        ChuyenDe chuyenDe=new ChuyenDe();
        List<ChuyenDe> expResult=new ArrayList<>();
        expResult.add(chuyenDe);
        PowerMockito.doReturn(expResult).when(chuyenDeDaoSpy,"selectBySQL",ArgumentMatchers.anyString());
        List<ChuyenDe> result=chuyenDeDaoSpy.selectAll();
        assertThat(result, CoreMatchers.is(expResult));
      
    }

    @Test
    public void testSelectByIDWithNotFound() throws Exception {
        System.out.println("selectByID");
        String macd = "";
        
        ChuyenDe expResult=null;
        List<ChuyenDe> resultList=new ArrayList<>();
        
        PowerMockito.doReturn(resultList).when(chuyenDeDaoSpy,"selectBySQL",ArgumentMatchers.anyString(),
                ArgumentMatchers.any());
        ChuyenDe result=chuyenDeDaoSpy.selectByID(macd);
        assertThat(result, CoreMatchers.is(expResult));
        
    }
    @Test
    public void testSelectByIDWithFound() throws Exception {
        System.out.println("selectByID");
        String macd = "12";
        
        ChuyenDe expResult=new ChuyenDe();
        List<ChuyenDe> resultList=new ArrayList<>();
        resultList.add(expResult);
        PowerMockito.doReturn(resultList).when(chuyenDeDaoSpy,"selectBySQL",ArgumentMatchers.anyString(),
                ArgumentMatchers.any());
        ChuyenDe result=chuyenDeDaoSpy.selectByID(macd);
        assertThat(result, CoreMatchers.is(expResult));
        
    }

    @Test
    public void testSelectBySQL() {
        System.out.println("selectBySQL");
        String sql = "";
        Object[] args = null;
        ChuyenDeDAO instance = new ChuyenDeDAO();
        List<ChuyenDe> expResult = null;
        List<ChuyenDe> result = instance.selectBySQL(sql, args);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testFindNameWithNotFound() throws Exception {
        System.out.println("selectByID");
        String macd = "";
        
        ChuyenDe expResult=null;
        List<ChuyenDe> resultList=new ArrayList<>();
        
        PowerMockito.doReturn(resultList).when(chuyenDeDaoSpy,"selectBySQL",ArgumentMatchers.anyString(),
                ArgumentMatchers.any());
        List<ChuyenDe> result=chuyenDeDaoSpy.findName(macd);
        assertThat(result, CoreMatchers.is(expResult));
    }
    @Test
    public void testFindNameWithFound() throws Exception {
        System.out.println("selectByID");
        String macd = "12";
        
        ChuyenDe expResult=new ChuyenDe();
        List<ChuyenDe> resultList=new ArrayList<>();
        resultList.add(expResult);
        PowerMockito.doReturn(resultList).when(chuyenDeDaoSpy,"selectBySQL",ArgumentMatchers.anyString(),
                ArgumentMatchers.any());
        List<ChuyenDe> result=chuyenDeDaoSpy.findName(macd);
        assertThat(result, CoreMatchers.is(expResult));
    }
    
}
