package raphask.com.github;

import raphask.com.github.modelo.Tabuleiro;
import raphask.com.github.visao.TabuleiroConsole;

public class Aplicacao {
public static void main(String[] args) {
	

 Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
 new TabuleiroConsole(tabuleiro);


}
}
