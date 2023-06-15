package raphask.com.github.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;



public class Campo {
private int coluna;
private int linha;

private boolean aberto;
private boolean minado;
private boolean marcado;

List<Campo> campo = new ArrayList<>();
private List<CampoObservadores> 
observadores = new ArrayList<>();
  
 public Campo(int coluna, int linha) {
	this.linha = linha;
	this.coluna = coluna;
	}
 
 public void registrarObservador(CampoObservadores e) {
		observadores.add(e);
	}
 private void notificarObservadores(CampoEvento cE) {
	 observadores.stream().forEach(o -> o.eventoOcorreu(this, cE));
 }
boolean adicionarVizinhos(Campo vizinho) {
boolean linhaVizinha = linha != vizinho.linha;
boolean colunaVizinha = coluna != vizinho.coluna;
boolean diagonal = linhaVizinha && colunaVizinha;

int deltaLinha = Math.abs(linha - vizinho.linha);
int deltaColuna = Math.abs(coluna - vizinho.coluna);
int deltaGeral = deltaLinha + deltaColuna;
 
if (deltaGeral == 1 && !diagonal) {
	campo.add(vizinho);
	return true;
}else if (deltaGeral == 1 && diagonal) {
	campo.add(vizinho);
	return true;
}
else if (deltaGeral == 2 && !diagonal) {
	campo.add(vizinho);
	return true;
}else if (deltaGeral == 2 && diagonal) {
	campo.add(vizinho);
	return true;
}else {
	return false;
}
 }
 
 public void alternarMarcação() {
	 if (!aberto) {
      marcado = !marcado;
      
      if (marcado) {
		notificarObservadores(CampoEvento.MARCAR);
	}else {
		notificarObservadores(CampoEvento.DESMARCAR);
	}
	 }
  }
 public boolean abrir() {
 if(!aberto && !marcado) {
	 aberto = true;
 
 if (minado) {
notificarObservadores(CampoEvento.EXPLODIR);
 return true;
 
 }
 setAberto(true);
 
 if (vizinhançaSegura()) {
 campo.forEach(v -> v.abrir());
}
  return true;
 }else {
return false;
}
 
 }
public boolean vizinhançaSegura() {
return campo.stream().noneMatch(v-> v.minado);
}
public boolean isMarcado() {
return marcado;
}
void minado() {
minado = true;
	}
public boolean isMinado() {
	return minado;
}

 void setAberto(boolean aberto) {
 this.aberto = aberto;
 if (aberto) {
 notificarObservadores(CampoEvento.ABRIR);
}
}
public boolean isAberto() {
	return aberto;
}
public boolean isFechado() {
	return !isAberto();
}
public int getLinha() {
	return linha;
}
public int getColuna() {
	return coluna;
}
boolean objetivoAlcançado() {
	boolean desvendado = !minado && aberto;
	boolean protegido = minado && isMarcado();
	return desvendado || protegido;
}
public int minasNaVizinhanca() {
	return (int) campo.stream().filter(v -> v.minado).count();
}
void reiniciar() {
	aberto = false;
	minado = false;
	marcado = false;
	notificarObservadores(CampoEvento.REINICIAR);
}
public String toString() {
	if (marcado) {
		return "x";
	}
	else if (aberto && minado) {
		return "*";
	}
	else if (aberto && minasNaVizinhanca() > 0) {
		return Long.toString(minasNaVizinhanca());
	}
	else if (aberto) {
		return " ";
	} else {
		return "?";
	}
}
	
} 

