/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.edusys.entity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author thanh
 */
public class ChuyenDeTest {
    
    public ChuyenDeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getMaCD method, of class ChuyenDe.
     */
   @Test
    public void testGetMaCD() {
         ChuyenDe instance = new ChuyenDe();
        String expResult = "";
        String result = instance.getMaCD();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMaCD method, of class ChuyenDe.
     */
    @Test
    public void testSetMaCD() {
         String MaCD = "M01";
        ChuyenDe instance = new ChuyenDe();
        instance.setMaCD(MaCD);
        String expected = "M01";
        assertEquals(expected, instance.getMaCD());
    }

    /**
     * Test of getTenCD method, of class ChuyenDe.
     */
    @Test
    public void testGetTenCD() {
         ChuyenDe instance = new ChuyenDe();
        String expResult = "";
        String result = instance.getTenCD();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTenCD method, of class ChuyenDe.
     */
    @Test
    public void testSetTenCD() {
        String TenCD = "CD01";
        ChuyenDe instance = new ChuyenDe();
        instance.setTenCD(TenCD);
        String expected = "CD01";
        assertEquals(expected, instance.getTenCD());
    }

    /**
     * Test of getHocPhi method, of class ChuyenDe.
     */
    @Test
    public void testGetHocPhi() {
         ChuyenDe instance = new ChuyenDe();
         instance.setHocPhi(0.0);
        double expResult = 0.0;
        double result = instance.getHocPhi();
        assertEquals(expResult, result, 0.0);

    }

    /**
     * Test of setHocPhi method, of class ChuyenDe.
     */
    @Test
    public void testSetHocPhi() {
        
        double HocPhi = 600;
        ChuyenDe instance = new ChuyenDe();
        instance.setHocPhi(HocPhi);

        double expected = 600;
        double result = instance.getHocPhi();
        assertEquals(expected, result, 0.0);
    }
   @Test
    public void testSetHocPhiWithLarge() {
        double HocPhi = 6000000000000d;
        ChuyenDe instance = new ChuyenDe();

        Exception exception = assertThrows(Exception.class,
                () -> instance.setHocPhi(HocPhi));

    }
     @Test
    public void testSetHocPhiWithNegative() {
        double HocPhi = -600;
        ChuyenDe instance = new ChuyenDe();

        Exception exception = assertThrows(Exception.class,
                ()->instance.setHocPhi(HocPhi));

    }

    /**
     * Test of getThoiLuong method, of class ChuyenDe.
     */
    @Test
    public void testGetThoiLuong() {
       
        ChuyenDe instance = new ChuyenDe();
        int expResult = 0;
        int result = instance.getThoiLuong();
        assertEquals(expResult, result);
    }

    /**
     * Test of setThoiLuong method, of class ChuyenDe.
     */
    @Test
    public void testSetThoiLuong() {
         int ThoiLuong = 10;
        ChuyenDe instance = new ChuyenDe();
        instance.setThoiLuong(ThoiLuong);
        int expected = 10;
        int result = instance.getThoiLuong();
        assertEquals(expected, result);
    }

    /**
     * Test of getHinh method, of class ChuyenDe.
     */
     @Test
    public void testSetThoiLuongWithNegative() {

        int ThoiLuong = -10;
        ChuyenDe instance = new ChuyenDe();
         Exception exception = assertThrows(Exception.class,
                ()->instance.setThoiLuong(ThoiLuong));
        
    }
    @Test
    public void testSetThoiLuongWithLarge() {

        int ThoiLuong = 1000000000;
        ChuyenDe instance = new ChuyenDe();
         Exception exception = assertThrows(Exception.class,
                ()->instance.setThoiLuong(ThoiLuong));
        
    }
   
    /**
     

    /**
     * Test of getMoTa method, of class ChuyenDe.
     */
    @Test
    public void testGetMoTa() {
        ChuyenDe instance = new ChuyenDe();
        String expResult = "";
        String result = instance.getMoTa();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMoTa method, of class ChuyenDe.
     */
    @Test
    public void testSetMoTa() {
       
        String MoTa = "Mota1";
        ChuyenDe instance = new ChuyenDe();
        instance.setMoTa(MoTa);
        String expected="Mota1";
        assertEquals(expected, instance.getMoTa());
    }

    /**
     * Test of toString method, of class ChuyenDe.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        ChuyenDe instance = new ChuyenDe();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class ChuyenDe.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        ChuyenDe instance = new ChuyenDe();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
