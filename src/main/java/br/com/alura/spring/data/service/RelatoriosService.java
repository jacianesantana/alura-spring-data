package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
public class RelatoriosService {

    private Boolean system = true;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final FuncionarioRepository funcionarioRepository;

    public RelatoriosService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println("Qual ação de cargo deseja executar?");
            System.out.println("0 - Sair");
            System.out.println("1 - Busca funcionário por nome");
            System.out.println("2 - Busca funcionário por nome, salário maior e data contratação");
            System.out.println("3 - Busca funcionário por data contratação");
            System.out.println("4 - Pesquisa funcionário salário");

            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    buscaFuncionarioPorNome(scanner);
                    break;
                case 2:
                    buscaFuncionarioPorNomeSalarioMaiorEDataContratacao(scanner);
                    break;
                case 3:
                    buscaFuncionarioPorDataContratacao(scanner);
                    break;
                case 4:
                    pesquisaFuncionarioSalario();
                    break;
                default:
                    system = false;
                    break;
            }
        }
    }

    private void buscaFuncionarioPorNome(Scanner scanner) {
        System.out.println("Qual nome deseja pesquisar");
        String nome = scanner.next();

        List<Funcionario> list = funcionarioRepository.findByNome(nome);
        list.forEach(System.out::println);
    }

    private void buscaFuncionarioPorNomeSalarioMaiorEDataContratacao(Scanner scanner) {
        System.out.println("Qual nome deseja pesquisar");
        String nome = scanner.next();

        System.out.println("Qual maior salario deseja pesquisar");
        Double salario = scanner.nextDouble();

        System.out.println("Qual data de contratação deseja pesquisar");
        String dataContratacao = scanner.next();
        LocalDate localDate = LocalDate.parse(dataContratacao, formatter);

        List<Funcionario> list = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);
        list.forEach(System.out::println);
    }

    private void buscaFuncionarioPorDataContratacao(Scanner scanner) {
        System.out.println("Qual data de contratação deseja pesquisar");
        String dataContratacao = scanner.next();
        LocalDate localDate = LocalDate.parse(dataContratacao, formatter);

        List<Funcionario> list = funcionarioRepository.findDataContratacaoMaior(localDate);
        list.forEach(System.out::println);
    }

    private void pesquisaFuncionarioSalario() {
        List<FuncionarioProjecao> list = funcionarioRepository.findFuncionarioSalario();
        list.forEach(f -> System.out.println("Funcionário: id: " + f.getId()
                + " | nome: " + f.getNome()
                + " | salário: " + f.getSalario()));
    }

}
