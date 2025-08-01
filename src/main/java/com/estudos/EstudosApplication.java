package com.estudos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

@SpringBootApplication
public class EstudosApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstudosApplication.class, args);

		// Expressões Lambda e Streams
		// O que é uma Lambda?
		// Uma lambda representa uma função anônima - pode ser usada para implementar
		// uma interface funcional de forma concisa.

		List<String> nomes = Arrays.asList("Ana", "João", "Pedro");
		nomes.forEach(nome -> System.out.println(nome));

		// O que é um Stream?
		// Stream é uma abstração que permite processar dados de forma funcional,
		// encadeada e paralelizável( em alguns casos).
		// Trabalha com pipelines (ex: filter -> map -> collect)

		// Exemplo: sem Stream vs com Stream
		// Sem Lambda/Stream( Java 7)

		List<String> nomesComA = new ArrayList<>();
		for (String nome : nomes) {
			if (nome.startsWith("A")) {
				nomesComA.add(nome.toUpperCase());
			}
		}
		Collections.sort(nomesComA);
		System.out.println(nomesComA);

		// Com Lambda e Stream (Java 8+)
		List<String> nomesComAv2 = nomes.stream()
				.filter(n -> n.startsWith("A"))
				.map(String::toUpperCase)
				.sorted()
				.collect(Collectors.toList());
		System.out.println(nomesComAv2);
		// Mais legível, mais expressivo, menos verboso e mais declarativo.

		// Outros exemplos úteis
		// 1. Somar todos os números pares:
		List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);
		int soma = numeros.stream()
				.filter(n -> n % 2 == 0)
				.mapToInt(Integer::intValue)
				.sum();
		System.out.println(soma);

		// 2. Contar elementos distintos
		long count = nomes.stream()
				.distinct()
				.count();
		System.out.println(count);

		// 3. Agrupar por condição
		Map<Boolean, List<String>> agrupado = nomes.stream().collect(Collectors.partitioningBy(n -> n.length() > 3));
		System.out.println(agrupado);

		// Diferença entre Expressão Lambda e Classe Anônima
		// Exemplo prático
		// 1. Classe anônima
		Runnable r1 = new Runnable() {
			@Override
			public void run() {
				System.out.println("Executando com classe anônima");
			}
		};
		// 2. Expressão Lambda
		Runnable r2 = () -> System.out.println("Executando com lambda");

		// Observação sobre 'this'
		Teste teste = new Teste("André");
		teste.executar();

		// Diferença entre map() e flatMap()
		// Exemplo com map()
		// Vamos supor que voce tenha uma lista de nomes e quer transformar cada nome em
		// maiúsculas:
		List<String> nomesMaiusculos = nomes.stream()
				.map(String::toUpperCase)
				.collect(Collectors.toList());

		System.out.println(nomesMaiusculos);
		// Aqui:
		// map() aplicação a função toUpperCase() em cada elemento.
		// Retorna um Stream<String>

		// Exemplo com flatMap()
		// Agora suponha que voce tem uma lista de listas de inteiros e quer transformar
		// em uma lista única;
		List<List<Integer>> listas = Arrays.asList(
				Arrays.asList(1, 2, 3),
				Arrays.asList(4, 5),
				Arrays.asList(6, 7, 8, 9));

		List<Integer> todos = listas.stream()
				.flatMap(List::stream)
				.collect(Collectors.toList());

		System.out.println(todos);

		// Aqui:
		// map(List::stream) -> gera uma Stream<Stream<Integer>>
		// flatMap(List::stream) -> 'achata' para uma Stream<Integer>

		// Como funciona reduce()
		// Exemplo prático: Soamr números
		// Exemplo 1 - Sem valor inicial(Optional):
		Optional<Integer> somav2 = numeros.stream()
				.reduce((a, b) -> a + b);

		somav2.ifPresent(System.out::println);

		// Exemplo 2 - Com valor inicial:
		int somav3 = numeros.stream()
				.reduce(0, (a, b) -> a + b);
		System.out.println(somav3);

		// Exemplo útil: Concatenar strings
		Optional<String> resultado = nomes.stream()
				.reduce((a, b) -> a + ", " + b);
		resultado.ifPresent(System.out::println);

		// Exemplo mais interssante: Encontrar o maior número
		Optional<Integer> max = numeros.stream()
				.reduce(Integer::max);

		max.ifPresent(System.out::println);

		// Optional
		// Exemplo clássico (pré-Java 8):
		String nome = teste.getNome();
		if (nome != null) {
			System.out.println(nome.toUpperCase());
		}
		// Se você esquecer o 'if', toma uma exceção.

		// Exemplo prático com Optional
		// Sem Optional(perigoso):
		// public String buscarNome(String id) {
		// Pessoa p = banco.buscarPessoa(id);
		// return p != null ? p.getNome() : null;
		// }

		// Com Optional(mais seguro):
		// public Optional<String> buscarNome(String id) {
		// return Optional.ofNullable(banco.buscarPessoa(id))
		// .map(Pessoa::getNome);
		// }

		// E no consumidor:
		// Optional<String> nome = buscarNome("123");
		// nome.ifPresent(n -> System.out.println(n.toUpperCase()));

		// HashMap
		//Exemplo prático
		Map<String,String> mapa = new HashMap<>();
		mapa.put("joao", "valor1");
		mapa.put("ana", "valor2");
		System.out.println(mapa.get("joao"));

		//2.1 String – Imutável
		nome.concat(" Tavares");
		System.out.println(nome);
		// O método 'concat() cria uma nova String, pois String é imutável
		
		// 2.2 StringBuilder – Mutável e eficiente
		StringBuilder sb = new StringBuilder("André");
		sb.append(" Tavares");
		System.out.println(sb);
		//Sem criar novos objetos.
		//Muito mais perfomático em concatenações em loops.

		//2.3 StringBuffer – Igual ao StringBuilder, mas sincronizado
		StringBuffer sbf = new StringBuffer("André");
		sbf.append(" Tavares");
		System.out.println(sbf);
		//Evita problemas de concorrência, mas é mais lento.

		teste.main();

	}

	//Carga de Métodos(Method Overloading)
	//Exemplo:
	public class Calculadora{
		public int somar(int a,int b){
			return a+b;
		}

		public double somar(double a, double b){
			return a+b;
		}

		public int somar(int a,int b,int c){
			return a+b+c;
		}
		//O compilador decide qual servao usar com base nos tipos e quantidade de argumentos.
		//Nãop é permitido apenas mudar o tipo de retorno


	}

	public static class Teste {

		private String nome;

		public Teste() {

		}

		public Teste(String nome) {
			this.nome = nome;
		}

		public void executar() {
			Runnable anonima = new Runnable() {
				@Override
				public void run() {
					System.out.println(this.getClass().getName());// Classe anônima
				}
			};

			Runnable lambda = () -> {
				System.out.println(this.getClass().getName());// Classe externa
			};

			anonima.run();
			lambda.run();
			// Nesse exemplo, 'this' em 'lambda' aponta para Teste, mas em 'anonima' aponta
			// para a propria classe anonima gerada.
		}

		public String getNome() {
			return nome;
		}

		public static void main(){
			long inicio = System.currentTimeMillis();

			StringBuilder sb = new StringBuilder();
			for(int i=0;i<100000;i++){
				sb.append("x");
			}
			long fim = System.currentTimeMillis();
			System.out.println("Tempo: " + (fim-inicio) + " ms");
		}
	}

}
