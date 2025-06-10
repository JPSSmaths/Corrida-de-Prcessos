import java.util.Scanner;

public class Gerenciador {
    private Fila processos = new Fila();
    private Fila postagens = new Fila();
    private Fila postagensValidas = new Fila();

    private Processo processo1 = new Processo("Produtor");
    private Processo processo2 = new Processo("Validador");
    private Processo processo3 = new Processo("Publicador");

    public void inserirProcessosNaFila() {
        this.processos.add(this.processo1);
        this.processos.add(this.processo2);
        this.processos.add(this.processo3);
    }

    public void removerProcessosDaFila(){
        while(processos.getHead() != null){
            processos.remove();
        }
    }

    public void exibirVencedor(Processo p1, Processo p2, Processo p3) {
        System.out.println("\n1º LUGAR: " + p1.getNome() + " com " + p1.getContagemExecucoes() + " execuções");
        System.out.println("\n2º LUGAR: " + p2.getNome() + " com " + p2.getContagemExecucoes() + " execuções");
        System.out.println("\n3º LUGAR: " + p3.getNome() + " com " + p3.getContagemExecucoes() + " execuções");
    }

    public void verificarVencedor() {
        System.out.println("\nA CORRIDA ACABOU!");
        if (this.processo1.getContagemExecucoes() > this.processo2.getContagemExecucoes()
                && this.processo1.getContagemExecucoes() > this.processo3.getContagemExecucoes()) {
            if (this.processo2.getContagemExecucoes() >= this.processo3.getContagemExecucoes()) {
                exibirVencedor(this.processo1, this.processo2, this.processo3);
            } else {
                exibirVencedor(this.processo1, this.processo3, this.processo2);
            }
        }
        if (this.processo2.getContagemExecucoes() > this.processo1.getContagemExecucoes()
                && this.processo2.getContagemExecucoes() > this.processo3.getContagemExecucoes()) {
            if (this.processo1.getContagemExecucoes() > this.processo3.getContagemExecucoes()) {
                exibirVencedor(this.processo2, this.processo1, this.processo3);
            } else {
                exibirVencedor(this.processo2, this.processo3, this.processo1);
            }
        }
        if (this.processo3.getContagemExecucoes() > this.processo1.getContagemExecucoes()
                && this.processo3.getContagemExecucoes() > this.processo2.getContagemExecucoes()) {
            if (this.processo1.getContagemExecucoes() > this.processo2.getContagemExecucoes()) {
                exibirVencedor(this.processo3, this.processo1, this.processo2);
            } else {
                exibirVencedor(this.processo3, this.processo2, this.processo1);
            }
        }
        if (this.processo1.getContagemExecucoes() == this.processo2.getContagemExecucoes()
                && this.processo1.getContagemExecucoes() == this.processo3.getContagemExecucoes()) {
            System.out.println("\nOS PROCESSOS EMPATARAM COM " + processo1.getContagemExecucoes() + " EXECUÇÕES");
        }
        this.processo1.setContagemExecucoes(0);
        this.processo2.setContagemExecucoes(0);
        this.processo3.setContagemExecucoes(0);
    }

