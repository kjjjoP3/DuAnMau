/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.ui;

import com.edusys.DAO.NhanVienDAO;
import com.edusys.entity.NhanVien;
import com.edusys.utils.Auth;
import com.edusys.utils.MgsBox;
import com.edusys.utils.XImages;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Minh Huy
 */
public class NhanVienJPanel extends javax.swing.JPanel {
    NhanVienDAO dao=new NhanVienDAO();
    /**
     * Creates new form NhanVienJPanel
     */
    public NhanVienJPanel() {
        initComponents();
        init();
    }
    int row=-1;
    void init(){
    
    fillTable();
    this.row=-1;
    updateStatus();
    
    }
    void fillTable(){
        DefaultTableModel model=(DefaultTableModel)tblGridView.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> ls=dao.selectAll();
            for (NhanVien l : ls) {
                model.addRow(new Object[]{l.getMaNV(),l.getMatKhau(),l.getHoTen(),l.getVaiTro()?"Trưởng Phòng":"Nhân Viên"});
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            MgsBox.Alert(this,"Lỗi");
        }
    
    }
    boolean check(){
    boolean check=true;
    StringBuilder sb=new StringBuilder();
    if(txtMaNV.getText().equals("")){
    check=false;
    sb.append("Mã sinh viên không được bỏ trống \n");
    }
    if(new String(txtMatKhau.getPassword()).equals("")){
    check=false;
    sb.append("Mật khẩu không được bỏ trống \n");
    }
    if(txtHoTen.getText().equals("")){
    check=false;
    sb.append("Họ tên không được bỏ trống \n");
    }
    if(new String(txtXacNhanMK.getPassword()).equals("")){
    check=false;
    sb.append("Bạn phải xác nhận lại mật khẩu \n");
    }
    if(!new String(txtXacNhanMK.getPassword()).equals(new String(txtMatKhau.getPassword()))){
    check=false;
    sb.append("Xác nhận mật khẩu không trùng khớp !!!\n");
    }
    
    if(sb.length()>0){
    JOptionPane.showMessageDialog(this, sb.toString(),"ERROR",JOptionPane.ERROR_MESSAGE);
    }
    return check;
    }
    void setModel(NhanVien nv){
        try {
            txtMaNV.setText(nv.getMaNV());
    txtHoTen.setText(nv.getHoTen());
    txtMatKhau.setText(nv.getMatKhau());
    txtXacNhanMK.setText(nv.getMatKhau());
//    if(nv.getVaiTro()==true){
//    rdoTruongPhong.setSelected(true);
//    }else{
//    rdoNhanVien.setSelected(true);
//    }
    rdoTruongPhong.setSelected(nv.getVaiTro());
    rdoNhanVien.setSelected(!nv.getVaiTro());
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    void updateStatus(){
       boolean edit=(this.row>=0);
       boolean first=(this.row==0);
       boolean last=(this.row==tblGridView.getRowCount()-1);
       // Trạng thái form
       txtMaNV.setEditable(!edit);
       btnInsert.setEnabled(!edit);
       btnUpdate.setEnabled(edit);
       btnDelete.setEnabled(edit);
       // Trạng thái thanh điều hướng
       btnFirst.setEnabled(edit && !first);
       btnPrev.setEnabled(edit && !first);
       btnNext.setEnabled(edit && !last);
       btnLast.setEnabled(edit && !last);
    }
    NhanVien getForm(){
    NhanVien nv=new NhanVien();
    nv.setMaNV(txtMaNV.getText());
    nv.setMatKhau(new String(txtMatKhau.getPassword()));
    nv.setHoTen(txtHoTen.getText());
    if(rdoTruongPhong.isSelected()){
    nv.setVaiTro(true);
    }else{
    nv.setVaiTro(false);
    }
    return nv;
    }
    private void edit(){
    
    String manv=(String)tblGridView.getValueAt(row,0);
    NhanVien nv=dao.selectByID(manv);
    this.setModel(nv);
    
    this.updateStatus();
    }
    
    private void insert() {
        NhanVien nv=getForm();
        if(check()==true){
        String mk2=new String(txtXacNhanMK.getPassword());
        if(!mk2.equals(nv.getMatKhau())){
            JOptionPane.showMessageDialog(this,"Xác nhận mật khẩu không đúng ?","Invalid",JOptionPane.ERROR_MESSAGE);
        }else{
        try {
            dao.insert(nv);
            fillTable();
            clearForm();
            MgsBox.Alert(this,"Thêm vào thành công !!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Thêm mới thất bại !!!","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    }
    }
    private void update() {
        NhanVien nv=getForm();
        if(!Auth.isManager()){
        
            JOptionPane.showMessageDialog(this,"Bạn không có quyền cập nhập","ERROR",JOptionPane.ERROR_MESSAGE);
        }else{
        if(check()==true){
        String mk2=new String(txtXacNhanMK.getPassword());
        if(!mk2.equals(nv.getMatKhau())){
            JOptionPane.showMessageDialog(this,"Xác nhận mật khẩu không đúng ?","Invalid",JOptionPane.ERROR_MESSAGE);
        }else{
        try {
            dao.update(nv);
            fillTable();
            
            MgsBox.Alert(this,"Cập nhập thành công !!!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Cập nhập thất bại !!!","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    }
    }
    }
    private void delete() {
        String manv=txtMaNV.getText();
        if(!Auth.isManager()){
            
        JOptionPane.showMessageDialog(this,"Bạn không có quyền xóa","ERROR",JOptionPane.ERROR_MESSAGE);
        }else{
        if(manv.equals(Auth.user.getMaNV())){
            JOptionPane.showMessageDialog(this,"Bạn không được phép xóa chính mình","ERROR",JOptionPane.ERROR_MESSAGE);
        }else{
            try {
                if(JOptionPane.showConfirmDialog(this,"Bạn có chắc muốn xóa ?","Confirmation",JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION){
                return;
                }
                
                dao.delete(manv);
                fillTable();
                clearForm();
                MgsBox.Alert(this,"Xóa thành công !!!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,"Xóa thất bại","Lỗi",JOptionPane.ERROR_MESSAGE);
            }
        }
        }
    }

    private void clearForm() {
//        NhanVien nv=new NhanVien();
//        this.setModel(nv);
//        this.row=-1;
        txtMaNV.setText("");
        txtHoTen.setText("");
        txtMatKhau.setText("");
        txtXacNhanMK.setText("");
        txtMaNV.setEnabled(true);
         txtHoTen.setEnabled(true);
          txtMatKhau.setEnabled(true);
           txtXacNhanMK.setEnabled(true);
           tblGridView.clearSelection();
    }

    private void first() {
        row=0;
        edit();
       tblGridView.setRowSelectionInterval(row, row);
    }

    private void prev() {
        if(row==0){
        row=tblGridView.getRowCount()-1;
        }else{
        row--;
        }
        edit();
        tblGridView.setRowSelectionInterval(row, row);
    }

    private void next() {
        if(row==tblGridView.getRowCount()-1){
        row=0;
        }
        row++;
        edit();
        tblGridView.setRowSelectionInterval(row, row);
    }

    private void last() {
        row=tblGridView.getRowCount()-1;
        edit();
        tblGridView.setRowSelectionInterval(row, row);
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblMaNV = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        lblMatKhau = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        lblHoTen = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        lblVaiTro = new javax.swing.JLabel();
        rdoTruongPhong = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGridView = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        lblXacNhanMK = new javax.swing.JLabel();
        txtXacNhanMK = new javax.swing.JPasswordField();

        setBackground(new java.awt.Color(255, 204, 102));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Users.png"))); // NOI18N
        jLabel1.setText("QUẢN LÝ NHÂN VIÊN");

        jPanel2.setPreferredSize(new java.awt.Dimension(1520, 660));

        lblMaNV.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lblMaNV.setText("Mã Nhân Viên");

        lblMatKhau.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lblMatKhau.setText("Mật Khẩu");

        lblHoTen.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lblHoTen.setText("Họ Tên");

        lblVaiTro.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lblVaiTro.setText("Vai Trò");

        buttonGroup1.add(rdoTruongPhong);
        rdoTruongPhong.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        rdoTruongPhong.setText("Trưởng Phòng");

        buttonGroup1.add(rdoNhanVien);
        rdoNhanVien.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        rdoNhanVien.setText("Nhân Viên");

        tblGridView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Ma Nhan Vien", "Mat Khau", "Ho Ten", "Vai Tro"
            }
        ));
        tblGridView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGridViewMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGridView);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CHUC NANG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri Light", 1, 15), new java.awt.Color(255, 51, 51))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(255, 51, 51));
        jPanel1.setToolTipText("");

        jPanel4.setBackground(new java.awt.Color(255, 102, 102));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnInsert.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-add-32.png"))); // NOI18N
        btnInsert.setText("ADD");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-update-32.png"))); // NOI18N
        btnUpdate.setText("UPDATE");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-delete-32.png"))); // NOI18N
        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnNew.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-clear-symbol-32.png"))); // NOI18N
        btnNew.setText("NEW");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnInsert)
                .addGap(42, 42, 42)
                .addComponent(btnUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(btnDelete)
                .addGap(43, 43, 43)
                .addComponent(btnNew)
                .addGap(34, 34, 34))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnInsert)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DIEU HUONG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri Light", 1, 15), new java.awt.Color(255, 51, 0))); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 102, 102));
        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnFirst.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-first-32.png"))); // NOI18N
        btnFirst.setText("FIRST");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-previous-32.png"))); // NOI18N
        btnPrev.setText("PREVIOUS");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-next-32.png"))); // NOI18N
        btnNext.setText("NEXT");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-last-32.png"))); // NOI18N
        btnLast.setText("LAST");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnFirst)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(btnPrev)
                .addGap(43, 43, 43)
                .addComponent(btnNext)
                .addGap(56, 56, 56)
                .addComponent(btnLast)
                .addGap(23, 23, 23))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(196, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "TIM KIEM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 14), new java.awt.Color(255, 102, 102))); // NOI18N

        btnFind.setFont(new java.awt.Font("Calibri", 1, 11)); // NOI18N
        btnFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-find-32.png"))); // NOI18N
        btnFind.setText("FIND");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem)
                .addGap(18, 18, 18)
                .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        lblXacNhanMK.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lblXacNhanMK.setText("Xác Nhận Mật Khẩu");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtHoTen)
                        .addComponent(lblHoTen)
                        .addComponent(txtMaNV)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMaNV)
                        .addComponent(lblXacNhanMK)
                        .addComponent(lblMatKhau)
                        .addComponent(txtMatKhau)
                        .addComponent(txtXacNhanMK))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblVaiTro)
                        .addGap(49, 49, 49)
                        .addComponent(rdoTruongPhong)
                        .addGap(39, 39, 39)
                        .addComponent(rdoNhanVien)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblMaNV)
                        .addGap(18, 18, 18)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblMatKhau)
                        .addGap(18, 18, 18)
                        .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblXacNhanMK)
                        .addGap(18, 18, 18)
                        .addComponent(txtXacNhanMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lblHoTen)
                        .addGap(19, 19, 19)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoTruongPhong)
                            .addComponent(rdoNhanVien)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(84, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1492, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 1302, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void tblGridViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGridViewMouseClicked
        // TODO add your handling code here:
        row=tblGridView.getSelectedRow();
        edit();
    }//GEN-LAST:event_tblGridViewMouseClicked
    int timkiem(String mas){
        List<NhanVien> ls = new NhanVienDAO().FindByKeyword(mas);
        if (ls.isEmpty()) {
            return -1;
        } else {
            for (int i = 0; i < ls.size(); i++) {
                if (ls.get(i).getHoTen().contains(mas)) {
                    return i;
                }

            }

        }

        return -1;
    }
    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        // TODO add your handling code here:
        try {
            DefaultTableModel model=(DefaultTableModel)tblGridView.getModel();
            model.setRowCount(0);
            List<NhanVien> ls=new NhanVienDAO().FindByKeyword(txtTimKiem.getText());
            for (NhanVien l : ls) {
                model.addRow(new Object[]{l.getMaNV(),l.getMatKhau(),l.getHoTen(),l.getVaiTro()});
            }
            model.fireTableDataChanged();
            int pos=timkiem(txtTimKiem.getText());
            tblGridView.setRowSelectionInterval(pos,pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnFindActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JLabel lblXacNhanMK;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoTruongPhong;
    private javax.swing.JTable tblGridView;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JPasswordField txtXacNhanMK;
    // End of variables declaration//GEN-END:variables
}
