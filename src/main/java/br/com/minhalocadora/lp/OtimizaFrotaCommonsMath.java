package br.com.minhalocadora.lp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NonNegativeConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

public class OtimizaFrotaCommonsMath {

    public static void main(String[] args) {
        // Para formatação de moeda
        Locale.setDefault(new Locale("pt", "BR"));

        // 1) Definir os dados de entrada (conforme exemplo do PDF)
        String[] nomesCategorias = { "Econômico", "Executivo", "SUV", "Van" };
        double B = 1_200_000.0; // Orçamento disponível (R$)
        int H = 5; // Horizonte de amortização (anos)

        // Preço de compra por categoria
        double[] P = { 55_000.0, 85_000.0, 120_000.0, 105_000.0 };
        // Custo médio anual de manutenção por categoria
        double[] Cim = { 6_000.0, 8_500.0, 10_000.0, 9_000.0 };
        // Receita média anual esperada por veículo, por categoria
        double[] R = { 22_000.0, 31_500.0, 36_000.0, 24_700.0 };
        // Demanda máxima anual estimada (unidades por categoria)
        int[] D = { 10, 6, 4, 3 };
        // Espaço de estacionamento ocupado por veículo (vagas)
        double[] s = { 1.0, 1.2, 1.5, 1.3 };
        // Capacidade total de estacionamento (vagas)
        double S = 12.0;
        int n = 4;

        // 2) Construir a função-objetivo
        double[] coefObjetivo = new double[n];
        for (int i = 0; i < n; i++) {
            // Coeficiente de x[i] = R[i] - Cim[i] - (P[i]/H)
            coefObjetivo[i] = R[i] - Cim[i] - (P[i] / H);
        }

        LinearObjectiveFunction objetivo = new LinearObjectiveFunction(coefObjetivo, 0.0);

        // 3) Construir as restrições lineares
        Collection<LinearConstraint> restricoes = new ArrayList<>();

        // Restrição de Orçamento: Σ(P[i] * x[i]) ≤ B
        restricoes.add(new LinearConstraint(P, Relationship.LEQ, B));

        // Restrições de Demanda Máxima: xi ≤ D[i]
        for (int i = 0; i < n; i++) {
            double[] coefDemanda = new double[n];
            coefDemanda[i] = 1.0;
            restricoes.add(
                    new LinearConstraint(coefDemanda, Relationship.LEQ, D[i]));
        }

        // Restrição de Estacionamento: Σ(s[i] * x[i]) ≤ S
        restricoes.add(new LinearConstraint(s, Relationship.LEQ, S));

        // 4) Configurar e chamar o solver Simplex
        SimplexSolver solver = new SimplexSolver();
        PointValuePair resultado = solver.optimize(
                new MaxIter(1000),
                objetivo,
                new LinearConstraintSet(restricoes),
                GoalType.MAXIMIZE,
                new NonNegativeConstraint(true));

        
        // 5) Extrair a solução e gerar relatório claro
        //
        double valorOtimo = resultado.getValue();
        double[] solucao = resultado.getPoint();

        System.out.println("=========================================================");
        System.out.println("   RELATÓRIO DE OTIMIZAÇÃO DE FROTA - RESULTADO FINAL   ");
        System.out.println("=========================================================");
        System.out.printf("\nO lucro líquido anual máximo estimado é de R$ %,.2f.%n", valorOtimo);

        System.out.println("\n--- DETALHAMENTO DA SOLUÇÃO ÓTIMA (CONTÍNUA) ---");
        System.out.println("Para alcançar o lucro máximo, a quantidade de veículos a adquirir é:");
        for (int i = 0; i < n; i++) {
            System.out.printf("  - %-12s: %.4f unidades%n", nomesCategorias[i], solucao[i]);
        }

        System.out.println("\n--- RECOMENDAÇÃO PRÁTICA (SOLUÇÃO ARREDONDADA) ---");
        System.out.println("Como não é possível comprar frações de carros, arredondamos os valores:");
        int[] xInt = new int[n];
        double custoTotalArredondado = 0;
        double espacoTotalArredondado = 0;
        double lucroTotalArredondado = 0;

        for (int i = 0; i < n; i++) {
            xInt[i] = (int) Math.round(solucao[i]);
            custoTotalArredondado += xInt[i] * P[i];
            espacoTotalArredondado += xInt[i] * s[i];
            lucroTotalArredondado += xInt[i] * coefObjetivo[i];
            System.out.printf("  - Comprar %d carros da categoria %s.%n", xInt[i], nomesCategorias[i]);
        }
        System.out.printf("\nO lucro anual com a frota arredondada seria de aproximadamente R$ %,.2f.%n",
                lucroTotalArredondado);

        System.out.println("\n--- ANÁLISE DE USO DOS RECURSOS (Solução Arredondada) ---");

        // Verificação de viabilidade da solução arredondada
        boolean orcamentoOk = custoTotalArredondado <= B;
        boolean espacoOk = espacoTotalArredondado <= S;

        System.out.println("1. Orçamento:");
        System.out.printf("  - Custo Total: R$ %,.2f%n", custoTotalArredondado);
        System.out.printf("  - Orçamento Disponível: R$ %,.2f%n", B);
        System.out.printf("  - Utilização do Orçamento: %.2f%%%n", (custoTotalArredondado / B) * 100);
        System.out.printf("  - Status: %s%n",
                orcamentoOk ? "VÁLIDO (dentro do orçamento)" : "INVÁLIDO (excede o orçamento!)");

        System.out.println("\n2. Estacionamento:");
        System.out.printf("  - Vagas Utilizadas: %.2f vagas%n", espacoTotalArredondado);
        System.out.printf("  - Vagas Disponíveis: %.2f vagas%n", S);
        System.out.printf("  - Ocupação do Estacionamento: %.2f%%%n", (espacoTotalArredondado / S) * 100);
        System.out.printf("  - Status: %s%n",
                espacoOk ? "VÁLIDO (dentro da capacidade)" : "INVÁLIDO (excede a capacidade!)");

        System.out.println("\n=========================================================");
    }
}