package raphask.com.github.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import raphask.com.github.excecao.ExplosãoException;
import raphask.com.github.excecao.SairExcecao;
import raphask.com.github.modelo.Tabuleiro;

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner(System.in);
	public TabuleiroConsole(Tabuleiro tabuleiro) {
	this.tabuleiro = tabuleiro;
    executarJogo();
	}
	private void executarJogo() {
		try {
			boolean continuar = true;
			while (continuar) {
		    cicloDoJogo();
		   System.out.println("Outra partida? (S/n)");
			String resposta = entrada.nextLine();
		if ("n".equalsIgnoreCase(resposta)) {
			continuar = false;
			} else {
				tabuleiro.reiniciar();
			}
			}
		} catch (SairExcecao e) {
			System.out.println("Tchau");
		} finally{
			entrada.close();
		}
	}
	private void cicloDoJogo() {
		try {
			while (!tabuleiro.objetivoAlcançado()) {
				System.out.println(tabuleiro);
				String digitado = capturarDigitado("Digite linha e coluna (x, y)");
			   
				Iterator <Integer> xy = Arrays.stream(digitado.split(","))
			 .map(e -> Integer.parseInt(e.trim())).iterator();
			 
				digitado = capturarDigitado("1 pra abrir e 2 pra desmacar");
			    if ("1".equals(digitado)) {
				tabuleiro.abrir(xy.next(), xy.next());
			 }
				else if ("2".equals(digitado)) {
				tabuleiro.alternarMarcacao(xy.next(), xy.next());
				}
			}
			
		  System.out.println("Você Ganhou");
		} catch (ExplosãoException e) {
			System.out.println(tabuleiro);
			System.out.println("vOCÊ PERDEU");
		}
		}
	private String capturarDigitado(String texto) {
		System.out.println(texto);
	    String digitado = entrada.nextLine();
	    
	    if ("sair".equalsIgnoreCase(digitado)) {
			throw new SairExcecao();
		}
	 return digitado;
	}
}