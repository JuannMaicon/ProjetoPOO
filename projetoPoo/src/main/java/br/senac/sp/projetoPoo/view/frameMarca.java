package br.senac.sp.projetoPoo.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.senac.sp.projetoPoo.dao.ConnectionFactory;
import br.senac.sp.projetoPoo.dao.MarcaDAO;
import br.senac.sp.projetoPoo.modelo.Marca;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class frameMarca extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNome;
	private JTextField tfId;
	private JTextField textField_2;
	private JTextField lblLogo;
	private Marca marca;
	private  MarcaDAO dao;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frameMarca frame = new frameMarca();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frameMarca() {
		dao = new MarcaDAO(ConnectionFactory.getConexao());
		setTitle("Cadastro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("NOME:");
		lblNewLabel.setBounds(10, 61, 46, 14);
		contentPane.add(lblNewLabel);

		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(10, 9, 46, 17);
		contentPane.add(lblId);

		JButton btnExcluir = new JButton("EXCLUIR");
		btnExcluir.setMnemonic('e');
		btnExcluir.setBounds(155, 86, 89, 23);
		contentPane.add(btnExcluir);

		JButton btnSalvar = new JButton("SALVAR");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfNome.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(frameMarca.this, "Informe o Nome", "Aviso",
							JOptionPane.INFORMATION_MESSAGE);
					tfNome.requestFocus();
				} else {
					marca = new Marca();
					marca.setNome(tfNome.getText().trim());
				//TODO setar a logo
					try {
						dao.inserir(marca);
						limpar();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(frameMarca.this, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
					
				}
			}
		});
		btnSalvar.setMnemonic('s');
		btnSalvar.setBounds(10, 86, 89, 23);
		contentPane.add(btnSalvar);

		JButton btnLimpar = new JButton("LIMPAR");
		btnLimpar.setMnemonic('l');
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLimpar.setBounds(320, 86, 89, 23);
		contentPane.add(btnLimpar);

		tfNome = new JTextField();
		tfNome.setBounds(47, 58, 277, 20);
		contentPane.add(tfNome);
		tfNome.setColumns(10);

		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setColumns(10);
		tfId.setBounds(47, 6, 164, 20);
		contentPane.add(tfId);

		textField_2 = new JTextField();
		textField_2.setBounds(10, 129, 399, 121);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		lblLogo = new JTextField();
		lblLogo.setBackground(new Color(192, 192, 192));
		lblLogo.setBounds(345, 6, 64, 69);
		contentPane.add(lblLogo);
		lblLogo.setOpaque(true);
		lblLogo.setColumns(10);
	}
	private void limpar() {
		tfId.setText("");
		tfNome.setText("");
		marca = null;
		tfNome.requestFocus();
		
	}
}
