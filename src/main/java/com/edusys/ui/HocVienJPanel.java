/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.ui;


import com.edusys.DAO.ChuyenDeDAO;
import com.edusys.DAO.HocVienDAO;
import com.edusys.DAO.KhoaHocDAO;
import com.edusys.DAO.NguoiHocDAO;
import com.edusys.entity.ChuyenDe;
import com.edusys.entity.HocVien;
import com.edusys.entity.KhoaHoc;
import com.edusys.entity.NguoiHoc;
import com.edusys.utils.Auth;
import com.edusys.utils.MgsBox;
import com.edusys.utils.XDate;
import com.edusys.utils.XImages;
import java.awt.Image;
import java.io.File;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Minh Huy
 */
public class HocVienJPanel extends javax.swing.JPanel {
    ChuyenDeDAO dao=new ChuyenDeDAO();
    /**
     * Creates new form NhanVienJPanel
     */
    public HocVienJPanel() {
        initComponents();
        init();
    }
    int count=0;
    int row=-1;
    void init(){
    fillComboBoxChuyenDe();
    
    }
    void fillComboBoxChuyenDe(){
        ChuyenDeDAO dao=new ChuyenDeDAO();
        DefaultComboBoxModel model=(DefaultComboBoxModel)cbChuyenDe.getModel();
       
        List<ChuyenDe> ls=dao.selectAll();
        for (ChuyenDe l : ls) {
            model.addElement(l);
        }
       fillComboBoxKhoaHoc();
    }
    
    void fillComboBoxKhoaHoc(){
    DefaultComboBoxModel model=(DefaultComboBoxModel)cbKhoaHoc.getModel();
    model.removeAllElements();
    ChuyenDe cd=(ChuyenDe)cbChuyenDe.getSelectedItem();
    if(cd!=null){
        KhoaHocDAO dao=new KhoaHocDAO();
        List<KhoaHoc> ls=dao.SelectByCD(cd.getMaCD());
        for (KhoaHoc ki : ls) {
            model.addElement(ki);
        }
   fillTableHocVien();
    }
    }
    
    void fillTableHocVien(){
        DefaultTableModel model=(DefaultTableModel)tblHocVien.getModel();
        model.setRowCount(0);
        KhoaHoc kh=(KhoaHoc)cbKhoaHoc.getSelectedItem();
        HocVienDAO dao=new HocVienDAO();
        NguoiHocDAO dao1=new NguoiHocDAO();
        if(kh!=null){
        List<HocVien> ls=dao.selectByKhoaHoc(kh.getMaKH());
        for(int i=0;i<ls.size();i++){
        HocVien hv=ls.get(i);
        String hoten=new NguoiHocDAO().selectByID1(hv.getMaNH()).getHoTen();
        model.addRow(new Object[]{i+1,hv.getMaHV(),hv.getMaNH(),hoten,hv.getDiem()});
        }
        }
        model.fireTableDataChanged();
        
        fillTableNguoiHoc();
    }
    void fillHVChuaNhapDiem(){
    DefaultTableModel model=(DefaultTableModel)tblHocVien.getModel();
    model.setRowCount(0);
    KhoaHoc kh=(KhoaHoc)cbKhoaHoc.getSelectedItem();
    if(kh!=null){
    List<HocVien> ls=new HocVienDAO().selectbyChuaNhapDiem(kh.getMaKH());
    for(int i=0;i<ls.size();i++){
    HocVien hv=ls.get(i);
    String hoten=new NguoiHocDAO().selectByID1(hv.getMaNH()).getHoTen();
    model.addRow(new Object[]{i+1,hv.getMaHV(),hv.getMaNH(),hoten,hv.getDiem()});
    }
    model.fireTableDataChanged();
    }
    rdoChuaND.setSelected(true);
    }
    