    public void roundRobin() {
        inserirProcessosNaFila();
        int contadorProcessos = 1;
        long quantum = 50;

        for (int i = 0; i < 50; i++) {
            System.out.println("\nIteração " + (i + 1));

            No noProcesso = processos.remove();

            if (noProcesso == null) {
                continue;
            }

            Processo processo = (Processo) noProcesso.getObject();

            long tempoInicio = System.currentTimeMillis();
            while (System.currentTimeMillis() - tempoInicio < quantum) {
                System.out.println("\nProcessando: " + processo.getNome());

                if (System.currentTimeMillis() - tempoInicio < quantum) {
                    Processo.executarProcesso(processo, contadorProcessos, this.postagens, this.postagensValidas);
                    contadorProcessos++;
                }

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("\nO quantum do processo " + processo.getNome() + " acabou");
            processos.add(processo);
        }
        verificarVencedor();
        removerProcessosDaFila();
    }

    public void setarPrioridade(Scanner sc, Processo processo) {
        try {
            System.out.println("\nEscolha a prioridade do processo (digite um número entre -19 e 20): ");
            int prioridade = sc.nextInt();

            if (!Processo.prioridadeInvalida(prioridade)) {
                processo.setPrioridade(prioridade);
                System.out.println("\nPrioridade alterada!");
            }
        } catch (NumberFormatException e) {
            System.out.println("\nO valor informado não é um número, digite um valor válido da próxima");
        }
    }

    public void alterarPrioridade(Scanner sc) {
        System.out.println("\nEscolha a prioridade do processo que será alterado:\n1) Criador\n2) Validador\n3) Publicador");
        String escolha = sc.nextLine();

        switch (escolha) {
            case "1" -> {
                setarPrioridade(sc, this.processo1);
            }
            case "2" -> {
                setarPrioridade(sc, this.processo2);
            }
            case "3" -> {
                setarPrioridade(sc, this.processo3);
            }
            case "4" -> {
                System.out.println("\nOpção inválida!");
            }
        }
    }

    public void inserirFilaComPrioridade(){
        Processo[] p = new Processo[3];
        p[0] = processo1;
        p[1] = processo2;
        p[2] = processo3;

        for(int i = 0; i < 2; i++){
            boolean trocou = false;
            for(int j = 0; j < 2 - i; j++){
                if(p[j].getPrioridade() > p[j+1].getPrioridade()){
                    Processo temp = p[j];
                    p[j] = p[j+1];
                    p[j+1] = temp;
                    trocou = true;
                }
            }
            if(!trocou){
                break;
            }
        }

        for(int i = 0; i < 3; i++){
            this.processos.add(p[i]);
        }
    }

    public void trocarProcesso(int contadorProcessos){
        No n = this.processos.remove();
        Processo p = (Processo) n.getObject();

        if(p.getNome().equals("Produtor")){
            p.produtorDeConteudo(contadorProcessos, postagens);
            System.out.println("\nPrioridade: " + p.getPrioridade());
        }else if(p.getNome().equals("Validador")){
            p.validador(this.postagens, this.postagensValidas);
            System.out.println("\nPrioridade: " + p.getPrioridade());
        }else{
            p.publicador(this.postagensValidas);
            System.out.println("\nPrioridade: " + p.getPrioridade());
        }

        contadorProcessos++;
        this.processos.add(p);
    }

    public void escalonamentoPrioridade() {
        inserirFilaComPrioridade();
        int contadorProcessos = 1;

        for(int i = 0; i < 50; i++){
            System.out.println("\nIteração " + (i + 1));
            trocarProcesso(contadorProcessos);
            contadorProcessos++;
            if(processo1.getContagemExecucoes() == 50 || processo2.getContagemExecucoes() == 50 || processo3.getContagemExecucoes() == 50){
                break;
            }
            trocarProcesso(contadorProcessos);
            contadorProcessos++;
            if(processo1.getContagemExecucoes() == 50 || processo2.getContagemExecucoes() == 50 || processo3.getContagemExecucoes() == 50){
                break;
            }
            trocarProcesso(contadorProcessos);
            contadorProcessos++;
            if(processo1.getContagemExecucoes() == 50 || processo2.getContagemExecucoes() == 50 || processo3.getContagemExecucoes() == 50){
                break;
            }
        }
        System.out.println("\n" + processo1.getContagemExecucoes());
        System.out.println("\n" + processo2.getContagemExecucoes());
        System.out.println("\n" + processo3.getContagemExecucoes());
        verificarVencedor();
        removerProcessosDaFila();
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n------------ CORRIDA DE PROCESSOS ------------");
            System.out.println("\n1) Começar corrida\n2) Alterar Prioridade\n3) Sair");
            System.out.println("----------------------------------------------");
            String opcao = sc.nextLine();

            switch (opcao) {
                case "1" -> {
                    System.out.println("\nEscolha o algoritmo:\n1) Round Robin\n2) Escalonamento por Prioridade\n3) Sair");
                    opcao = sc.nextLine();

                    switch (opcao) {
                        case "1" -> {
                            roundRobin();
                        }
                        case "2" -> {
                            escalonamentoPrioridade();
                        }
                        case "3" -> {
                            break;
                        }
                        default -> {
                            System.out.println("\nOpcao invalida!");
                        }
                    }
                }
                case "2" -> {
                    alterarPrioridade(sc);
                    sc.nextLine();
                }
                case "3" -> {
                    System.out.println("\nSaindo do sistema...");
                    System.exit(0);
                }
                default -> {
                    System.out.println("\nOpção inválida!");
                }
            }
        }
    }
}