package menjacnica.gui.kontroler;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.gui.DodajKursGUI;
import menjacnica.gui.IzvrsiZamenuGUI;
import menjacnica.gui.MenjacnicaGUI;
import menjacnica.gui.ObrisiKursGUI;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {

	public static MenjacnicaInterface menjacnica = new Menjacnica();
	
	public static MenjacnicaGUI gp;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIKontroler.gp = new MenjacnicaGUI();
					GUIKontroler.gp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				menjacnica.ucitajIzFajla(file.getAbsolutePath());
				gp.prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				menjacnica.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI(gp);
		prozor.setLocationRelativeTo(gp);
		prozor.setVisible(true);
	}
	
	public static void unesiKurs(String naziv, String skraceno, int sifra, double prodajni, double kupovni, double srednji) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceno);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajni);
			valuta.setKupovni(kupovni);
			valuta.setSrednji(srednji);
			
			// Dodavanje valute u kursnu listu
			menjacnica.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			gp.prikaziSveValute(menjacnica);
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void prikaziObrisiKursGUI(JTable table) {

		if (table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(table.getModel());
			ObrisiKursGUI prozor = new ObrisiKursGUI(gp,
					model.vratiValutu(table.getSelectedRow()));
			prozor.setLocationRelativeTo(null);
			prozor.setVisible(true);
		}
	}
	
	public static void obrisiValutu(Valuta valuta) {
		try{
			menjacnica.obrisiValutu(valuta);
			
			gp.prikaziSveValute(menjacnica);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void prikaziIzvrsiZamenuGUI(JTable table) {
		if (table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(table.getModel());
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(gp,
					model.vratiValutu(table.getSelectedRow()));
			prozor.setLocationRelativeTo(null);
			prozor.setVisible(true);
		}
	}
	
	public static double izvrsiZamenu(Valuta valuta, boolean prodaja, double iznos){
		double konacniIznos = 0;
		try{
			konacniIznos = menjacnica.izvrsiTransakciju(valuta, prodaja, iznos);
		
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(null, e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
		}
		return konacniIznos;
	}
	
}
