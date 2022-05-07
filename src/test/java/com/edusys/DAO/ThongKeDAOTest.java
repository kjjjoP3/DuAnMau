/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.DAO;

import com.edusys.helper.JdbcHelper;
import com.edusys.utils.XDate;
import com.mockrunner.mock.jdbc.MockConnection;
import com.mockrunner.mock.jdbc.MockResultSet;
import com.mockrunner.mock.jdbc.MockStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author Minh Huy
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JdbcHelper.class, ThongKeDAO.class})
public class ThongKeDAOTest {

    ThongKeDAO thongKeDaoSpy;

    @Mock
    MockConnection connection;
    @Mock
    MockStatement statement;
    @Spy
    @InjectMocks
    MockResultSet rs = new MockResultSet("myMock");

    public ThongKeDAOTest() {
    }

    @Before
    public void setUp() {
        PowerMockito.mockStatic(JdbcHelper.class);
        thongKeDaoSpy = PowerMockito.spy(new ThongKeDAO());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetBangDiem() throws Exception {
        System.out.println("Test BangDiem");
        int makh=1;
        List<Object[]> bangDiem=new ArrayList<>();
        bangDiem.add(new Object[]{"NH01","Bui Tien Thanh",10});
        PowerMockito.doReturn(bangDiem).when(thongKeDaoSpy,"getListOfArray",ArgumentMatchers.anyString()
                ,ArgumentMatchers.any(),ArgumentMatchers.any());
        List result=thongKeDaoSpy.getBangDiem(makh);
        assertEquals(result, bangDiem);
    }
    
// hi
    @Test
    public void testGetLuongNguoiHoc() throws SQLException, ParseException, Exception {
       System.out.println("Test LuongNguoiHoc");
       
        List<Object[]> luongNH=new ArrayList<>();
        luongNH.add(new Object[]{2022,11,XDate.toDate("20/01/2021","dd/MM/yyyy"),XDate.toDate("20/01/2022","dd/MM/yyyy")});
        PowerMockito.doReturn(luongNH).when(thongKeDaoSpy,"getListOfArray",ArgumentMatchers.anyString()
                ,ArgumentMatchers.any());
        List result=thongKeDaoSpy.getLuongNguoiHoc();
        assertEquals(result, luongNH);
    }


    @Test
    public void testGetDiemChuyenDe() throws Exception {
         System.out.println("Test DiemChuyenDe");
       
        List<Object[]> diemCD=new ArrayList<>();
        diemCD.add(new Object[]{"ThongKe",11,10.0,5.5,7.5});
        PowerMockito.doReturn(diemCD).when(thongKeDaoSpy,"getListOfArray",ArgumentMatchers.anyString()
                ,ArgumentMatchers.any());
        List result=thongKeDaoSpy.getDiemChuyenDe();
        assertEquals(result, diemCD);
    }

    @Test
    public void testGetDoanhThu() throws Exception {
        System.out.println("Test DoanhThu");
        int nam=2022;
        List<Object[]> doanhThu=new ArrayList<>();
        doanhThu.add(new Object[]{"Advanced Java",5,15,150.0,990.0,85.5});
        PowerMockito.doReturn(doanhThu).when(thongKeDaoSpy,"getListOfArray",ArgumentMatchers.anyString()
                ,ArgumentMatchers.any(),ArgumentMatchers.any());
        List result=thongKeDaoSpy.getDoanhThu(nam);
        assertEquals(result, doanhThu);
    }

    @Test
    public void testGetNangXuat() throws Exception {
        System.out.println("Test NangXuat");
        int nam=2022;
        List<Object[]> nangXuat=new ArrayList<>();
        nangXuat.add(new Object[]{"NV01","Bui Tien Thanh",25});
        PowerMockito.doReturn(nangXuat).when(thongKeDaoSpy,"getListOfArray",ArgumentMatchers.anyString()
                ,ArgumentMatchers.any(),ArgumentMatchers.any());
        List result=thongKeDaoSpy.getNangXuat(nam);
        assertEquals(result, nangXuat);
    }
    
    
    private MockResultSet initMockResultSet() throws Exception  {
        rs.addColumn("Nam",new Integer[]{1});
        rs.addColumn("SoLuong", new Integer[]{1});
        rs.addColumn("Dautien", new java.sql.Date[]{
                new java.sql.Date(new java.util.Date().getTime())
        });
        rs.addColumn("CuoiCung", new java.sql.Date[]{
                new java.sql.Date(new java.util.Date().getTime())
        });
        rs.beforeFirst();
        return rs;
    }

    private MockResultSet initMultipleDataMockResultSet() throws Exception {
        rs.addColumn("Nam",new Integer[]{1,2,5});
        rs.addColumn("SoLuong", new Integer[]{1,5,7});
        rs.addColumn("Dautien", new java.sql.Date[]{
                new java.sql.Date(new java.util.Date().getTime()),
                new java.sql.Date(new java.util.Date().getTime()),
                new java.sql.Date(new java.util.Date().getTime())
        });
        rs.addColumn("CuoiCung", new java.sql.Date[]{
                new java.sql.Date(new java.util.Date().getTime()),
                new java.sql.Date(new java.util.Date().getTime()),
                new java.sql.Date(new java.util.Date().getTime())
        });
        rs.beforeFirst();
        return rs;
    }

//    @Test
//    public void testSelectYearFromCD() {
//        
//    }
//
//    @Test
//    public void testSelectYearsNX() {
//    }

    

}
