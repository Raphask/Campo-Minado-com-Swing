package raphask.com.github.visao;

import java.awt.Color;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import raphask.com.github.modelo.Campo;
import raphask.com.github.modelo.CampoEvento;
import raphask.com.github.modelo.CampoObservadores;

public class BotãoCampo extends JButton implements CampoObservadores, MouseListener {

	private Campo campo;
	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color BG_EXPLODIR = new Color(255, 0, 0);
	private final Color BG_MARCAR = new Color(135, 206, 250);
	private final Color BG_DESMARCAR = new Color(0, 100, 0);
public BotãoCampo(Campo campo) {
  this.campo = campo;
 
  setBackground(BG_PADRAO);
  setBorder(BorderFactory.createBevelBorder(0));
	 addMouseListener(this);
	 campo.registrarObservador(this);

}

@Override
public void eventoOcorreu(Campo campo, CampoEvento evento) {
	switch (evento) {
	case ABRIR: 
		aplicarEstiloAbrir();
		break;
	
	case MARCAR: 
		aplicarEstiloMarcar();
		break;
	
	case EXPLODIR: 
		aplicarEstiloExplodir();
		break;
	
	default:
		aplicarEstiloPadrao();
	 setBorder(BorderFactory.createBevelBorder(0));
	}
	
}

private void aplicarEstiloMarcar() {
	setBackground(BG_MARCAR);
	setForeground(Color.BLACK);
	setText("M");
	
}

private void aplicarEstiloExplodir() {
	setBackground(BG_EXPLODIR);
	setForeground(Color.WHITE);
	setText("X");
	
}

private void aplicarEstiloPadrao() {
	setBackground(BG_PADRAO);
	setBorder(BorderFactory.createLineBorder(Color.GRAY));
    setText("");
}

private void aplicarEstiloAbrir() {
	setBorder(BorderFactory.createLineBorder(Color.GRAY));
	
	if (campo.isMinado()) {
		aplicarEstiloExplodir();
		return;
	}
	
	setBackground(BG_PADRAO);
	
	switch(campo.minasNaVizinhanca()) {
	case 1: 
		setForeground(BG_PADRAO);
	    break;
	case 2: 
		setForeground(Color.BLUE);
		break;
	case 3:
		setForeground(Color.YELLOW);
		break;
	case 4:
	case 5:
	case 6:
		setForeground(Color.RED);
		break;
	default: 
	    setForeground(Color.PINK);
	}
    String valor = !campo.vizinhançaSegura() ? campo.minasNaVizinhanca() 
    		+ "" : "" ;
    setText(valor);
}





@Override
public void mouseClicked(java.awt.event.MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(java.awt.event.MouseEvent e) {
	if (e.getButton() == 1) {
		campo.abrir();
	} 
	else {
		campo.alternarMarcação();
	}
   }
	


@Override
public void mouseReleased(java.awt.event.MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseEntered(java.awt.event.MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(java.awt.event.MouseEvent e) {
	// TODO Auto-generated method stub
	
}
}

