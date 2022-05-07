/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.ui;


import com.edusys.DAO.ChuyenDeDAO;
import com.edusys.DAO.KhoaHocDAO;
import com.edusys.entity.ChuyenDe;
import com.edusys.entity.KhoaHoc;
import com.edusys.utils.Auth;
import com.edusys.utils.MgsBox;
import com.edusys.utils.XDate;
import com.edusys.utils.XImages;
import java.awt.Image;
import java.io.File;
import java.text.ParseException;
import java.util.Date;
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
public class KhoaHocJPanel extends javax.swing.JPanel {
    ChuyenDeDAO dao=new ChuyenDeDAO();
    /**
     * Creates new form NhanVienJPanel
     */
    public KhoaHocJPanel() {
        initComponents();
        init();
        txtNguoiTao.setText(Auth.user.getMaNV());
    }
    int row=-1;
    void init(){
    fillComboBoxChuyenDe();
    fillTable1();
    this.row=-1;
    updateStatus();
    
    }
    void updateStatus(){
    boolean edit=(this.row>=0);
    boolean first=(this.row==0);
    boolean last=(this.row==tblGridView.getRowCount()-1);
       // Trạng thái form
       
       
       btnUpdate.setEnabled(edit);
       btnDelete.setEnabled(edit);
       // Trạng thái thanh điều hướng
       btnFirst.setEnabled(edit && !first);
       btnPrev.setEnabled(edit && !first);
       btnNext.setEnabled(edit && !last);
       btnLast.setEnabled(edit && !last);
    
    }
    
    void fillTable1(){
        DefaultTableModel model=(DefaultTableModel)tblGridView.getModel();
        model.setRowCount(0);
        try {
            ChuyenDe cd=(ChuyenDe)cboChuyenDe.getSelectedItem();
            List<KhoaHoc> ls=new KhoaHocDAO().SelectByCD(cd.getMaCD());
            for (KhoaHoc l : ls) {
               model.addRow(new Object[]{l.getMaKH(),l.getMaCD(),l.getThoiLuong(),l.getHocPhi(),l.getNgayKG(),l.getMaNV(),l.getNgayTao()});
            }
            
            model.fireTableDataChanged();
        } catch (Exception e) {
        }
    }
    
