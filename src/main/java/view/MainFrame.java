package view;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.ZooService;
import db.DatabaseManager;
import model.Animal;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableAnimaux;
	private JTextField textFieldId;
	private JTextField textFieldNom;
	private JTextField textFieldEspece;
	private JTextField textFieldAge;
	private JTextField textFieldRegimeAlimentaire;
	private DefaultTableModel tableModel;
	private DatabaseManager databaseManager;
	
	private ZooService service;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		
		databaseManager = new DatabaseManager();
		service = new ZooService(databaseManager);
		
		setTitle("Examen 2 - POO 2 (Raphael et Ismail)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 564);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(39, 302, 643, 164);
		contentPane.add(scrollPane);
		
		tableModel = createTableAnimauxModel();
		
		tableAnimaux = new JTable(tableModel);
		tableAnimaux.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
					int row = tableAnimaux.getSelectedRow();
					if(row != -1) {
						int id = (int) tableModel.getValueAt(row, 0);
						Animal animal = service.getAnimalById(id);
                        textFieldId.setText(String.valueOf(animal.getId()));
                        textFieldNom.setText(animal.getNom());
                        textFieldEspece.setText(animal.getEspece());
                        textFieldAge.setText(String.valueOf(animal.getAge()));
                        textFieldRegimeAlimentaire.setText(animal.getRegimeAlimentaire());
                    }
					
				}
			}
		});
		scrollPane.setViewportView(tableAnimaux);
		
		JLabel lblNewLabel = new JLabel("Id");
		lblNewLabel.setBounds(39, 21, 46, 14);
		contentPane.add(lblNewLabel);
		
		textFieldId = new JTextField();
		textFieldId.setEditable(false);
		textFieldId.setBounds(39, 39, 179, 20);
		contentPane.add(textFieldId);
		textFieldId.setColumns(10);
		
		JLabel lblNom = new JLabel("Nom");
		lblNom.setBounds(39, 70, 46, 14);
		contentPane.add(lblNom);
		
		textFieldNom = new JTextField();
		textFieldNom.setColumns(10);
		textFieldNom.setBounds(39, 88, 179, 20);
		contentPane.add(textFieldNom);
		
		textFieldEspece = new JTextField();
		textFieldEspece.setColumns(10);
		textFieldEspece.setBounds(39, 137, 179, 20);
		contentPane.add(textFieldEspece);
		
		JLabel lblEspece = new JLabel("Espece");
		lblEspece.setBounds(39, 119, 46, 14);
		contentPane.add(lblEspece);
		
		textFieldAge = new JTextField();
		textFieldAge.setColumns(10);
		textFieldAge.setBounds(39, 185, 179, 20);
		contentPane.add(textFieldAge);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setBounds(39, 167, 46, 14);
		contentPane.add(lblAge);
		
		JLabel lblRegimeAlimentaire = new JLabel("Regime Alimentaire");
		lblRegimeAlimentaire.setBounds(39, 216, 179, 14);
		contentPane.add(lblRegimeAlimentaire);
		
		textFieldRegimeAlimentaire = new JTextField();
		textFieldRegimeAlimentaire.setColumns(10);
		textFieldRegimeAlimentaire.setBounds(39, 234, 179, 20);
		contentPane.add(textFieldRegimeAlimentaire);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Create un nouveau animal
				int response = javax.swing.JOptionPane.showConfirmDialog(
						null,
						"Voulez-vous vraiment ajouter ce animal?",
						"Confirmation",
						javax.swing.JOptionPane.YES_NO_OPTION,
						javax.swing.JOptionPane.QUESTION_MESSAGE
					);
					
					if (response == javax.swing.JOptionPane.YES_OPTION) {
						if(createAnimal()) {
							fillTableAnimaux();
							clearAllFields();
						}
						
					}
				}
			
		});
		btnAjouter.setBounds(239, 38, 138, 23);
		contentPane.add(btnAjouter);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response = javax.swing.JOptionPane.showConfirmDialog(
						null,
						"Voulez-vous vraiment supprimer ce livre?",
						"Confirmation",
						javax.swing.JOptionPane.YES_NO_OPTION,
						javax.swing.JOptionPane.QUESTION_MESSAGE
					);
				
				if (response == javax.swing.JOptionPane.YES_OPTION) {
					if(deleteAnimal()) {
						fillTableAnimaux();
						clearAllFields();
					}
				}
			}
		});
		btnSupprimer.setBounds(239, 87, 138, 23);
		contentPane.add(btnSupprimer);
		
		JButton btnMettreAJour = new JButton("Mettre à Jour");
		btnMettreAJour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Mise à jour d'un animal
                int response = javax.swing.JOptionPane.showConfirmDialog(
                        null,
                        "Voulez-vous vraiment mettre à jour ce animal?",
                        "Confirmation",
                        javax.swing.JOptionPane.YES_NO_OPTION,
                        javax.swing.JOptionPane.QUESTION_MESSAGE
                    );
                
                if (response == javax.swing.JOptionPane.YES_OPTION) {
                    if(updateAnimal()) {
                        fillTableAnimaux();
                        clearAllFields();
                    }
                }
			}
		});
		btnMettreAJour.setBounds(239, 136, 138, 23);
		contentPane.add(btnMettreAJour);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAllFields();
				fillTableAnimaux();
			}
		});
		btnRefresh.setBounds(240, 182, 138, 23);
		contentPane.add(btnRefresh);
		
		//Appeler les fonctions qui vont construir les choses
		fillTableAnimaux();
	}
	
	private boolean updateAnimal() {
		
		try {
			int id = Integer.parseInt(textFieldId.getText());
			Animal animal = new Animal(
				id,
				textFieldNom.getText(),
				textFieldEspece.getText(),
				Integer.parseInt(textFieldAge.getText()),
				textFieldRegimeAlimentaire.getText()
			);
			service.updateAnimal(animal);
			
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Animal mis à jour avec succès!",
                    "Succès",
					javax.swing.JOptionPane.INFORMATION_MESSAGE
									);
			return true;
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Une erreur s'est produite lors de la mise à jour de l'animal",
                    "Erreur",
                    javax.swing.JOptionPane.ERROR_MESSAGE
                );
        }

		return false;
	}

	private boolean deleteAnimal() {
		try {
			int id = Integer.parseInt(textFieldId.getText());
			service.deleteAnimal(id);
			
			javax.swing.JOptionPane.showMessageDialog(
					this,
					"Animal supprimé avec succès!",
					"Succès",
					javax.swing.JOptionPane.INFORMATION_MESSAGE
				);
			return true;
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(
					this,
					"Une erreur s'est produite lors de la suppression du animal",
					"Erreur",
					javax.swing.JOptionPane.ERROR_MESSAGE
				);
		}
		return false;
	}

	private void clearAllFields() {
		textFieldId.setText("");
		textFieldNom.setText("");
		textFieldEspece.setText("");
		textFieldAge.setText("");
		textFieldRegimeAlimentaire.setText("");
	}
		
	
	private boolean createAnimal() {
		try {
			Animal newAnimal = new Animal(
				textFieldNom.getText(),
				textFieldEspece.getText(),
				Integer.parseInt(textFieldAge.getText()),
				textFieldRegimeAlimentaire.getText()
			);
			
			service.addAnimal(newAnimal);
			
			javax.swing.JOptionPane.showMessageDialog(
					this,
					"Animal ajouté avec succès!",
					"Succès",
					javax.swing.JOptionPane.INFORMATION_MESSAGE
				);
				return true;
		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(
					this,
					"Une erreur s'est produite lors de l'ajout du animal",
					"Erreur",
					javax.swing.JOptionPane.ERROR_MESSAGE
				);
		}
		return false;
	}
	
	private void fillTableAnimaux() {
		tableModel.setRowCount(0);
		List<Animal> animaux = service.getAllAnimals();
		
		for(Animal animal : animaux) {
			tableModel.addRow(new Object[] {
					animal.getId(),
					animal.getNom(),
					animal.getEspece(),
					animal.getAge(),
					animal.getRegimeAlimentaire()
			});
		}
	}
	
	private DefaultTableModel createTableAnimauxModel() {
		tableModel = new DefaultTableModel();
		
		tableModel.addColumn("Id");
		tableModel.addColumn("Nom");
		tableModel.addColumn("Espece");
		tableModel.addColumn("Age");
		tableModel.addColumn("Regime Alimentaire");
		
		return tableModel;
	}
	
}
