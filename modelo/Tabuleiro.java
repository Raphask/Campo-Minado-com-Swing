package raphask.com.github.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;



public class Tabuleiro implements CampoObservadores {
private final int colunas;
private final int linhas;
private final int minas;

private final List<Campo> campos = new ArrayList<>();
private List<Consumer<Boolean>> observadores = new ArrayList<>();

public Tabuleiro(int colunas, int linhas, int minas) {
	this.colunas = colunas;
	this.linhas = linhas;
	this.minas = minas;
    gerarCampos();
    associarVizinhos();
    sortearMinas();
}
public void getCampos(Consumer<Campo> funcao) {
	campos.forEach(funcao);
}
public int getColunas() {
	return colunas;
}

public int getLinhas() {
	return linhas;
}

public void registrarObservadores(Consumer<Boolean> observador) {
	observadores.add(observador);
}

public void notificarObservadores(boolean resultado) {
	observadores.stream().forEach(o -> o.accept(resultado));
}
public void abrir(int linha, int coluna) {
	
		campos.parallelStream().filter(c -> c.getLinha() 
				== linha && c.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.abrir());
	 }
public void mostrarMinas() {
campos.stream().filter(c -> c.isMinado()).filter(c -> !c.isMarcado())
.forEach(c -> c.setAberto(true));
}
public void alternarMarcacao(int linha, int coluna) {
	campos.parallelStream().filter(c -> c.getLinha() 
	== linha && c.getColuna() == coluna).findFirst()
	.ifPresent(c -> c.alternarMarcação());
}
private void gerarCampos() {
 for (int linha = 0; linha < linhas; linha++) {
	for (int coluna = 0; coluna < colunas; coluna++) {
		Campo campo = new Campo(coluna, linha);
		campo.registrarObservador(this);
		campos.add(campo);
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
@Override
public void eventoOcorreu(Campo campo, CampoEvento evento) {
	if (evento == CampoEvento.EXPLODIR) {
	    mostrarMinas();
		notificarObservadores(false);
	} else if (objetivoAlcançado()) {
		System.out.println("VocÊ ganhou");
		notificarObservadores(true);
	} 
}
}
