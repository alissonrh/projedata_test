package projedata_iniflex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Principal {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Cria a lista de meses pra pesquisar os aniversariantes
    static List<Integer> meses = Arrays.asList(10, 12);

    public static void main(String[] args) {
        // Cria a lista de funcionários
        ArrayList<Funcionario> funcionarios = new InstanceControl().loader("tabela_funcionarios.csv");

        imprimeFuncionarios(funcionarios);
        removeFuncionarios(funcionarios, "João");
        aumentaSalario(funcionarios, 10);
        imprimeFuncionariosPorFuncao(funcionarios);
        imprimeAniversariantesDoMes(funcionarios, meses);
        imprimeMaiorIdade(funcionarios);
        imprimeFuncionariosOrdemAlfabetica(funcionarios);
        somaSalarios(funcionarios);
        imprimeQuantidadeDeSalarioMinPorFuncionario(funcionarios, 1212);
         
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

    // Remove funcionario (João)

    public static void removeFuncionarios(List<Funcionario> funcionarios, String funcionario) {
        funcionarios.removeIf(f -> f.getNome().equals(funcionario));
        imprimeFuncionarios(funcionarios);
    }

    // Aumenta o salário dos funcionários em 10%
    public static void aumentaSalario(List<Funcionario> funcionarios, double percent) {
        BigDecimal increase = BigDecimal.valueOf(1 + percent / 100);
        funcionarios.forEach(
                f -> f.setSalario(f.getSalario().multiply(increase).setScale(2, RoundingMode.HALF_UP)));
                imprimeFuncionarios(funcionarios);
    }

    // Imprimi os funcionários, agrupados por função.

    public static void imprimeFuncionariosPorFuncao(List<Funcionario> funcionarios) {
        // Agrupa os funcionários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        funcionarios.forEach(f -> {
            if (!funcionariosPorFuncao.containsKey(f.getFuncao())) {
                funcionariosPorFuncao.put(f.getFuncao(), new ArrayList<>());
            }
            funcionariosPorFuncao.get(f.getFuncao()).add(f);
        });

        // Imprime os funcionarios

        funcionariosPorFuncao.forEach((funcao, funcionariosDaFuncao) -> {
            System.out.println("Funcionários da função: " + funcao);
            funcionariosDaFuncao.forEach(f -> {
                System.out.println("Nome: " + f.getNome());
                System.out.println("Data de nascimento: " + f.getDataNascimento().format(formatter));
                System.out.println("Salário: " + f.getSalario().toString().replace(".", ","));
            });
        });

    }

    // Imprimi os funcionários que fazem aniversário no mês 10 e 12.

    public static void imprimeAniversariantesDoMes(List<Funcionario> funcionarios, List<Integer> meses) {
        funcionarios.stream()
                .filter(f -> meses.contains(f.getDataNascimento().getMonthValue()))
                .forEach(f -> System.out.println("Nome: " + f.getNome() + ", Data de nascimento: " + f.getDataNascimento().format(formatter)));
    }

    // Imprimi o funcionário com a maior idade, exibir os atributos: nome e idade

    public static void imprimeMaiorIdade(List<Funcionario> funcionarios) {
        funcionarios.stream()
                .min(Comparator.comparing(f -> f.getDataNascimento().toEpochDay()))
                .ifPresent(f -> System.out.println("Nome: " + f.getNome() + " Idade: "
                        + Period.between(f.getDataNascimento(), LocalDate.now()).getYears()));
    }

    // Imprimi a lista de funcionários por ordem alfabética.

    public static void imprimeFuncionariosOrdemAlfabetica(List<Funcionario> funcionarios) {
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));
        funcionarios.forEach(f -> {
            System.out.println("Nome: " + f.getNome());
            System.out.println("Data de nascimento: " + f.getDataNascimento().format(formatter));
            System.out.println("Salário: " + f.getSalario().toString().replace(".", ","));
            System.out.println("Função: " + f.getFuncao());
        });
    }

    // Imprimi o total dos salários dos funcionários.

    public static void somaSalarios(List<Funcionario> funcionarios) {
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total de salários: " + totalSalarios);
    }

    // Imprimi quantos salários mínimos ganha cada funcionário

    public static void imprimeQuantidadeDeSalarioMinPorFuncionario(List<Funcionario> funcionarios, double salarioMin) {
        final BigDecimal salarioMinimo = new BigDecimal(salarioMin);
        funcionarios.forEach(f -> {
            System.out.println("Nome: " + f.getNome());
            System.out.println(
                    "Quantidade de salários mínimos: " + f.getSalario().divide(salarioMinimo, RoundingMode.HALF_UP));
        });

    }
}

//
