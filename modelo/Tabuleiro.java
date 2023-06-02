package raphask.com.github.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import raphask.com.github.excecao.ExplosãoException;

public class Tabuleiro {
private int colunas;
private int linhas;
private int minas;

private final List<Campo> campos = new ArrayList<>();

public Tabuleiro(int colunas, int linhas, int minas) {
	this.colunas = colunas;
	this.linhas = linhas;
	this.minas = minas;
    gerarCampos();
    associarVizinhos();
    sortearMinas();
}
public void abrir(int linha, int coluna) {
	try {
		campos.parallelStream().filter(c -> c.getLinha() 
				== linha && c.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.abrir());
	} catch (ExplosãoException e) {
		campos.forEach(c -> c.setAberto(true));
		throw e;
	}
}
public void alternarMarcacao(int linha, int coluna) {
	campos.parallelStream().filter(c -> c.getLinha() 
	== linha && c.getColuna() == coluna).findFirst()
	.ifPresent(c -> c.alternarMarcação());
}
private void gerarCampos() {
 for (int linha = 0; linha < linhas; linha++) {
	for (int coluna = 0; coluna < colunas; coluna++) {
		campos.add(new Campo(linha, coluna));
	}
}
}

private void associarVizinhos() {
	for (Campo campo : campos) {
	for (Campo campo2 : campos) {
	campo.adicionarVizinhos(campo2);
		}
	}
	
}private void sortearMinas() {
	long minasArmadas= 0;
	Predicate<Campo> minado = c -> c.isMinado(); 
	do {
    int aleatorio =(int) (Math.random() * campos.size());
	campos.get(aleatorio).minado();
	minasArmadas = campos.stream().filter(minado).count();
	} while (minasArmadas < minas);
	
}
public boolean objetivoAlcançado() {
return campos.stream().allMatch(c -> c.objetivoAlcançado());
}
public void reiniciar() {
	campos.stream().forEach(c -> c.reiniciar());
	sortearMinas();
}
   public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("   ");
	for (int c = 0; c < colunas; c++) {
		sb.append(" ");
		sb.append(c);
		sb.append(" ");
	}
	 sb.append("\n");
	
	 int i = 0;
	for (int l = 0; l < linhas; l++) {
		sb.append(" ");
		sb.append(l);
		sb.append(" ");
		for (int c = 0; c < colunas; c++) {
		sb.append(" ");	
		sb.append(campos.get(i));	
		sb.append(" ");	
		i++;
		}
	  sb.append("\n");
	}
	
	return sb.toString();
}
}
