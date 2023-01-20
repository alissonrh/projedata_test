package projedata_iniflex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Principal {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {

        // Cria a lista de funcionários
        List<Funcionario> funcionarios = new ArrayList<>();

        // Insere os funcionários na lista
        // LocalDate format yyyy-MM-dd
        funcionarios
                .add(new Funcionario("Maria", LocalDate.parse("2000-10-18"), new BigDecimal("2009.44"), "Operador"));
        funcionarios
                .add(new Funcionario("João", LocalDate.parse("1990-05-12"), new BigDecimal("2284.38"), "Operador"));

        funcionarios
                .add(new Funcionario("Caio", LocalDate.parse("1961-05-02"), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios
                .add(new Funcionario("Miguel", LocalDate.parse("1988-10-14"), new BigDecimal("19119.88"), "Diretor"));
        funcionarios
                .add(new Funcionario("Alice", LocalDate.parse("1995-01-05"), new BigDecimal("2234.68"),
                        "Recepcionista"));
        funcionarios
                .add(new Funcionario("Heitor", LocalDate.parse("1999-11-19"), new BigDecimal("1582.72"), "Operador"));
        funcionarios
                .add(new Funcionario("Arthur", LocalDate.parse("1993-03-31"), new BigDecimal("4071.45"), "Contador"));
        funcionarios
                .add(new Funcionario("Laura", LocalDate.parse("1994-07-08"), new BigDecimal("3017.45"), "Gerente"));
        funcionarios
                .add(new Funcionario("Heloísa", LocalDate.parse("2003-05-24"), new BigDecimal("1606.85"),
                        "Eletricista"));
        funcionarios
                .add(new Funcionario("Helena", LocalDate.parse("1996-09-02"), new BigDecimal("2799.93"), "Gerente"));

        // Remove o funcionário "João" da lista
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        // Aumenta o salário dos funcionários em 10%
        funcionarios.forEach(
                f -> f.setSalario(f.getSalario().multiply(new BigDecimal("1.1")).setScale(2, RoundingMode.HALF_UP)));

        // Agrupa os funcionários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        funcionarios.forEach(f -> {
            if (!funcionariosPorFuncao.containsKey(f.getFuncao())) {
                funcionariosPorFuncao.put(f.getFuncao(), new ArrayList<>());
            }
            funcionariosPorFuncao.get(f.getFuncao()).add(f);
        });

        // Imprimir os funcionários, agrupados por função.

        funcionariosPorFuncao.forEach((funcao, funcionariosDaFuncao) -> {
            System.out.println("Funcionários da função: " + funcao);
            funcionariosDaFuncao.forEach(f -> {
                System.out.println("Nome: " + f.getNome());
                System.out.println("Data de nascimento: " + f.getDataNascimento().format(formatter));
                System.out.println("Salário: " + f.getSalario().toString().replace(".", ","));
            });
        });

        // Imprimir os funcionários que fazem aniversário no mês 10 e 12.

        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .forEach(f -> {
                    System.out.println("Nome: " + f.getNome());
                    System.out.println("Data de nascimento: " + f.getDataNascimento().format(formatter));
                    System.out.println("Salário: " + f.getSalario().toString().replace(".", ","));
                    System.out.println("Função: " + f.getFuncao());
                });

    /* imprimeFuncionarios(funcionarios); */
    imprimeMaiorIdade(funcionarios);

    }

    


    // Imprime todos os funcionários com suas informações
    public static void imprimeFuncionarios(List<Funcionario> funcionarios) {
        funcionarios.forEach(f -> {
            System.out.println("Nome: " + f.getNome());
            System.out.println("Data de nascimento: " + f.getDataNascimento().format(formatter));
            System.out.println("Salário: " + f.getSalario().toString().replace(".", ","));
            System.out.println("Função: " + f.getFuncao());
        });
    }

    // Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade

    public static void imprimeMaiorIdade(List<Funcionario> funcionarios) {
        funcionarios.stream()
                    .min(Comparator.comparing(f -> f.getDataNascimento().toEpochDay()))
                    .ifPresent(f -> System.out.println("Nome: " + f.getNome() + " Idade: " + Period.between(f.getDataNascimento(), LocalDate.now()).getYears()));
    }
    
}
//
