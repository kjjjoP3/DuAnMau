/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.DAO;


import com.edusys.entity.KhoaHoc;
import com.edusys.helper.JdbcHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author Minh Huy
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JdbcHelper.class,KhoaHocDAO.class})
public class KhoaHocDAOTest {
    KhoaHocDAO khoahocDAO;
    KhoaHocDAO khoahocDaoSpy;
    public KhoaHocDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        khoahocDAO =new KhoaHocDAO();
        PowerMockito.mockStatic(JdbcHelper.class);
        khoahocDaoSpy = PowerMockito.spy(new KhoaHocDAO());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insert method, of class KhoaHocDAO.
     */
    @Test(expected = Exception.class)
    public void testInsertWithNullModel() {
        KhoaHoc e = null;

        khoahocDAO.insert(e);
    }

    @Test(expected = Exception.class)
    public void testInsertWithEmptyModel() {
        KhoaHoc e = new KhoaHoc();
        khoahocDAO.insert(e);
    }

    @Test
    public void testInsert() {
        KhoaHoc e=new KhoaHoc();
        e.setGhiChu("a");
        e.setHocPhi(8.5);
        e.setMaCD("macd");
        e.setMaKH(1);
        e.setMaNV("nv01");
        e.setNgayKG(new Date());
        e.setNgayTao(new Date());
        e.setThoiLuong(15);
        khoahocDAO.insert(e);
    }

    /**
     * Test of update method, of class KhoaHocDAO.
     */
    @Test(expected = Exception.class)
    public void testUpdateWithNullModel() {

        KhoaHoc e = null;
        khoahocDAO.update(e);
    }

    @Test(expected = Exception.class)
    public void testUpdateWithEmptyModel() {

        KhoaHoc e = new KhoaHoc();
        khoahocDAO.update(e);
    }

    @Test
    public void testUpdateWithValidModel() {
        KhoaHoc e = new KhoaHoc();
        e.setGhiChu("a");
        e.setHocPhi(8.5);
        e.setMaCD("macd");
        e.setMaKH(1);
        e.setMaNV("nv01");
        e.setNgayKG(new Date());
        e.setNgayTao(new Date());
        e.setThoiLuong(15);
        khoahocDAO.update(e);
    }

    
    @Test(expected = Exception.class)
    public void testDeleteWithEmptyId() {
        int key = 0;
        khoahocDAO.delete(key);
    }

    @Test(expected = Exception.class)
    public void testDeleteWithNullId() {
        int key = 0;
        khoahocDAO.delete(key);
    }

    @Test()
    public void testDeleteWithValidId() {
        int key = 12;
        khoahocDAO.delete(key);
    }

    
    @Test
    public void testSelectAll() throws Exception {
        KhoaHoc khoaHoc = new KhoaHoc();
        List<KhoaHoc> expResult = new ArrayList<>();
        expResult.add(khoaHoc);
        PowerMockito.doReturn(expResult).when(khoahocDaoSpy, "selectBySQL", ArgumentMatchers.anyString());
        List<KhoaHoc> result = khoahocDaoSpy.selectAll();
        assertThat(result, CoreMatchers.is(expResult));
    }

    
    @Test
    public void testSelectByIDWithNotFound() throws Exception {
        System.out.println("selectByID");
        int macd = 0;

        KhoaHoc expResult = null;
        List<KhoaHoc> resultList = new ArrayList<>();

        PowerMockito.doReturn(resultList).when(khoahocDaoSpy, "selectBySQL", ArgumentMatchers.anyString(),
                ArgumentMatchers.any());
        KhoaHoc result = khoahocDaoSpy.selectByID(macd);
        assertThat(result, CoreMatchers.is(expResult));

    }

    @Test
    public void testSelectByIDWithFound() throws Exception {
        System.out.println("selectByID");
        int macd = 12;

        KhoaHoc expResult = new KhoaHoc();
        List<KhoaHoc> resultList = new ArrayList<>();
        resultList.add(expResult);
        PowerMockito.doReturn(resultList).when(khoahocDaoSpy, "selectBySQL", ArgumentMatchers.anyString(),
                ArgumentMatchers.any());
        KhoaHoc result = khoahocDaoSpy.selectByID(macd);
        assertThat(result, CoreMatchers.is(expResult));

    }

    /**
     * Test of selectBySQL method, of class KhoaHocDAO.
     */
    @Test
    public void testSelectBySQL() {
        System.out.println("selectBySQL");
        String sql = "";
        Object[] args = null;
        KhoaHocDAO instance = new KhoaHocDAO();
        List<KhoaHoc> expResult = null;
        List<KhoaHoc> result = instance.selectBySQL(sql, args);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SelectByCD method, of class KhoaHocDAO.
     */
//    @Test
//    public void testSelectByCD() {
//        System.out.println("SelectByCD");
//        String cd = "";
//        KhoaHocDAO instance = new KhoaHocDAO();
//        List<KhoaHoc> expResult = null;
//        List<KhoaHoc> result = instance.SelectByCD(cd);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of selectYears method, of class KhoaHocDAO.
     */
//    @Test
//    public void testSelectYears() {
//        System.out.println("selectYears");
//        KhoaHocDAO instance = new KhoaHocDAO();
//        List<Integer> expResult = null;
//        List<Integer> result = instance.selectYears();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
