public class Fila {
    private No head;
    private No tail;

    public Fila() {
        this.head = null;
        this.tail = null;
    }

    public void add(Object object){
        No novo = new No(object);

        if(this.head == null){
            this.head = novo;
            this.tail = novo;
            return;
        }

        this.tail.setNext(novo);
        this.tail = novo;
    }

    public No remove(){
        No current = this.head;

        if(this.head == null){
            System.out.println("\nA fila esta vazia!");
            return null;
        }

        if(this.head == this.tail){
            this.head = null;
            this.tail = null;
            return current;
        }

        this.head = this.head.getNext();
        current.setNext(null);
        return current;
    }

    public No getHead() {
        return head;
    }

    public No getTail() {
        return tail;
    }
}