    void fillHVDaNhapDiem(){
    DefaultTableModel model=(DefaultTableModel)tblHocVien.getModel();
    model.setRowCount(0);
    KhoaHoc kh=(KhoaHoc)cbKhoaHoc.getSelectedItem();
    if(kh!=null){
    List<HocVien> ls=new HocVienDAO().selectbyDaNhapDiem(kh.getMaKH());
    for(int i=0;i<ls.size();i++){
    HocVien hv=ls.get(i);
    String hoten=new NguoiHocDAO().selectByID1(hv.getMaNH()).getHoTen();
    model.addRow(new Object[]{i+1,hv.getMaHV(),hv.getMaNH(),hoten,hv.getDiem()});
    }
    model.fireTableDataChanged();
    }
    rdoDaND.setSelected(true);
    }
    void fillTableNguoiHoc(){
    DefaultTableModel model=(DefaultTableModel)tblNguoiHoc.getModel();
    model.setRowCount(0);
    KhoaHoc kh=(KhoaHoc)cbKhoaHoc.getSelectedItem();
    if(kh!=null){
    String keyword=txtTimKiem.getText();
    List<NguoiHoc> ls=new NguoiHocDAO().selectNotInCourse(kh.getMaKH(), keyword);
        for (NguoiHoc l : ls) {
         model.addRow(new Object[]{l.getMaNH(),l.getHoTen(),l.getGioiTinh()?"Nam":"Nữ",l.getNgaySinh(),l.getDienThoai(),l.getEmail()});
        }
        model.fireTableDataChanged();
    }
    }
    void chuanhapdiem(){
    if(rdoChuaND.isSelected()){
    fillHVChuaNhapDiem();
    count=1;
    }
    }
    void danhapdiem(){
    if(rdoDaND.isSelected()){
    fillHVDaNhapDiem();
    count=2;
    }
    }
    void fillSortNameCND(){
    DefaultTableModel model=(DefaultTableModel)tblHocVien.getModel();
    model.setRowCount(0);
    KhoaHoc kh=(KhoaHoc)cbKhoaHoc.getSelectedItem();
    List<HocVien> ls=new HocVienDAO().sortNameCND(kh.getMaKH());
        for(int i=0;i<ls.size();i++){
    HocVien hv=ls.get(i);
    String hoten=new NguoiHocDAO().selectByID1(hv.getMaNH()).getHoTen();
    model.addRow(new Object[]{i+1,hv.getMaHV(),hv.getMaNH(),hoten,hv.getDiem()});
    }
        model.fireTableDataChanged();
    }
    
