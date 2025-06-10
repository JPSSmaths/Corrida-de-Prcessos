public class Processo {
    private String nome;
    private int prioridade = 0;
    private int contagemExecucoes = 0;

    public Processo(String nome) {
        this.nome = nome;
    }

    public static boolean prioridadeInvalida(int prioridade) {
        if (prioridade < -20 || prioridade > 19) {
            System.out.println("\nPrioridade inválida, digite uma prioridade de -20 a 19 da proxima!");
            return true;
        }
        return false;
    }

    public void produtorDeConteudo(int i, Fila postangens) {
        Postagem postagem = new Postagem();
        postagem.setNome("Postagem " + i);
        postagem.setStatus("Iniciado");
        System.out.println("\nA postagem " + postagem.getNome() + " foi criada");
        postangens.add(postagem);
        this.contagemExecucoes++;
    }

    public void validador(Fila postagens, Fila postagensValidas) {
        if (postagens.getHead() != null) {
            Postagem postagem = (Postagem) postagens.remove().getObject();

            if (postagem.getStatus().equals("Iniciado")) {
                postagem.setStatus("Validada");
                System.out.println("\nA postagem " + postagem.getNome() + " foi validada");
                postagensValidas.add(postagem);
            }
        }else{
            System.out.println("\nNão há postagens para validar");
        }
        this.contagemExecucoes++;
    }

    public void publicador(Fila postagensValidas) {
        if (postagensValidas.getHead() != null) {
            Postagem postagemValida = (Postagem) postagensValidas.remove().getObject();

            if (postagemValida.getStatus().equals("Validada")) {
                System.out.println("\nA postagem " + postagemValida.getNome() + " foi postada");
            }
        }else{
            System.out.println("\nNão há postagens para publicar!");
        }
        this.contagemExecucoes++;
    }

    public static void executarProcesso(Processo processo, int i, Fila postagens, Fila postagensValidas) {
        if (processo.nome.equals("Produtor")) {
            processo.produtorDeConteudo(i, postagens);
        } else if (processo.nome.equals("Validador")) {
            processo.validador(postagens, postagensValidas);
        } else {
            processo.publicador(postagensValidas);
        }
    }

    public String getNome() {
        return nome;
    }

    public int getContagemExecucoes() {
        return contagemExecucoes;
    }

    public void setContagemExecucoes(int contagemExecucoes) {
        this.contagemExecucoes = contagemExecucoes;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }
}