    void chonCD(){
        try{
    ChuyenDe cd=(ChuyenDe)cboChuyenDe.getSelectedItem();
    
    txtChuyenDe.setText(cd.getTenCD());
    txtHocPhi.setText(String.valueOf(cd.getHocPhi()));
    txtThoiLuong.setText(String.valueOf(cd.getThoiLuong()));
    txtGhiChu.setText(cd.getMoTa());
    
    fillTable1();
    updateStatus();
    
        }catch(NullPointerException e){
        e.printStackTrace();
        }
    }
    void fillComboBoxChuyenDe(){
        ChuyenDeDAO dao=new ChuyenDeDAO();
        DefaultComboBoxModel model=(DefaultComboBoxModel)cboChuyenDe.getModel();
        model.removeAllElements();
        List<ChuyenDe> ls=dao.selectAll();
        for (ChuyenDe l : ls) {
            model.addElement(l);
        }
    }
    void setModel(KhoaHoc kh){
        ChuyenDe cd=(ChuyenDe)cboChuyenDe.getSelectedItem();
        txtChuyenDe.setText(cd.getMaCD());
        txtHocPhi.setText(kh.getHocPhi()+"");
        txtNgayKG.setDate(kh.getNgayKG());
        txtThoiLuong.setText(kh.getThoiLuong()+"");
        txtNgayTao.setDate(kh.getNgayTao());
        txtNguoiTao.setText(kh.getMaNV());
    }
    KhoaHoc getModel() throws ParseException {

        KhoaHoc model = new KhoaHoc();
        ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
        model.setMaCD(chuyenDe.getMaCD());
        model.setNgayKG(txtNgayKG.getDate());
        model.setHocPhi(Double.valueOf(txtHocPhi.getText()));
        model.setThoiLuong(Integer.valueOf(txtThoiLuong.getText()));
        model.setGhiChu(chuyenDe.getMoTa());
        model.setMaNV(Auth.user.getMaNV());
        model.setNgayTao(txtNgayTao.getDate());
       
        return model;
    }
    void edit(){
    Integer ma=(Integer)tblGridView.getValueAt(row,0);
        try {
            KhoaHocDAO dao = new KhoaHocDAO();
            KhoaHoc kh = dao.selectByID(ma);
            setModel(kh);
            
            updateStatus();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    boolean checkngay(){
    StringBuilder sb=new StringBuilder();
    Date ngay1=txtNgayTao.getDate();
    Date ngay2=txtNgayKG.getDate();
    boolean check=ngay1.before(ngay2);// ngày kg có trước ngày tạo
    if(check==false){
    sb.append("Ngày kg phải lớn hơn ngày tạo\n");
    }
    if(sb.length()>0){
    JOptionPane.showMessageDialog(this, sb.toString(),"ERROR",JOptionPane.ERROR_MESSAGE);
    }
    return check;
    // false
    
    }
    
    boolean check() {
    boolean check=true;
    StringBuilder sb=new StringBuilder();
    if(txtChuyenDe.getText().equals("")){
    check=false;
    sb.append("Mã chuyên đề không được bỏ trống \n");
    }
    if(txtHocPhi.getText().equals("")){
    check=false;
    sb.append("Học phí không được bỏ trống \n");
    }else{
        try {
            double hp=Double.parseDouble(txtHocPhi.getText());
            if(hp<0){
            check=false;
            sb.append("Học phí phải lớn hơn 0\n");
            }
        } catch (NumberFormatException e) {
            check=false;
            sb.append("Học phí phải là số \n");
        }
    }
    if(txtNguoiTao.getText().equals("")){
    check=false;
    sb.append("Người tạo không được bỏ trống \n");
    }
    
    if(txtThoiLuong.getText().equals("")){
    check=false;
    sb.append("Thời lượng không được bỏ trống \n");
    }else{
        try {
            int tl=Integer.parseInt(txtThoiLuong.getText());
            if(tl<=0){
            check=false;
            sb.append("Thời lượng phải lớn hơn 0\n");
            }
        } catch (NumberFormatException e) {
            check=false;
            sb.append("Thời lượng phải là số \n");
        }
    }
    
    if(sb.length()>0){
        JOptionPane.showMessageDialog(this, sb.toString(),"ERROR",JOptionPane.ERROR_MESSAGE);
    }
    return check;
    }
    private void insert() {
        if (check() == true && checkngay()==true) {
            try {
                KhoaHoc kh = getModel();
                KhoaHocDAO dao = new KhoaHocDAO();
                dao.insert(kh);
                fillTable1();
                clearForm();
                updateStatus();
                MgsBox.Alert(this,"Thêm vào thành công !!!");
            } catch (Exception e) {
                e.printStackTrace();
                MgsBox.Alert(this, "Lỗi thêm vào");
            }

        }
    }

    private void update() {
        if (check() == true && checkngay()==true) {
            try {
                KhoaHoc model = new KhoaHoc();
                ChuyenDe chuyenDe = (ChuyenDe) cboChuyenDe.getSelectedItem();
                int row=tblGridView.getSelectedRow();
                int makh=(Integer)tblGridView.getValueAt(row,0);
        model.setMaCD(chuyenDe.getMaCD());
        model.setNgayKG(txtNgayKG.getDate());
        model.setHocPhi(Double.valueOf(txtHocPhi.getText()));
        model.setThoiLuong(Integer.valueOf(txtThoiLuong.getText()));
        model.setGhiChu(chuyenDe.getMoTa());
        model.setMaNV(txtNguoiTao.getText());
        model.setNgayTao(txtNgayTao.getDate());
        model.setMaKH(makh);
                KhoaHocDAO dao = new KhoaHocDAO();
                dao.update(model);
                fillTable1();
                clearForm();
                updateStatus();
                MgsBox.Alert(this,"Cập nhập thành công !!!");
            } catch (Exception e) {
                e.printStackTrace();
                MgsBox.Alert(this, "Lỗi cập nhập !!!");
            }

        }
    }

    private void delete() {
        if(!Auth.isManager()){
        MgsBox.Alert(this,"Bạn không có quyền xóa");
        }else{
        if(JOptionPane.showConfirmDialog(this,"Bạn có chắc muốn xóa ?","Confirmation",JOptionPane.YES_NO_OPTION)==JOptionPane.NO_OPTION){
        return ;
        }
            try {
        
        row=tblGridView.getSelectedRow();
        Integer makh=(Integer)tblGridView.getValueAt(row,0);

        new KhoaHocDAO().delete(makh);
        fillTable1();
        clearForm();
        updateStatus();
        MgsBox.Alert(this,"Xóa thành công !!!");   
            } catch (Exception e) {
                MgsBox.Alert(this,"Thất bại");
                e.printStackTrace();
            }
        }
        
    }

    private void clearForm() throws ParseException {
        String now="1800-01-01";
        
        txtChuyenDe.setText("");
        txtGhiChu.setText("");
        txtNgayKG.setDate(XDate.toDate(now,"yyyy-dd-MM"));
        txtNguoiTao.setText("");
        txtNgayTao.setDate(XDate.toDate(now,"yyyy-dd-MM"));
        txtHocPhi.setText("");
        txtThoiLuong.setText("");
        tblGridView.clearSelection();
    }

    private void first() {
        row=0;
        edit();
        
    }

    private void prev() {
        if(row>0){
        row--;
        }
        edit();
    }

    private void next() {
        if(row>0){
        row++;
        }
        edit();
    }

    private void last() {
        row=tblGridView.getRowCount()-1;
        edit();
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
        lblHocPhi = new javax.swing.JLabel();
        txtHocPhi = new javax.swing.JTextField();
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
        lblChuyenDe = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        lblGhiChu = new javax.swing.JLabel();
        lblNgayKG = new javax.swing.JLabel();
        txtNgayKG = new com.toedter.calendar.JDateChooser();
        lblThoiLuong = new javax.swing.JLabel();
        txtThoiLuong = new javax.swing.JTextField();
        lblNguoiTao = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        cboChuyenDe = new javax.swing.JComboBox<>();
        lblNgayTao = new javax.swing.JLabel();
        txtNgayTao = new com.toedter.calendar.JDateChooser();
        txtChuyenDe = new javax.swing.JTextField();

        setBackground(new java.awt.Color(204, 255, 204));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-google-classroom-24.png"))); // NOI18N
        jLabel1.setText("QUẢN LÝ KHÓA HỌC");

        jPanel2.setPreferredSize(new java.awt.Dimension(1520, 660));

        lblHocPhi.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lblHocPhi.setText("Học Phí");

        tblGridView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Ma KH", "Chuyen De", "Thoi Luong", "Hoc Phi", "Ngay KG", "Tao Boi", "Ngay Tao"
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        lblChuyenDe.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lblChuyenDe.setText("Chuyên Đề");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane2.setViewportView(txtGhiChu);

        lblGhiChu.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lblGhiChu.setText("Ghi Chú");

        lblNgayKG.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lblNgayKG.setText("Ngày Khai Giảng");

        lblThoiLuong.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lblThoiLuong.setText("Thời Lượng(Giờ)");

        txtThoiLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThoiLuongActionPerformed(evt);
            }
        });

