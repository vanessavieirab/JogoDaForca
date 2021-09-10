
/*
 * Projeto1 de POO
 * Aluna: Vanessa Vieira Borges
 */
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Random;

public class JogoDaForca {
	private int N; // quantidade de palavras do arquivo (lido do arquivo)
	private String[] palavras; // um array com as N palavras (lidas do arquivo)
	private String[] dicas; // um array com as N dicas (lidas do arquivo)
	private String palavra; // a palavra sorteada
	private String palavra2 = "";
	private int indice = 0; // indice da palavra sorteada no jogo
	private int acertos; // total de acertos do jogo
	private int erros = 0; // total de erros do jogo
	private String letrasUsadas = "";
	private String[] penalidades = { "perna", "perna", "braço", "braço", "tronco", "cabeça" };

	public JogoDaForca(String nomearquivo) throws Exception { // construtor que lê o arquivo com as N palavras e dicas e as coloca nos
															  // respectivos arrays.
		Scanner arquivo = null;
		String separador = "";
		String[] arrayPD;

		try {
			arquivo = new Scanner(new File(nomearquivo));

			int contador = 0;
			this.N = Integer.parseInt(arquivo.nextLine());

			arrayPD = new String[N];
			this.palavras = new String[N];
			this.dicas = new String[N];

			while (arquivo.hasNextLine()) {
				separador = arquivo.nextLine();
				arrayPD = separador.split(";");
				this.palavras[contador] = arrayPD[0];
				this.dicas[contador] = arrayPD[1];
				contador++;
			}
		} catch (FileNotFoundException e) {
			throw new Exception("Arquivo inexistente!");
		}
	}

	public void iniciar() { // inicia o jogo com o sorteio de uma das N palavras existentes.
		Random sorteador = new Random();
		this.indice = sorteador.nextInt(N);
		this.palavra = palavras[indice];
	}

	public boolean adivinhou(String letra) throws Exception { // retorna true, caso a letra exista dentro da palavra sorteada e retorna false, caso contrário.
															 // Além disso, o método marca as posições encontradas e contabiliza X acertos para as X ocorrências
															// da letra encontrada dentro da palavra ou contabiliza 1 erro para a inexistência da letra na palavra.
		Pattern padrao = Pattern.compile("[a-zA-Z]");
		Matcher alvo = padrao.matcher(letra);
		
		if(alvo.matches()) {
			letra = letra.toUpperCase();
			palavra2 = new String(this.palavra);
			for(int i=0; i<this.palavra.length(); i++) {
				if(this.palavra.contains(letra)) {
					if(this.palavra.substring(i,i+1).equals(letra) && !this.letrasUsadas.contains(letra)) {
						this.acertos +=1;
					}
				}	
			}
			
			if(this.letrasUsadas.indexOf(letra)>=0){
				throw new Exception("Você já tentou a letra "+letra);
			} else {
				this.letrasUsadas += letra;
			}
			
			if(this.palavra.indexOf(letra)>=0) {
				palavra2 = "";
				for (int j = 0; j < this.palavra.length(); j++) {
					palavra2 += this.letrasUsadas.indexOf(this.palavra.charAt(j)) >= 0 ? this.palavra.charAt(j) : "*";
				}	//se a condição for verdadeira palavra2 recebe a letra, se não for, continua com *
			}
			
			if(!this.palavra.contains(letra)){
				this.erros +=1;
			}
			return this.palavra.contains(letra);
		} else {
			throw new Exception("Apenas letras!");
		}
	}

	public boolean terminou() { // retorna true, se o total de acertos atingir o total de letras da palavra
								// sorteada ou se o total de erros atingir seis.
		if ((this.erros == 6) || (this.acertos == this.palavra.length())) {
			return true;
		} 
		else {
			return false;
		}
	}

	public String getPalavra() { // retorna a palavra sorteada com as letras adivinhadas reveladas e com as
								// letras não adivinhadas escondidas com “*”.
		return palavra2;
	}

	public String getDica() { // retorna a dica da palavra sorteada.
		return this.dicas[indice];
	}

	public String getPenalidade() { // retorna o nome da penalidade de acordo com o total de erros.
		switch(this.erros) {
			case 1:
				return this.penalidades[0];
			case 2:
				return this.penalidades[1];
			case 3:
				return this.penalidades[2];
			case 4:
				return this.penalidades[3];
			case 5:
				return this.penalidades[4];
			default:
				return this.penalidades[5];
		}
	}

	public int getAcertos() { // retorna o total de acertos
		return this.acertos;
	}

	public int getErros() { // retorna o total de erros
		return this.erros;
	}

	public String getResultado() { // retorna “ganhou o jogo” ou “você foi enforcado”
		if (this.acertos == this.palavra.length()) {
			return "Você ganhou o jogo!";
		} else {
			return "Você foi enforcado!";
		}
	}
}
