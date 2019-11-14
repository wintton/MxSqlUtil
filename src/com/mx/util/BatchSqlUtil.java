package com.mx.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mx.handle.HandleManager;
import com.mx.handle.HandleUtil;

public class BatchSqlUtil extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String driver = "com.mysql.jdbc.Driver";
	private String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/db";
	private String account = "请输入您的数据库账号";
	private String password = "请输入您的数据库密码";
	private int height = 600;
	private int width = 900;
	private int btn_width = 200;

	private JFrame frame; // 当前窗口
	private JComboBox<String> jc, exportselbox;
	private JButton btn_file_path, btn_yulan; // 按钮

	private JTextField mysql_db_rul, read_file_path, mysql_db_account, mysql_db_pwd; // 输入框
	private JTextArea text_result;
	private JButton btn_export_xls;
	// 下面第三个面板的控件
	private FileDialog openDia;
	private JFileChooser chooser;
	private HandleManager handleManager;

	public BatchSqlUtil() {

	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BatchSqlUtil(HandleManager handleManager) {
		this.handleManager = handleManager;
	}

	public BatchSqlUtil(String jdbcUrl, String account, String password, HandleManager handleManager)
			throws HeadlessException {
		super();
		this.jdbcUrl = jdbcUrl;
		this.account = account;
		this.password = password;
		this.handleManager = handleManager;
	}

	public void show() {
		if (handleManager == null) {
			handleManager = new HandleManager();
		}
		// 得到当前屏幕的尺寸
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		openDia = new FileDialog(this, "打开", FileDialog.LOAD);
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setCurrentDirectory(new File("D:\\"));
		// frame指底层窗口
		frame = new JFrame("批量数据库处理工具");
		frame.setBounds((screenWidth - 900) / 2, (screenHeight - 500) / 2, width, height); // 设置位置及大小
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.lightGray);
		frame.getContentPane().setLayout(null); // 使用绝对布局
		frame.setResizable(false); // 设置窗口大小不可变

		mysql_db_rul = new JTextField();
		mysql_db_rul.setBounds(2, 45, (int) (width * 0.98), 40);
		mysql_db_rul.setVisible(true);
		mysql_db_rul.setText(jdbcUrl);
		frame.getContentPane().add(mysql_db_rul);

		// sleep_time_in = new JTextField();
		// sleep_time_in.setBounds(2 + (int) (width * 0.5), 45, (int) (width * 0.5),
		// 40);
		// sleep_time_in.setVisible(true);
		// sleep_time_in.setText("请输入生成条数和随机字符串长度（生成字符串时有效，格式：‘字符串长度，条数’）");
		// frame.getContentPane().add(sleep_time_in);

		read_file_path = new JTextField();
		read_file_path.setBounds(2, 5, (int) (width * 0.78), 40);
		read_file_path.setVisible(true);
		read_file_path.setText("D:\\test.xls");
		frame.getContentPane().add(read_file_path);

		btn_file_path = new JButton("选择xls文件路径");
		btn_file_path.setVisible(true);
		btn_file_path.setBounds(702, 5, (int) (width * 0.22), 40);
		frame.getContentPane().add(btn_file_path);
		btn_file_path.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openDia.setVisible(true); // 打开文件选择
				read_file_path.setText(openDia.getDirectory() + openDia.getFile());
			}
		});

		mysql_db_account = new JTextField(account);
		mysql_db_account.setBounds(2, 83, (int) (width * 0.5), 40);
		mysql_db_account.setVisible(true);
		frame.getContentPane().add(mysql_db_account);

		mysql_db_pwd = new JTextField(password);
		mysql_db_pwd.setBounds(452, 83, (int) (width * 0.5) - 4, 40);
		mysql_db_pwd.setVisible(true);
		frame.getContentPane().add(mysql_db_pwd);
		jc = new JComboBox<>();

		jc.setBounds(2, 125, (int) (width * 0.5) - 2, 40);

		for (HandleUtil handleUtil : handleManager.getHandleList()) {
			jc.addItem(handleUtil.getName());
		}
		jc.setVisible(true);
		frame.getContentPane().add(jc);

		exportselbox = new JComboBox<>();
		exportselbox.setBounds(2 + (int) (width * 0.5) - 2, 125, (int) (width * 0.5) - 2, 40);
		exportselbox.addItem("不导出处理结果");
		exportselbox.addItem("导出处理结果");
		exportselbox.setVisible(true);
		frame.getContentPane().add(exportselbox);
		text_result = new JTextArea();
		text_result.setBounds(2, 170, width, 300);
		text_result.setEditable(false);
		text_result.setLineWrap(true);
		JScrollPane jScrollPane = new JScrollPane(text_result);
		text_result.setText("控制台显示");
		jScrollPane.setBounds(2, 170, width - 2, 300);
		frame.getContentPane().add(jScrollPane);

		btn_yulan = new JButton("连接测试");
		btn_yulan.setVisible(true);
		btn_yulan.setBounds(245, 500, btn_width, 40);
		frame.getContentPane().add(btn_yulan);
		btn_yulan.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {

						try {
							String mySqlDBAccount = mysql_db_account.getText();
							String MysqlUrl = mysql_db_rul.getText();
							String MysqlPwd = mysql_db_pwd.getText();

							if (MysqlUrl == null || MysqlUrl.length() == 0 || MysqlUrl.indexOf("请输入") >= 0) {
								JOptionPane.showMessageDialog(null, "数据库地址不能为空", "提示", JOptionPane.PLAIN_MESSAGE);
								return;
							}

							if (MysqlUrl.indexOf("jdbc:mysql://") < 0) {
								JOptionPane.showMessageDialog(null, "数据库地址格式不正确", "提示", JOptionPane.PLAIN_MESSAGE);
								return;
							}

							if (mySqlDBAccount == null || mySqlDBAccount.length() == 0
									|| mySqlDBAccount.indexOf("请输入") >= 0) {
								JOptionPane.showMessageDialog(null, "数据库账号不能为空", "提示", JOptionPane.PLAIN_MESSAGE);
								return;
							}

							if (MysqlPwd == null || MysqlPwd.length() == 0 || MysqlPwd.indexOf("请输入") >= 0) {
								JOptionPane.showMessageDialog(null, "数据库密码不能为空", "提示", JOptionPane.PLAIN_MESSAGE);
								return;
							}

							text_result.setText("开始测试数据库连接\n");
							text_result.append("数据库地址：" + MysqlUrl + "\n");
							text_result.append("数据库账号：" + mySqlDBAccount + "\n");
							text_result.append("数据库密码：" + MysqlPwd + "\n");
							text_result.append("连接中\n");

							Class.forName(driver);
							Connection conn = null;
							conn = DriverManager.getConnection(MysqlUrl, mySqlDBAccount, MysqlPwd);

							if (!conn.isClosed()) {
								text_result.append("连接成功，可以正常使用\n");
								conn.close();
								return;
							}
							text_result.append("连接失败，请确认账号密码或路径是否正确\n");

							conn.close();
						} catch (Exception e1) {
							text_result.setText("数据连接测试失败：" + e1.toString());
							e1.printStackTrace();
						}
					}
				}).start();

			}
		});

		btn_export_xls = new JButton("开始处理");
		btn_export_xls.setVisible(true);
		btn_export_xls.setBounds(245 + btn_width + 10, 500, btn_width, 40);

		frame.getContentPane().add(btn_export_xls);
		btn_export_xls.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						// 开始处理 text_result
						String exportfilepath = read_file_path.getText();
						if (exportfilepath == null || exportfilepath.length() == 0
								|| exportfilepath.indexOf("请输入") >= 0) {
							JOptionPane.showMessageDialog(null, "未选择xls文件路径", "提示", JOptionPane.PLAIN_MESSAGE);
							return;
						}
						if (!exportfilepath.endsWith("xls")) {
							JOptionPane.showMessageDialog(null, "目前仅支持xls格式", "提示", JOptionPane.PLAIN_MESSAGE);
							return;
						}

						String mySqlDBAccount = mysql_db_account.getText();
						String MysqlUrl = mysql_db_rul.getText();
						String MysqlPwd = mysql_db_pwd.getText();

						HandleUtil.address = MysqlUrl;
						HandleUtil.account = mySqlDBAccount;
						HandleUtil.password = MysqlPwd;
						HandleUtil.isExport = exportselbox.getSelectedIndex() == 1;
						try {
							int index = jc.getSelectedIndex();
							System.out.print(index);
							handleManager.handle(exportfilepath, text_result, index);
						} catch (Exception e1) {
							text_result.append("发生错误：错误信息：" + e1.toString() + "\n");
							e1.printStackTrace();
						}

					}
				}).start();

			}
		});
		// 需添加完组件后设置可见
		frame.setVisible(true); // 设置窗口可见
	}

}