        lblNguoiTao.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lblNguoiTao.setText("Người Tạo");

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "CHUYÊN ĐỀ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 14), new java.awt.Color(255, 102, 102))); // NOI18N

        cboChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChuyenDeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboChuyenDe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        lblNgayTao.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lblNgayTao.setText("Ngày Tạo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblGhiChu)
                                .addComponent(lblNguoiTao)
                                .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblChuyenDe)
                                        .addComponent(txtHocPhi, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                                        .addComponent(lblHocPhi)
                                        .addComponent(txtChuyenDe))
                                    .addGap(40, 40, 40)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNgayKG, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNgayKG)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblThoiLuong)
                                            .addComponent(txtThoiLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                                            .addComponent(lblNgayTao)
                                            .addComponent(txtNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblChuyenDe)
                            .addComponent(lblNgayKG))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNgayKG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblThoiLuong)
                            .addComponent(lblHocPhi))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNguoiTao)
                            .addComponent(lblNgayTao))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addComponent(lblGhiChu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1493, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 1307, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
                .addContainerGap())
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
        try {
            // TODO add your handling code here:
            clearForm();
        } catch (ParseException ex) {
            Logger.getLogger(KhoaHocJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private void txtThoiLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThoiLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThoiLuongActionPerformed

    private void cboChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChuyenDeActionPerformed
        // TODO add your handling code here:
        chonCD();
    }//GEN-LAST:event_cboChuyenDeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboChuyenDe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblChuyenDe;
    private javax.swing.JLabel lblGhiChu;
    private javax.swing.JLabel lblHocPhi;
    private javax.swing.JLabel lblNgayKG;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblNguoiTao;
    private javax.swing.JLabel lblThoiLuong;
    private javax.swing.JTable tblGridView;
    private javax.swing.JTextField txtChuyenDe;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHocPhi;
    private com.toedter.calendar.JDateChooser txtNgayKG;
    private com.toedter.calendar.JDateChooser txtNgayTao;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtThoiLuong;
    // End of variables declaration//GEN-END:variables
}
