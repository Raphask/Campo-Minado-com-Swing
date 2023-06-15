package raphask.com.github.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import raphask.com.github.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel {
 public PainelTabuleiro(Tabuleiro tabuleiro) {
	setLayout(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));
	tabuleiro.getCampos(c -> add(new BotãoCampo(c)));
	 tabuleiro.registrarObservadores(e -> {
		SwingUtilities.invokeLater(() -> {
		 if (tabuleiro.objetivoAlcançado()) {
			JOptionPane.showMessageDialog(this, "Você ganhou");
		}
		else {
			JOptionPane.showMessageDialog(this, "Você perdeu");
		
		}
		  tabuleiro.reiniciar();
		 }); 
	 }); 
}
}
