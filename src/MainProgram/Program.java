package MainProgram;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class Program {

	private JFrame frmFileSelector;
	File folder;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Program window = new Program();
					window.frmFileSelector.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Program() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFileSelector = new JFrame();
		frmFileSelector.setTitle("File Selector");
		frmFileSelector.setBounds(100, 100, 450, 367);
		frmFileSelector.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFileSelector.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 410, 189);
		frmFileSelector.getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JButton button = new JButton("Buscar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 14));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Evento ao clicar botão
				String msg = SelectAndFind.FindFiles(folder, textArea.getText());
				JOptionPane.showMessageDialog(null, msg);
			}
		});
		button.setBounds(10, 251, 416, 32);
		frmFileSelector.getContentPane().add(button);
		
		JLabel lblPastaSelecionada = new JLabel("Nenhuma pasta selecionada");
		lblPastaSelecionada.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPastaSelecionada.setBounds(178, 10, 248, 32);
		frmFileSelector.getContentPane().add(lblPastaSelecionada);
		
		
		JButton btnPasta = new JButton("Selecionar Pasta");
		btnPasta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog(fileChooser);
				folder = fileChooser.getSelectedFile();
				
				// set browser path JLabel
				lblPastaSelecionada.setText(folder.getPath());
			}
		});
		btnPasta.setBounds(10, 10, 158, 32);
		frmFileSelector.getContentPane().add(btnPasta);
		
		
		
		JLabel lblMadeByPedro = new JLabel("Made by Pedro Camata Andreon(github.com/PedroCamata)");
		lblMadeByPedro.setBounds(12, 296, 414, 16);
		frmFileSelector.getContentPane().add(lblMadeByPedro);
		
		
		
		
	}
}
