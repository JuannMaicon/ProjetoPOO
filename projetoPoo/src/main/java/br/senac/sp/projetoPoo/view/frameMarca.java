package br.senac.sp.projetoPoo.view;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.senac.sp.projetoPoo.dao.ConnectionFactory;
import br.senac.sp.projetoPoo.dao.MarcaDAO;
import br.senac.sp.projetoPoo.modelo.Marca;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class frameMarca extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNome;
	private JTextField tfId;
	private JTextField textField_2;
	private JLabel lblLogo;
	private Marca marca;
	private  MarcaDAO dao;
	private JFileChooser chooser;
	private FileFilter imageFilter;
	private File selecionado;

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
		chooser = new JFileChooser();
		imageFilter = new FileNameExtensionFilter("Imagens", ImageIO.getReaderFileSuffixes());
		setTitle("Cadastro de Marcas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("NOME:");
		lblNewLabel.setBounds(10, 84, 46, 14);
		contentPane.add(lblNewLabel);

		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(10, 9, 46, 17);
		contentPane.add(lblId);

		JButton btnExcluir = new JButton("EXCLUIR");
		btnExcluir.setMnemonic('e');
		btnExcluir.setBounds(162, 197, 89, 23);
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
						if(selecionado != null) {
							byte[]imagemBytes = Files.readAllBytes(selecionado.toPath());
							marca.setLogo(imagemBytes);
						}
						dao.inserir(marca);
						limpar();
					} catch (SQLException | IOException e1) {
						JOptionPane.showMessageDialog(frameMarca.this, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
					
				}
			}
		});
		btnSalvar.setMnemonic('s');
		btnSalvar.setBounds(10, 197, 89, 23);
		contentPane.add(btnSalvar);

		JButton btnLimpar = new JButton("LIMPAR");
		btnLimpar.setMnemonic('l');
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(320, 197, 89, 23);
		contentPane.add(btnLimpar);

		tfNome = new JTextField();
		tfNome.setBounds(47, 81, 277, 20);
		contentPane.add(tfNome);
		tfNome.setColumns(10);

		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setColumns(10);
		tfId.setBounds(47, 6, 164, 20);
		contentPane.add(tfId);

		lblLogo = new JLabel();
		lblLogo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()==2) {
					chooser.setFileFilter(imageFilter);
					if(chooser.showOpenDialog(frameMarca.this)== JFileChooser.APPROVE_OPTION) {
						selecionado = chooser.getSelectedFile();
						try {
						BufferedImage bufimg = ImageIO.read(selecionado);
						Image imagem =bufimg.getScaledInstance
								(lblLogo.getWidth(), lblLogo.getHeight(),Image.SCALE_SMOOTH);
						ImageIcon imgLabel = new ImageIcon(imagem);
						lblLogo.setIcon(imgLabel);
					}catch(IOException e1) {
						e1.printStackTrace();
					}}
				}
				
				
									
			}
		});
		lblLogo.setBackground(new Color(192, 192, 192));
		lblLogo.setBounds(345, 6, 64, 69);
		contentPane.add(lblLogo);
		lblLogo.setOpaque(true);
		
	}
	
	private void limpar() {
		tfId.setText("");
		tfNome.setText("");
		marca = null;
		tfNome.requestFocus();
	}
}