    void fillSortDiemDND(){
    DefaultTableModel model=(DefaultTableModel)tblHocVien.getModel();
    model.setRowCount(0);
    KhoaHoc kh=(KhoaHoc)cbKhoaHoc.getSelectedItem();
    List<HocVien> ls=new HocVienDAO().sortPointDND(kh.getMaKH());
        for(int i=0;i<ls.size();i++){
    HocVien hv=ls.get(i);
    String hoten=new NguoiHocDAO().selectByID1(hv.getMaNH()).getHoTen();
    model.addRow(new Object[]{i+1,hv.getMaHV(),hv.getMaNH(),hoten,hv.getDiem()});
    }
        model.fireTableDataChanged();
    }
    void fillSortNameDND(){
    DefaultTableModel model=(DefaultTableModel)tblHocVien.getModel();
    model.setRowCount(0);
    KhoaHoc kh=(KhoaHoc)cbKhoaHoc.getSelectedItem();
    List<HocVien> ls=new HocVienDAO().sortNameDND(kh.getMaKH());
        for(int i=0;i<ls.size();i++){
    HocVien hv=ls.get(i);
    String hoten=new NguoiHocDAO().selectByID1(hv.getMaNH()).getHoTen();
    model.addRow(new Object[]{i+1,hv.getMaHV(),hv.getMaNH(),hoten,hv.getDiem()});
    }
        model.fireTableDataChanged();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        cbChuyenDe = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        cbKhoaHoc = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHocVien = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        rdoChuaND = new javax.swing.JRadioButton();
        rdoDaND = new javax.swing.JRadioButton();
        btnSapXep = new javax.swing.JButton();
        btnXoaKhoiLop = new javax.swing.JButton();
        btnCapNhapDiem = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNguoiHoc = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btnAddHocVien = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 204, 102));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-student-24.png"))); // NOI18N
        jLabel1.setText("QUẢN LÝ HỌC VIÊN");

        jPanel2.setPreferredSize(new java.awt.Dimension(1520, 660));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "CHUYÊN ĐỀ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 14), new java.awt.Color(255, 102, 102))); // NOI18N

        cbChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbChuyenDeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbChuyenDe, 0, 694, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "KHOA HOC", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 14), new java.awt.Color(255, 51, 51))); // NOI18N

        cbKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKhoaHocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbKhoaHoc, 0, 700, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblHocVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "TT", "MA HV", "MA NH", "HO TEN", "DIEM"
            }
        ));
        jScrollPane2.setViewportView(tblHocVien);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CHUC NANG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 12), new java.awt.Color(255, 51, 51))); // NOI18N

        buttonGroup2.add(rdoChuaND);
        rdoChuaND.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        rdoChuaND.setText("Chưa Nhập Điểm");
        rdoChuaND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoChuaNDActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoDaND);
        rdoDaND.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        rdoDaND.setText("Đã Nhập Điểm");
        rdoDaND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDaNDActionPerformed(evt);
            }
        });

        btnSapXep.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        btnSapXep.setText("SẮP XẾP");
        btnSapXep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSapXepActionPerformed(evt);
            }
        });

        btnXoaKhoiLop.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        btnXoaKhoiLop.setText("XÓA KHỎI KHÓA HỌC");
        btnXoaKhoiLop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKhoiLopActionPerformed(evt);
            }
        });

        btnCapNhapDiem.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        btnCapNhapDiem.setText("CẬP NHẬP ĐIỂM");
        btnCapNhapDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhapDiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdoChuaND)
                .addGap(10, 10, 10)
                .addComponent(rdoDaND)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSapXep)
                .addGap(32, 32, 32)
                .addComponent(btnXoaKhoiLop)
                .addGap(29, 29, 29)
                .addComponent(btnCapNhapDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoChuaND)
                    .addComponent(rdoDaND)
                    .addComponent(btnSapXep)
                    .addComponent(btnXoaKhoiLop)
                    .addComponent(btnCapNhapDiem))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111))
        );

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnTimKiem.setText("TÌM KIẾM");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        tblNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MA NH", "HO TEN", "GIOI TINH", "NGAY SINH", "DIEN THOAI", "EMAIL"
            }
        ));
        jScrollPane1.setViewportView(tblNguoiHoc);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CHUC NANG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 12), new java.awt.Color(255, 51, 51))); // NOI18N

        btnAddHocVien.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        btnAddHocVien.setText("THÊM VÀO KHÓA HỌC");
        btnAddHocVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddHocVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddHocVien, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnAddHocVien)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtTimKiem)
                                .addGap(18, 18, 18)
                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(37, 37, 37))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1559, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 1382, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbChuyenDeActionPerformed
        // TODO add your handling code here:
        fillComboBoxKhoaHoc();
    }//GEN-LAST:event_cbChuyenDeActionPerformed

    private void btnSapXepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSapXepActionPerformed
        // TODO add your handling code here:
        if(count==1){// danh sách chưa nhập điểm
        fillSortNameCND();
    }else if(count==2){// danh sách đã nhập điểm
    int mes = JOptionPane.QUESTION_MESSAGE;
        String[] option = {"Sắp xếp theo tên", "Sắp xếp theo điểm"};
        int code = JOptionPane.showOptionDialog(this, "Mời bạn chọn ?", "Hộp thoại lựa chọn", 0, mes, null, option, null);
        if(code==0){
        fillSortNameDND();
        }else if(code==1){
        fillSortDiemDND();
        }
    }
    
    }//GEN-LAST:event_btnSapXepActionPerformed
    void TimKiem(){
    DefaultTableModel model=(DefaultTableModel)tblNguoiHoc.getModel();
    model.setRowCount(0);
    String key=txtTimKiem.getText();
    List<NguoiHoc> ls=new NguoiHocDAO().selectByKeyWord(key);
        for (NguoiHoc l : ls) {
            model.addRow(new Object[]{l.getMaNH(),l.getHoTen(),l.getGioiTinh(),l.getNgaySinh(),l.getDienThoai(),l.getEmail()});
        }
    model.fireTableDataChanged();
    int pos=find(txtTimKiem.getText());
    tblNguoiHoc.setRowSelectionInterval(pos,pos);
    }
    int find(String ma){
        List<NguoiHoc> ls=new NguoiHocDAO().selectAll();
        if(ls.isEmpty()){
        return -1;
        }else{
        for(int i=0;i<ls.size();i++){
        if(ls.get(i).getHoTen().contains(ma)){
        return i;
        }
        }
        }
    return -1;
    }
    private void btnXoaKhoiLopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKhoiLopActionPerformed
        // TODO add your handling code here:
        if(!Auth.isManager()){
        MgsBox.Alert(this,"Bạn không có quyền xóa");
        }else{
        if(JOptionPane.showConfirmDialog(this,"Bạn có chắc muốn xóa?","Confirmation",JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION){
        return;
        }
        HocVienDAO dao=new HocVienDAO();
        int[] rows=tblHocVien.getSelectedRows();
            for (int row : rows) {
               int mahv=(Integer)tblHocVien.getValueAt(row,1);
               
               dao.delete(mahv);
               this.fillTableHocVien();
               MgsBox.Alert(this,"Xóa thành công");
            }
            
        }
    }//GEN-LAST:event_btnXoaKhoiLopActionPerformed

    private void btnCapNhapDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhapDiemActionPerformed
        // TODO add your handling code here:
        int row=tblHocVien.getSelectedRow();
          int mahv=(Integer)tblHocVien.getValueAt(row,1);
          String manh=(String)tblHocVien.getValueAt(row, 2);
          String hoten=(String)tblHocVien.getValueAt(row, 3);
          HocVienDAO dao=new HocVienDAO();
          HocVien hv=dao.selectByID(mahv);
          String diem=JOptionPane.showInputDialog(this,"Cập nhập điểm cho sinh viên  " + hoten.trim(),"Input",JOptionPane.QUESTION_MESSAGE);
          
          hv.setDiem(Double.parseDouble(diem));
          dao.update(hv);
          System.out.println(hv.getDiem());
          
          fillHVDaNhapDiem();
          
    }//GEN-LAST:event_btnCapNhapDiemActionPerformed

    private void rdoChuaNDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoChuaNDActionPerformed
        // TODO add your handling code here:
        chuanhapdiem();
    }//GEN-LAST:event_rdoChuaNDActionPerformed

    private void rdoDaNDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDaNDActionPerformed
        // TODO add your handling code here:
        danhapdiem();
    }//GEN-LAST:event_rdoDaNDActionPerformed

    private void btnAddHocVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddHocVienActionPerformed
        // TODO add your handling code here:
        KhoaHoc kh=(KhoaHoc)cbKhoaHoc.getSelectedItem();
        int[] rows=tblNguoiHoc.getSelectedRows();
        for (int row : rows) {
            HocVien hv=new HocVien();
            hv.setMaKH(kh.getMaKH());
            hv.setDiem(0.0);
            hv.setMaNH((String)tblNguoiHoc.getValueAt(row,0));
            HocVienDAO dao=new HocVienDAO();
            dao.insert(hv);
            MgsBox.Alert(this,"Thêm thành công");
        }
        fillTableHocVien();
       
    }//GEN-LAST:event_btnAddHocVienActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        TimKiem();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void cbKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKhoaHocActionPerformed
        // TODO add your handling code here:
//        fillComboBoxKhoaHoc();
        fillTableHocVien();
//        fillTableNguoiHoc();
    }//GEN-LAST:event_cbKhoaHocActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddHocVien;
    private javax.swing.JButton btnCapNhapDiem;
    private javax.swing.JButton btnSapXep;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoaKhoiLop;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbChuyenDe;
    private javax.swing.JComboBox<String> cbKhoaHoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoChuaND;
    private javax.swing.JRadioButton rdoDaND;
    private javax.swing.JTable tblHocVien;
    private javax.swing.JTable tblNguoiHoc;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
