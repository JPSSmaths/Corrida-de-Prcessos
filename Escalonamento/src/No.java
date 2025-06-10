public class No {
    private Object object;
    private No next;

    public No(Object object) {
        this.next = null;
        this.object = object;
    }

    public No getNext() {
        return next;
    }

    public void setNext(No next) {
        this.next = next;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}